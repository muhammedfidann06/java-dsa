package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.tree.AvlTree;
import io.github.muhammedfidann06.dsa.tree.BinarySearchTree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.SplittableRandom;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AvlTreeTest {

    @Test
    void sortierteDatenBleibenFlach() {
        // Der normale Suchbaum wird bei sortierten Daten zur Liste, der AVL-Baum nicht.
        AvlTree<Integer> avl = new AvlTree<>();
        BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < 1000; i++) {
            avl.insert(i);
            bst.put(i, i);
        }
        assertEquals(1000, bst.height());
        // Theorie: Höhe ≤ 1,44 · log2(n) ≈ 14,4
        assertTrue(avl.height() <= 14, "Höhe " + avl.height());
        assertTrue(avl.isValid());
    }

    @Test
    void alleRotationsFaelle() {
        int[][] cases = {{3, 2, 1}, {1, 2, 3}, {3, 1, 2}, {1, 3, 2}}; // LL, RR, LR, RL
        for (int[] keys : cases) {
            AvlTree<Integer> t = new AvlTree<>();
            for (int k : keys) {
                t.insert(k);
            }
            assertEquals(2, t.height());
            assertTrue(t.isValid());
        }
    }

    @Test
    void zufaelligGegenTreeSet() {
        AvlTree<Integer> t = new AvlTree<>();
        TreeSet<Integer> ref = new TreeSet<>();
        SplittableRandom r = new SplittableRandom(8);
        for (int i = 0; i < 5000; i++) {
            int k = r.nextInt(500);
            if (r.nextInt(3) > 0) {
                t.insert(k);
                ref.add(k);
            } else {
                t.remove(k);
                ref.remove(k);
            }
            if (i % 500 == 0) {
                assertTrue(t.isValid());
            }
        }
        assertEquals(new ArrayList<>(ref), t.inOrder());
        assertEquals(ref.size(), t.size());
        assertTrue(t.isValid());
        assertFalse(t.contains(-1));
    }
}
