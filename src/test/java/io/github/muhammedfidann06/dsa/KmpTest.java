package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.string.Kmp;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KmpTest {

    @Test
    void praefixFunktion() {
        assertArrayEquals(new int[]{0, 0, 1, 2}, Kmp.prefixFunction("ABAB"));
        assertArrayEquals(new int[]{0, 1, 0, 1, 2, 0, 1}, Kmp.prefixFunction("AABAACA"));
    }

    @Test
    void findetAuchUeberlappendeTreffer() {
        assertEquals(List.of(0, 2, 4), Kmp.search("ABABABB", "AB"));
        assertEquals(List.of(0, 1, 2), Kmp.search("AAAA", "AA"));
        assertEquals(List.of(), Kmp.search("ABC", "D"));
        assertThrows(IllegalArgumentException.class, () -> Kmp.search("A", ""));
    }

    @Test
    void gegenNaiveSuche() {
        SplittableRandom r = new SplittableRandom(30);
        for (int t = 0; t < 200; t++) {
            String text = randomString(r, 60);
            String pat = randomString(r, 1 + r.nextInt(4));
            List<Integer> naive = new ArrayList<>();
            for (int i = text.indexOf(pat); i >= 0; i = text.indexOf(pat, i + 1)) {
                naive.add(i);
            }
            assertEquals(naive, Kmp.search(text, pat), text + " / " + pat);
        }
    }

    // Nur zwei Buchstaben -> viele Wiederholungen, gut zum Testen der Sprünge
    private static String randomString(SplittableRandom r, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append((char) ('A' + r.nextInt(2)));
        }
        return sb.toString();
    }
}
