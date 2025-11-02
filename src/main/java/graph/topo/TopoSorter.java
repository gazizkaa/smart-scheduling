package graph.topo;

import java.util.*;

public class TopoSorter {
    private final int n;
    private final List<List<Integer>> adj;

    public TopoSorter(int n) {
        this.n = n;
        this.adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
    }

    public List<Integer> topologicalSort() {
        boolean[] visited = new boolean[n];
        boolean[] onStack = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, visited, onStack, stack);
            }
        }


        return new ArrayList<>(stack);
    }

    private void dfs(int v, boolean[] visited, boolean[] onStack, Deque<Integer> stack) {
        visited[v] = true;
        onStack[v] = true;

        for (int nei : adj.get(v)) {
            if (!visited[nei]) {
                dfs(nei, visited, onStack, stack);
            } else if (onStack[nei]) {
                throw new IllegalStateException("Cycle detected—not a DAG!");
            }
        }

        onStack[v] = false;
        stack.push(v);
    }
}
