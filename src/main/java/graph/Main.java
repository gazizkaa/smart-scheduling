package graph;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import graph.scc.SCCFinder;
import graph.topo.TopoSorter;
import graph.dagsp.DagShortestPaths;
import graph.dagsp.DagLongestPath;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        String[] datasets = {
                "data/small_cycle1.json",
                "data/small_cycle2.json",
                "data/small_dag.json",
                "data/medium_mixed1.json",
                "data/medium_mixed2.json",
                "data/medium_dag.json",
                "data/large_dag1.json",
                "data/large_dag2.json",
                "data/large_dag3.json"
        };

        List<String[]> table = new ArrayList<>();
        table.add(new String[]{"Dataset", "Nodes", "Edges", "Type", "Time(ms)", "Metric"});

        for (String dataset : datasets) {
            JsonObject graphJson = JsonParser.parseReader(new FileReader(dataset)).getAsJsonObject();
            int n = graphJson.get("n").getAsInt();
            boolean directed = graphJson.get("directed").getAsBoolean();
            JsonArray edgesJson = graphJson.getAsJsonArray("edges");
            int source = graphJson.has("source") ? graphJson.get("source").getAsInt() : 0;

            Graph g = new Graph(n, directed);
            for (JsonElement e : edgesJson) {
                JsonObject obj = e.getAsJsonObject();
                int u = obj.get("u").getAsInt();
                int v = obj.get("v").getAsInt();
                int w = obj.get("w").getAsInt();
                g.addEdge(u, v, w);
            }

            long start = System.nanoTime();
            SCCFinder sccFinder = new SCCFinder(n);
            for (JsonElement e : edgesJson) {
                JsonObject obj = e.getAsJsonObject();
                sccFinder.addEdge(obj.get("u").getAsInt(), obj.get("v").getAsInt());
            }
            List<List<Integer>> sccList = sccFinder.findSCCs();
            long end = System.nanoTime();

            String type = sccList.size() == n ? "DAG" : "Cyclic";
            int metric = sccList.size();
            long timeMs = (end - start) / 1_000_000;

            table.add(new String[]{
                    dataset,
                    String.valueOf(n),
                    String.valueOf(edgesJson.size()),
                    type,
                    String.valueOf(timeMs),
                    String.valueOf(metric)
            });

            // Optional: run DAG-SP if DAG
            if (type.equals("DAG")) {
                DagShortestPaths dagSP = new DagShortestPaths(n);
                for (JsonElement e : edgesJson) {
                    JsonObject obj = e.getAsJsonObject();
                    dagSP.addEdge(obj.get("u").getAsInt(), obj.get("v").getAsInt(), obj.get("w").getAsInt());
                }
                dagSP.shortestPathsFrom(source);

                DagLongestPath dagLP = new DagLongestPath(n);
                for (JsonElement e : edgesJson) {
                    JsonObject obj = e.getAsJsonObject();
                    dagLP.addEdge(obj.get("u").getAsInt(), obj.get("v").getAsInt(), obj.get("w").getAsInt());
                }
                dagLP.longestPath(source);
            }
        }


        System.out.printf("%-25s %-6s %-6s %-10s %-10s %-6s%n", "Dataset", "Nodes", "Edges", "Type", "Time(ms)", "Metric");
        for (int i = 1; i < table.size(); i++) {
            String[] row = table.get(i);
            System.out.printf("%-25s %-6s %-6s %-10s %-10s %-6s%n",
                    row[0], row[1], row[2], row[3], row[4], row[5]);
        }


        try (PrintWriter pw = new PrintWriter("results.csv")) {
            for (String[] row : table) {
                pw.println(String.join(",", row));
            }
            System.out.println("\nSaved results to results.csv");
        }
    }
}
