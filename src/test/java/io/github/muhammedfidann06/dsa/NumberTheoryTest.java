package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.math.NumberTheory;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumberTheoryTest {

    @Test
    void ggtUndKgv() {
        assertEquals(6, NumberTheory.gcd(48, 18));
        assertEquals(6, NumberTheory.gcd(-48, 18));
        assertEquals(5, NumberTheory.gcd(0, 5));
        assertEquals(144, NumberTheory.lcm(48, 18));
        assertEquals(0, NumberTheory.lcm(0, 7));
    }

    @Test
    void modPowGegenBigInteger() {
        SplittableRandom r = new SplittableRandom(50);
        for (int i = 0; i < 500; i++) {
            long base = r.nextLong(-1_000_000_000_000L, 1_000_000_000_000L);
            long exp = r.nextLong(0, 1_000_000);
            long m = r.nextLong(1, 4_000_000_000_000_000_000L);
            long expected = BigInteger.valueOf(base).modPow(BigInteger.valueOf(exp), BigInteger.valueOf(m)).longValue();
            assertEquals(expected, NumberTheory.modPow(base, exp, m), base + "^" + exp + " mod " + m);
        }
        assertEquals(0, NumberTheory.modPow(5, 3, 1));
        assertThrows(IllegalArgumentException.class, () -> NumberTheory.modPow(2, 3, 0));
    }

    @Test
    void siebDesEratosthenes() {
        assertEquals(List.of(2, 3, 5, 7, 11, 13, 17, 19), NumberTheory.primesUpTo(20));
        assertEquals(List.of(), NumberTheory.primesUpTo(1));
        assertEquals(168, NumberTheory.primesUpTo(1000).size());
    }

    @Test
    void primfaktoren() {
        assertEquals(List.of(2L, 2L, 2L, 3L, 3L, 5L), NumberTheory.primeFactors(360));
        assertEquals(List.of(97L), NumberTheory.primeFactors(97));
        assertEquals(List.of(), NumberTheory.primeFactors(1));
        assertThrows(IllegalArgumentException.class, () -> NumberTheory.primeFactors(0));
    }
}
