package graph;

import static support.graph.Constants.MAX_VERTICES;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.GraphEdge;
import support.graph.GraphVertex;
import support.graph.DirectionException;
import support.graph.InvalidEdgeException;
import support.graph.InvalidVertexException;
import support.graph.NoSuchEdgeException;
import support.graph.NoSuchVertexException;
import support.graph.NodeSequence;
    
/**
 * This class defines a Graph that tracks its edges through the use of an
 * adjacency matrix. Please review the lecture slides and the book before
 * attempting to write this program. An adjacency matrix consists of a 2D array
 * of Vertices, with each vertex of the graph appearing in both dimensions.
 *
 */
public class AdjacencyMatrixGraph<V> implements Graph<V> {

    // The underlying data structure of your graph: the adjacency matrix
    private CS16Edge<V>[][] _adjMatrix;
    // Sets to store the vertices and edges of your graph
    private Set<CS16Vertex<V>> _vertices;
    private Set<CS16Edge<V>> _edges;
    //number of vertices
    private int _numVertices;
    // boolean that keeps track of directedness of graph
    private boolean _directed;

    /**
     * Constructor for your Graph, where among other things, you will most
     * likely want to instantiate your matrix array and your Sets.
     *
     * Takes in a boolean that represents whether the graph will be directed.
     *
     * This runs in O(1) time.
     */
    public AdjacencyMatrixGraph(boolean directed) {
        _adjMatrix = this.makeEmptyEdgeArray();
        _vertices = new HashSet<>();
        _edges = new HashSet<>();
        _numVertices = 0;
        if(directed){
            _directed = true;
        }
    }

    /**
     * Returns an iterator holding all the Vertices of the graph.
     *
     * <p>
     * This runs in O(1) time.
     * </p>
     * * Note that the visualizer uses this method to display the graph's
     * vertices, so you should implement it first.
     *
     * @return an Iterator containing the vertices of the Graph.
     */
    @Override
    public Iterator<CS16Vertex<V>> vertices() {
        return _vertices.iterator();
    }

    /**
     * Returns an iterator holding all the edges of the graph.
     *
     * <p>
     * This runs in O(|1|) time.
     * </p>
     *
     * Note that the visualizer uses this method to display the graph's edges.
     *
     * @return an Iterator containing the edges of the Graph.
     */
    @Override
    public Iterator<CS16Edge<V>> edges() {
        return _edges.iterator();
    }

    /**
     * Inserts a new Vertex into the Graph.
     *
     * <p>
     * This runs in O(1) time.
     * </p>
     * 
     * @param vertElement
     *            the element to be added to the graph as a vertex
     */
    @Override
    public CS16Vertex<V> insertVertex(V vertElement) {

        CS16Vertex<V> newVertex = new GraphVertex<>(vertElement);

        _numVertices++;
       newVertex.setVertexNumber(_numVertices);

        _vertices.add(newVertex);
        return newVertex;
    }

    /**
     * Inserts a new Edge into the Graph.
     * 
     * <p>
     * This must run in O(1) time.
     * </p>
     * 
     * @param v1
     *            The first vertex of the edge connection.
     * @param v2
     *            The second vertex of the edge connection.
     * @param edgeElement
     *            The element of the newly inserted edge.
     * @return Returns the newly inserted Edge.
     * @throws InvalidVertexException
     *             Thrown when either Vertex is null.
     */
    @Override
    public CS16Edge<V> insertEdge(CS16Vertex<V> v1, CS16Vertex<V> v2, Integer edgeElement)
            throws InvalidVertexException {
        if(v1 == null || v2 == null){
            throw new InvalidVertexException("A vertex is null.");
        }

        CS16Edge<V> newEdge = new GraphEdge<>(edgeElement, v1, v2);
        _edges.add(newEdge);

        _adjMatrix[v1.getVertexNumber()][v2.getVertexNumber()] = newEdge;

        if(!_directed){
            _adjMatrix[v2.getVertexNumber()][v1.getVertexNumber()] = newEdge;
        }

        return newEdge;
    }

    /**
     * Removes a Vertex from the graph.
     * <p>
     * This runs in O(|V|) time.
     * </p>
     *
     * @param vert
     *            The Vertex to remove.
     * @return The element of the removed Vertex.
     * @throws InvalidVertexException
     *             Thrown when the Vertex is null.
     */
    @Override
    public V removeVertex(CS16Vertex<V> vert) throws InvalidVertexException {

        if(vert == null){
            throw new InvalidVertexException("A vertex is null.");
        }

        Iterator<CS16Vertex<V>> it = vertices();
        while(it.hasNext()){
            CS16Vertex<V> vertex = it.next();
            if(this.areAdjacent(vert, vertex)){
               this.removeEdge(this.connectingEdge(vert, vertex));
            }
        }

        _vertices.remove(vert);
        return vert.element();
    }

