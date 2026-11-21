package io.github.muhammedfidann06.dsa.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Einfach verkettete Liste. Jeder Knoten kennt nur seinen Nachfolger.
 *
 * <p>Laufzeit: addFirst/removeFirst O(1), addLast O(1) (wir merken uns das Ende),
 * get(i) O(n).
 */
public class SinglyLinkedList<T> implements Iterable<T> {

    private static final class Node<T> {
        T value;
        Node<T> next;

        Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T value) {
        head = new Node<>(value, head);
        if (tail == null) {
            tail = head;
        }
        size++;
    }

    public void addLast(T value) {
        Node<T> node = new Node<>(value, null);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public T getFirst() {
        if (head == null) {
            throw new NoSuchElementException("Liste ist leer");
        }
        return head.value;
    }

    public T removeFirst() {
        T value = getFirst();
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return value;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index);
        }
        Node<T> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.value;
    }

    /** Erstes Vorkommen löschen. Gibt true zurück, wenn etwas gelöscht wurde. */
    public boolean remove(T value) {
        Node<T> prev = null;
        for (Node<T> cur = head; cur != null; prev = cur, cur = cur.next) {
            if (Objects.equals(cur.value, value)) {
                if (prev == null) {
                    head = cur.next;
                } else {
                    prev.next = cur.next;
                }
                if (cur == tail) {
                    tail = prev;
                }
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * Liste umdrehen – in place, ohne neue Knoten. Klassische Interview-Frage.
     * Wir drehen jeden Zeiger einzeln um: O(n) Zeit, O(1) Speicher.
     */
    public void reverse() {
        Node<T> prev = null;
        Node<T> cur = head;
        tail = head;
        while (cur != null) {
            Node<T> next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        head = prev;
    }

    /**
     * Mittleres Element mit zwei Zeigern: "langsam" geht 1 Schritt, "schnell" geht 2 Schritte.
     * Wenn schnell am Ende ist, ist langsam in der Mitte.
     */
    public T middle() {
        if (head == null) {
            throw new NoSuchElementException("Liste ist leer");
        }
        Node<T> slow = head;
        Node<T> fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow.value;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> cur = head;

            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public T next() {
                if (cur == null) {
                    throw new NoSuchElementException();
                }
                T v = cur.value;
                cur = cur.next;
                return v;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Node<T> cur = head; cur != null; cur = cur.next) {
            sb.append(cur.value);
            if (cur.next != null) {
                sb.append(", ");
            }
        }
        return sb.append(']').toString();
    }
}
