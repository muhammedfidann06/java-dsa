package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.hash.ChainedHashMap;
import io.github.muhammedfidann06.dsa.tree.BinarySearchTree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapAndTreeTest {

    @Test
    void hashMapVerhaeltSichWieJavaHashMap() {
        // Zufällige Operationen und mit java.util.HashMap vergleichen.
        ChainedHashMap<Integer, Integer> mine = new ChainedHashMap<>();
        Map<Integer, Integer> ref = new HashMap<>();
        SplittableRandom r = new SplittableRandom(11);
        for (int i = 0; i < 5000; i++) {
            int key = r.nextInt(-500, 500); // auch negative Schlüssel (negativer hashCode!)
            switch (r.nextInt(3)) {
                case 0 -> assertEquals(ref.put(key, i), mine.put(key, i));
                case 1 -> assertEquals(ref.remove(key), mine.remove(key));
                default -> assertEquals(ref.get(key), mine.get(key));
            }
        }
        assertEquals(ref.size(), mine.size());
        assertEquals(new TreeSet<>(ref.keySet()), new TreeSet<>(mine.keys()));
    }

    @Test
    void hashMapVergroessertSichUndErlaubtNull() {
        ChainedHashMap<String, String> m = new ChainedHashMap<>();
        for (int i = 0; i < 100; i++) {
            m.put("k" + i, "v" + i);
        }
        assertEquals("v77", m.get("k77"));
        m.put(null, "null-key");
        assertEquals("null-key", m.get(null));
        assertTrue(m.containsKey(null));
        assertNull(m.get("fehlt"));
    }

    @Test
    void suchbaumSortiertUndFindet() {
        BinarySearchTree<Integer, String> t = new BinarySearchTree<>();
        for (int k : new int[]{50, 30, 70, 20, 40, 60, 80}) {
            t.put(k, "v" + k);
        }
        assertEquals(List.of(20, 30, 40, 50, 60, 70, 80), t.inOrder());
        assertEquals("v40", t.get(40).orElseThrow());
        assertFalse(t.contains(45));
        assertEquals(20, t.min().orElseThrow());
        assertEquals(80, t.max().orElseThrow());
        assertEquals(3, t.height());
    }

    @Test
    void suchbaumLoeschenAlleDreiFaelle() {
        BinarySearchTree<Integer, Integer> t = new BinarySearchTree<>();
        for (int k : new int[]{50, 30, 70, 20, 40, 60, 80, 65}) {
            t.put(k, k);
        }
        t.remove(20); // Blatt
        t.remove(60); // ein Kind (65)
        t.remove(50); // zwei Kinder, Wurzel
        assertEquals(List.of(30, 40, 65, 70, 80), t.inOrder());
        assertEquals(5, t.size());
        t.remove(999); // gibt es nicht -> nichts passiert
        assertEquals(5, t.size());
    }

    @Test
    void suchbaumGegenTreeSet() {
        BinarySearchTree<Integer, Integer> t = new BinarySearchTree<>();
        TreeSet<Integer> ref = new TreeSet<>();
        SplittableRandom r = new SplittableRandom(3);
        for (int i = 0; i < 3000; i++) {
            int k = r.nextInt(300);
            if (r.nextBoolean()) {
                t.put(k, k);
                ref.add(k);
            } else {
                t.remove(k);
                ref.remove(k);
            }
        }
        assertEquals(new ArrayList<>(ref), t.inOrder());
        assertEquals(ref.size(), t.size());
    }
}
