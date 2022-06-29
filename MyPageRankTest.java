package graph;

import static org.junit.Assert.*;

import org.junit.Test;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;
import java.util.Map;
import java.util.HashMap;

/**
 * This class tests the functionality of your PageRank algorithm on a
 * directed AdjacencyMatrixGraph. The general framework of a test case is:
 * - Name the test method descriptively,
 * - Mention what is being tested (it is ok to have slightly verbose method names here)
 *
 * Some tips to keep in mind when writing test cases:
 * - All pages' ranks should total to 1.
 * - It will be easier to start out by writing test cases on smaller graphs.
 *
 */
public class MyPageRankTest {

	// This is your margin of error for testing
	double _epsilon = 0.03;

	/**
     * A simple test with four pages. Each page only has one
	 * outgoing link to a different page, resulting in a square
	 * shape or cycle when visualized. The pages' total ranks is 1.
     */
	@Test
	public void testFourEqualRanks() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");

		/**
		  * Inserting an edge with a null element since a weighted edge is not
		  * meaningful for the PageRank algorithm.
		  */

		CS16Edge<String> e0 = adjMatrix.insertEdge(a,b,null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(b,c,null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(c,d,null);
		CS16Edge<String> e3 = adjMatrix.insertEdge(d,a,null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		// Check that the number of vertices returned by PageRank is 4
		assertEquals(output.size(), 4);
		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}

		// The weights of each vertex should be 0.25
		double expectedRankA = 0.25;
		double expectedRankB = 0.25;
		double expectedRankC = 0.25;
		double expectedRankD = 0.25;

		// The sum of weights should always be 1
		assertEquals(total, 1, _epsilon);

		// The Rank for each vertex should be 0.25 +/- epsilon
		assertEquals(expectedRankA, output.get(a), _epsilon);
		assertEquals(expectedRankB, output.get(b), _epsilon);
		assertEquals(expectedRankC, output.get(c), _epsilon);
		assertEquals(expectedRankD, output.get(d), _epsilon);

	}

	/**
     	 * A simple test with three pages. Note that vertex A's
	 * rank is not 0 even though it has no incoming edges,
	 * demonstrating the effects of the damping factor and handling sinks.
     	 */
	@Test
	public void simpleTestOne() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Edge<String> e0 = adjMatrix.insertEdge(a,b,null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(b,c,null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 3);
		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}

		// These are precomputed values
		double expectedRankA = 0.186;
		double expectedRankB = 0.342;
		double expectedRankC = 0.471;

		assertEquals(total, 1, _epsilon);
		assertEquals(expectedRankA, output.get(a), _epsilon);
		assertEquals(expectedRankB, output.get(b), _epsilon);
		assertEquals(expectedRankC, output.get(c), _epsilon);

	}

