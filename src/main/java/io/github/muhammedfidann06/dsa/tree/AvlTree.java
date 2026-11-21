package io.github.muhammedfidann06.dsa.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * AVL-Baum: ein binärer Suchbaum, der sich selbst balanciert.
 *
 * <p>Regel: Bei jedem Knoten unterscheiden sich die Höhen von links und rechts
 * höchstens um 1. Nach jedem Einfügen/Löschen reparieren wir das mit Rotationen.
 * Dadurch bleibt die Höhe immer O(log n) – auch bei sortierten Daten,
 * wo der normale {@link BinarySearchTree} zur Liste wird.
 */
public class AvlTree<K extends Comparable<? super K>> {

    private static final class Node<K> {
        final K key;
        Node<K> left;
        Node<K> right;
        int height = 1;

        Node(K key) {
            this.key = key;
        }
    }

    private Node<K> root;
    private int size;

    public void insert(K key) {
        root = insert(root, key);
    }

    public void remove(K key) {
        root = remove(root, key);
    }

    public boolean contains(K key) {
        Node<K> cur = root;
        while (cur != null) {
            int c = key.compareTo(cur.key);
            if (c == 0) {
                return true;
            }
            cur = c < 0 ? cur.left : cur.right;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public int height() {
        return h(root);
    }

    public List<K> inOrder() {
        List<K> out = new ArrayList<>(size);
        collect(root, out);
        return out;
    }

    /** Für Tests: Ist der Baum wirklich überall balanciert und sortiert? */
    public boolean isValid() {
        return check(root, null, null) >= 0;
    }

    // ---------- Einfügen und Löschen ----------

    private Node<K> insert(Node<K> node, K key) {
        if (node == null) {
            size++;
            return new Node<>(key);
        }
        int c = key.compareTo(node.key);
        if (c < 0) {
            node.left = insert(node.left, key);
        } else if (c > 0) {
            node.right = insert(node.right, key);
        } else {
            return node; // keine Duplikate
        }
        return rebalance(node);
    }

    private Node<K> remove(Node<K> node, K key) {
        if (node == null) {
            return null;
        }
        int c = key.compareTo(node.key);
        if (c < 0) {
            node.left = remove(node.left, key);
        } else if (c > 0) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                size--;
                return node.left != null ? node.left : node.right;
            }
            // Zwei Kinder: kleinsten Knoten rechts holen und dort löschen
            Node<K> min = node.right;
            while (min.left != null) {
                min = min.left;
            }
            Node<K> replacement = new Node<>(min.key);
            replacement.right = remove(node.right, min.key);
            replacement.left = node.left;
            node = replacement;
        }
        return rebalance(node);
    }

    // ---------- Balancieren ----------

    private int h(Node<K> n) {
        return n == null ? 0 : n.height;
    }

    private void update(Node<K> n) {
        n.height = 1 + Math.max(h(n.left), h(n.right));
    }

    /** Positiv = links zu hoch, negativ = rechts zu hoch. */
    private int balance(Node<K> n) {
        return h(n.left) - h(n.right);
    }

    /*
     * Rechtsrotation um y:
     *        y            x
     *       / \          / \
     *      x   C   ->   A   y
     *     / \              / \
     *    A   B            B   C
     */
    private Node<K> rotateRight(Node<K> y) {
        Node<K> x = y.left;
        y.left = x.right;
        x.right = y;
        update(y);
        update(x);
        return x;
    }

    private Node<K> rotateLeft(Node<K> x) {
        Node<K> y = x.right;
        x.right = y.left;
        y.left = x;
        update(x);
        update(y);
        return y;
    }

    private Node<K> rebalance(Node<K> n) {
        update(n);
        int b = balance(n);
        if (b > 1) {
            if (balance(n.left) < 0) {
                n.left = rotateLeft(n.left); // Links-Rechts-Fall
            }
            return rotateRight(n);
        }
        if (b < -1) {
            if (balance(n.right) > 0) {
                n.right = rotateRight(n.right); // Rechts-Links-Fall
            }
            return rotateLeft(n);
        }
        return n;
    }

    // ---------- Hilfsmethoden ----------

    private void collect(Node<K> n, List<K> out) {
        if (n != null) {
            collect(n.left, out);
            out.add(n.key);
            collect(n.right, out);
        }
    }

    /** Gibt die Höhe zurück oder −1, wenn etwas kaputt ist. */
    private int check(Node<K> n, K min, K max) {
        if (n == null) {
            return 0;
        }
        if ((min != null && n.key.compareTo(min) <= 0) || (max != null && n.key.compareTo(max) >= 0)) {
            return -1;
        }
        int l = check(n.left, min, n.key);
        int r = check(n.right, n.key, max);
        if (l < 0 || r < 0 || Math.abs(l - r) > 1 || n.height != 1 + Math.max(l, r)) {
            return -1;
        }
        return n.height;
    }
}
