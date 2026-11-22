package io.github.muhammedfidann06.dsa.tree;

/**
 * Sparse Table für Bereichs-Minimum auf einem festen Array: Aufbau O(n log n), Abfrage O(1).
 *
 * <p>table[k][i] = Minimum von 2^k Elementen ab i. Jede Abfrage wird von zwei (überlappenden)
 * Zweierpotenz-Blöcken abgedeckt – beim Minimum stört die Überlappung nicht.
 * Unterschied zum {@link SegmentTree}: schneller, aber keine Änderungen möglich.
 */
public class SparseTable {

    private final int[][] table;
    private final int[] log; // log[x] = floor(log2(x))

    public SparseTable(int[] values) {
        int n = values.length;
        if (n == 0) {
            throw new IllegalArgumentException("leeres Array");
        }
        log = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            log[i] = log[i / 2] + 1;
        }
        int levels = log[n] + 1;
        table = new int[levels][];
        table[0] = values.clone();
        for (int k = 1; k < levels; k++) {
            int half = 1 << (k - 1);
            table[k] = new int[n - (1 << k) + 1];
            for (int i = 0; i < table[k].length; i++) {
                table[k][i] = Math.min(table[k - 1][i], table[k - 1][i + half]);
            }
        }
    }

    /** Minimum von from … to (beide inklusive) in O(1). */
    public int min(int from, int to) {
        if (from < 0 || to >= table[0].length || from > to) {
            throw new IndexOutOfBoundsException(from + ".." + to);
        }
        int k = log[to - from + 1];
        return Math.min(table[k][from], table[k][to - (1 << k) + 1]);
    }

    public int size() {
        return table[0].length;
    }
}
