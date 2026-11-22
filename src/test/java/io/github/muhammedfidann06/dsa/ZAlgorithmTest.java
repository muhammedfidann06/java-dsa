package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.string.ZAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ZAlgorithmTest {

    @Test
    void zArrayBeispiel() {
        assertArrayEquals(new int[]{7, 1, 0, 0, 3, 1, 0}, ZAlgorithm.zArray("aabxaab"));
        assertArrayEquals(new int[]{4, 3, 2, 1}, ZAlgorithm.zArray("aaaa"));
        assertArrayEquals(new int[0], ZAlgorithm.zArray(""));
    }

    @Test
    void sucheUeberlappend() {
        assertEquals(List.of(0, 1, 2), ZAlgorithm.search("AAAA", "AA"));
        assertEquals(List.of(1, 3, 9), ZAlgorithm.search("GATATATGCATATACTT", "ATAT")); // Rosalind SUBS (0-basiert)
        assertEquals(List.of(), ZAlgorithm.search("abc", "abcd"));
        assertThrows(IllegalArgumentException.class, () -> ZAlgorithm.search("abc", ""));
    }

    @Test
    void zufallsVergleichMitNaiverSuche() {
        SplittableRandom r = new SplittableRandom(7);
        for (int t = 0; t < 300; t++) {
            String text = random(r, 1 + r.nextInt(40));
            String pattern = random(r, 1 + r.nextInt(4));
            List<Integer> expected = new ArrayList<>();
            for (int i = text.indexOf(pattern); i >= 0; i = text.indexOf(pattern, i + 1)) {
                expected.add(i);
            }
            assertEquals(expected, ZAlgorithm.search(text, pattern));
        }
    }

    private static String random(SplittableRandom r, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append((char) ('a' + r.nextInt(2)));
        }
        return sb.toString();
    }
}
