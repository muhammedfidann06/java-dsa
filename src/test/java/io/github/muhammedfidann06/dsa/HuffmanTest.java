package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.greedy.Huffman;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HuffmanTest {

    @Test
    void hinUndZurueck() {
        for (String text : List.of("abracadabra", "Servus aus Hagenberg!", "aaaaabbbc", "xyz")) {
            Huffman h = new Huffman(text);
            assertEquals(text, h.decode(h.encode(text)));
        }
    }

    @Test
    void haeufigeZeichenHabenKuerzereCodes() {
        Huffman h = new Huffman("aaaaaaaabbbc");
        Map<Character, String> c = h.codes();
        assertTrue(c.get('a').length() < c.get('c').length());
    }

    @Test
    void codesSindPraefixfrei() {
        Map<Character, String> c = new Huffman("the quick brown fox jumps over the lazy dog").codes();
        for (String x : c.values()) {
            for (String y : c.values()) {
                if (!x.equals(y)) {
                    assertFalse(y.startsWith(x), x + " ist Präfix von " + y);
                }
            }
        }
    }

    @Test
    void kuerzerAlsFesteBreite() {
        String text = "aaaaaaaaaaaaaaaabbbbccd"; // 4 verschiedene Zeichen -> feste Breite 2 Bit
        Huffman h = new Huffman(text);
        assertTrue(h.encode(text).length() < 2 * text.length());
    }

    @Test
    void sonderfaelle() {
        Huffman h = new Huffman("zzzz");
        assertEquals("0000", h.encode("zzzz"));
        assertEquals("zzzz", h.decode("0000"));
        assertThrows(IllegalArgumentException.class, () -> new Huffman(""));
        assertThrows(IllegalArgumentException.class, () -> h.encode("a"));
    }
}
