package io.github.muhammedfidann06.dsa.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Binärer Suchbaum: links kleiner, rechts größer.
 *
 * <p>Laufzeit: O(h), h = Höhe. Bei zufälligen Daten ist h ≈ log n,
 * bei sortierten Daten wird der Baum aber zur Liste (h = n). Deshalb gibt es balancierte
 * Bäume (AVL, Rot-Schwarz) – hier zeigen wir die einfache Version.
 */
public class BinarySearchTree<K extends Comparable<? super K>, V> {

    private final class Node {
        final K key;
        V value;
        Node left;
        Node right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    private int size;

    public void put(K key, V value) {
        root = put(root, key, value);
    }

    // Rekursiv: Gibt den (eventuell neuen) Teilbaum zurück.
    private Node put(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        int c = key.compareTo(node.key);
        if (c < 0) {
            node.left = put(node.left, key, value);
        } else if (c > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value; // Schlüssel gibt es schon -> Wert ersetzen
        }
        return node;
    }

    public Optional<V> get(K key) {
        Node cur = root;
        while (cur != null) {
            int c = key.compareTo(cur.key);
            if (c == 0) {
                return Optional.ofNullable(cur.value);
            }
            cur = c < 0 ? cur.left : cur.right;
        }
        return Optional.empty();
    }

    public boolean contains(K key) {
        return get(key).isPresent();
    }

    public void remove(K key) {
        root = remove(root, key);
    }

    private Node remove(Node node, K key) {
        if (node == null) {
            return null;
        }
        int c = key.compareTo(node.key);
        if (c < 0) {
            node.left = remove(node.left, key);
            return node;
        }
        if (c > 0) {
            node.right = remove(node.right, key);
            return node;
        }
        size--;
        // Fall 1 und 2: kein oder ein Kind
        if (node.left == null) {
            return node.right;
        }
        if (node.right == null) {
            return node.left;
        }
        // Fall 3: zwei Kinder -> durch den kleinsten Knoten im rechten Teilbaum ersetzen
        Node successor = node.right;
        while (successor.left != null) {
            successor = successor.left;
        }
        size++; // das folgende remove zählt sonst doppelt
        node.right = remove(node.right, successor.key);
        Node replacement = new Node(successor.key, successor.value);
        replacement.left = node.left;
        replacement.right = node.right;
        return replacement;
    }

    /** Schlüssel in sortierter Reihenfolge (In-Order: links, Knoten, rechts). */
    public List<K> inOrder() {
        List<K> out = new ArrayList<>(size);
        inOrder(root, out);
        return out;
    }

    private void inOrder(Node node, List<K> out) {
        if (node == null) {
            return;
        }
        inOrder(node.left, out);
        out.add(node.key);
        inOrder(node.right, out);
    }

    public Optional<K> min() {
        if (root == null) {
            return Optional.empty();
        }
        Node cur = root;
        while (cur.left != null) {
            cur = cur.left;
        }
        return Optional.of(cur.key);
    }

    public Optional<K> max() {
        if (root == null) {
            return Optional.empty();
        }
        Node cur = root;
        while (cur.right != null) {
            cur = cur.right;
        }
        return Optional.of(cur.key);
    }

    /** Höhe: leerer Baum = 0, nur Wurzel = 1. */
    public int height() {
        return height(root);
    }

    private int height(Node node) {
        return node == null ? 0 : 1 + Math.max(height(node.left), height(node.right));
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
