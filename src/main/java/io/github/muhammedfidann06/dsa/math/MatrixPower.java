package io.github.muhammedfidann06.dsa.math;

/**
 * Matrix-Potenzen modulo m – damit wird z. B. Fibonacci in O(log n) berechenbar.
 *
 * <p>Trick: [[1,1],[1,0]]^n = [[F(n+1), F(n)], [F(n), F(n-1)]].
 * Mit "Square and Multiply" (wie bei Zahlen) brauchen wir nur O(log n) Matrixmultiplikationen.
 */
public final class MatrixPower {

    private MatrixPower() {
    }

    /** Produkt zweier quadratischer Matrizen modulo m. */
    public static long[][] multiply(long[][] a, long[][] b, long m) {
        int n = a.length;
        long[][] c = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                if (a[i][k] == 0) {
                    continue;
                }
                for (int j = 0; j < n; j++) {
                    c[i][j] = (c[i][j] + a[i][k] * b[k][j]) % m;
                }
            }
        }
        return c;
    }

    /** a^exp modulo m. Werte müssen kleiner als ~3·10^9 sein, damit a·b nicht überläuft. */
    public static long[][] power(long[][] a, long exp, long m) {
        if (exp < 0) {
            throw new IllegalArgumentException("exp muss ≥ 0 sein");
        }
        int n = a.length;
        long[][] result = new long[n][n];
        for (int i = 0; i < n; i++) {
            result[i][i] = 1 % m; // Einheitsmatrix
        }
        long[][] base = a;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = multiply(result, base, m);
            }
            base = multiply(base, base, m);
            exp >>= 1;
        }
        return result;
    }

    /** n-te Fibonacci-Zahl modulo m in O(log n). F(0) = 0, F(1) = 1. */
    public static long fibonacci(long n, long m) {
        if (n == 0) {
            return 0;
        }
        return power(new long[][]{{1, 1}, {1, 0}}, n, m)[0][1];
    }
}
