package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.sort.QuickSelect;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuickSelectTest {

    @Test
    void jedesKGegenSortiertesArray() {
        int[] a = new SplittableRandom(4).ints(300, -50, 50).toArray(); // viele Duplikate
        int[] sorted = a.clone();
        Arrays.sort(sorted);
        int[] copy = a.clone();
        for (int k = 0; k < a.length; k++) {
            assertEquals(sorted[k], QuickSelect.kthSmallest(a, k));
        }
        assertArrayEquals(copy, a, "Original darf sich nicht ändern");
    }

    @Test
    void median() {
        assertEquals(3.0, QuickSelect.median(new int[]{5, 1, 3}));
        assertEquals(2.5, QuickSelect.median(new int[]{4, 1, 3, 2}));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.median(new int[0]));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.kthSmallest(new int[]{1}, 1));
    }
}
