package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.dp.LongestIncreasingSubsequence;
import org.junit.jupiter.api.Test;

import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LongestIncreasingSubsequenceTest {

    @Test
    void beispiel() {
        int[] a = {10, 9, 2, 5, 3, 7, 101, 18};
        int[] lis = LongestIncreasingSubsequence.find(a);
        assertEquals(4, lis.length); // z. B. 2, 3, 7, 18
        assertStrictlyIncreasingSubsequence(a, lis);
    }

    @Test
    void randfaelle() {
        assertEquals(0, LongestIncreasingSubsequence.length(new int[0]));
        assertEquals(1, LongestIncreasingSubsequence.length(new int[]{5, 5, 5}));
        assertEquals(1, LongestIncreasingSubsequence.length(new int[]{3, 2, 1}));
        assertEquals(4, LongestIncreasingSubsequence.length(new int[]{1, 2, 3, 4}));
    }

    @Test
    void gegenQuadratischeLoesung() {
        SplittableRandom r = new SplittableRandom(60);
        for (int t = 0; t < 300; t++) {
            int[] a = r.ints(r.nextInt(0, 60), 0, 30).toArray();
            int[] lis = LongestIncreasingSubsequence.find(a);
            assertEquals(LongestIncreasingSubsequence.lengthQuadratic(a), lis.length);
            assertStrictlyIncreasingSubsequence(a, lis);
        }
    }

    // Prüft: lis ist streng steigend UND kommt in dieser Reihenfolge in a vor
    private static void assertStrictlyIncreasingSubsequence(int[] a, int[] lis) {
        int pos = 0;
        for (int k = 0; k < lis.length; k++) {
            if (k > 0) {
                assertTrue(lis[k - 1] < lis[k]);
            }
            while (pos < a.length && a[pos] != lis[k]) {
                pos++;
            }
            assertTrue(pos < a.length, "nicht in der Reihenfolge enthalten");
            pos++;
        }
    }
}
