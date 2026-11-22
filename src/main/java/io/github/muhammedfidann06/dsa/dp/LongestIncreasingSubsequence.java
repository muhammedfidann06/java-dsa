package io.github.muhammedfidann06.dsa.dp;

import java.util.Arrays;

/**
 * Längste streng steigende Teilfolge (LIS) in O(n log n).
 *
 * <p>Die einfache DP-Lösung ist O(n²). Schneller: tails[k] = kleinstes mögliches
 * Endelement einer steigenden Folge der Länge k+1. Dieses Array ist sortiert,
 * deshalb finden wir die richtige Stelle mit binärer Suche ("Patience Sorting").
 */
public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {
    }

    /** Eine längste streng steigende Teilfolge (es kann mehrere geben). */
    public static int[] find(int[] a) {
        int n = a.length;
        int[] tailIndex = new int[n]; // Index im Array a für tails[k]
        int[] prev = new int[n];      // Vorgänger in der Folge, für die Rekonstruktion
        int length = 0;
        for (int i = 0; i < n; i++) {
            // Erste Länge k, bei der a[tail[k]] >= a[i] (binäre Suche)
            int lo = 0;
            int hi = length;
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (a[tailIndex[mid]] < a[i]) {
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            }
            prev[i] = lo > 0 ? tailIndex[lo - 1] : -1;
            tailIndex[lo] = i;
            if (lo == length) {
                length++;
            }
        }
        int[] result = new int[length];
        for (int k = length - 1, i = length > 0 ? tailIndex[length - 1] : -1; k >= 0; k--, i = prev[i]) {
            result[k] = a[i];
        }
        return result;
    }

    /** Nur die Länge – praktisch für Tests und schnelle Abfragen. */
    public static int length(int[] a) {
        return find(a).length;
    }

    /** O(n²)-Variante zum Vergleich (klassische DP-Tabelle). */
    public static int lengthQuadratic(int[] a) {
        int[] best = new int[a.length];
        Arrays.fill(best, 1);
        int max = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < i; j++) {
                if (a[j] < a[i]) {
                    best[i] = Math.max(best[i], best[j] + 1);
                }
            }
            max = Math.max(max, best[i]);
        }
        return max;
    }
}
