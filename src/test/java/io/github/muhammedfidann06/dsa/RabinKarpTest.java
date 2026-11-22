package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.string.Kmp;
import io.github.muhammedfidann06.dsa.string.RabinKarp;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RabinKarpTest {

    @Test
    void einfacheBeispiele() {
        assertEquals(List.of(0, 1, 2), RabinKarp.search("AAAA", "AA"));
        assertEquals(List.of(7), RabinKarp.search("Servus Hagenberg", "Hagenberg"));
        assertEquals(List.of(), RabinKarp.search("abc", "abcd"));
        assertThrows(IllegalArgumentException.class, () -> RabinKarp.search("a", ""));
    }

    @Test
    void gleichesErgebnisWieKmp() {
        SplittableRandom r = new SplittableRandom(80);
        for (int t = 0; t < 300; t++) {
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < 80; i++) {
                text.append((char) ('a' + r.nextInt(3)));
            }
            StringBuilder pat = new StringBuilder();
            for (int i = 0, len = 1 + r.nextInt(5); i < len; i++) {
                pat.append((char) ('a' + r.nextInt(3)));
            }
            assertEquals(Kmp.search(text.toString(), pat.toString()),
                    RabinKarp.search(text.toString(), pat.toString()));
        }
    }
}