    /**
     * Removes an Edge from the Graph.
     *
     * <p>
     * This runs in O(1) time.
     * </p>
     *
     * @param edge
     *            The Edge to remove.
     * @return The element of the removed Edge.
     * @throws InvalidEdgeException
     *             Thrown when the Edge is null.
     */
    @Override
    public Integer removeEdge(CS16Edge<V> edge) throws InvalidEdgeException {

        if(edge == null){
            throw new InvalidEdgeException("Edge is null.");
        }

        int i = edge.getVertexOne().getVertexNumber();
        int j = edge.getVertexTwo().getVertexNumber();

        _adjMatrix[i][j] = null;

        if(!_directed){
            _adjMatrix[j][i] = null;
        }

        _edges.remove(edge);

        return edge.element();
    }

    /**
     * Returns the edge that connects the two vertices. You will want to consult
     * your adjacency matrix to see if they are connected. If so, return that
     * edge, otherwise throw a NoSuchEdgeException.
     * 
     * If the graph is directed, then two nodes are connected if there is an
     * edge from the first vertex to the second. 
     * If the graph is undirectd, then two nodes are connected if there is an
     * edge from the first vertex to the second and vice versa. 
     *
     * <p>
     * This runs in O(1) time.
     * </p>
     *
     * @param v1
     *            The first vertex that may be connected.
     * @param v2
     *            The second vertex that may be connected.
     * @return The edge that connects the first and second vertices.
     * @throws InvalidVertexException
     *             Thrown when either vertex is null.
     * @throws NoSuchEdgeException
     *             Thrown when no edge connects the vertices.
     */
    @Override
    public CS16Edge<V> connectingEdge(CS16Vertex<V> v1, CS16Vertex<V> v2)
            throws InvalidVertexException, NoSuchEdgeException {

        if(v1 == null || v2 == null){
            throw new InvalidVertexException("A vertex is null.");
        }

        if(_adjMatrix[v1.getVertexNumber()][v2.getVertexNumber()] != null){
            return _adjMatrix[v1.getVertexNumber()][v2.getVertexNumber()];

        } else {
            throw new NoSuchEdgeException("No edge connecting the vertices.");

        }
    }

    /**
     * Returns an Iterator over all the Edges that are incoming to this Vertex.
     * <p>
     * This runs in O(|V|) time;
     * </p>
     * 
     *
     * @param vert
     *            The vertex to find the incoming edges on.
     * @return Returns an Iterator holding the incoming edges on v.
     * @throws InvalidVertexException
     *             Thrown when the Vertex is null.
     */
    @Override
    public Iterator<CS16Edge<V>> incomingEdges(CS16Vertex<V> vert) throws InvalidVertexException {
        if(vert == null){
            throw new InvalidVertexException("A vertex is null.");
        }

        ArrayList<CS16Edge<V>> incomingEdges = new ArrayList<>();

        Iterator<CS16Edge<V>> it = edges();

        while(it.hasNext()){
            CS16Edge<V> edge = it.next();
            if(edge.getVertexTwo() == vert){
                incomingEdges.add(edge);
            }

            if(!_directed){
                if(edge.getVertexOne() == vert){
                    incomingEdges.add(edge);
                }
            }
        }

        return incomingEdges.iterator();
    }
    
    /**
     * Returns an Iterator of all the Edges that are outgoing from this vertex.
     * <p>
     * This runs in O(|V|) time;
     * </p>

     * @param vert
     *            The vertex to find the outgoing edges on.
     * @return Returns an Iterator holding the outgoing edges on v.
     * @throws InvalidVertexException
     *             Thrown when the Vertex is null.
     */
    @Override
    public Iterator<CS16Edge<V>> outgoingEdges(CS16Vertex vert) throws InvalidVertexException {

        if(vert == null){
            throw new InvalidVertexException("A vertex is null.");
        }

        Set<CS16Edge<V>> outgoingEdges = new HashSet<>();

        Iterator<CS16Edge<V>> it = edges();

        while(it.hasNext()){
            CS16Edge<V> edge = it.next();
            if(edge.getVertexOne() == vert){
                outgoingEdges.add(edge);
            }
            if(!_directed){
                if(edge.getVertexTwo() == vert){
                    outgoingEdges.add(edge);
                }
            }
        }

        return outgoingEdges.iterator();
    }

    /**
     * Returns an int of the number Edges that are leaving from this Vertex. This should only
     * work if called on a directed graph. This method will be used in MyPageRank.
     * 
     * @param vert
     *            The vertex to find the outgoing edges on.
     * @return an int
     * @throws InvalidVertexException
     *             Thrown when the Vertex is not valid.
     * @throws DirectionException
     *             Thrown when this method is called on an undirected graph.
     */
    @Override
    public int numOutgoingEdges(CS16Vertex<V> vert) throws InvalidVertexException, DirectionException {

        if(vert == null){
            throw new InvalidVertexException("A vertex is null.");
        }

        if(!_directed){
            throw new DirectionException("Graph is undirected.");
        }

        int numOutgoingEdges = 0;

        Iterator<CS16Edge<V>> it = this.outgoingEdges(vert);

        while(it.hasNext()){
            CS16Edge<V> edge = it.next();
            if(edge.getVertexOne() == vert){
                numOutgoingEdges++;
            }
        }

        return numOutgoingEdges;
    }

