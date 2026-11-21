package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.graph.Graph;
import io.github.muhammedfidann06.dsa.graph.UnionFind;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphTest {

    /*
     * Ungerichteter Testgraph:
     *   0 — 1 — 2      5 — 6
     *   |       |
     *   3 ————— 4
     */
    private Graph sample() {
        Graph g = new Graph(7, false);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(0, 3);
        g.addEdge(2, 4);
        g.addEdge(3, 4);
        g.addEdge(5, 6);
        return g;
    }

    @Test
    void bfsZaehltKanten() {
        assertArrayEquals(new int[]{0, 1, 2, 1, 2, -1, -1}, sample().bfsDistances(0));
    }

    @Test
    void dfsBesuchtKleinereNachbarnZuerst() {
        assertEquals(List.of(0, 1, 2, 4, 3), sample().dfsOrder(0));
    }

    @Test
    void zusammenhangskomponenten() {
        assertEquals(2, sample().connectedComponents());
    }

    @Test
    void dijkstraFindetDenBilligstenWeg() {
        // Direkt 0->3 kostet 10, über 1 und 2 nur 1+2+3 = 6.
        Graph g = new Graph(5, true);
        g.addEdge(0, 3, 10);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 2);
        g.addEdge(2, 3, 3);
        Graph.ShortestPaths sp = g.dijkstra(0);
        assertEquals(6, sp.distance()[3]);
        assertEquals(List.of(0, 1, 2, 3), sp.pathTo(3).orElseThrow());
        assertTrue(sp.pathTo(4).isEmpty(), "Knoten 4 ist nicht erreichbar");
    }

    @Test
    void negativeGewichteSindVerboten() {
        assertThrows(IllegalArgumentException.class, () -> new Graph(2, true).addEdge(0, 1, -1));
    }

    @Test
    void topologischeSortierung() {
        // Kurse: 0 = Programmieren 1, 1 = Programmieren 2, 2 = Algorithmen, 3 = Mathe
        Graph g = new Graph(4, true);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(3, 2);
        List<Integer> order = g.topologicalOrder().orElseThrow();
        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(1) < order.indexOf(2));
        assertTrue(order.indexOf(3) < order.indexOf(2));

        g.addEdge(2, 0); // Zyklus
        assertTrue(g.topologicalOrder().isEmpty());
    }

    @Test
    void unionFind() {
        UnionFind uf = new UnionFind(6);
        assertTrue(uf.union(0, 1));
        assertTrue(uf.union(1, 2));
        assertFalse(uf.union(0, 2), "schon verbunden");
        uf.union(3, 4);
        assertTrue(uf.connected(0, 2));
        assertFalse(uf.connected(2, 3));
        assertEquals(3, uf.sets()); // {0,1,2} {3,4} {5}
    }
}
