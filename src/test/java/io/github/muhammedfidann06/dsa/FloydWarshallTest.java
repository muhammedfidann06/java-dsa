package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.graph.FloydWarshall;
import io.github.muhammedfidann06.dsa.graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.SplittableRandom;

import static io.github.muhammedfidann06.dsa.graph.FloydWarshall.INF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FloydWarshallTest {

    @Test
    void kleinesBeispielMitNegativerKante() {
        long[][] w = {
                {0, 3, INF, 7},
                {8, 0, 2, INF},
                {5, INF, 0, 1},
                {2, INF, -1, 0},
        };
        long[][] d = FloydWarshall.allPairs(w);
        assertEquals(5, d[0][2]);  // 0 -> 1 -> 2
        assertEquals(6, d[0][3]);  // 0 -> 1 -> 2 -> 3
        assertEquals(-1, d[3][2]); // negative Kante direkt
        assertEquals(5, d[3][1]);  // 3 -> 0 -> 1 = 2 + 3
        assertEquals(3, w[0][1], "Eingabe bleibt unverändert");
    }

    @Test
    void gleichesErgebnisWieDijkstra() {
        SplittableRandom r = new SplittableRandom(40);
        int n = 25;
        long[][] w = new long[n][n];
        for (long[] row : w) {
            Arrays.fill(row, INF);
        }
        Graph g = new Graph(n, true);
        for (int e = 0; e < 90; e++) {
            int a = r.nextInt(n);
            int b = r.nextInt(n);
            int c = r.nextInt(1, 40);
            g.addEdge(a, b, c);
            w[a][b] = Math.min(w[a][b], c);
        }
        long[][] d = FloydWarshall.allPairs(w);
        for (int s = 0; s < n; s++) {
            long[] dij = g.dijkstra(s).distance();
            for (int t = 0; t < n; t++) {
                long expected = dij[t] == Long.MAX_VALUE ? INF : dij[t];
                assertEquals(expected, d[s][t], s + " -> " + t);
            }
        }
    }

    @Test
    void negativerZyklus() {
        long[][] w = {{0, 1}, {-3, 0}};
        assertThrows(IllegalArgumentException.class, () -> FloydWarshall.allPairs(w));
    }
}
