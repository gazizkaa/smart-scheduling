package graph;

import java.util.*;

public class Graph {
    private final int vertices;
    private final Map<Integer, List<Integer>> adjList = new HashMap<>();

    public Graph(int vertices) {
        this.vertices = vertices;
        for (int i = 0; i < vertices; i++) {
            adjList.put(i, new ArrayList<>());
        }
    }

    public void addEdge(int from, int to) {
        adjList.get(from).add(to);
    }

    public List<Integer> getNeighbors(int v) {
        return adjList.getOrDefault(v, Collections.emptyList());
    }

    public int getVertexCount() {
        return vertices;
    }

    public Map<Integer, List<Integer>> getAdjList() {
        return adjList;
    }
}
