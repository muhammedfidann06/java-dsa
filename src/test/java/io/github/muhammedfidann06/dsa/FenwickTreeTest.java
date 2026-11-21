package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.tree.FenwickTree;
import org.junit.jupiter.api.Test;

import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FenwickTreeTest {

    @Test
    void gegenNaiveSummen() {
        SplittableRandom r = new SplittableRandom(21);
        long[] naive = r.longs(200, -1000, 1000).toArray();
        FenwickTree ft = new FenwickTree(naive.clone());
        for (int step = 0; step < 2000; step++) {
            if (r.nextBoolean()) {
                int i = r.nextInt(naive.length);
                long d = r.nextLong(-50, 50);
                naive[i] += d;
                ft.add(i, d);
            } else {
                int a = r.nextInt(naive.length);
                int b = r.nextInt(a, naive.length);
                long expected = 0;
                for (int k = a; k <= b; k++) {
                    expected += naive[k];
                }
                assertEquals(expected, ft.rangeSum(a, b));
            }
        }
    }

    @Test
    void leerGestartet() {
        FenwickTree ft = new FenwickTree(5);
        ft.add(2, 7);
        ft.add(4, 3);
        assertEquals(0, ft.prefixSum(1));
        assertEquals(10, ft.prefixSum(4));
        assertThrows(IndexOutOfBoundsException.class, () -> ft.add(5, 1));
        assertThrows(IllegalArgumentException.class, () -> ft.rangeSum(3, 2));
    }
}
