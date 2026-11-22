package io.github.muhammedfidann06.dsa.linear;

import java.util.ArrayDeque;

/**
 * Maximum in jedem Fenster der Länge k – mit einer monotonen Deque in O(n) statt O(n·k).
 *
 * <p>Die Deque enthält Indizes, deren Werte von vorne nach hinten fallen. Ein neues Element
 * wirft hinten alle kleineren raus (die können nie mehr Maximum werden). Vorne steht immer das Maximum.
 */
public final class SlidingWindow {

    private SlidingWindow() {
    }

    public static int[] maxPerWindow(int[] values, int k) {
        if (k <= 0 || k > values.length) {
            throw new IllegalArgumentException("k muss zwischen 1 und " + values.length + " liegen");
        }
        int[] result = new int[values.length - k + 1];
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < values.length; i++) {
            if (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst(); // Index ist aus dem Fenster gefallen
            }
            while (!deque.isEmpty() && values[deque.peekLast()] <= values[i]) {
                deque.pollLast();
            }
            deque.addLast(i);
            if (i >= k - 1) {
                result[i - k + 1] = values[deque.peekFirst()];
            }
        }
        return result;
    }
}
