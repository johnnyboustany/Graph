package graph;

import java.util.*;

import net.datastructures.Entry;
import support.graph.CS16AdaptableHeapPriorityQueue;
import support.graph.CS16Edge;
import support.graph.CS16GraphVisualizer;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.MinSpanForest;

/**
 * In this class you will implement a slightly modified version
 * of the Prim-Jarnik algorithm for generating Minimum Spanning trees.
 * The original version of this algorithm will only generate the 
 * minimum spanning tree of the connected vertices in a graph, given
 * a starting vertex. Like Kruskal's, this algorithm can be modified to 
 * produce a minimum spanning forest with very little effort.
 *
 * See the handout for details on Prim-Jarnik's algorithm.
 * Like Kruskal's algorithm this algorithm makes extensive use of 
 * the decorator pattern, so make sure you know it.
 */
public class MyPrimJarnik<V> implements MinSpanForest<V> {

    /**
     * This method implements Prim-Jarnik's algorithm and extends 
     * it slightly to account for disconnected graphs. You must return 
     * the collection of edges of the Minimum Spanning Forest (MSF) for 
     * the given graph, g.
     * 
     * This algorithm must run in O((|E| + |V|)log(|V|)) time
     * @param g Your graph
     * @param visualizer Only used if you implement the optional animation.
     * @return returns a data structure that contains the edges of your MSF that implements java.util.Collection
     */
    @Override
    public Collection<CS16Edge<V>> genMinSpanForest(Graph<V> g, CS16GraphVisualizer<V> visualizer) {

        // decorators
        MyDecorator<CS16Vertex<V>,Integer> costDec = new MyDecorator<>();
        MyDecorator<CS16Vertex<V>,CS16Vertex<V>> prevDec = new MyDecorator<>();
        MyDecorator<CS16Vertex<V>, Boolean> visitedDec = new MyDecorator<>();
        MyDecorator<CS16Vertex<V>,  Entry<Integer,CS16Vertex<V>>> entryDec = new MyDecorator<>();

        // PQ
        CS16AdaptableHeapPriorityQueue<Integer, CS16Vertex<V>> priorityQ = new CS16AdaptableHeapPriorityQueue<>();

        Iterator<CS16Vertex<V>> it = g.vertices();
        int counter = 0;

        while(it.hasNext()){
            CS16Vertex<V> vertex = it.next();

            int priorityKey;

            // start node gets a cost of 0
            if(counter==0){
                priorityKey = 0;
                counter = 1;

                // other nodes start with a cost of infinity
            } else{
                priorityKey = Integer.MAX_VALUE;
            }

            costDec.setDecoration(vertex, priorityKey);

            // prev is null for all nodes at first
            prevDec.setDecoration(vertex, null);

            // insert the vertex into a priority queue with the key above
            // store the entry as a decoration for later use
            Entry<Integer,CS16Vertex<V>> entry = priorityQ.insert(priorityKey, vertex);
            entryDec.setDecoration(vertex, entry);

            // all nodes are unvisited at first
            visitedDec.setDecoration(vertex, false);
        }

        // using a hashset for the MSF
        Collection<CS16Edge<V>> MSF = new HashSet<>();

        while(!priorityQ.isEmpty()) {

            //  Remove the minimum vertex from the priority queue
            CS16Vertex<V> currV = priorityQ.removeMin().getValue();

            // nodes are visited once they are removed from the queue
            visitedDec.setDecoration(currV, true);


            // previous node of currV
            CS16Vertex<V> prevV = prevDec.getDecoration(currV);

            // if currV's prev node isn't null, then...
            if (prevV != null) {

                // ...add the edge that most recently updated the vertex to the MSF
                MSF.add(g.connectingEdge(currV, prevV));
            }

            // checking through all of currV’s incident edges e...

            Iterator<CS16Edge<V>> iterator = g.incomingEdges(currV);
            while (iterator.hasNext()) {

                CS16Edge<V> edge = iterator.next();

                // ...whose opposite vertex remains unvisited
                CS16Vertex<V> oppV = g.opposite(currV,edge);
                if(!visitedDec.getDecoration(oppV)){

                    int edgeWeight = edge.element();

                    // if the cost of the opposite vertex is greater than that of the edge..
                    if(costDec.getDecoration(oppV) > edgeWeight){

                        // ...then, update cost and prev of the opposite vertex...
                        costDec.setDecoration(oppV, edgeWeight);
                        prevDec.setDecoration(oppV, currV);

                        // ... and decrease the key of the opposite vertex in the priority queue
                        Entry<Integer,CS16Vertex<V>> entry = entryDec.getDecoration(oppV);
                        priorityQ.replaceKey(entry, edgeWeight);

                    }
                }
            }
        }

        return MSF;
    }
}
