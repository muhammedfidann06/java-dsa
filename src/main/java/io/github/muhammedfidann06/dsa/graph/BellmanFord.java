package io.github.muhammedfidann06.dsa.graph;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Bellman-Ford: kürzeste Wege auch mit NEGATIVEN Kantengewichten.
 *
 * <p>Dijkstra funktioniert nur mit Gewichten ≥ 0. Bellman-Ford "entspannt" einfach
 * (V−1)-mal alle Kanten. Das ist langsamer – O(V·E) –, aber er erkennt auch
 * negative Zyklen (dann gibt es keinen kürzesten Weg, man könnte unendlich sparen).
 *
 * <p>Beispiel: Währungen umtauschen – ein negativer Zyklus wäre ein Gewinn ohne Risiko (Arbitrage).
 */
public final class BellmanFord {

    /** Gerichtete Kante, das Gewicht darf negativ sein. */
    public record Edge(int from, int to, long weight) {
    }

    private BellmanFord() {
    }

    /**
     * @return Distanzen vom Start (Long.MAX_VALUE = nicht erreichbar),
     *         oder leer, wenn ein vom Start erreichbarer negativer Zyklus existiert
     */
    public static Optional<long[]> shortestPaths(int vertices, List<Edge> edges, int start) {
        long[] dist = new long[vertices];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[start] = 0;

        for (int round = 0; round < vertices - 1; round++) {
            boolean changed = false;
            for (Edge e : edges) {
                if (dist[e.from()] != Long.MAX_VALUE && dist[e.from()] + e.weight() < dist[e.to()]) {
                    dist[e.to()] = dist[e.from()] + e.weight();
                    changed = true;
                }
            }
            if (!changed) {
                break; // nichts mehr verbessert -> früher fertig
            }
        }
        // Noch eine Runde: Wenn sich jetzt noch etwas verbessert, gibt es einen negativen Zyklus.
        for (Edge e : edges) {
            if (dist[e.from()] != Long.MAX_VALUE && dist[e.from()] + e.weight() < dist[e.to()]) {
                return Optional.empty();
            }
        }
        return Optional.of(dist);
    }
}
