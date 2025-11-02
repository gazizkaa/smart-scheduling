package graph;

import graph.scc.SCCFinder;
import graph.topo.TopoSorter;
import graph.dagsp.DagShortestPaths;
import graph.dagsp.DagLongestPath;

import java.util.*;

public class Main {
    public static void main(String[] args) {


        Graph g = new Graph(6, true);
        g.addEdge(0, 1, 3);
        g.addEdge(0, 2, 6);
        g.addEdge(1, 2, 4);
        g.addEdge(1, 3, 4);
        g.addEdge(2, 3, 8);
        g.addEdge(2, 4, 11);
        g.addEdge(3, 4, -4);

        System.out.println("Original Graph:");
        g.printGraph();


        SCCFinder sccFinder = new SCCFinder(g.size());
        for (int u = 0; u < g.size(); u++) {
            for (Graph.Edge e : g.getNeighbors(u)) {
                sccFinder.addEdge(u, e.to);
            }
        }

        List<List<Integer>> sccs = sccFinder.findSCCs();
        System.out.println("\nStrongly Connected Components:");
        for (List<Integer> comp : sccs) {
            System.out.println(comp);
        }


        List<List<Integer>> condensation = sccFinder.buildCondensation(sccs);
        System.out.println("\nCondensation DAG:");
        for (int i = 0; i < condensation.size(); i++) {
            System.out.println("SCC " + i + " -> " + condensation.get(i));
        }


        TopoSorter sorter = new TopoSorter(condensation.size());
        for (int i = 0; i < condensation.size(); i++) {
            for (int j : condensation.get(i)) {
                sorter.addEdge(i, j);
            }
        }
        List<Integer> topoOrder = sorter.topologicalSort();
        System.out.println("\nTopological Order of SCCs: " + topoOrder);


        DagShortestPaths dagSP = new DagShortestPaths(condensation.size());
        DagLongestPath dagLP = new DagLongestPath(condensation.size());


        for (int u = 0; u < condensation.size(); u++) {
            for (int v : condensation.get(u)) {
                dagSP.addEdge(u, v, 1);
                dagLP.addEdge(u, v, 1);
            }
        }

        int source = 0;
        int[] shortest = dagSP.shortestPathsFrom(source);
        int[] longest = dagLP.longestPath(source);

        System.out.println("\nShortest paths from SCC " + source + ": " + Arrays.toString(shortest));
        System.out.println("Longest paths from SCC " + source + ": " + Arrays.toString(longest));

        System.out.println("Reconstructed path to last SCC " + (condensation.size()-1) + ": " + dagSP.reconstructPath(condensation.size()-1));
    }
}
