package graph.dagsp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DagShortestPathsTest {

    @Test
    void testSimpleDAG() {
        DagShortestPaths dag = new DagShortestPaths(6);
        dag.addEdge(0, 1, 5);
        dag.addEdge(0, 2, 3);
        dag.addEdge(1, 3, 6);
        dag.addEdge(1, 2, 2);
        dag.addEdge(2, 4, 4);
        dag.addEdge(2, 5, 2);
        dag.addEdge(2, 3, 7);
        dag.addEdge(3, 4, -1);
        dag.addEdge(4, 5, -2);

        int[] dist = dag.shortestPathsFrom(1);


        assertEquals(0, dist[1]);
        assertEquals(2, dist[2]);
        assertEquals(6, dist[3]);
        assertEquals(5, dist[4]);
        assertEquals(3, dist[5]);
    }
}
