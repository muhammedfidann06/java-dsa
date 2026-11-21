package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.graph.Kruskal;
import io.github.muhammedfidann06.dsa.graph.Kruskal.WeightedEdge;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KruskalTest {

    @Test
    void klassischesBeispiel() {
        // 4 Orte, Kosten für jede mögliche Leitung
        List<WeightedEdge> edges = List.of(
                new WeightedEdge(0, 1, 10),
                new WeightedEdge(0, 2, 6),
                new WeightedEdge(0, 3, 5),
                new WeightedEdge(1, 3, 15),
                new WeightedEdge(2, 3, 4));
        Kruskal.SpanningTree t = Kruskal.minimumSpanningTree(4, edges);
        assertEquals(19, t.totalWeight()); // 4 + 5 + 10
        assertEquals(3, t.edges().size());
    }

    @Test
    void nichtZusammenhaengendGibtWald() {
        List<WeightedEdge> edges = List.of(new WeightedEdge(0, 1, 1), new WeightedEdge(2, 3, 2));
        Kruskal.SpanningTree t = Kruskal.minimumSpanningTree(4, edges);
        assertEquals(2, t.edges().size());
        assertEquals(3, t.totalWeight());
    }
}
