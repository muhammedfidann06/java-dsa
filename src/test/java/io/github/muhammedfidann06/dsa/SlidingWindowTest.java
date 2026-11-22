package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.linear.SlidingWindow;
import org.junit.jupiter.api.Test;

import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SlidingWindowTest {

    @Test
    void beispiel() {
        assertArrayEquals(new int[]{3, 3, 5, 5, 6, 7},
                SlidingWindow.maxPerWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3));
        assertArrayEquals(new int[]{4}, SlidingWindow.maxPerWindow(new int[]{4}, 1));
        assertArrayEquals(new int[]{2, 2}, SlidingWindow.maxPerWindow(new int[]{2, 2, 2}, 2));
    }

    @Test
    void ungueltigesK() {
        assertThrows(IllegalArgumentException.class, () -> SlidingWindow.maxPerWindow(new int[]{1, 2}, 0));
        assertThrows(IllegalArgumentException.class, () -> SlidingWindow.maxPerWindow(new int[]{1, 2}, 3));
    }

    @Test
    void zufallsVergleich() {
        SplittableRandom r = new SplittableRandom(5);
        for (int t = 0; t < 200; t++) {
            int[] a = new int[1 + r.nextInt(50)];
            for (int i = 0; i < a.length; i++) {
                a[i] = r.nextInt(-20, 20);
            }
            int k = 1 + r.nextInt(a.length);
            int[] expected = new int[a.length - k + 1];
            for (int i = 0; i < expected.length; i++) {
                int max = Integer.MIN_VALUE;
                for (int j = i; j < i + k; j++) {
                    max = Math.max(max, a[j]);
                }
                expected[i] = max;
            }
            assertArrayEquals(expected, SlidingWindow.maxPerWindow(a, k));
        }
    }
}
