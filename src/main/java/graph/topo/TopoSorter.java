package graph.topo;

import java.util.*;

public class TopoSorter {
    private final int n;
    private final List<List<Integer>> adj;

    private int pushCount = 0;
    private int popCount = 0;
    private long elapsedTimeNs = 0;

    public TopoSorter(int n) {
        this.n = n;
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
    }

    public List<Integer> topologicalSort() {
        long start = System.nanoTime();

        boolean[] visited = new boolean[n];
        boolean[] onStack = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (!visited[i]) dfs(i, visited, onStack, stack);

        List<Integer> order = new ArrayList<>();
        while (!stack.isEmpty()) {
            order.add(stack.pop());
            popCount++;
        }

        elapsedTimeNs = System.nanoTime() - start;
        return order;
    }

    private void dfs(int v, boolean[] visited, boolean[] onStack, Deque<Integer> stack) {
        visited[v] = true;
        onStack[v] = true;

        for (int nei : adj.get(v)) {
            if (!visited[nei]) dfs(nei, visited, onStack, stack);
            else if (onStack[nei]) throw new IllegalStateException("Cycle detected—not a DAG!");
        }

        onStack[v] = false;
        stack.push(v);
        pushCount++;
    }

    // Metrics getters
    public int getPushCount() { return pushCount; }
    public int getPopCount() { return popCount; }
    public long getElapsedTimeNs() { return elapsedTimeNs; }
}
