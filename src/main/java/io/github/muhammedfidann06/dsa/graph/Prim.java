package io.github.muhammedfidann06.dsa.graph;

import java.util.PriorityQueue;

/**
 * Minimaler Spannbaum mit dem Algorithmus von Prim. O(E log V).
 *
 * <p>Kruskal sortiert alle Kanten global. Prim wächst stattdessen von einem Startknoten aus:
 * Es nimmt immer die billigste Kante, die den Baum mit einem NEUEN Knoten verbindet.
 * Gut für dichte Graphen, die schon als Adjazenzliste vorliegen.
 */
public final class Prim {

    private Prim() {
    }

    /**
     * Gesamtgewicht des minimalen Spannbaums eines ungerichteten Graphen.
     *
     * @throws IllegalArgumentException wenn der Graph nicht zusammenhängend ist
     */
    public static long minimumSpanningTreeWeight(Graph g) {
        int n = g.vertices();
        if (n == 0) {
            return 0;
        }
        boolean[] inTree = new boolean[n];
        // Einträge {Gewicht, Knoten}; der kleinste kommt zuerst
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.add(new long[]{0, 0});
        long total = 0;
        int added = 0;
        while (!pq.isEmpty() && added < n) {
            long[] top = pq.poll();
            int v = (int) top[1];
            if (inTree[v]) {
                continue; // veralteter Eintrag – Knoten ist schon im Baum
            }
            inTree[v] = true;
            total += top[0];
            added++;
            for (Graph.Edge e : g.neighbours(v)) {
                if (!inTree[e.to()]) {
                    pq.add(new long[]{e.weight(), e.to()});
                }
            }
        }
        if (added < n) {
            throw new IllegalArgumentException("Graph ist nicht zusammenhängend");
        }
        return total;
    }
}
