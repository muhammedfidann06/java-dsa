package io.github.muhammedfidann06.dsa.sort;

import java.util.random.RandomGenerator;

/**
 * Klassische Sortierverfahren für int-Arrays (aufsteigend, in place).
 *
 * <pre>
 * Verfahren        bester Fall   Durchschnitt   schlechtester   stabil   Speicher
 * Insertion Sort   O(n)          O(n²)          O(n²)           ja       O(1)
 * Merge Sort       O(n log n)    O(n log n)     O(n log n)      ja       O(n)
 * Quick Sort       O(n log n)    O(n log n)     O(n²)           nein     O(log n)
 * Heap Sort        O(n log n)    O(n log n)     O(n log n)      nein     O(1)
 * Counting Sort    O(n + k)      O(n + k)       O(n + k)        ja       O(k)
 * </pre>
 */
public final class Sorting {

    private Sorting() {
        // Nur statische Hilfsmethoden.
    }

    // ---------- Insertion Sort ----------

    /** Gut für kleine oder fast sortierte Arrays. */
    public static void insertionSort(int[] a) {
        insertionSort(a, 0, a.length - 1);
    }

    private static void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int x = a[i];
            int j = i - 1;
            // Größere Elemente nach rechts schieben, bis die Lücke für x passt.
            while (j >= lo && a[j] > x) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = x;
        }
    }

    // ---------- Merge Sort ----------

    public static void mergeSort(int[] a) {
        int[] buffer = new int[a.length]; // nur einmal Speicher holen
        mergeSort(a, buffer, 0, a.length - 1);
    }

    private static void mergeSort(int[] a, int[] buf, int lo, int hi) {
        if (hi - lo < 16) {
            insertionSort(a, lo, hi); // kleine Stücke: Insertion Sort ist schneller
            return;
        }
        int mid = (lo + hi) >>> 1; // >>> verhindert Überlauf bei sehr großen Indizes
        mergeSort(a, buf, lo, mid);
        mergeSort(a, buf, mid + 1, hi);
        if (a[mid] <= a[mid + 1]) {
            return; // schon sortiert
        }
        System.arraycopy(a, lo, buf, lo, hi - lo + 1);
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = buf[j++];
            } else if (j > hi) {
                a[k] = buf[i++];
            } else if (buf[j] < buf[i]) {
                a[k] = buf[j++];
            } else {
                a[k] = buf[i++]; // bei Gleichheit links zuerst -> stabil
            }
        }
    }

    // ---------- Quick Sort ----------

    public static void quickSort(int[] a) {
        quickSort(a, 0, a.length - 1, RandomGenerator.getDefault());
    }

    /**
     * Quick Sort mit zufälligem Pivot und 3-Wege-Partition (Dijkstra).
     * Der zufällige Pivot macht den schlechtesten Fall sehr unwahrscheinlich,
     * die 3-Wege-Partition hilft bei vielen gleichen Werten.
     */
    private static void quickSort(int[] a, int lo, int hi, RandomGenerator rnd) {
        while (lo < hi) {
            int pivot = a[rnd.nextInt(lo, hi + 1)];
            int lt = lo;
            int gt = hi;
            int i = lo;
            // a[lo..lt-1] < pivot, a[lt..i-1] == pivot, a[gt+1..hi] > pivot
            while (i <= gt) {
                if (a[i] < pivot) {
                    swap(a, lt++, i++);
                } else if (a[i] > pivot) {
                    swap(a, i, gt--);
                } else {
                    i++;
                }
            }
            // Kleineren Teil rekursiv, größeren in der Schleife -> Stack-Tiefe O(log n)
            if (lt - lo < hi - gt) {
                quickSort(a, lo, lt - 1, rnd);
                lo = gt + 1;
            } else {
                quickSort(a, gt + 1, hi, rnd);
                hi = lt - 1;
            }
        }
    }

    // ---------- Heap Sort ----------

    public static void heapSort(int[] a) {
        int n = a.length;
        // 1. Max-Heap bauen (von unten nach oben), O(n)
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(a, i, n);
        }
        // 2. Größtes Element ans Ende tauschen und Heap verkleinern
        for (int end = n - 1; end > 0; end--) {
            swap(a, 0, end);
            siftDown(a, 0, end);
        }
    }

    private static void siftDown(int[] a, int i, int n) {
        while (true) {
            int largest = i;
            int l = 2 * i + 1;
            int r = l + 1;
            if (l < n && a[l] > a[largest]) {
                largest = l;
            }
            if (r < n && a[r] > a[largest]) {
                largest = r;
            }
            if (largest == i) {
                return;
            }
            swap(a, i, largest);
            i = largest;
        }
    }

    // ---------- Counting Sort ----------

    /** Nur für kleine Wertebereiche sinnvoll (k = max − min + 1). */
    public static void countingSort(int[] a) {
        if (a.length == 0) {
            return;
        }
        int min = a[0];
        int max = a[0];
        for (int x : a) {
            min = Math.min(min, x);
            max = Math.max(max, x);
        }
        int[] count = new int[max - min + 1];
        for (int x : a) {
            count[x - min]++;
        }
        int k = 0;
        for (int v = 0; v < count.length; v++) {
            for (int c = 0; c < count[v]; c++) {
                a[k++] = v + min;
            }
        }
    }

    // ---------- Binäre Suche ----------

    /** Index von key im sortierten Array, oder −1. O(log n). */
    public static int binarySearch(int[] sorted, int key) {
        int lo = 0;
        int hi = sorted.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            if (sorted[mid] < key) {
                lo = mid + 1;
            } else if (sorted[mid] > key) {
                hi = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
