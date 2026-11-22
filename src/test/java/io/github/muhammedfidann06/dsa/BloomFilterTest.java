package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.hash.BloomFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BloomFilterTest {

    @Test
    void keineFalschNegativen() {
        BloomFilter f = new BloomFilter(10_000, 0.01);
        for (int i = 0; i < 10_000; i++) {
            f.add("user-" + i);
        }
        for (int i = 0; i < 10_000; i++) {
            assertTrue(f.mightContain("user-" + i), "falsch negativ bei " + i);
        }
    }

    @Test
    void fehlerrateUngefaehrWieGeplant() {
        BloomFilter f = new BloomFilter(10_000, 0.01);
        for (int i = 0; i < 10_000; i++) {
            f.add("user-" + i);
        }
        int falsePositives = 0;
        int trials = 50_000;
        for (int i = 0; i < trials; i++) {
            if (f.mightContain("other-" + i)) {
                falsePositives++;
            }
        }
        double rate = (double) falsePositives / trials;
        assertTrue(rate < 0.03, "Fehlerrate zu hoch: " + rate); // geplant 1 %, mit Spielraum
    }

    @Test
    void parameterAusDerTheorie() {
        // n = 1000, p = 1 %  ->  m ≈ 9586 Bits, k ≈ 7
        BloomFilter f = new BloomFilter(1000, 0.01);
        assertEquals(9586, f.bitCount());
        assertEquals(7, f.hashFunctions());
        assertThrows(IllegalArgumentException.class, () -> new BloomFilter(10, 1.5));
    }
}
