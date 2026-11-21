package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.hash.LruCache;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LruCacheTest {

    @Test
    void aeltesterEintragFliegtRaus() {
        LruCache<String, Integer> c = new LruCache<>(2);
        c.put("a", 1);
        c.put("b", 2);
        c.get("a");      // a ist jetzt neuer als b
        c.put("c", 3);   // b fliegt raus
        assertTrue(c.get("b").isEmpty());
        assertEquals(1, c.get("a").orElseThrow());
        assertEquals(3, c.get("c").orElseThrow());
        assertEquals(2, c.size());
    }

    @Test
    void ueberschreibenZaehltAlsBenutzung() {
        LruCache<Integer, String> c = new LruCache<>(3);
        c.put(1, "x");
        c.put(2, "y");
        c.put(3, "z");
        c.put(1, "neu");
        assertEquals(List.of(1, 3, 2), c.keysByRecency());
        c.put(4, "w"); // 2 ist am ältesten
        assertEquals(List.of(4, 1, 3), c.keysByRecency());
        assertEquals("neu", c.get(1).orElseThrow());
    }

    @Test
    void kapazitaetMussPositivSein() {
        assertThrows(IllegalArgumentException.class, () -> new LruCache<String, String>(0));
    }
}
