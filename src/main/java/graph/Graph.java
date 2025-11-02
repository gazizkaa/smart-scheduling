package graph;

import java.util.*;

public class Graph {

    public static class Edge {
        public int to, weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    private final int n; // number of nodes
    private final boolean directed;
    private final List<List<Edge>> adj;

    public Graph(int n, boolean directed) {
        this.n = n;
        this.directed = directed;
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
    }


    public void addEdge(int u, int v, int weight) {
        adj.get(u).add(new Edge(v, weight));
        if (!directed) {
            adj.get(v).add(new Edge(u, weight));
        }
    }

    public List<Edge> getNeighbors(int u) {
        return Collections.unmodifiableList(adj.get(u));
    }

    public int size() {
        return n;
    }

    public boolean isDirected() {
        return directed;
    }


    public void printGraph() {
        for (int i = 0; i < n; i++) {
            System.out.print(i + " -> ");
            for (Edge e : adj.get(i)) {
                System.out.print("(" + e.to + ", " + e.weight + ") ");
            }
            System.out.println();
        }
    }
}
