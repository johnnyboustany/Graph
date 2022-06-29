package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.PageRank;

/**
 * In this class I implemented one of many different versions
 * of the PageRank algorithm. This algorithm will only work on
 * directed graphs.
 *
 */
public class MyPageRank<V> implements PageRank<V> {
	private Graph<V> _g;
	private List<CS16Vertex<V>> _vertices;
	private Map<CS16Vertex<V>, Double> _vertsToRanks;
	private static final double _dampingFactor = 0.85;
	private static final int _maxIterations = 100;
	private static final double _error = 0.01;
	private int[] _numOfOutEdges;
	private double[] _prevRank;
	private double[] _currRank;
	private double _iterNum;
	private boolean _stopConvergence;

	/**
	 * The main method that does the calculations
	 *
	 * @return A Map of every Vertex to its corresponding rank
	 */
	@Override
	public Map<CS16Vertex<V>, Double> calcPageRank(Graph<V> g) {
		_g = g;
		_vertices = new ArrayList<>();
		_vertsToRanks = new HashMap<>();
		_numOfOutEdges = new int[g.getNumVertices()];
		_prevRank = new double[g.getNumVertices()];
		_currRank = new double[g.getNumVertices()];
		_iterNum = 0;
		_stopConvergence = false;

		// iterating through vertices to add them to _vertices
		// and initialize _numOfOutEdges and _currRank
		Iterator<CS16Vertex<V>> it = g.vertices();
		while (it.hasNext()) {
			CS16Vertex<V> vertex = it.next();

			_vertices.add(vertex);
			_numOfOutEdges[_vertices.indexOf(vertex)] = g.numOutgoingEdges(vertex);
			_currRank[_vertices.indexOf(vertex)] = 1.0 / g.getNumVertices();
		}

		// use the adding edges method to handle sinks
		this.handleSinks();

		// main loop
		while(!_stopConvergence && _iterNum <= _maxIterations) {

			_iterNum++; // new iteration of this algorithm

			// only updating _stopConvergence if _iterNum > 1
			// b/c _prevRank is empty for the first iteration until it gets updated
			if(_iterNum > 1){
				_stopConvergence = this.stopConvergence(); // updating the boolean variable
			}

			// cloning _currRank into _prevRank and setting currentRank to all 0.0s
			this.updatePrevRank();

			// updating currentRank
			this.updateRank();
		}

		// putting all current ranks in the hashMap that is returned by this method
		for (CS16Vertex<V> v : _vertices) {
			_vertsToRanks.put(v, _currRank[_vertices.indexOf(v)]);
		}

		return _vertsToRanks;
	}

	/**
	 * Method used to account for sink pages (those with no outgoing
	 * edges).
	 */
	private void handleSinks() {

		// iterating through all vertices
		for (CS16Vertex<V> v : _vertices) {

			if (_numOfOutEdges[_vertices.indexOf(v)] == 0) {

				for (CS16Vertex<V> u : _vertices) {
						_g.insertEdge(v, u, 1);

						// updating the sink's number of outgoing edges
						_numOfOutEdges[_vertices.indexOf(v)] += 1;
				}
			}
		}
	}

	/**
	 * Method used to check if convergence should be stopped.
	 */
	private boolean stopConvergence() {

		// iterating through all vertices
		for (CS16Vertex<V> v : _vertices) {
			double previousRank = _prevRank[_vertices.indexOf(v)];
			double currentRank = _currRank[_vertices.indexOf(v)];

			// absolute value must be taken
			if (Math.abs(previousRank - currentRank) <= _error) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method used to update the current rank of all the vertices
	 * based on the page rank formula.
	 */
	private void updateRank() {

		for (CS16Vertex<V> v : _vertices) {

			double PR = (1 - _dampingFactor) / (_g.getNumVertices());

			Iterator<CS16Edge<V>> it = _g.incomingEdges(v);
			while (it.hasNext()) {
				CS16Edge<V> edge = it.next();

				// page that links to page v (with an incoming edge)
				CS16Vertex<V> u = _g.opposite(v, edge);

				int uIndex = _vertices.indexOf(u);
				PR += _dampingFactor * (_prevRank[uIndex] / _numOfOutEdges[uIndex]);
			}

			_currRank[_vertices.indexOf(v)] = PR;
		}
	}

	/**
	 * Method used to clone the current rank all into
	 * the previous rank and also update current rank to all 0.0s.
	 */
	private void updatePrevRank(){
		for (CS16Vertex<V> v : _vertices) {
			_prevRank[_vertices.indexOf(v)] = _currRank[_vertices.indexOf(v)];

			// resetting _currRank to be 0 for all vertices
			_currRank[_vertices.indexOf(v)] = 0.0;
		}
	}
}
