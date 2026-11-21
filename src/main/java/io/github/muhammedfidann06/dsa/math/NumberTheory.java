package io.github.muhammedfidann06.dsa.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Zahlentheorie: ggT, kgV, schnelles Potenzieren und Primzahlen.
 */
public final class NumberTheory {

    private NumberTheory() {
    }

    /** Größter gemeinsamer Teiler mit dem Euklidischen Algorithmus. O(log min(a, b)). */
    public static long gcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            long t = a % b; // ggT(a, b) = ggT(b, a mod b)
            a = b;
            b = t;
        }
        return a;
    }

    /** Kleinstes gemeinsames Vielfaches. Erst teilen, dann multiplizieren (weniger Überlauf). */
    public static long lcm(long a, long b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return Math.abs(a / gcd(a, b) * b);
    }

    /**
     * (base ^ exp) mod m mit "Square and Multiply" in O(log exp).
     * Wichtig z. B. für RSA-Verschlüsselung.
     */
    public static long modPow(long base, long exp, long m) {
        if (m <= 0 || exp < 0) {
            throw new IllegalArgumentException("m muss > 0 und exp ≥ 0 sein");
        }
        long result = 1 % m;
        base %= m;
        if (base < 0) {
            base += m;
        }
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = mulMod(result, base, m); // Bit gesetzt -> diese Potenz mitnehmen
            }
            base = mulMod(base, base, m); // base, base², base⁴, base⁸, …
            exp >>= 1;
        }
        return result;
    }

    /**
     * (a · b) mod m für 0 ≤ a, b < m ohne Überlauf.
     * Passt das Produkt in einen long, rechnen wir direkt, sonst mit BigInteger.
     */
    private static long mulMod(long a, long b, long m) {
        if (Math.multiplyHigh(a, b) == 0 && a * b >= 0) {
            return (a * b) % m;
        }
        return java.math.BigInteger.valueOf(a).multiply(java.math.BigInteger.valueOf(b))
                .mod(java.math.BigInteger.valueOf(m)).longValue();
    }

    /**
     * Sieb des Eratosthenes: alle Primzahlen ≤ n. O(n log log n).
     * Wir streichen die Vielfachen jeder Primzahl – ab p², weil kleinere schon gestrichen sind.
     */
    public static List<Integer> primesUpTo(int n) {
        List<Integer> primes = new ArrayList<>();
        if (n < 2) {
            return primes;
        }
        boolean[] composite = new boolean[n + 1];
        for (int p = 2; (long) p * p <= n; p++) {
            if (!composite[p]) {
                for (int k = p * p; k <= n; k += p) {
                    composite[k] = true;
                }
            }
        }
        for (int i = 2; i <= n; i++) {
            if (!composite[i]) {
                primes.add(i);
            }
        }
        return primes;
    }

    /** Primfaktorzerlegung durch Probedivision bis √n. Beispiel 360 = [2, 2, 2, 3, 3, 5]. */
    public static List<Long> primeFactors(long n) {
        if (n < 1) {
            throw new IllegalArgumentException("n muss ≥ 1 sein");
        }
        List<Long> factors = new ArrayList<>();
        for (long p = 2; p * p <= n; p++) {
            while (n % p == 0) {
                factors.add(p);
                n /= p;
            }
        }
        if (n > 1) {
            factors.add(n); // was übrig bleibt, ist selbst eine Primzahl
        }
        return factors;
    }
}
