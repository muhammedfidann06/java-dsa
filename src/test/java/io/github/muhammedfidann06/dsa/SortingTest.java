package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.sort.Sorting;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.SplittableRandom;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SortingTest {

    // Jedes Verfahren bekommt die gleichen schwierigen Eingaben.
    static Stream<Arguments> algorithms() {
        return Stream.of(
                Arguments.of("insertion", (Consumer<int[]>) Sorting::insertionSort),
                Arguments.of("merge", (Consumer<int[]>) Sorting::mergeSort),
                Arguments.of("quick", (Consumer<int[]>) Sorting::quickSort),
                Arguments.of("heap", (Consumer<int[]>) Sorting::heapSort),
                Arguments.of("counting", (Consumer<int[]>) Sorting::countingSort));
    }

    private static int[][] inputs() {
        SplittableRandom r = new SplittableRandom(99);
        int[] sorted = new int[500];
        Arrays.setAll(sorted, i -> i);
        int[] reversed = new int[500];
        Arrays.setAll(reversed, i -> 500 - i);
        return new int[][]{
                {},
                {1},
                {2, 1},
                {5, -3, 0, 5, 5, -3, 8},
                sorted,
                reversed,
                r.ints(1000, 0, 5).toArray(),        // viele Duplikate
                r.ints(2000, -1000, 1000).toArray(), // zufällig mit negativen Zahlen
        };
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("algorithms")
    void sortiertRichtig(String name, Consumer<int[]> sort) {
        for (int[] input : inputs()) {
            int[] expected = input.clone();
            Arrays.sort(expected);
            int[] actual = input.clone();
            sort.accept(actual);
            assertArrayEquals(expected, actual, name + " bei Länge " + input.length);
        }
    }

    @Test
    void binaereSuche() {
        int[] a = {1, 3, 5, 7, 9, 11};
        assertEquals(0, Sorting.binarySearch(a, 1));
        assertEquals(5, Sorting.binarySearch(a, 11));
        assertEquals(3, Sorting.binarySearch(a, 7));
        assertEquals(-1, Sorting.binarySearch(a, 4));
        assertEquals(-1, Sorting.binarySearch(new int[0], 4));
    }
}
