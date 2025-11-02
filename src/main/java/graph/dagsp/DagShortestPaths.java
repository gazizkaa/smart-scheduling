package graph.dagsp;

import graph.topo.TopoSorter;
import java.util.*;

public class DagShortestPaths {

    private final int n;
    private final List<List<Edge>> adj;

    static class Edge {
        int to, weight;
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public DagShortestPaths(int n) {
        this.n = n;
        this.adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new Edge(v, w));
    }

    public int[] shortestPathsFrom(int source) {
        TopoSorter sorter = new TopoSorter(n);
        for (int u = 0; u < n; u++) {
            for (Edge e : adj.get(u)) {
                sorter.addEdge(u, e.to);
            }
        }

        List<Integer> order = sorter.topologicalSort();


        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;


        for (int u : order) {
            if (dist[u] != Integer.MAX_VALUE) {
                for (Edge e : adj.get(u)) {
                    if (dist[e.to] > dist[u] + e.weight) {
                        dist[e.to] = dist[u] + e.weight;
                    }
                }
            }
        }

        return dist;
    }
}
