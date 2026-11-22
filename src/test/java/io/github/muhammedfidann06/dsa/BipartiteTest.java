package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.graph.Bipartite;
import io.github.muhammedfidann06.dsa.graph.Graph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BipartiteTest {

    private static Graph cycle(int n) {
        Graph g = new Graph(n, false);
        for (int i = 0; i < n; i++) {
            g.addEdge(i, (i + 1) % n);
        }
        return g;
    }

    @Test
    void geraderKreisIstBipartit() {
        Graph g = cycle(6);
        int[] color = Bipartite.coloring(g).orElseThrow();
        // Jede Kante verbindet zwei verschiedene Farben
        for (int v = 0; v < g.vertices(); v++) {
            for (Graph.Edge e : g.neighbours(v)) {
                assertNotEquals(color[v], color[e.to()]);
            }
        }
    }

    @Test
    void ungeraderKreisIstNichtBipartit() {
        assertFalse(Bipartite.isBipartite(cycle(5)));
        assertFalse(Bipartite.isBipartite(cycle(3)));
    }

    @Test
    void mehrereKomponenten() {
        // Komponente 1: Kante 0-1 (bipartit), Komponente 2: Dreieck 2-3-4 (nicht bipartit)
        Graph g = new Graph(5, false);
        g.addEdge(0, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 2);
        assertFalse(Bipartite.isBipartite(g));
        assertTrue(Bipartite.isBipartite(new Graph(3, false)));
    }
}
