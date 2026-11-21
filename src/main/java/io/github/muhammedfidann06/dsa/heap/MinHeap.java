package io.github.muhammedfidann06.dsa.heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Binärer Min-Heap (Prioritätswarteschlange). Das kleinste Element ist immer oben.
 *
 * <p>Gespeichert als Array: Kinder von i sind 2i+1 und 2i+2, Eltern von i ist (i-1)/2.
 * add und poll sind O(log n), peek ist O(1).
 */
public class MinHeap<T> {

    private Object[] data = new Object[16];
    private int size;
    private final Comparator<? super T> cmp;

    public MinHeap(Comparator<? super T> cmp) {
        this.cmp = cmp;
    }

    /** Für Typen mit natürlicher Ordnung (Integer, String, ...). */
    public static <T extends Comparable<? super T>> MinHeap<T> natural() {
        return new MinHeap<>(Comparator.naturalOrder());
    }

    public void add(T value) {
        if (size == data.length) {
            data = Arrays.copyOf(data, size * 2);
        }
        data[size] = value;
        siftUp(size++);
    }

    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Heap ist leer");
        }
        return at(0);
    }

    /** Kleinstes Element entfernen und zurückgeben. */
    public T poll() {
        T top = peek();
        data[0] = data[--size]; // letztes Element nach oben
        data[size] = null;
        if (size > 0) {
            siftDown(0);
        }
        return top;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Nach oben wandern, solange der Elternknoten größer ist.
    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (cmp.compare(at(i), at(parent)) >= 0) {
                return;
            }
            swap(i, parent);
            i = parent;
        }
    }

    // Nach unten wandern und mit dem kleineren Kind tauschen.
    private void siftDown(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = left + 1;
            int smallest = i;
            if (left < size && cmp.compare(at(left), at(smallest)) < 0) {
                smallest = left;
            }
            if (right < size && cmp.compare(at(right), at(smallest)) < 0) {
                smallest = right;
            }
            if (smallest == i) {
                return;
            }
            swap(i, smallest);
            i = smallest;
        }
    }

    @SuppressWarnings("unchecked")
    private T at(int i) {
        return (T) data[i];
    }

    private void swap(int a, int b) {
        Object tmp = data[a];
        data[a] = data[b];
        data[b] = tmp;
    }
}
