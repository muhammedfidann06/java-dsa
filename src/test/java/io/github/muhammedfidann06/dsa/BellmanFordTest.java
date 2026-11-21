package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.graph.BellmanFord;
import io.github.muhammedfidann06.dsa.graph.BellmanFord.Edge;
import io.github.muhammedfidann06.dsa.graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BellmanFordTest {

    @Test
    void negativeKanteOhneZyklus() {
        // 0 -> 1 kostet 4, aber 0 -> 2 -> 1 kostet 5 + (−3) = 2
        List<Edge> edges = List.of(new Edge(0, 1, 4), new Edge(0, 2, 5), new Edge(2, 1, -3), new Edge(1, 3, 1));
        long[] d = BellmanFord.shortestPaths(5, edges, 0).orElseThrow();
        assertEquals(2, d[1]);
        assertEquals(3, d[3]);
        assertEquals(Long.MAX_VALUE, d[4], "Knoten 4 ist nicht erreichbar");
    }

    @Test
    void negativerZyklusWirdErkannt() {
        List<Edge> edges = List.of(new Edge(0, 1, 1), new Edge(1, 2, -2), new Edge(2, 1, 1));
        assertTrue(BellmanFord.shortestPaths(3, edges, 0).isEmpty());
    }

    @Test
    void gleichesErgebnisWieDijkstraBeiPositivenGewichten() {
        SplittableRandom r = new SplittableRandom(12);
        int n = 30;
        Graph g = new Graph(n, true);
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            int a = r.nextInt(n);
            int b = r.nextInt(n);
            int w = r.nextInt(1, 50);
            g.addEdge(a, b, w);
            edges.add(new Edge(a, b, w));
        }
        assertArrayEquals(g.dijkstra(0).distance(), BellmanFord.shortestPaths(n, edges, 0).orElseThrow());
    }
}
