package graph.tests;

import graph.scc.SCCFinder;
import graph.topo.TopoSorter;
import graph.dagsp.DagShortestPaths;
import graph.dagsp.DagLongestPath;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ExtraGraphTests {


    @Test
    public void testEmptyGraphSCC() {
        SCCFinder scc = new SCCFinder(0);
        List<List<Integer>> components = scc.findSCCs();
        assertTrue(components.isEmpty());
    }

    @Test
    public void testSingleNodeSCC() {
        SCCFinder scc = new SCCFinder(1);
        List<List<Integer>> components = scc.findSCCs();
        assertEquals(1, components.size());
        assertEquals(List.of(0), components.get(0));
    }

    @Test
    public void testAllNodesOneSCC() {
        SCCFinder scc = new SCCFinder(3);
        scc.addEdge(0, 1);
        scc.addEdge(1, 2);
        scc.addEdge(2, 0);
        List<List<Integer>> components = scc.findSCCs();
        assertEquals(1, components.size());
        assertTrue(components.get(0).containsAll(Arrays.asList(0, 1, 2)));
    }


    @Test
    public void testEmptyGraphTopo() {
        TopoSorter sorter = new TopoSorter(0);
        List<Integer> order = sorter.topologicalSort();
        assertTrue(order.isEmpty());
    }

    @Test
    public void testSingleNodeTopo() {
        TopoSorter sorter = new TopoSorter(1);
        List<Integer> order = sorter.topologicalSort();
        assertEquals(List.of(0), order);
    }

    @Test
    public void testCycleDetection() {
        TopoSorter sorter = new TopoSorter(2);
        sorter.addEdge(0, 1);
        sorter.addEdge(1, 0);
        assertThrows(IllegalStateException.class, sorter::topologicalSort);
    }


    @Test
    public void testSingleNodeShortestPath() {
        DagShortestPaths dag = new DagShortestPaths(1);
        int[] dist = dag.shortestPathsFrom(0);
        assertEquals(0, dist[0]);
    }

    @Test
    public void testEmptyGraphShortestPath() {
        DagShortestPaths dag = new DagShortestPaths(0);
        int[] dist = dag.shortestPathsFrom(0); // should handle gracefully
        assertEquals(0, dist.length);
    }


    @Test
    public void testSingleNodeLongestPath() {
        DagLongestPath dag = new DagLongestPath(1);
        int[] dist = dag.longestPath(0);
        assertEquals(0, dist[0]);
    }

    @Test
    public void testEmptyGraphLongestPath() {
        DagLongestPath dag = new DagLongestPath(0);
        int[] dist = dag.longestPath(0); // should handle gracefully
        assertEquals(0, dist.length);
    }


    @Test
    public void testLargeRandomDAG() {
        int n = 50;
        DagShortestPaths dagSP = new DagShortestPaths(n);
        DagLongestPath dagLP = new DagLongestPath(n);
        Random rand = new Random(42);

        // Generate a random DAG (edges only from lower to higher index)
        for (int u = 0; u < n; u++) {
            int edges = rand.nextInt(3); // up to 2 edges per node
            for (int i = 0; i < edges; i++) {
                int v = u + 1 + rand.nextInt(n - u - 1);
                int w = rand.nextInt(10) + 1;
                dagSP.addEdge(u, v, w);
                dagLP.addEdge(u, v, w);
            }
        }

        int[] distSP = dagSP.shortestPathsFrom(0);
        int[] distLP = dagLP.longestPath(0);


        assertEquals(0, distSP[0]);
        assertEquals(0, distLP[0]);
    }
}
