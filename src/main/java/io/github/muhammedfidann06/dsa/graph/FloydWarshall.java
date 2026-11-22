package io.github.muhammedfidann06.dsa.graph;

import java.util.Arrays;

/**
 * Floyd-Warshall: kürzeste Wege zwischen ALLEN Knotenpaaren. O(V³).
 *
 * <p>Idee: dist[i][j] wird Schritt für Schritt besser, wenn wir Knoten k als
 * "Zwischenstation" erlauben: dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j]).
 * Gut für kleine, dichte Graphen (z. B. Entfernungstabelle zwischen Städten).
 */
public final class FloydWarshall {

    /** "Unendlich" – groß, aber ohne Überlauf beim Addieren. */
    public static final long INF = Long.MAX_VALUE / 4;

    private FloydWarshall() {
    }

    /**
     * @param weights Matrix mit Kantengewichten, INF = keine Kante. Wird nicht verändert.
     * @return Matrix der kürzesten Distanzen
     * @throws IllegalArgumentException bei einem negativen Zyklus
     */
    public static long[][] allPairs(long[][] weights) {
        int n = weights.length;
        long[][] d = new long[n][];
        for (int i = 0; i < n; i++) {
            d[i] = Arrays.copyOf(weights[i], n);
            d[i][i] = Math.min(d[i][i], 0); // von sich selbst: 0
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                if (d[i][k] == INF) {
                    continue; // über k geht es von i aus nicht weiter
                }
                for (int j = 0; j < n; j++) {
                    if (d[k][j] != INF && d[i][k] + d[k][j] < d[i][j]) {
                        d[i][j] = d[i][k] + d[k][j];
                    }
                }
            }
        }
        // Negativer Zyklus: ein Knoten erreicht sich selbst mit negativen Kosten
        for (int i = 0; i < n; i++) {
            if (d[i][i] < 0) {
                throw new IllegalArgumentException("negativer Zyklus über Knoten " + i);
            }
        }
        return d;
    }
}
