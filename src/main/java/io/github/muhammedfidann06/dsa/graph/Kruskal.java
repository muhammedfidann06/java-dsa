package io.github.muhammedfidann06.dsa.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Minimaler Spannbaum mit dem Kruskal-Algorithmus.
 *
 * <p>Idee: Kanten nach Gewicht sortieren und die billigste nehmen, die keinen Kreis macht.
 * Ob eine Kante einen Kreis macht, prüft {@link UnionFind} in fast O(1).
 * Gesamt: O(E log E) wegen dem Sortieren.
 *
 * <p>Beispiel aus dem Alltag: Glasfaserkabel zwischen Orten verlegen, möglichst billig,
 * aber jeder Ort muss angeschlossen sein.
 */
public final class Kruskal {

    /** Eine ungerichtete Kante. */
    public record WeightedEdge(int from, int to, int weight) {
    }

    /** Ergebnis: gewählte Kanten und Gesamtgewicht. */
    public record SpanningTree(List<WeightedEdge> edges, long totalWeight) {
    }

    private Kruskal() {
    }

    /**
     * @param vertices Anzahl Knoten (0 … n−1)
     * @return Spannbaum; wenn der Graph nicht zusammenhängend ist, ein Spannwald
     */
    public static SpanningTree minimumSpanningTree(int vertices, List<WeightedEdge> edges) {
        List<WeightedEdge> sorted = new ArrayList<>(edges);
        sorted.sort(Comparator.comparingInt(WeightedEdge::weight));

        UnionFind uf = new UnionFind(vertices);
        List<WeightedEdge> chosen = new ArrayList<>();
        long total = 0;
        for (WeightedEdge e : sorted) {
            // union gibt false zurück, wenn beide Knoten schon verbunden sind -> Kreis
            if (uf.union(e.from(), e.to())) {
                chosen.add(e);
                total += e.weight();
                if (chosen.size() == vertices - 1) {
                    break; // ein Baum mit n Knoten hat genau n−1 Kanten
                }
            }
        }
        return new SpanningTree(chosen, total);
    }
}
