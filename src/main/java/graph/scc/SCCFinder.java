package graph.scc;

import java.util.*;

public class SCCFinder {

    private final int n;
    private final List<List<Integer>> adj;

    // Metrics
    private int dfsVisits = 0;
    private int dfsEdges = 0;
    private long elapsedTimeNs = 0;

    public SCCFinder(int n) {
        this.n = n;
        this.adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
    }

    public List<List<Integer>> findSCCs() {
        long start = System.nanoTime();

        boolean[] visited = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (!visited[i]) dfs1(i, visited, stack);
        }

        List<List<Integer>> transposed = transpose();
        Arrays.fill(visited, false);
        List<List<Integer>> sccList = new ArrayList<>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited[v]) {
                List<Integer> component = new ArrayList<>();
                dfs2(v, visited, transposed, component);
                sccList.add(component);
            }
        }

        elapsedTimeNs = System.nanoTime() - start;
        return sccList;
    }

    private void dfs1(int v, boolean[] visited, Deque<Integer> stack) {
        visited[v] = true;
        dfsVisits++;
        for (int nei : adj.get(v)) {
            dfsEdges++;
            if (!visited[nei]) dfs1(nei, visited, stack);
        }
        stack.push(v);
    }

    private void dfs2(int v, boolean[] visited, List<List<Integer>> transposed, List<Integer> component) {
        visited[v] = true;
        dfsVisits++;
        component.add(v);
        for (int nei : transposed.get(v)) {
            dfsEdges++;
            if (!visited[nei]) dfs2(nei, visited, transposed, component);
        }
    }

    private List<List<Integer>> transpose() {
        List<List<Integer>> rev = new ArrayList<>();
        for (int i = 0; i < n; i++) rev.add(new ArrayList<>());
        for (int u = 0; u < n; u++) for (int v : adj.get(u)) rev.get(v).add(u);
        return rev;
    }

    public List<List<Integer>> buildCondensation(List<List<Integer>> sccList) {
        int[] nodeToSCC = new int[n];
        for (int i = 0; i < sccList.size(); i++) {
            for (int v : sccList.get(i)) nodeToSCC[v] = i;
        }

        List<Set<Integer>> dag = new ArrayList<>();
        for (int i = 0; i < sccList.size(); i++) dag.add(new HashSet<>());

        for (int u = 0; u < n; u++)
            for (int v : adj.get(u)) {
                int sccU = nodeToSCC[u];
                int sccV = nodeToSCC[v];
                if (sccU != sccV) dag.get(sccU).add(sccV);
            }

        List<List<Integer>> condensation = new ArrayList<>();
        for (Set<Integer> neighbors : dag) condensation.add(new ArrayList<>(neighbors));
        return condensation;
    }


    public int getDfsVisits() { return dfsVisits; }
    public int getDfsEdges() { return dfsEdges; }
    public long getElapsedTimeNs() { return elapsedTimeNs; }
}
