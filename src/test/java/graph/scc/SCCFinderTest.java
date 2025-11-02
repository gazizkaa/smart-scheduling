package graph.scc;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SCCFinderTest {

    @Test
    void testSimpleGraph() {
        SCCFinder sccFinder = new SCCFinder(5);
        sccFinder.addEdge(0, 2);
        sccFinder.addEdge(2, 1);
        sccFinder.addEdge(1, 0);
        sccFinder.addEdge(0, 3);
        sccFinder.addEdge(3, 4);

        List<List<Integer>> components = sccFinder.findSCCs();


        assertEquals(3, components.size());
        assertTrue(components.stream().anyMatch(c -> c.containsAll(Arrays.asList(0, 1, 2))));
        assertTrue(components.stream().anyMatch(c -> c.equals(Collections.singletonList(3))));
        assertTrue(components.stream().anyMatch(c -> c.equals(Collections.singletonList(4))));
    }
}
