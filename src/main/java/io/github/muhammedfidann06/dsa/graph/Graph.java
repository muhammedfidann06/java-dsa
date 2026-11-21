package io.github.muhammedfidann06.dsa.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

/**
 * Gewichteter Graph mit Knoten 0 … n−1, gespeichert als Adjazenzliste.
 * Speicher O(V + E) – besser als eine Matrix (O(V²)) für dünne Graphen.
 */
public class Graph {

    /** Eine Kante zum Knoten {@code to} mit Gewicht {@code weight}. */
    public record Edge(int to, int weight) {
    }

    private final List<List<Edge>> adj;
    private final boolean directed;

    public Graph(int vertices, boolean directed) {
        this.directed = directed;
        this.adj = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public int vertices() {
        return adj.size();
    }

    public void addEdge(int from, int to, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Dijkstra braucht nicht-negative Gewichte");
        }
        adj.get(from).add(new Edge(to, weight));
        if (!directed) {
            adj.get(to).add(new Edge(from, weight));
        }
    }

    public void addEdge(int from, int to) {
        addEdge(from, to, 1);
    }

    public List<Edge> neighbours(int v) {
        return Collections.unmodifiableList(adj.get(v));
    }

    // ---------- Breitensuche (BFS) ----------

    /**
     * Anzahl Kanten vom Start zu jedem Knoten (−1 = nicht erreichbar).
     * BFS besucht die Knoten "Schicht für Schicht" mit einer Warteschlange. O(V + E).
     */
    public int[] bfsDistances(int start) {
        int[] dist = new int[vertices()];
        Arrays.fill(dist, -1);
        Deque<Integer> queue = new ArrayDeque<>();
        dist[start] = 0;
        queue.add(start);
        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (Edge e : adj.get(v)) {
                if (dist[e.to()] == -1) {
                    dist[e.to()] = dist[v] + 1;
                    queue.add(e.to());
                }
            }
        }
        return dist;
    }

    // ---------- Tiefensuche (DFS) ----------

    /** Reihenfolge der besuchten Knoten bei der Tiefensuche (iterativ mit Stapel). */
    public List<Integer> dfsOrder(int start) {
        boolean[] seen = new boolean[vertices()];
        List<Integer> order = new ArrayList<>();
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (seen[v]) {
                continue;
            }
            seen[v] = true;
            order.add(v);
            // Rückwärts pushen, damit kleinere Nachbarn zuerst besucht werden.
            List<Edge> edges = adj.get(v);
            for (int i = edges.size() - 1; i >= 0; i--) {
                if (!seen[edges.get(i).to()]) {
                    stack.push(edges.get(i).to());
                }
            }
        }
        return order;
    }

    /** Anzahl der Zusammenhangskomponenten (für ungerichtete Graphen). */
    public int connectedComponents() {
        boolean[] seen = new boolean[vertices()];
        int count = 0;
        for (int v = 0; v < vertices(); v++) {
            if (!seen[v]) {
                count++;
                for (int u : dfsOrder(v)) {
                    seen[u] = true;
                }
            }
        }
        return count;
    }

    // ---------- Dijkstra ----------

    /** Ergebnis von Dijkstra: Distanzen und Vorgänger für den Weg. */
    public record ShortestPaths(long[] distance, int[] previous) {

        /** Weg vom Start zu {@code target}, oder leer wenn nicht erreichbar. */
        public Optional<List<Integer>> pathTo(int target) {
            if (distance[target] == Long.MAX_VALUE) {
                return Optional.empty();
            }
            List<Integer> path = new ArrayList<>();
            for (int v = target; v != -1; v = previous[v]) {
                path.add(v);
            }
            Collections.reverse(path);
            return Optional.of(path);
        }
    }

    /**
     * Kürzeste Wege von {@code start} mit einer Prioritätswarteschlange. O((V + E) log V).
     * Wir nehmen immer den Knoten mit der kleinsten bekannten Distanz und "entspannen" seine Kanten.
     */
    public ShortestPaths dijkstra(int start) {
        long[] dist = new long[vertices()];
        int[] prev = new int[vertices()];
        Arrays.fill(dist, Long.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[start] = 0;

        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        pq.add(new long[]{start, 0});
        while (!pq.isEmpty()) {
            long[] top = pq.poll();
            int v = (int) top[0];
            if (top[1] > dist[v]) {
                continue; // veralteter Eintrag – es gibt schon einen kürzeren Weg
            }
            for (Edge e : adj.get(v)) {
                long nd = dist[v] + e.weight();
                if (nd < dist[e.to()]) {
                    dist[e.to()] = nd;
                    prev[e.to()] = v;
                    pq.add(new long[]{e.to(), nd});
                }
            }
        }
        return new ShortestPaths(dist, prev);
    }

    // ---------- Topologische Sortierung ----------

    /**
     * Kahn-Algorithmus: Reihenfolge, in der jeder Knoten nach seinen Vorgängern kommt
     * (z. B. Lehrveranstaltungen mit Voraussetzungen). Leer, wenn es einen Zyklus gibt.
     */
    public Optional<List<Integer>> topologicalOrder() {
        if (!directed) {
            throw new IllegalStateException("Nur für gerichtete Graphen");
        }
        int[] inDegree = new int[vertices()];
        for (List<Edge> edges : adj) {
            for (Edge e : edges) {
                inDegree[e.to()]++;
            }
        }
        Deque<Integer> ready = new ArrayDeque<>();
        for (int v = 0; v < vertices(); v++) {
            if (inDegree[v] == 0) {
                ready.add(v);
            }
        }
        List<Integer> order = new ArrayList<>();
        while (!ready.isEmpty()) {
            int v = ready.poll();
            order.add(v);
            for (Edge e : adj.get(v)) {
                if (--inDegree[e.to()] == 0) {
                    ready.add(e.to());
                }
            }
        }
        return order.size() == vertices() ? Optional.of(order) : Optional.empty();
    }
}
