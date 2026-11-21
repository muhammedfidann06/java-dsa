package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.list.DynamicArray;
import io.github.muhammedfidann06.dsa.list.SinglyLinkedList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListTest {

    @Test
    void dynamicArrayWaechstUndSchrumpft() {
        DynamicArray<Integer> a = new DynamicArray<>();
        for (int i = 0; i < 100; i++) {
            a.add(i);
        }
        assertEquals(100, a.size());
        assertEquals(42, a.get(42));
        for (int i = 0; i < 95; i++) {
            a.removeAt(0);
        }
        assertEquals(5, a.size());
        assertEquals(95, a.get(0));
    }

    @Test
    void dynamicArrayEinfuegenInDerMitte() {
        DynamicArray<String> a = new DynamicArray<>();
        a.add("a");
        a.add("c");
        a.add(1, "b");
        List<String> out = new ArrayList<>();
        a.forEach(out::add);
        assertEquals(List.of("a", "b", "c"), out);
        assertEquals(1, a.indexOf("b"));
        assertTrue(a.contains("c"));
        assertEquals("b", a.set(1, "B"));
        assertThrows(IndexOutOfBoundsException.class, () -> a.get(3));
    }

    @Test
    void linkedListGrundfunktionen() {
        SinglyLinkedList<Integer> l = new SinglyLinkedList<>();
        l.addLast(2);
        l.addLast(3);
        l.addFirst(1);
        assertEquals("[1, 2, 3]", l.toString());
        assertEquals(2, l.get(1));
        assertTrue(l.remove(3));
        assertFalse(l.remove(99));
        l.addLast(4); // tail muss nach remove noch stimmen
        assertEquals("[1, 2, 4]", l.toString());
        assertEquals(1, l.removeFirst());
        assertEquals(2, l.size());
    }

    @Test
    void linkedListUmdrehenUndMitte() {
        SinglyLinkedList<Integer> l = new SinglyLinkedList<>();
        for (int i = 1; i <= 5; i++) {
            l.addLast(i);
        }
        assertEquals(3, l.middle());
        l.reverse();
        assertEquals("[5, 4, 3, 2, 1]", l.toString());
        l.addLast(0); // nach reverse ist das alte head das neue tail
        assertEquals("[5, 4, 3, 2, 1, 0]", l.toString());
    }

    @Test
    void leereListeWirftException() {
        SinglyLinkedList<Integer> l = new SinglyLinkedList<>();
        assertThrows(NoSuchElementException.class, l::removeFirst);
        assertThrows(NoSuchElementException.class, l::middle);
    }
}
