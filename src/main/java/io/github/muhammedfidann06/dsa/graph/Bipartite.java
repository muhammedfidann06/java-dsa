package io.github.muhammedfidann06.dsa.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Optional;

/**
 * Ist ein ungerichteter Graph bipartit (zweifärbbar)? O(V + E).
 *
 * <p>Bipartit heißt: Man kann die Knoten in zwei Gruppen teilen, und jede Kante
 * verbindet die beiden Gruppen (z. B. Studierende und Kurse). Das geht genau dann,
 * wenn es keinen Kreis mit ungerader Länge gibt.
 * Wir färben mit Breitensuche abwechselnd 0 und 1 – ein Konflikt heißt "nicht bipartit".
 */
public final class Bipartite {

    private Bipartite() {
    }

    /** Färbung (0 oder 1 pro Knoten), oder leer, wenn der Graph nicht bipartit ist. */
    public static Optional<int[]> coloring(Graph g) {
        int[] color = new int[g.vertices()];
        Arrays.fill(color, -1);
        Deque<Integer> queue = new ArrayDeque<>();
        for (int start = 0; start < g.vertices(); start++) {
            if (color[start] != -1) {
                continue; // jede Zusammenhangskomponente einzeln
            }
            color[start] = 0;
            queue.add(start);
            while (!queue.isEmpty()) {
                int v = queue.poll();
                for (Graph.Edge e : g.neighbours(v)) {
                    int w = e.to();
                    if (color[w] == -1) {
                        color[w] = 1 - color[v]; // Nachbar bekommt die andere Farbe
                        queue.add(w);
                    } else if (color[w] == color[v]) {
                        return Optional.empty(); // gleiche Farbe -> ungerader Kreis
                    }
                }
            }
        }
        return Optional.of(color);
    }

    public static boolean isBipartite(Graph g) {
        return coloring(g).isPresent();
    }
}