    /**
     * Returns the Vertex that is on the other side of Edge e opposite of Vertex
     * v.
     * 
     * If the edge is not incident on v, then throw a NoSuchVertexException.
     *
     * <p>
     * This must run in O(1) time.
     * </p>
     *
     * @param vert
     *            The first vertex on Edge e.
     * @param edge
     *            The edge connecting Vertex v and the unknown opposite Vertex.
     * @return The opposite Vertex of v across Edge e.
     * @throws InvalidVertexException
     *             Thrown when the Vertex is not valid.
     * @throws InvalidEdgeException
     *             Thrown when the Edge is not valid.
     * @throws NoSuchVertexException
     *             Thrown when Edge e is not incident on v.
     */
    @Override
    public CS16Vertex<V> opposite(CS16Vertex<V> vert, CS16Edge<V> edge)
            throws InvalidVertexException, InvalidEdgeException, NoSuchVertexException {

        if(vert == null){
            throw new InvalidVertexException("Vertex is null.");
        }

        if(edge == null){
            throw new InvalidEdgeException("Edge is null.");
        }

        if(edge.getVertexOne() == vert){
            return edge.getVertexTwo();

        } else if(edge.getVertexTwo() == vert){
            return edge.getVertexOne();
        } else {
            throw new NoSuchVertexException("Ege e is not incident on v.");

        }
    }

    /**
     * Returns the two Vertices that the Edge e is connected to.
     * 
     * Checking the adjacency matrix may be too costly for this method.
     *
     * <p>
     * This must run in O(1) time.
     * </p>
     * 
     * Note that the visualizer uses this method to display the graph's edges.
     *
     * @param e
     *            The edge to find the connecting Vertex's on.
     * @return a list of Vertex's holding the two connecting vertices.
     * @throws InvalidEdgeException
     *             Thrown when the Edge e is null.
     */
    @Override
    public List<CS16Vertex<V>> endVertices(CS16Edge<V> e) throws InvalidEdgeException {

        List<CS16Vertex<V>> endVertices = new ArrayList<>();
        if(e == null){
            throw new InvalidEdgeException("Edge is null.");
        }

        endVertices.add(e.getVertexOne());
        endVertices.add(e.getVertexTwo());

        return endVertices;
    }

    /**
     * Returns true if there exists an Edge that starts from Vertex v1 and ends
     * at Vertex v2 for both a directed and undirected graph. For a directed graph
     * two vertices are adjacent if there is an edge from the first vertex to the 
     * second vertex.
     * 
     * <p>
     * This runs in O(1) time.
     * </p>
     * 
     * @param v1
     *            The first Vertex to test adjacency.
     * @param v2
     *            The second Vertex to test adjacency.
     * @return Returns true if the vertices are adjacent.
     * @throws InvalidVertexException
     *             Thrown if either vertex is null.
     */
    @Override
    public boolean areAdjacent(CS16Vertex<V> v1, CS16Vertex<V> v2) throws InvalidVertexException {

        if(v1 == null || v2 == null){
            throw new InvalidVertexException("A vertex is null.");
        }

        if(!_directed){
           if(_adjMatrix[v1.getVertexNumber()][v2.getVertexNumber()] != null && _adjMatrix[v2.getVertexNumber()][v1.getVertexNumber()] != null){
               return true;
            }

        } else {
            if(_adjMatrix[v1.getVertexNumber()][v2.getVertexNumber()] != null){
                return true;
            }
        }

        return false;
    }

    /**
     * Toggles the directedness of the graph.
     */
    @Override
    public void toggleDirected() {
        _directed = !_directed;
        this.clear();
    }

    /**
     * Clears all the vertices and edges from the graph. You will want to also
     * clear the adjacency matrix.
     *
     * <p>
     * This runs in O(1) time.
     * </p>
     */
    @Override
    public void clear() {
    _vertices = new HashSet<>();
    _numVertices = 0;
    _edges = new HashSet<>();
    _adjMatrix = this.makeEmptyEdgeArray();
    }

    /**
     * Returns the number of vertices in the graph.
     */
    @Override
	public int getNumVertices() {
		return _numVertices;
	}

    // Do not change this method!
    @SuppressWarnings("unchecked")
    private CS16Edge<V>[][] makeEmptyEdgeArray() {
        return new CS16Edge[MAX_VERTICES][MAX_VERTICES];
    }
}