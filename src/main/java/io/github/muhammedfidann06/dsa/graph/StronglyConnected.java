package io.github.muhammedfidann06.dsa.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Stark zusammenhängende Komponenten (SCC) mit dem Algorithmus von Tarjan. O(V + E).
 *
 * <p>In einer SCC kann jeder Knoten jeden anderen erreichen. Anwendung: Zyklen in
 * Abhängigkeiten finden (z. B. Module, die sich gegenseitig importieren).
 *
 * <p>Idee: Tiefensuche; jeder Knoten bekommt eine Nummer (index) und den kleinsten
 * Index, den er über Rückkanten erreicht (low). Ist low == index, ist der Knoten
 * die "Wurzel" einer SCC – alles darüber auf dem Stapel gehört dazu.
 * Iterativ geschrieben, damit große Graphen keinen StackOverflowError auslösen.
 */
public final class StronglyConnected {

    private StronglyConnected() {
    }

    /** Liste der Komponenten; jede Komponente ist eine Liste von Knoten. */
    public static List<List<Integer>> components(Graph g) {
        int n = g.vertices();
        int[] index = new int[n];
        int[] low = new int[n];
        boolean[] onStack = new boolean[n];
        Arrays.fill(index, -1);
        Deque<Integer> stack = new ArrayDeque<>();
        List<List<Integer>> result = new ArrayList<>();
        int counter = 0;

        int[] nextEdge = new int[n]; // wie weit wir bei jedem Knoten in der Nachbarliste sind
        Deque<Integer> call = new ArrayDeque<>(); // ersetzt den Rekursions-Stapel

        for (int start = 0; start < n; start++) {
            if (index[start] != -1) {
                continue;
            }
            call.push(start);
            while (!call.isEmpty()) {
                int v = call.peek();
                if (index[v] == -1) { // erster Besuch
                    index[v] = low[v] = counter++;
                    stack.push(v);
                    onStack[v] = true;
                }
                List<Graph.Edge> edges = g.neighbours(v);
                if (nextEdge[v] < edges.size()) {
                    int w = edges.get(nextEdge[v]++).to();
                    if (index[w] == -1) {
                        call.push(w); // "rekursiver Aufruf"
                    } else if (onStack[w]) {
                        low[v] = Math.min(low[v], index[w]);
                    }
                    continue;
                }
                // Alle Nachbarn fertig -> "Rückkehr aus der Rekursion"
                call.pop();
                if (!call.isEmpty()) {
                    int parent = call.peek();
                    low[parent] = Math.min(low[parent], low[v]);
                }
                if (low[v] == index[v]) {
                    List<Integer> comp = new ArrayList<>();
                    int w;
                    do {
                        w = stack.pop();
                        onStack[w] = false;
                        comp.add(w);
                    } while (w != v);
                    result.add(comp);
                }
            }
        }
        return result;
    }
}
