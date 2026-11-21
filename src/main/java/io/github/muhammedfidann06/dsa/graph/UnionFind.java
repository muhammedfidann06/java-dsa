package io.github.muhammedfidann06.dsa.graph;

/**
 * Union-Find (Disjoint Set Union) mit Pfadkompression und Union by Size.
 * Beide Tricks zusammen: fast O(1) pro Operation (genauer: inverse Ackermann-Funktion).
 *
 * <p>Anwendung: Kruskal-Algorithmus, Zusammenhang prüfen, Clustering.
 */
public class UnionFind {

    private final int[] parent;
    private final int[] size;
    private int sets;

    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        sets = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i; // am Anfang ist jeder sein eigener Chef
            size[i] = 1;
        }
    }

    /** Wurzel (Repräsentant) der Menge von x. */
    public int find(int x) {
        int root = x;
        while (parent[root] != root) {
            root = parent[root];
        }
        // Pfadkompression: alle Knoten auf dem Weg zeigen jetzt direkt auf die Wurzel.
        while (parent[x] != root) {
            int next = parent[x];
            parent[x] = root;
            x = next;
        }
        return root;
    }

    /** Mengen von a und b vereinen. Gibt false zurück, wenn sie schon zusammen waren. */
    public boolean union(int a, int b) {
        int ra = find(a);
        int rb = find(b);
        if (ra == rb) {
            return false;
        }
        // Kleinere Menge unter die größere hängen -> Bäume bleiben flach.
        if (size[ra] < size[rb]) {
            int t = ra;
            ra = rb;
            rb = t;
        }
        parent[rb] = ra;
        size[ra] += size[rb];
        sets--;
        return true;
    }

    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }

    public int sets() {
        return sets;
    }
}
