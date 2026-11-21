package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.heap.MinHeap;
import io.github.muhammedfidann06.dsa.linear.ArrayStack;
import io.github.muhammedfidann06.dsa.linear.RingBufferQueue;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinearTest {

    @Test
    void stapelIstLifo() {
        ArrayStack<Integer> s = new ArrayStack<>();
        for (int i = 0; i < 20; i++) {
            s.push(i);
        }
        assertEquals(19, s.pop());
        assertEquals(18, s.peek());
        assertEquals(19, s.size());
    }

    @Test
    void klammernPruefen() {
        assertTrue(ArrayStack.isBalanced("{[()]}()"));
        assertTrue(ArrayStack.isBalanced("f(a[1]) { return; }"));
        assertFalse(ArrayStack.isBalanced("([)]"));
        assertFalse(ArrayStack.isBalanced("(("));
        assertFalse(ArrayStack.isBalanced(")"));
    }

    @Test
    void umgekehrtePolnischeNotation() {
        assertEquals(14, ArrayStack.evaluateRpn("3 4 + 2 *"));
        assertEquals(5, ArrayStack.evaluateRpn("10 2 8 * + 3 - 4 /"));
        assertThrows(IllegalArgumentException.class, () -> ArrayStack.evaluateRpn("1 2"));
    }

    @Test
    void warteschlangeIstFifoAuchNachDemUmlauf() {
        RingBufferQueue<Integer> q = new RingBufferQueue<>();
        // Abwechselnd rein und raus, damit head "im Kreis" läuft, dann wachsen lassen.
        for (int i = 0; i < 6; i++) {
            q.enqueue(i);
        }
        for (int i = 0; i < 4; i++) {
            q.dequeue();
        }
        for (int i = 6; i < 30; i++) {
            q.enqueue(i);
        }
        for (int expected = 4; expected < 30; expected++) {
            assertEquals(expected, q.dequeue());
        }
        assertTrue(q.isEmpty());
    }

    @Test
    void heapGibtElementeSortiertZurueck() {
        MinHeap<Integer> h = MinHeap.natural();
        SplittableRandom r = new SplittableRandom(5);
        for (int i = 0; i < 1000; i++) {
            h.add(r.nextInt(10_000));
        }
        int last = Integer.MIN_VALUE;
        while (!h.isEmpty()) {
            int x = h.poll();
            assertTrue(x >= last);
            last = x;
        }
    }

    @Test
    void heapMitEigenemComparator() {
        // Max-Heap durch umgedrehten Comparator, Strings nach Länge
        MinHeap<String> h = new MinHeap<>(Comparator.comparingInt((String s) -> s.length()).reversed());
        h.add("ab");
        h.add("abcd");
        h.add("a");
        assertEquals("abcd", h.poll());
        assertEquals("ab", h.peek());
    }
}
