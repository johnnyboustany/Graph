package graph;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static support.graph.Constants.MAX_VERTICES;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.datastructures.EmptyPriorityQueueException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import support.graph.*;

/**
 * This class tests the functionality of a Graph based on a 'String' type
 * parameter for the vertices. Edge elements are Integers. The general framework
 * of a test case is: - Name the test method descriptively, mentioning what is
 * being tested (it is ok to have slightly verbose method names here) - Set-up
 * the program state (ex: instantiate a heap and insert K,V pairs into it) - Use
 * assertions to validate that the program is in the state you expect it to be
 * See header comments over tests for what each test does
 * 
 * Before each test is run, you can assume that the '_graph' variable is reset to
 * a new instance of the a Graph<String> that you can simply use 'as is'
 * via the methods under the 'DO NOT MODIFY ANYTHING BELOW THIS LINE' line.
 * Of course, please do not modify anything below that, or the above 
 * assumptions may be broken.
 */
@RunWith(Parameterized.class)
public class GraphTest {
    

    // Undirected Graph instance variable
    private Graph<String> _graph;
    // Directed Graph instance variable
    private Graph<String> _dirGraph;
    private String _graphClassName;
	
    /**
     * A simple test for insertVertex, that adds 3 vertices and then checks to
     * make sure they were added by accessing them through the vertices
     * iterator.
     */
    @Test(timeout = 10000)
    public void testInsertVertex() {
        // insert vertices
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");

        // use the vertex iterator to get a list of the vertices in the actual
        // graph
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it = _graph.vertices();
        while (it.hasNext()) {
            actualVertices.add(it.next());
        }

        // assert that the graph state is consistent with what you expect
        assertThat(actualVertices.size(), is(3));
        assertThat(actualVertices.contains(A), is(true));
        assertThat(actualVertices.contains(B), is(true));
        assertThat(actualVertices.contains(C), is(true));
    }

    // Same test as above, but with a directed graph
    @Test(timeout = 10000)
    public void testInsertVertexDirected() {
        // insert vertices
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");

        // use the vertex iterator to get a list of the vertices in the actual
        // graph
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it = _dirGraph.vertices();
        while (it.hasNext()) {
            actualVertices.add(it.next());
        }

        // assert that the graph state is consistent with what you expect
        assertThat(actualVertices.size(), is(3));
        assertThat(actualVertices.contains(A), is(true));
        assertThat(actualVertices.contains(B), is(true));
        assertThat(actualVertices.contains(C), is(true));
    }


    /**
     * A simple test for removeVertex, that adds 3 vertices, removes 2 of them
     * and checks if the 2 vertices were removed. It also checks that all edges
     * inserted are deleted.
     */
    @Test(timeout = 10000)
    public void testRemoveVertex() {
        // insert vertices
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");

        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2);

        _graph.removeVertex(A);
        _graph.removeVertex(B);

        // use the vertex iterator to get a list of the vertices in the actual
        // graph
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it = _graph.vertices();
        while (it.hasNext()) {
            actualVertices.add(it.next());
        }

        // assert that the graph state is consistent with what you expect
        assertThat(actualVertices.size(), is(1));
        assertThat(actualVertices.contains(A), is(false));
        assertThat(actualVertices.contains(B), is(false));
        assertThat(actualVertices.contains(C), is(true));

        // use the edge iterator to get a list of the edges in the actual graph.
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> iterator = _graph.edges();
        while (iterator.hasNext()) {
            actualEdges.add(iterator.next());
        }

