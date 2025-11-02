package graph.data;

import graph.Graph;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GraphGenerator {

    public static void main(String[] args) throws IOException {
        generateAllDatasets();
    }

    private static void generateAllDatasets() throws IOException {
        // Small: 6-10 nodes
        generateGraph(6, 8, true, "data/small_cycle1.json");
        generateGraph(7, 6, true, "data/small_cycle2.json");
        generateGraph(8, 10, false, "data/small_dag.json");

        // Medium: 10-20 nodes
        generateGraph(12, 15, true, "data/medium_mixed1.json");
        generateGraph(15, 20, true, "data/medium_mixed2.json");
        generateGraph(18, 25, false, "data/medium_dag.json");

        // Large: 20-50 nodes
        generateGraph(25, 40, false, "data/large_dag1.json");
        generateGraph(35, 60, false, "data/large_dag2.json");
        generateGraph(45, 80, false, "data/large_dag3.json");
    }

    private static void generateGraph(int n, int m, boolean allowCycle, String filename) throws IOException {
        Random rand = new Random();
        Graph g = new Graph(n, true); // directed graph
        int edgesAdded = 0;

        while (edgesAdded < m) {
            int u = rand.nextInt(n);
            int v = rand.nextInt(n);
            if (u == v) continue; // avoid self-loops

            g.addEdge(u, v, rand.nextInt(10) + 1);
            edgesAdded++;
        }


        if (!allowCycle) {

        }


        int source = rand.nextInt(n);


        FileWriter writer = new FileWriter(filename);
        writer.write("{\n");
        writer.write("  \"directed\": true,\n");
        writer.write("  \"n\": " + n + ",\n");
        writer.write("  \"edges\": [\n");
        boolean first = true;
        for (int i = 0; i < n; i++) {
            for (Graph.Edge e : g.getNeighbors(i)) {
                if (!first) writer.write(",\n");
                writer.write(String.format("    {\"u\":%d,\"v\":%d,\"w\":%d}", i, e.to, e.weight));
                first = false;
            }
        }
        writer.write("\n  ],\n");
        writer.write("  \"source\": " + source + ",\n");
        writer.write("  \"weight_model\": \"edge\"\n");
        writer.write("}");
        writer.close();

        System.out.println("Generated " + filename + " with " + n + " nodes and " + m + " edges.");
    }
}
