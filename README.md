# Graph

Welcome to the Graph project! This project focuses on implementing efficient algorithms for graph manipulation and analysis. It boasts two main algorithms: the Prim-Jarnik algorithm and the Page Rank algorithm, both designed to handle various types of graphs and produce accurate results.

## Design Choices:

For the prim-jarnik algorithm, I used several new instances of the myDecorator class.
I stored all cost integers in costDec, boolean variables dictating whether a node
was visited in visitedDec, previous nodes in prevDec and entries in entryDec.
These decorations made it much easier to store a lot of information without using
messy data structures.

For the page rank algorithm, I handled sinks by adding edges from the sink to all other nodes
and itself. I didn't use decorations for page rank as I used arrays instead (for current and
prev rank).

## Testing:
The JUnit tests in GraphTest check that all the methods work and also check for any exceptions
that must be raised. All methods that work for the demo also work when I run my app.

The JUnit tests in MsfTest check if the Prim Jarnik algorithm works on an empty graph, graph
with 1, 2, and more vertices. All graphs tested were undirected. The prim jarnik algorithm
works the same for me as the demo.

The JUnit tests in MsfTest check if the Page Rank algorithm works on an empty graph, graph
with 1, 2 vertices, graphs with sinks, graphs with edge-less vertices and more.
All graphs tested were directed. The page rank algorithm works the same for me as the demo,
including when loading "olympic_links".

To ensure a page has the lowest ranking of any vertex, steps can be taken to remove
all of its incoming edges (or simply treat its number of incoming edges as 0). Hence,
make the number of incoming edges to be 0 for the blacklisted page.

This way its page rank would be equal to only (1-d)/N and not be equal to that plus
the sum of the amounts of page rank from other pages. It still could possibly get
some additional pageRank if there is a sink (that connects to it with an edge).
However, it will still have the lowest ranking as the sink will connect to all the other
pages too (and also to the sink itself).
