package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.tree.Trie;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrieTest {

    private Trie sample() {
        Trie t = new Trie();
        for (String w : List.of("haus", "hausaufgabe", "hallo", "hagenberg", "linz", "haus")) {
            t.insert(w);
        }
        return t;
    }

    @Test
    void einfuegenUndSuchen() {
        Trie t = sample();
        assertEquals(5, t.size(), "Duplikat 'haus' zählt nur einmal");
        assertTrue(t.contains("haus"));
        assertFalse(t.contains("hau"), "nur ein Präfix, kein Wort");
        assertTrue(t.startsWith("hau"));
        assertFalse(t.startsWith("x"));
    }

    @Test
    void vorschlaegeSindSortiertUndBegrenzt() {
        Trie t = sample();
        assertEquals(List.of("hagenberg", "hallo", "haus", "hausaufgabe"), t.suggest("ha", 10));
        assertEquals(List.of("hagenberg", "hallo"), t.suggest("ha", 2));
        assertTrue(t.suggest("zz", 5).isEmpty());
    }
}
