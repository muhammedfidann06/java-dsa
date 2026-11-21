package io.github.muhammedfidann06.dsa.sort;

import java.util.SplittableRandom;

/**
 * Quickselect: das k-kleinste Element finden, OHNE das ganze Array zu sortieren.
 *
 * <p>Gleiche Idee wie Quick Sort, aber wir machen nur in EINER Hälfte weiter.
 * Durchschnitt O(n) statt O(n log n). Anwendung: Median, Top-k, Perzentile.
 */
public final class QuickSelect {

    private QuickSelect() {
    }

    /**
     * @param k 0-basiert: k = 0 ist das Minimum, k = n−1 das Maximum
     * @return das k-kleinste Element (das Array selbst bleibt unverändert)
     */
    public static int kthSmallest(int[] input, int k) {
        if (k < 0 || k >= input.length) {
            throw new IllegalArgumentException("k muss zwischen 0 und n−1 liegen");
        }
        int[] a = input.clone(); // Original nicht verändern
        SplittableRandom rnd = new SplittableRandom(7);
        int lo = 0;
        int hi = a.length - 1;
        while (true) {
            if (lo == hi) {
                return a[lo];
            }
            // Zufälliger Pivot -> schlechtester Fall O(n²) praktisch nie
            int p = partition(a, lo, hi, lo + rnd.nextInt(hi - lo + 1));
            if (k == p) {
                return a[p];
            } else if (k < p) {
                hi = p - 1;
            } else {
                lo = p + 1;
            }
        }
    }

    /** Median: bei gerader Länge der Mittelwert der zwei mittleren Werte. */
    public static double median(int[] a) {
        int n = a.length;
        if (n == 0) {
            throw new IllegalArgumentException("leeres Array");
        }
        if (n % 2 == 1) {
            return kthSmallest(a, n / 2);
        }
        return (kthSmallest(a, n / 2 - 1) + (double) kthSmallest(a, n / 2)) / 2;
    }

    // Lomuto-Partition: kleinere Werte nach links, Pivot an seine endgültige Stelle
    private static int partition(int[] a, int lo, int hi, int pivotIndex) {
        int pivot = a[pivotIndex];
        swap(a, pivotIndex, hi);
        int store = lo;
        for (int i = lo; i < hi; i++) {
            if (a[i] < pivot) {
                swap(a, i, store++);
            }
        }
        swap(a, store, hi);
        return store;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
