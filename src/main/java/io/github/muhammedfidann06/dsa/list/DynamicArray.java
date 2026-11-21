package io.github.muhammedfidann06.dsa.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Ein Array, das automatisch wächst (wie {@code ArrayList}).
 *
 * <p>Laufzeit: get/set O(1), add am Ende amortisiert O(1), add/remove in der Mitte O(n).
 * Amortisiert heißt: Manchmal müssen wir kopieren (O(n)), aber im Durchschnitt ist es O(1),
 * weil wir die Größe jedes Mal verdoppeln.
 */
public class DynamicArray<T> implements Iterable<T> {

    private static final int START_CAPACITY = 8;

    private Object[] data = new Object[START_CAPACITY];
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(T value) {
        ensureCapacity(size + 1);
        data[size++] = value;
    }

    public void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index);
        }
        ensureCapacity(size + 1);
        // Alles ab index eine Stelle nach rechts schieben.
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = value;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    public T set(int index, T value) {
        T old = get(index);
        data[index] = value;
        return old;
    }

    public T removeAt(int index) {
        T old = get(index);
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[--size] = null; // Referenz löschen, damit der Garbage Collector aufräumen kann
        shrinkIfNeeded();
        return old;
    }

    public int indexOf(T value) {
        for (int i = 0; i < size; i++) {
            if (java.util.Objects.equals(data[i], value)) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(T value) {
        return indexOf(value) >= 0;
    }

    /** Nur für Tests interessant: wie groß ist das interne Array? */
    int capacity() {
        return data.length;
    }

    private void ensureCapacity(int needed) {
        if (needed > data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
    }

    // Wenn nur noch ein Viertel benutzt wird, halbieren wir das Array.
    // (Bei der Hälfte würde es bei add/remove im Wechsel ständig kopieren.)
    private void shrinkIfNeeded() {
        if (data.length > START_CAPACITY && size <= data.length / 4) {
            data = Arrays.copyOf(data, data.length / 2);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + ", Größe " + size);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int i;

            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return get(i++);
            }
        };
    }
}
