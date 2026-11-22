package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.tree.SparseTable;
import org.junit.jupiter.api.Test;

import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SparseTableTest {

    @Test
    void kleinesBeispiel() {
        SparseTable st = new SparseTable(new int[]{5, 2, 4, 7, 1, 3});
        assertEquals(2, st.min(0, 3));
        assertEquals(1, st.min(2, 5));
        assertEquals(7, st.min(3, 3));
        assertEquals(1, st.min(0, 5));
        assertEquals(6, st.size());
    }

    @Test
    void fehler() {
        assertThrows(IllegalArgumentException.class, () -> new SparseTable(new int[0]));
        SparseTable st = new SparseTable(new int[]{1, 2, 3});
        assertThrows(IndexOutOfBoundsException.class, () -> st.min(2, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> st.min(0, 3));
    }

    @Test
    void alleBereicheGegenBruteForce() {
        SplittableRandom r = new SplittableRandom(3);
        for (int t = 0; t < 30; t++) {
            int[] a = new int[1 + r.nextInt(60)];
            for (int i = 0; i < a.length; i++) {
                a[i] = r.nextInt(-100, 100);
            }
            SparseTable st = new SparseTable(a);
            for (int i = 0; i < a.length; i++) {
                int min = Integer.MAX_VALUE;
                for (int j = i; j < a.length; j++) {
                    min = Math.min(min, a[j]);
                    assertEquals(min, st.min(i, j));
                }
            }
        }
    }
}
