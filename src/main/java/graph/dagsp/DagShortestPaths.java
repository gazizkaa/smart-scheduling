package graph.dagsp;

import graph.topo.TopoSorter;

import java.util.*;

public class DagShortestPaths {

    private final int n;
    private final List<List<Edge>> adj;
    private int[] prev;

    // Metrics
    private int relaxations = 0;
    private long elapsedTimeNs = 0;

    public static class Edge {
        public final int to;
        public final int weight;
        public Edge(int to, int weight) { this.to = to; this.weight = weight; }
    }

    public DagShortestPaths(int n) {
        this.n = n;
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        prev = new int[n];
    }

    public void addEdge(int u, int v, int w) { adj.get(u).add(new Edge(v, w)); }

    public int[] shortestPathsFrom(int source) {
        long start = System.nanoTime();

        TopoSorter sorter = new TopoSorter(n);
        for (int u = 0; u < n; u++) for (Edge e : adj.get(u)) sorter.addEdge(u, e.to);
        List<Integer> order = sorter.topologicalSort();

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        prev = new int[n];
        Arrays.fill(prev, -1);

        for (int u : order) {
            if (dist[u] == Integer.MAX_VALUE) continue;
            for (Edge e : adj.get(u)) {
                if (dist[u] + e.weight < dist[e.to]) {
                    dist[e.to] = dist[u] + e.weight;
                    prev[e.to] = u;
                    relaxations++;
                }
            }
        }

        elapsedTimeNs = System.nanoTime() - start;
        return dist;
    }

    public List<Integer> reconstructPath(int target) {
        List<Integer> path = new ArrayList<>();
        if (prev[target] == -1) return path;
        for (int at = target; at != -1; at = prev[at]) path.add(at);
        Collections.reverse(path);
        return path;
    }

    // Metrics getters
    public int getRelaxations() { return relaxations; }
    public long getElapsedTimeNs() { return elapsedTimeNs; }
}
