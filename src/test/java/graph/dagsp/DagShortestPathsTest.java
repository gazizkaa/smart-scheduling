package graph.dagsp;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DagShortestPathsTest {

    @Test
    public void testShortestPathsAndReconstruction() {
        int n = 5;
        DagShortestPaths dag = new DagShortestPaths(n);

        // Graph edges: (from, to, weight)
        dag.addEdge(0, 1, 3);
        dag.addEdge(0, 2, 6);
        dag.addEdge(1, 2, 4);
        dag.addEdge(1, 3, 4);
        dag.addEdge(2, 3, 8);
        dag.addEdge(2, 4, 11);
        dag.addEdge(3, 4, -4);

        // Compute shortest paths from source 0
        int[] dist = dag.shortestPathsFrom(0);

        // Expected distances from 0: 0,3,7,7,3+4+(-4)? Actually compute step by step:
        // 0->1 = 3
        // 0->2 = min(6, 3+4=7) => 6
        // 0->3 = min(∞, 3+4=7, 6+8=14) => 7
        // 0->4 = min(∞, 7+(-4)=3, 6+11=17) => 3
        assertEquals(0, dist[0]);
        assertEquals(3, dist[1]);
        assertEquals(6, dist[2]);
        assertEquals(7, dist[3]);
        assertEquals(3, dist[4]);

        // Test path reconstruction to node 4
        List<Integer> path = dag.reconstructPath(4);
        // The shortest path from 0 -> 4 is 0 -> 1 -> 3 -> 4
        assertEquals(List.of(0, 1, 3, 4), path);
    }
}
