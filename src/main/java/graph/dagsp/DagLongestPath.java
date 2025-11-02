package graph.dagsp;

import java.util.*;

public class DagLongestPath {

    public static class Edge {
        public int to, weight;
        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    private final int n;
    private final List<List<Edge>> adj;
    private int[] prev; // store previous nodes for path reconstruction

    public DagLongestPath(int n) {
        this.n = n;
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new Edge(v, w));
    }

    public int[] longestPath(int source) {
        boolean[] visited = new boolean[n];
        Deque<Integer> topo = new ArrayDeque<>();

        // Build topological order
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, visited, topo);
            }
        }

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MIN_VALUE);
        dist[source] = 0;

        prev = new int[n];
        Arrays.fill(prev, -1);

        while (!topo.isEmpty()) {
            int u = topo.pop();
            if (dist[u] != Integer.MIN_VALUE) {
                for (Edge e : adj.get(u)) {
                    if (dist[e.to] < dist[u] + e.weight) {
                        dist[e.to] = dist[u] + e.weight;
                        prev[e.to] = u;
                    }
                }
            }
        }

        return dist;
    }

    public List<Integer> reconstructPath(int target) {
        List<Integer> path = new ArrayList<>();
        for (int at = target; at != -1; at = prev[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    private void dfs(int u, boolean[] visited, Deque<Integer> topo) {
        visited[u] = true;
        for (Edge e : adj.get(u)) {
            if (!visited[e.to]) {
                dfs(e.to, visited, topo);
            }
        }
        topo.push(u);
    }
}
