package io.github.muhammedfidann06.dsa.tree;

import java.util.Arrays;

/**
 * Segmentbaum für Bereichs-Minimum mit Punkt-Änderungen.
 *
 * <p>Jeder Knoten speichert das Minimum eines Bereichs. Die Wurzel hat das ganze Array,
 * die Blätter je ein Element. Abfrage und Änderung: O(log n).
 * Gespeichert als Array der Größe 2n (iterative Variante, "bottom-up").
 */
public class SegmentTree {

    private final int n;
    private final int[] tree; // Blätter liegen bei n … 2n−1

    public SegmentTree(int[] values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("leeres Array");
        }
        n = values.length;
        tree = new int[2 * n];
        System.arraycopy(values, 0, tree, n, n);
        // Von unten nach oben: Eltern = Minimum der zwei Kinder
        for (int i = n - 1; i > 0; i--) {
            tree[i] = Math.min(tree[2 * i], tree[2 * i + 1]);
        }
    }

    /** Wert an Position index setzen. */
    public void set(int index, int value) {
        check(index);
        int i = index + n;
        tree[i] = value;
        // Nach oben gehen und die Minima neu berechnen
        for (i /= 2; i >= 1; i /= 2) {
            tree[i] = Math.min(tree[2 * i], tree[2 * i + 1]);
        }
    }

    /** Minimum von from … to (beide inklusive). */
    public int min(int from, int to) {
        check(from);
        check(to);
        if (from > to) {
            throw new IllegalArgumentException("from > to");
        }
        int result = Integer.MAX_VALUE;
        // Halboffenes Intervall [l, r) auf den Blättern
        for (int l = from + n, r = to + n + 1; l < r; l /= 2, r /= 2) {
            if ((l & 1) == 1) {
                result = Math.min(result, tree[l++]);
            }
            if ((r & 1) == 1) {
                result = Math.min(result, tree[--r]);
            }
        }
        return result;
    }

    public int size() {
        return n;
    }

    private void check(int index) {
        if (index < 0 || index >= n) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(tree, n, 2 * n));
    }
}
