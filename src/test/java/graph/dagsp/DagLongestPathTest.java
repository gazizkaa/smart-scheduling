package graph.dagsp;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class DagLongestPathTest {

    @Test
    void testLongestPathAndReconstruction() {
        DagLongestPath dag = new DagLongestPath(6);

        // Graph edges with weights
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


        assertEquals(Integer.MIN_VALUE, dist[0]);
        assertEquals(0, dist[1]);
        assertEquals(2, dist[2]);
        assertEquals(9, dist[3]);   // updated expected value
        assertEquals(8, dist[4]);   // updated expected value
        assertEquals(10, dist[5]);  // updated expected value


        List<Integer> path = dag.reconstructPath(5);


        assertEquals(Arrays.asList(1, 2, 3, 5), path);
    }
}
