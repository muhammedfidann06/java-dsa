package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.graph.Graph;
import io.github.muhammedfidann06.dsa.graph.StronglyConnected;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StronglyConnectedTest {

    private static Set<Set<Integer>> asSets(List<List<Integer>> comps) {
        return comps.stream().map(c -> (Set<Integer>) new TreeSet<>(c)).collect(Collectors.toSet());
    }

    @Test
    void klassischesBeispiel() {
        // 0->1->2->0 ist ein Zyklus, 3->4->3 auch, 5 ist allein
        Graph g = new Graph(6, true);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 3);
        g.addEdge(4, 5);
        Set<Set<Integer>> expected = Set.of(Set.of(0, 1, 2), Set.of(3, 4), Set.of(5));
        assertEquals(expected, asSets(StronglyConnected.components(g)));
    }

    @Test
    void ohneKantenIstJederKnotenAllein() {
        assertEquals(4, StronglyConnected.components(new Graph(4, true)).size());
    }

    @Test
    void langeKetteOhneStackOverflow() {
        // 100 000 Knoten in einem großen Kreis -> eine einzige Komponente
        int n = 100_000;
        Graph g = new Graph(n, true);
        for (int i = 0; i < n; i++) {
            g.addEdge(i, (i + 1) % n);
        }
        List<List<Integer>> comps = StronglyConnected.components(g);
        assertEquals(1, comps.size());
        assertEquals(n, new HashSet<>(comps.get(0)).size());
    }
}