        // assert that the graph state is consistent with what you expect.
        assertThat(actualEdges.size(), is(0));
        assertThat(actualEdges.contains(ab), is(false));
        assertThat(actualEdges.contains(bc), is(false));
    }

    // Same test as above, but with a directed graph
    @Test(timeout = 10000)
    public void testRemoveVertexDirected() {
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");

        // use the edge iterator to get a list of the edges in the actual graph.
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);

        _dirGraph.removeVertex(A);
        _dirGraph.removeVertex(B);

        // use the vertex iterator to get a list of the vertices in the actual
        // graph
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it = _dirGraph.vertices();
        while (it.hasNext()) {
            actualVertices.add(it.next());
        }

        // assert that the graph state is consistent with what you expect
        assertThat(actualVertices.size(), is(1));
        assertThat(actualVertices.contains(A), is(false));
        assertThat(actualVertices.contains(B), is(false));
        assertThat(actualVertices.contains(C), is(true));

        // use the edge iterator to get a list of the edges in the actual graph.
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> iterator = _dirGraph.edges();
        while (iterator.hasNext()) {
            actualEdges.add(iterator.next());
        }

        // assert that the graph state is consistent with what you expect.
        assertThat(actualEdges.size(), is(0));
        assertThat(actualEdges.contains(ab), is(false));
        assertThat(actualEdges.contains(bc), is(false));
    }

    /**
     * A simple test for checking if exception is raised
     * if a null edge is passed into removeEdge().
     */
    @Test(expected = InvalidVertexException.class, timeout = 10000)
    public void testRemoveVertexException() {
        _graph.removeVertex(null);
    }

    // same test as above for directed
    @Test(expected = InvalidVertexException.class, timeout = 10000)
    public void testRemoveVertexExceptionDirected() {
        _dirGraph.removeVertex(null);
    }

    /**
     * A simple test for insertEdges that adds 3 vertices, adds two edges to the
     * graph and then asserts that both edges were in fact added using the edge
     * iterator as well as checks to make sure their from and to vertices were
     * set correctly.
     */
    @Test(timeout = 10000)
    public void testInsertEdges() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");

        // use the edge iterator to get a list of the edges in the actual graph.
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2);

        // use the edge iterator to get a list of the edges in the actual graph.
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _graph.edges();
        while (it.hasNext()) {
            actualEdges.add(it.next());
        }

        // assert that the graph state is consistent with what you expect.
        assertThat(actualEdges.size(), is(2));
        assertThat(actualEdges.contains(ab), is(true));
        assertThat(actualEdges.contains(bc), is(true));
    }

    // Same test as above, but with a directed graph.
    @Test(timeout = 10000)
    public void testInsertEdgesDirected() {
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");

        // use the edge iterator to get a list of the edges in the actual graph.
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);

        // use the edge iterator to get a list of the edges in the actual graph.
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _dirGraph.edges();
        while (it.hasNext()) {
            actualEdges.add(it.next());
        }

        // assert that the graph state is consistent with what you expect.
        assertThat(actualEdges.size(), is(2));
        assertThat(actualEdges.contains(ab), is(true));
        assertThat(actualEdges.contains(bc), is(true));

    }

    /**
     * A simple test for checking if exception is raised
     * if a null vertex is passed into insertEdges().
     */
    @Test(expected = InvalidVertexException.class, timeout = 10000)
    public void testInsertEdgesException() {
        _graph.insertEdge(null, null, 1);
    }

    // same test as above for directed
    @Test(expected = InvalidVertexException.class, timeout = 10000)
    public void testInsertEdgesExceptionDirected() {
        _dirGraph.insertEdge(null, null, 1);
    }

    /**
     * A simple test for removeEdges that adds 3 vertices, adds two edges to the
     * graph, removes them and then asserts that both edges were in fact removed using the edge
     * iterator.
     */
    @Test(timeout = 10000)
    public void testRemoveEdges() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");

        // use the edge iterator to get a list of the edges in the actual graph.
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2);

        // removing the edges
        _graph.removeEdge(ab);
        _graph.removeEdge(bc);

        // use the edge iterator to get a list of the edges in the actual graph.
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _graph.edges();
        while (it.hasNext()) {
            actualEdges.add(it.next());
        }

        // assert that the graph state is consistent with what you expect.
        assertThat(actualEdges.size(), is(0));
        assertThat(actualEdges.contains(ab), is(false));
        assertThat(actualEdges.contains(bc), is(false));
    }

    // Same test as above, but with a directed graph
    @Test(timeout = 10000)
    public void testRemoveEdgesDirected() {
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");

        // use the edge iterator to get a list of the edges in the actual graph.
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);

        // removing the edges
        _dirGraph.removeEdge(ab);
        _dirGraph.removeEdge(bc);

        // use the edge iterator to get a list of the edges in the actual graph.
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _dirGraph.edges();
        while (it.hasNext()) {
            actualEdges.add(it.next());
        }

        // assert that the graph state is consistent with what you expect.
        assertThat(actualEdges.size(), is(0));
        assertThat(actualEdges.contains(ab), is(false));
        assertThat(actualEdges.contains(bc), is(false));
    }

    /**
     * A simple test for checking if exception is raised
     * if a null edge is passed into removeEdge().
     */
    @Test(expected = InvalidEdgeException.class, timeout = 10000)
    public void testRemoveEdgeException() {
        _graph.removeEdge(null);
    }

    // same test as above for directed
    @Test(expected = InvalidEdgeException.class, timeout = 10000)
    public void testRemoveEdgeExceptionDirected() {
        _dirGraph.removeEdge(null);
    }

    /**
     * A test for connectingEdge that tests that the edge between 2
     * vertices is recognized despite of order of inputs, for undirected.
     */
    @Test(timeout = 10000)
    public void testConnectingEdge() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");

        // use the edge iterator to get a list of the edges in the actual graph.
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2);

        // testing if order matters w undirected
        assertThat(ab, is(_graph.connectingEdge(A,B)));
        assertThat(ab, is(_graph.connectingEdge(B,A)));
        assertThat(bc, is(_graph.connectingEdge(B,C)));
        assertThat(bc, is(_graph.connectingEdge(C,B)));

    }

    // Same test as above, but with a directed graph.
    @Test(timeout = 10000)
    public void testConnectingEdgeDirected() {
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");

        // use the edge iterator to get a list of the edges in the actual graph.
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);

        // testing
        assertThat(ab, is(_dirGraph.connectingEdge(A,B)));
        assertThat(bc, is(_dirGraph.connectingEdge(B,C)));

    }

    /**
     * A simple test for checking if NoSuchEdgeException is raised
     * if vertices are passed into connectingEdge() in the wrong order for Diretted.
     */
    @Test(expected = NoSuchEdgeException.class, timeout = 10000)
    public void testNoSuchEdgeConnectingEdgeDirected() {
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");

        // inserting edge between the above vertices
        _dirGraph.insertEdge(A, B, 1);

       // passing them in wrong order
        _dirGraph.connectingEdge(B,A);
    }

    /**
     * A simple test for checking if exception is raised
     * if null vertices are passed into connectingEdge() for Undirected graph.
     */
    @Test(expected = InvalidVertexException.class, timeout = 10000)
    public void testConnectingEdgeNullException() {
        _graph.connectingEdge(null,null);
    }

    // same test as above but for directed graph.
    @Test(expected = InvalidVertexException.class, timeout = 10000)
    public void testConnectingEdgeNullExceptionDirected() {
        _dirGraph.connectingEdge(null,null);
    }

    /**
     * A test for IncomingEdges() that checks if an undirected graph
     * recognizes any edges as incoming (as it should).
     */
    @Test(timeout = 10000)
    public void testIncomingEdges() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Vertex<String> D = _graph.insertVertex("D");

        CS16Edge<String> ab = _graph.insertEdge(B, A, 1); // outgoing
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2); // outgoing
        CS16Edge<String> bd = _graph.insertEdge(D, B, 2); // incoming

        // use the edge iterator to get a list of the edges from the iterator.
        List<CS16Edge<String>> actualEdges = new ArrayList<>();
        Iterator<CS16Edge<String>> iterator = _graph.incomingEdges(B);
        while (iterator.hasNext()) {
            actualEdges.add(iterator.next());
        }

        // assert that the graph state is consistent with what you expect.
        assertThat(actualEdges.size(), is(3));
        assertThat(actualEdges.contains(ab), is(true));
        assertThat(actualEdges.contains(bc), is(true));
        assertThat(actualEdges.contains(bd), is(true));
    }

    // Same test as above, but with a directed graph. This time only incoming edges are considered.
    @Test(timeout = 10000)
    public void testIncomingEdgesDirected() {
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        CS16Vertex<String> D = _dirGraph.insertVertex("C");

        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1); // incoming
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2); // outgoing
        CS16Edge<String> bd = _dirGraph.insertEdge(D, B, 2); // incoming

        // use the edge iterator to get a list of the edges from the iterator.
        List<CS16Edge<String>> actualEdges = new ArrayList<>();
        Iterator<CS16Edge<String>> iterator = _dirGraph.incomingEdges(B);
        while (iterator.hasNext()) {
            actualEdges.add(iterator.next());
        }

        // assert that the graph state is consistent with what you expect.
        assertThat(actualEdges.size(), is(2));
        assertThat(actualEdges.contains(ab), is(true));
        assertThat(actualEdges.contains(bc), is(false));
        assertThat(actualEdges.contains(bd), is(true));
    }


    /**
     * A simple test for checking if exception is raised
     * if null vertex is passed into incomingEdges() for Undirected graph.
     */
    @Test(expected = InvalidVertexException.class, timeout = 10000)
    public void testIncomingEdgeNullException() {
        _graph.incomingEdges(null);
    }

    // same test as above but for directed graph.
    @Test(expected = InvalidVertexException.class, timeout = 10000)
    public void testIncomingEdgeNullExceptionDirected() {
        _dirGraph.incomingEdges(null);
    }

    /**
     * A test for outgoingEdges that checks if an undirected graph
     * recognizes all edges as outgoing (as it should).
     */
    @Test(timeout = 10000)
    public void testOutgoingEdges() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Vertex<String> D = _graph.insertVertex("D");

        CS16Edge<String> ab = _graph.insertEdge(B, A, 1); // outgoing
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2); // outgoing
        CS16Edge<String> bd = _graph.insertEdge(D, B, 2); // incoming

        // use the edge iterator to get a list of the edges from the iterator.
        List<CS16Edge<String>> actualEdges = new ArrayList<>();
        Iterator<CS16Edge<String>> iterator = _graph.outgoingEdges(B);
        while (iterator.hasNext()) {
            actualEdges.add(iterator.next());
        }

        // assert that the graph state is consistent with what you expect.
        assertThat(actualEdges.size(), is(3));
        assertThat(actualEdges.contains(ab), is(true));
        assertThat(actualEdges.contains(bc), is(true));
        assertThat(actualEdges.contains(bd), is(true));
    }

    // Same test as above, but with a directed graph. This time only outgoing edges are considered.
    @Test(timeout = 10000)
    public void testOutgoingEdgesDirected() {
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        CS16Vertex<String> D = _dirGraph.insertVertex("C");

        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1); // incoming
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2); // outgoing
        CS16Edge<String> bd = _dirGraph.insertEdge(D, B, 2); // incoming

        // use the edge iterator to get a list of the edges from the iterator.
        List<CS16Edge<String>> actualEdges = new ArrayList<>();
        Iterator<CS16Edge<String>> iterator = _dirGraph.outgoingEdges(B);
        while (iterator.hasNext()) {
            actualEdges.add(iterator.next());
        }

        // assert that the graph state is consistent with what you expect.
        assertThat(actualEdges.size(), is(1));
        assertThat(actualEdges.contains(ab), is(false));
        assertThat(actualEdges.contains(bc), is(true));
        assertThat(actualEdges.contains(bd), is(false));

    }

    /**
     * A simple test for checking if exception is raised
     * if null vertex is passed into incomingEdges() for Undirected graph.
     */
    @Test(expected = InvalidVertexException.class, timeout = 10000)
    public void testOutgoingEdgeNullException() {
        _graph.outgoingEdges(null);
    }

    // same test as above but for directed graph.
    @Test(expected = InvalidVertexException.class, timeout = 10000)
    public void testOutgoingEdgeNullExceptionDirected() {
        _dirGraph.outgoingEdges(null);
    }

    /**
     * A simple test for checking if exception is raised
     * if numOutgoingEdges() is used on an undirected graph.
     */
    @Test(expected = DirectionException.class, timeout = 10000)
    public void testNumOutgoingEdgesDirectionException() {

        CS16Vertex<String> A = _graph.insertVertex("A");
        _graph.numOutgoingEdges(A);
    }

    /**
     * A simple test for checking if exception is raised
     * if null vertex is passed into numOutgoingEdges() for undirected graph.
     */
    @Test(expected = InvalidVertexException.class, timeout = 10000)
    public void testNumOutgoingEdgesNullException() {
        _graph.numOutgoingEdges(null);
    }

    // same test as above but for directed graph.
    @Test(expected = InvalidVertexException.class, timeout = 10000)
    public void testNumOutgoingEdgesNullExceptionDirected() {
        _dirGraph.outgoingEdges(null);
    }

    /**
     * A simple test for checking the method correctly counts
     * the number of outgoing edges for a directed graph.
     */
    @Test(timeout = 10000)
    public void testNumOutgoingEdgesDirected() {
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        CS16Vertex<String> D = _dirGraph.insertVertex("C");

        _dirGraph.insertEdge(A, B, 1); // incoming
        _dirGraph.insertEdge(B, C, 2); // outgoing
        _dirGraph.insertEdge(B, D, 2); // outgoing
        _dirGraph.insertEdge(B, A, 1); // outgoing

        // assert that the there are 3 outgoing edges for B
        assertThat(_dirGraph.numOutgoingEdges(B), is(3));
    }

    /**
     * A simple test for checking if exception is raised
     * if null vertex is passed into opposite() for undirected graph.
     */
    @Test(expected = InvalidVertexException.class, timeout = 10000)
    public void testOppositeNullVertexException() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1); // incoming

        _graph.opposite(null, ab);
    }

    // same for directed
    @Test(expected = InvalidVertexException.class, timeout = 10000)
    public void testOppositeNullVertexExceptionDirected() {
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1); // incoming

        _dirGraph.opposite(null, ab);
    }

    /**
     * A simple test for checking if exception is raised
     * if null edge is passed into opposite() for undirected graph.
     */
    @Test(expected = InvalidEdgeException.class, timeout = 10000)
    public void testOppositeNullEdgeException() {
        CS16Vertex<String> A = _graph.insertVertex("A");

        _graph.opposite(A, null);
    }

    // same for directed
    @Test(expected = InvalidEdgeException.class, timeout = 10000)
    public void testOppositeNullEdgeExceptionDirected() {
        CS16Vertex<String> A = _dirGraph.insertVertex("A");

        _dirGraph.opposite(A, null);
    }

    /**
     * A simple test for checking if exception is raised
     * if the edge is not incident on v for undirected graph.
     */
    @Test(expected = NoSuchVertexException.class, timeout = 10000)
    public void testOppositeNoSuchVertexException() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");

        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);

        _graph.opposite(C, ab);
    }

    // same for directed
    @Test(expected = NoSuchVertexException.class, timeout = 10000)
    public void testOppositeNoSuchVertexExceptionDirected() {
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");

        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);

        _dirGraph.opposite(C, ab);
    }

    /**
     * A simple test for checking the method opposite() works
     * correctly for an undirected graph.
     */
    @Test(timeout = 10000)
    public void testOpposite() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");

        CS16Edge<String> ab = _graph.insertEdge(B, A, 1); // outgoing
        CS16Edge<String> cb = _graph.insertEdge(C, B, 2); // incoming

        // assert that opposite() is functioning correctly
        assertThat(_graph.opposite(B,ab), is(A));
        assertThat(_graph.opposite(A,ab), is(B));
        assertThat(_graph.opposite(C,cb), is(B));
        assertThat(_graph.opposite(B,cb), is(C));
    }

    // same for directed graph
    @Test(timeout = 10000)
    public void testOppositeDirected() {
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");

        CS16Edge<String> ab = _dirGraph.insertEdge(B, A, 1); // outgoing
        CS16Edge<String> cb = _dirGraph.insertEdge(C, B, 2); // incoming

        // assert that opposite() is functioning correctly
        assertThat(_dirGraph.opposite(B,ab), is(A));
        assertThat(_dirGraph.opposite(A,ab), is(B));
        assertThat(_dirGraph.opposite(C,cb), is(B));
        assertThat(_dirGraph.opposite(B,cb), is(C));
    }

    /**
     * A simple test for checking if exception is raised
     * if null edge is passed into opposite() for undirected graph.
     */
    @Test(expected = InvalidEdgeException.class, timeout = 10000)
    public void testEndVerticesNullEdgeException() {

        _graph.endVertices(null);
    }

    /**
     * A simple test for checking if exception is raised
     * if null edge is passed into opposite() for undirected graph.
     */
    @Test(expected = InvalidEdgeException.class, timeout = 10000)
    public void testEndVerticesNullEdgeExceptionDirected() {

        _dirGraph.endVertices(null);
    }

    /**
     * A simple test for checking if exception is raised
     * if null vertex is passed into opposite() for undirected graph.
     */
    @Test(expected = InvalidVertexException.class, timeout = 10000)
    public void testAreAdjacentNullVertexException() {

        _graph.areAdjacent(null, null);
    }

    // same for directed
    @Test(expected = InvalidVertexException.class, timeout = 10000)
    public void testAreAdjacentNullVertexExceptionDirected() {

        _dirGraph.areAdjacent(null, null);
    }



    /**
     * A simple test if areAdjacent() works for an undirected graph.
     * Also briefly testing other very simple helper methods.
     */
    @Test(timeout = 10000)
    public void testAreAdjacent() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");

        // briefly testing getNumVertices
        assertEquals(3, _graph.getNumVertices());


        _graph.insertEdge(B, A, 1); // outgoing
        _graph.insertEdge(C, B, 2); // incoming

        // assert that areAdjacent() is functioning correctly
        assertTrue(_graph.areAdjacent(A,B));
        assertTrue(_graph.areAdjacent(B,A));
        assertTrue(_graph.areAdjacent(C,B));
        assertTrue(_graph.areAdjacent(B,C));

        // briefly testing clear()
        _graph.clear();
        assertEquals(0, _graph.getNumVertices());

    }

    // same for directed graph
    @Test(timeout = 10000)
    public void testAreAdjacentDirected() {
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");

        // briefly testing getNumVertices
        assertEquals(3, _dirGraph.getNumVertices());

        // adding in the edges
        _dirGraph.insertEdge(B, A, 1); // outgoing
        _dirGraph.insertEdge(C, B, 2); // incoming

        // assert that areAdjacent() is functioning correctly
        assertTrue(_dirGraph.areAdjacent(B,A));
        assertFalse(_dirGraph.areAdjacent(A,B));

        assertTrue(_dirGraph.areAdjacent(C,B));
        assertFalse(_dirGraph.areAdjacent(B,C));

        // briefly testing clear()
        _dirGraph.clear();
        assertEquals(0, _dirGraph.getNumVertices());

    }

    /**
     * A simple test checking if toggleDirected() actually changes an undirected
     * graph to a directed graph.
     */
    @Test(timeout = 10000)
    public void testToggleDirected() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");

        _graph.insertEdge(B, A, 1);

        assertTrue(_graph.areAdjacent(A,B)); // this is true for undirected graph

        _graph.toggleDirected(); // now switching to directed graph
        assertEquals(0, _graph.getNumVertices());

        CS16Vertex<String> newA = _graph.insertVertex("A");
        CS16Vertex<String> newB = _graph.insertVertex("B");

        _graph.insertEdge(newB, newA, 1);

        assertFalse(_graph.areAdjacent(newA,newB)); // this is false for directed graph
    }

    /*
     * List of graphs for testing!
     */
    @Parameters(name = "with graph: {0}")
    public static Collection<String> graphs() {
        List<String> names = new ArrayList<>();
        names.add("graph.AdjacencyMatrixGraph");
        return names;
    }
    
    /*
     * ####################################################
     * 
     * DO NOT MODIFY ANYTHING BELOW THIS LINE
     * 
     * ####################################################
     */
	
	
    @SuppressWarnings("unchecked")
    @Before
	public void makeGraph() {
        Class<?> graphClass = null;
        try {
            graphClass = Class.forName(_graphClassName);
            Constructor<?> constructor = graphClass.getConstructors()[0];
            _graph = (Graph<String>) constructor.newInstance(false);
	    _dirGraph = (Graph<String>) constructor.newInstance(true);
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | IllegalArgumentException e) {
            System.err.println("Exception while instantiating Graph class in GraphTest.");
            e.printStackTrace();
        }
	}
	
    public GraphTest(String graphClassName) {
	    this._graphClassName = graphClassName;
	}
}
