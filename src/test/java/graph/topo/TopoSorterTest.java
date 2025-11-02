package graph.topo;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TopoSorterTest {

    @Test
    void testSimpleDAG() {
        TopoSorter sorter = new TopoSorter(5);
        sorter.addEdge(0, 1);
        sorter.addEdge(0, 2);
        sorter.addEdge(1, 3);
        sorter.addEdge(2, 3);
        sorter.addEdge(3, 4);

        List<Integer> order = sorter.topologicalSort();
        System.out.println("Topological order: " + order);
        assertEquals(5, order.size());
        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(0) < order.indexOf(2));
        assertTrue(order.indexOf(1) < order.indexOf(3));
        assertTrue(order.indexOf(2) < order.indexOf(3));
        assertTrue(order.indexOf(3) < order.indexOf(4));
    }
}
