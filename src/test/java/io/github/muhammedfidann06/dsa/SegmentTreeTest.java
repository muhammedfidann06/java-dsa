package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.tree.SegmentTree;
import org.junit.jupiter.api.Test;

import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SegmentTreeTest {

    @Test
    void gegenNaivesMinimum() {
        SplittableRandom r = new SplittableRandom(22);
        int[] naive = r.ints(150, -10_000, 10_000).toArray();
        SegmentTree st = new SegmentTree(naive.clone());
        for (int step = 0; step < 2000; step++) {
            if (r.nextInt(3) == 0) {
                int i = r.nextInt(naive.length);
                int v = r.nextInt(-10_000, 10_000);
                naive[i] = v;
                st.set(i, v);
            } else {
                int a = r.nextInt(naive.length);
                int b = r.nextInt(a, naive.length);
                int expected = Integer.MAX_VALUE;
                for (int k = a; k <= b; k++) {
                    expected = Math.min(expected, naive[k]);
                }
                assertEquals(expected, st.min(a, b));
            }
        }
    }

    @Test
    void einzelnesElementUndFehler() {
        SegmentTree st = new SegmentTree(new int[]{4});
        assertEquals(4, st.min(0, 0));
        assertThrows(IllegalArgumentException.class, () -> new SegmentTree(new int[0]));
        assertThrows(IndexOutOfBoundsException.class, () -> st.min(0, 1));
    }
}
