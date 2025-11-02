package graph.dagsp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DagLongestPathTest {

    @Test
    void testSimpleDAG() {
        DagLongestPath dag = new DagLongestPath(6);
        dag.addEdge(0, 1, 5);
        dag.addEdge(0, 2, 3);
        dag.addEdge(1, 3, 6);
        dag.addEdge(1, 2, 2);
        dag.addEdge(2, 4, 4);
        dag.addEdge(2, 5, 2);
        dag.addEdge(2, 3, 7);
        dag.addEdge(3, 5, 1);
        dag.addEdge(3, 4, -1);
        dag.addEdge(4, 5, -2);

        int source = 1;
        int[] dist = dag.longestPath(source);

        // Expected longest distances from node 1
        int[] expected = {Integer.MIN_VALUE, 0, 2, 9, 8, 10};

        assertArrayEquals(expected, dist);
    }
}
