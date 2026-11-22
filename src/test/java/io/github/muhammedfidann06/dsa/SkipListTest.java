package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.list.SkipList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SkipListTest {

    @Test
    void grundfunktionen() {
        SkipList<Integer> s = new SkipList<>(new SplittableRandom(1));
        assertTrue(s.add(5));
        assertTrue(s.add(1));
        assertTrue(s.add(3));
        assertFalse(s.add(3), "keine Duplikate");
        assertEquals(List.of(1, 3, 5), s.toList());
        assertTrue(s.contains(3));
        assertTrue(s.remove(3));
        assertFalse(s.remove(3));
        assertFalse(s.contains(3));
        assertEquals(2, s.size());
    }

    @Test
    void zufaelligGegenTreeSet() {
        SplittableRandom r = new SplittableRandom(90);
        SkipList<Integer> s = new SkipList<>(new SplittableRandom(2));
        TreeSet<Integer> ref = new TreeSet<>();
        for (int i = 0; i < 20_000; i++) {
            int x = r.nextInt(2000);
            switch (r.nextInt(3)) {
                case 0 -> assertEquals(ref.add(x), s.add(x));
                case 1 -> assertEquals(ref.remove(x), s.remove(x));
                default -> assertEquals(ref.contains(x), s.contains(x));
            }
        }
        assertEquals(new ArrayList<>(ref), s.toList());
        assertEquals(ref.size(), s.size());
    }

    @Test
    void funktioniertMitStrings() {
        SkipList<String> s = new SkipList<>();
        for (String w : List.of("Linz", "Hagenberg", "Wien", "Graz")) {
            s.add(w);
        }
        assertEquals(List.of("Graz", "Hagenberg", "Linz", "Wien"), s.toList());
    }
}
