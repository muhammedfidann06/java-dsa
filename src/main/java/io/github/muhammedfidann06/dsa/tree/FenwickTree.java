package io.github.muhammedfidann06.dsa.tree;

/**
 * Fenwick-Baum (Binary Indexed Tree): Präfixsummen mit Änderungen.
 *
 * <p>Ein normales Array: Summe O(n), Änderung O(1). Ein Präfixsummen-Array: Summe O(1),
 * Änderung O(n). Der Fenwick-Baum macht beides in O(log n).
 *
 * <p>Trick: {@code i & -i} ist das niedrigste gesetzte Bit von i. Jeder Index ist
 * für einen Bereich der Länge {@code i & -i} zuständig.
 */
public class FenwickTree {

    private final long[] tree; // 1-basiert, tree[0] wird nicht benutzt

    public FenwickTree(int n) {
        tree = new long[n + 1];
    }

    /** Baut den Baum aus einem Array in O(n). */
    public FenwickTree(long[] values) {
        this(values.length);
        for (int i = 1; i <= values.length; i++) {
            tree[i] += values[i - 1];
            int parent = i + (i & -i);
            if (parent < tree.length) {
                tree[parent] += tree[i];
            }
        }
    }

    public int size() {
        return tree.length - 1;
    }

    /** Wert an Position index (0-basiert) um delta ändern. */
    public void add(int index, long delta) {
        check(index);
        for (int i = index + 1; i < tree.length; i += i & -i) {
            tree[i] += delta;
        }
    }

    /** Summe der Positionen 0 … index (inklusive). */
    public long prefixSum(int index) {
        check(index);
        long sum = 0;
        for (int i = index + 1; i > 0; i -= i & -i) {
            sum += tree[i];
        }
        return sum;
    }

    /** Summe der Positionen from … to (beide inklusive). */
    public long rangeSum(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("from > to");
        }
        return prefixSum(to) - (from == 0 ? 0 : prefixSum(from - 1));
    }

    private void check(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(index);
        }
    }
}
