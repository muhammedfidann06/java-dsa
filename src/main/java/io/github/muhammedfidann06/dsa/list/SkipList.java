package io.github.muhammedfidann06.dsa.list;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

/**
 * Skip-Liste: eine sortierte Menge mit erwartet O(log n) für Suchen, Einfügen und Löschen.
 *
 * <p>Idee: mehrere verkettete Listen übereinander. Unten sind alle Elemente, jede Ebene
 * darüber enthält zufällig etwa die Hälfte der Elemente der Ebene darunter ("Expressspuren").
 * Viel einfacher zu programmieren als ein balancierter Baum – Redis benutzt das z. B. für
 * sortierte Mengen.
 */
public class SkipList<T extends Comparable<? super T>> {

    private static final int MAX_LEVEL = 32;

    private static final class Node<T> {
        final T value;
        @SuppressWarnings("unchecked")
        final Node<T>[] next = (Node<T>[]) new Node[MAX_LEVEL];

        Node(T value) {
            this.value = value;
        }
    }

    private final Node<T> head = new Node<>(null); // Wächter vor allen Elementen
    private final RandomGenerator random;
    private int level = 1; // aktuell benutzte Ebenen
    private int size;

    public SkipList(RandomGenerator random) {
        this.random = random;
    }

    public SkipList() {
        this(RandomGenerator.getDefault());
    }

    public boolean contains(T value) {
        Node<T> cur = head;
        for (int l = level - 1; l >= 0; l--) {
            while (cur.next[l] != null && cur.next[l].value.compareTo(value) < 0) {
                cur = cur.next[l]; // auf dieser Ebene so weit wie möglich nach rechts
            }
        }
        cur = cur.next[0];
        return cur != null && cur.value.compareTo(value) == 0;
    }

    /** Fügt ein; gibt false zurück, wenn der Wert schon da war. */
    public boolean add(T value) {
        @SuppressWarnings("unchecked")
        Node<T>[] update = (Node<T>[]) new Node[MAX_LEVEL]; // letzter Knoten vor value auf jeder Ebene
        Node<T> cur = head;
        for (int l = level - 1; l >= 0; l--) {
            while (cur.next[l] != null && cur.next[l].value.compareTo(value) < 0) {
                cur = cur.next[l];
            }
            update[l] = cur;
        }
        if (cur.next[0] != null && cur.next[0].value.compareTo(value) == 0) {
            return false;
        }
        int nodeLevel = randomLevel();
        if (nodeLevel > level) {
            for (int l = level; l < nodeLevel; l++) {
                update[l] = head;
            }
            level = nodeLevel;
        }
        Node<T> node = new Node<>(value);
        for (int l = 0; l < nodeLevel; l++) {
            node.next[l] = update[l].next[l];
            update[l].next[l] = node;
        }
        size++;
        return true;
    }

    /** Löscht; gibt false zurück, wenn der Wert nicht da war. */
    public boolean remove(T value) {
        @SuppressWarnings("unchecked")
        Node<T>[] update = (Node<T>[]) new Node[MAX_LEVEL];
        Node<T> cur = head;
        for (int l = level - 1; l >= 0; l--) {
            while (cur.next[l] != null && cur.next[l].value.compareTo(value) < 0) {
                cur = cur.next[l];
            }
            update[l] = cur;
        }
        Node<T> target = cur.next[0];
        if (target == null || target.value.compareTo(value) != 0) {
            return false;
        }
        for (int l = 0; l < level; l++) {
            if (update[l].next[l] == target) {
                update[l].next[l] = target.next[l];
            }
        }
        while (level > 1 && head.next[level - 1] == null) {
            level--; // leere Ebenen oben entfernen
        }
        size--;
        return true;
    }

    public int size() {
        return size;
    }

    /** Alle Werte sortiert (unterste Ebene von links nach rechts). */
    public List<T> toList() {
        List<T> out = new ArrayList<>(size);
        for (Node<T> n = head.next[0]; n != null; n = n.next[0]) {
            out.add(n.value);
        }
        return out;
    }

    // Münzwurf: mit Wahrscheinlichkeit 1/2 eine Ebene höher
    private int randomLevel() {
        int l = 1;
        while (l < MAX_LEVEL && random.nextBoolean()) {
            l++;
        }
        return l;
    }
}
