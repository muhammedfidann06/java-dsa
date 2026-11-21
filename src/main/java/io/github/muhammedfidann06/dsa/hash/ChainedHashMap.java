package io.github.muhammedfidann06.dsa.hash;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Hash-Tabelle mit Verkettung (separate chaining).
 *
 * <p>Jeder "Eimer" (bucket) ist eine kleine verkettete Liste. Wenn zu viele Einträge
 * drin sind (Ladefaktor > 0.75), verdoppeln wir die Tabelle und verteilen alles neu.
 * Im Durchschnitt sind put/get/remove O(1).
 */
public class ChainedHashMap<K, V> {

    private static final double MAX_LOAD = 0.75;

    private static final class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Entry<K, V>[] buckets = newTable(16);
    private int size;

    @SuppressWarnings("unchecked")
    private static <K, V> Entry<K, V>[] newTable(int n) {
        return (Entry<K, V>[]) new Entry[n];
    }

    // Math.floorMod statt %, weil hashCode() auch negativ sein kann.
    private int indexFor(Object key, int length) {
        return Math.floorMod(Objects.hashCode(key), length);
    }

    /** Speichert den Wert. Gibt den alten Wert zurück (oder null). */
    public V put(K key, V value) {
        int i = indexFor(key, buckets.length);
        for (Entry<K, V> e = buckets[i]; e != null; e = e.next) {
            if (Objects.equals(e.key, key)) {
                V old = e.value;
                e.value = value;
                return old;
            }
        }
        buckets[i] = new Entry<>(key, value, buckets[i]); // vorne einfügen
        size++;
        if (size > buckets.length * MAX_LOAD) {
            resize();
        }
        return null;
    }

    public V get(K key) {
        for (Entry<K, V> e = buckets[indexFor(key, buckets.length)]; e != null; e = e.next) {
            if (Objects.equals(e.key, key)) {
                return e.value;
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        for (Entry<K, V> e = buckets[indexFor(key, buckets.length)]; e != null; e = e.next) {
            if (Objects.equals(e.key, key)) {
                return true;
            }
        }
        return false;
    }

    public V remove(K key) {
        int i = indexFor(key, buckets.length);
        Entry<K, V> prev = null;
        for (Entry<K, V> e = buckets[i]; e != null; prev = e, e = e.next) {
            if (Objects.equals(e.key, key)) {
                if (prev == null) {
                    buckets[i] = e.next;
                } else {
                    prev.next = e.next;
                }
                size--;
                return e.value;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public List<K> keys() {
        List<K> result = new ArrayList<>(size);
        for (Entry<K, V> head : buckets) {
            for (Entry<K, V> e = head; e != null; e = e.next) {
                result.add(e.key);
            }
        }
        return result;
    }

    int capacity() {
        return buckets.length;
    }

    private void resize() {
        Entry<K, V>[] old = buckets;
        buckets = newTable(old.length * 2);
        for (Entry<K, V> head : old) {
            Entry<K, V> e = head;
            while (e != null) {
                Entry<K, V> next = e.next;
                int i = indexFor(e.key, buckets.length);
                e.next = buckets[i];
                buckets[i] = e;
                e = next;
            }
        }
    }
}
