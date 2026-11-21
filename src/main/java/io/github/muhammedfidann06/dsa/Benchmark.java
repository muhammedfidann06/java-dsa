package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.sort.Sorting;

import java.util.Arrays;
import java.util.SplittableRandom;
import java.util.function.Consumer;

/**
 * Kleiner Vergleich der Sortierverfahren. Start: {@code java -cp target/classes io.github.muhammedfidann06.dsa.Benchmark}.
 * Achtung: Ein echter Benchmark bräuchte JMH (Aufwärmphase, viele Wiederholungen).
 */
public class Benchmark {

    public static void main(String[] args) {
        int n = args.length > 0 ? Integer.parseInt(args[0]) : 200_000;
        int[] data = new SplittableRandom(42).ints(n, 0, 1_000_000).toArray();

        System.out.printf("Sortieren von %,d Zufallszahlen%n", n);
        run("Merge Sort", data, Sorting::mergeSort);
        run("Quick Sort", data, Sorting::quickSort);
        run("Heap Sort", data, Sorting::heapSort);
        run("Counting Sort", data, Sorting::countingSort);
        run("Arrays.sort (JDK)", data, Arrays::sort);
        if (n <= 50_000) {
            run("Insertion Sort", data, Sorting::insertionSort); // O(n²) – nur für kleine n
        }
    }

    private static void run(String name, int[] original, Consumer<int[]> sorter) {
        int[] a = original.clone();
        long start = System.nanoTime();
        sorter.accept(a);
        long ms = (System.nanoTime() - start) / 1_000_000;
        boolean ok = isSorted(a);
        System.out.printf("  %-20s %6d ms  %s%n", name, ms, ok ? "ok" : "FEHLER");
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) {
                return false;
            }
        }
        return true;
    }
}
