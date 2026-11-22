package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.graph.Graph;
import io.github.muhammedfidann06.dsa.graph.Kruskal;
import io.github.muhammedfidann06.dsa.graph.Prim;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PrimTest {

    @Test
    void gleichesGewichtWieKruskal() {
        SplittableRandom r = new SplittableRandom(70);
        for (int t = 0; t < 30; t++) {
            int n = 2 + r.nextInt(30);
            Graph g = new Graph(n, false);
            List<Kruskal.WeightedEdge> edges = new ArrayList<>();
            // Zuerst eine Kette, damit der Graph sicher zusammenhängt
            for (int i = 1; i < n; i++) {
                int w = r.nextInt(1, 100);
                g.addEdge(i - 1, i, w);
                edges.add(new Kruskal.WeightedEdge(i - 1, i, w));
            }
            for (int e = 0; e < 2 * n; e++) {
                int a = r.nextInt(n);
                int b = r.nextInt(n);
                int w = r.nextInt(1, 100);
                g.addEdge(a, b, w);
                edges.add(new Kruskal.WeightedEdge(a, b, w));
            }
            assertEquals(Kruskal.minimumSpanningTree(n, edges).totalWeight(), Prim.minimumSpanningTreeWeight(g));
        }
    }

    @Test
    void nichtZusammenhaengend() {
        Graph g = new Graph(3, false);
        g.addEdge(0, 1, 5);
        assertThrows(IllegalArgumentException.class, () -> Prim.minimumSpanningTreeWeight(g));
    }

    @Test
    void einKnoten() {
        assertEquals(0, Prim.minimumSpanningTreeWeight(new Graph(1, false)));
    }
}
