package io.github.muhammedfidann06.dsa.hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * LRU-Cache (least recently used): Wenn der Cache voll ist, fliegt der Eintrag raus,
 * der am längsten nicht benutzt wurde. Klassische Interview-Aufgabe.
 *
 * <p>Trick: HashMap (schneller Zugriff) + doppelt verkettete Liste (Reihenfolge).
 * get und put sind beide O(1).
 */
public class LruCache<K, V> {

    private final class Node {
        final K key;
        V value;
        Node prev;
        Node next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<K, Node> map = new HashMap<>();
    // Wächter-Knoten: Dann gibt es keine Sonderfälle für "Liste leer".
    private final Node head = new Node(null, null); // neueste Seite
    private final Node tail = new Node(null, null); // älteste Seite

    public LruCache(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Kapazität muss mindestens 1 sein");
        }
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public Optional<V> get(K key) {
        Node n = map.get(key);
        if (n == null) {
            return Optional.empty();
        }
        moveToFront(n); // gerade benutzt -> nach vorne
        return Optional.ofNullable(n.value);
    }

    public void put(K key, V value) {
        Node n = map.get(key);
        if (n != null) {
            n.value = value;
            moveToFront(n);
            return;
        }
        if (map.size() == capacity) {
            Node oldest = tail.prev;
            unlink(oldest);
            map.remove(oldest.key);
        }
        n = new Node(key, value);
        map.put(key, n);
        addFront(n);
    }

    public int size() {
        return map.size();
    }

    /** Schlüssel von neu nach alt – praktisch für Tests. */
    public List<K> keysByRecency() {
        List<K> out = new ArrayList<>();
        for (Node n = head.next; n != tail; n = n.next) {
            out.add(n.key);
        }
        return out;
    }

    private void moveToFront(Node n) {
        unlink(n);
        addFront(n);
    }

    private void unlink(Node n) {
        n.prev.next = n.next;
        n.next.prev = n.prev;
    }

    private void addFront(Node n) {
        n.next = head.next;
        n.prev = head;
        head.next.prev = n;
        head.next = n;
    }
}
