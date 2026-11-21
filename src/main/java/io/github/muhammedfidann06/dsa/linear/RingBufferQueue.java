package io.github.muhammedfidann06.dsa.linear;

import java.util.NoSuchElementException;

/**
 * Warteschlange (FIFO: first in, first out) als Ringpuffer.
 *
 * <p>Idee: Anfang und Ende "laufen im Kreis" durch das Array. So müssen wir beim
 * Entfernen nichts verschieben. enqueue und dequeue sind O(1) (enqueue amortisiert).
 */
public class RingBufferQueue<T> {

    private Object[] data = new Object[8];
    private int head; // Index des ersten Elements
    private int size;

    public void enqueue(T value) {
        if (size == data.length) {
            grow();
        }
        data[(head + size) % data.length] = value;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Warteschlange ist leer");
        }
        return (T) data[head];
    }

    public T dequeue() {
        T value = peek();
        data[head] = null;
        head = (head + 1) % data.length;
        size--;
        return value;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Beim Vergrößern kopieren wir die Elemente in der richtigen Reihenfolge nach vorne.
    private void grow() {
        Object[] bigger = new Object[data.length * 2];
        for (int i = 0; i < size; i++) {
            bigger[i] = data[(head + i) % data.length];
        }
        data = bigger;
        head = 0;
    }
}