	/**
	 * A simple test with an empty graph. Trying to see if the hashMap
	 * will have size 0, as expected.
	 */
	@Test
	public void testEmptyGraph() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		// checking that output has size 0
		assertEquals(output.size(), 0);
	}

	/**
	 * A simple test with a graph with a single vertex. Trying to see if the hashMap
	 * will have size 1, as expected.
	 */
	@Test
	public void testSingleVertexGraph() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> b = adjMatrix.insertVertex("A");

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		// checking that output has size 1
		assertEquals(output.size(), 1);
		assertEquals(output.get(b), 1.0, _epsilon);
	}

	/**
	 * A simple test with two disconnected vertices.
	 * Checking to see if they still get some page rank
	 * b/c of the dampening factor.
	 */
	@Test
	public void testTwoDisconnectedVerticesGraph() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		// checking that output has size 2
		assertEquals(output.size(), 2);
		assertEquals(output.get(a), 0.5, _epsilon);
		assertEquals(output.get(b), 0.5, _epsilon);
	}

	/**
	 * A simple test with three disconnected vertices.
	 * Checking to see if they still get some page rank
	 * b/c of the dampening factor.
	 */
	@Test
	public void testThreeDisconnectedVerticesGraph() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		// checking that output has size 2
		assertEquals(output.size(), 3);
		assertEquals(output.get(a), 0.33, _epsilon);
		assertEquals(output.get(b), 0.33, _epsilon);
		assertEquals(output.get(c), 0.33, _epsilon);
	}

	/**
	 * A simple test with 2 sinks.
	 */
	@Test
	public void testTwoSinksGraph() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");

		CS16Edge<String> e0 = adjMatrix.insertEdge(a, b, null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(b, c, null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(b, d, null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 4);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		// c and d are sinks. results are according to demo
		assertEquals(output.get(c),  output.get(d), _epsilon);
		assertTrue(output.get(a) < output.get(b));
		assertEquals(output.get(c),  output.get(b), _epsilon);

		// checking if all ranks sum to 1
		assertEquals(total, 1, _epsilon);
	}

	/**
	 * A more complicated test with three sinks.
	 */
	@Test
	public void testManySinksGraph() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");
		CS16Vertex<String> e = adjMatrix.insertVertex("E");
		CS16Vertex<String> f = adjMatrix.insertVertex("F");

		CS16Edge<String> e0 = adjMatrix.insertEdge(a, b, null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(a, e, null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(d, e, null);
		CS16Edge<String> e3 = adjMatrix.insertEdge(f, b, null);
		CS16Edge<String> e4 = adjMatrix.insertEdge(f, c, null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 6);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		// c, b and e are sinks. results are according to demo
		assertEquals(output.get(a),  output.get(d), _epsilon);
		assertTrue(output.get(c) > output.get(f));
		assertTrue(output.get(e) > output.get(b));
		assertEquals(output.get(a),  output.get(f), _epsilon);

		// checking if all ranks sum to 1
		assertEquals(total, 1, _epsilon);
	}

	/**
	 * A more complicated test with a graph consisting of two parts that are not connected.
	 */
	@Test
	public void testUnconnectedPartsofGraph() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");
		CS16Vertex<String> e = adjMatrix.insertVertex("E");
		CS16Vertex<String> f = adjMatrix.insertVertex("F");

		CS16Edge<String> e0 = adjMatrix.insertEdge(c, a, null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(a, b, null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(b, c, null);
		CS16Edge<String> e3 = adjMatrix.insertEdge(e, f, null);
		CS16Edge<String> e4 = adjMatrix.insertEdge(d, e, null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 6);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		// a, c and b are connected to each other
		// d, e and f are connected to each other
		assertEquals(output.get(a),  output.get(c), _epsilon);
		assertEquals(output.get(b),  output.get(c), _epsilon);
		assertTrue(output.get(c) > output.get(f));
		assertTrue(output.get(e) < output.get(b));
		assertTrue(output.get(f) > output.get(e));
		assertTrue(output.get(d) < output.get(e));

		// checking if all ranks sum to 1
		assertEquals(total, 1, _epsilon);
	}

	/**
	 * A more complicated graph (but with no sinks) test.
	 */
	@Test
	public void testComplicatedGraphWithNoSinks() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");
		CS16Vertex<String> e = adjMatrix.insertVertex("E");
		CS16Vertex<String> f = adjMatrix.insertVertex("F");

		CS16Edge<String> e0 = adjMatrix.insertEdge(f, a, null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(a, b, null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(b, e, null);
		CS16Edge<String> e3 = adjMatrix.insertEdge(e, f, null);
		CS16Edge<String> e4 = adjMatrix.insertEdge(b, c, null);
		CS16Edge<String> e5 = adjMatrix.insertEdge(c, d, null);
		CS16Edge<String> e6 = adjMatrix.insertEdge(d, b, null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 6);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		// results based on demo
		assertEquals(output.get(f),  output.get(d), _epsilon);
		assertTrue(output.get(b) > output.get(a));
		assertTrue(output.get(b) > output.get(c));
		assertTrue(output.get(b) > output.get(d));
		assertTrue(output.get(b) > output.get(e));

		// checking if all ranks sum to 1
		assertEquals(total, 1, _epsilon);
	}

	/**
	 * Checking if a graph still works if it is a cycle.
	 */
	@Test
	public void testCycleGraph() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");

		CS16Edge<String> e1 = adjMatrix.insertEdge(a, b, null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(b, c, null);
		CS16Edge<String> e3 = adjMatrix.insertEdge(c, a, null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 3);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		// results based on demo
		assertEquals(output.get(a),  output.get(b), _epsilon);
		assertEquals(output.get(a),  output.get(c), _epsilon);
		assertEquals(output.get(c),  output.get(b), _epsilon);

		// checking if all ranks sum to 1
		assertEquals(total, 1, _epsilon);
	}
}
