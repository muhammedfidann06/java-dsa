package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.math.MatrixPower;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatrixPowerTest {

    private static final long MOD = 1_000_000_007L;

    @Test
    void ersteFibonacciZahlen() {
        long[] expected = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55};
        for (int n = 0; n < expected.length; n++) {
            assertEquals(expected[n], MatrixPower.fibonacci(n, MOD));
        }
    }

    @Test
    void grosseIndizesGegenBigInteger() {
        // F(1000) iterativ mit BigInteger, dann modulo vergleichen
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;
        for (int i = 0; i < 1000; i++) {
            BigInteger t = a.add(b);
            a = b;
            b = t;
        }
        assertEquals(a.mod(BigInteger.valueOf(MOD)).longValue(), MatrixPower.fibonacci(1000, MOD));
        // Sehr großer Index (geht nur in O(log n)): Identität F(2n) = F(n) * (2*F(n+1) - F(n))
        long n = 1_000_000_000_000L;
        long fn = MatrixPower.fibonacci(n, MOD);
        long fn1 = MatrixPower.fibonacci(n + 1, MOD);
        long expected = fn * ((2 * fn1 - fn + MOD) % MOD) % MOD;
        assertEquals(expected, MatrixPower.fibonacci(2 * n, MOD));
    }

    @Test
    void einheitsmatrixUndFehler() {
        long[][] m = {{2, 3}, {4, 5}};
        assertArrayEquals(new long[][]{{1, 0}, {0, 1}}, MatrixPower.power(m, 0, MOD));
        assertArrayEquals(m, MatrixPower.power(m, 1, MOD));
        assertThrows(IllegalArgumentException.class, () -> MatrixPower.power(m, -1, MOD));
    }
}
