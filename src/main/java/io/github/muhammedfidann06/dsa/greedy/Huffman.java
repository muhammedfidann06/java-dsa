package io.github.muhammedfidann06.dsa.greedy;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Huffman-Kodierung: verlustfreie Kompression mit einem gierigen Algorithmus.
 *
 * <p>Häufige Zeichen bekommen kurze Codes, seltene lange. Wir verbinden immer die zwei
 * seltensten Teilbäume – das ergibt nachweisbar einen optimalen präfixfreien Code
 * (kein Code ist Anfang eines anderen, darum braucht man keine Trennzeichen).
 */
public final class Huffman {

    private abstract static sealed class Node permits Leaf, Inner {
        final long freq;

        Node(long freq) {
            this.freq = freq;
        }
    }

    private static final class Leaf extends Node {
        final char ch;

        Leaf(char ch, long freq) {
            super(freq);
            this.ch = ch;
        }
    }

    private static final class Inner extends Node {
        final Node left;
        final Node right;

        Inner(Node left, Node right) {
            super(left.freq + right.freq);
            this.left = left;
            this.right = right;
        }
    }

    private final Node root;
    private final Map<Character, String> codes = new HashMap<>();

    /** Baut den Code aus den Zeichenhäufigkeiten des Textes. */
    public Huffman(String text) {
        if (text.isEmpty()) {
            throw new IllegalArgumentException("leerer Text");
        }
        Map<Character, Long> freq = new HashMap<>();
        for (char c : text.toCharArray()) {
            freq.merge(c, 1L, Long::sum);
        }
        // Bei gleicher Häufigkeit nach Zeichen sortieren -> deterministische Codes
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> {
            int cmp = Long.compare(a.freq, b.freq);
            return cmp != 0 ? cmp : Character.compare(minChar(a), minChar(b));
        });
        freq.forEach((c, f) -> pq.add(new Leaf(c, f)));
        while (pq.size() > 1) {
            Node a = pq.poll();
            Node b = pq.poll();
            pq.add(new Inner(a, b)); // die zwei seltensten zusammenfügen
        }
        root = pq.poll();
        buildCodes(root, "");
    }

    private static char minChar(Node n) {
        return n instanceof Leaf l ? l.ch : (char) Math.min(minChar(((Inner) n).left), minChar(((Inner) n).right));
    }

    private void buildCodes(Node n, String prefix) {
        if (n instanceof Leaf l) {
            codes.put(l.ch, prefix.isEmpty() ? "0" : prefix); // nur ein Zeichen -> Code "0"
        } else {
            Inner in = (Inner) n;
            buildCodes(in.left, prefix + "0");
            buildCodes(in.right, prefix + "1");
        }
    }

    public Map<Character, String> codes() {
        return Map.copyOf(codes);
    }

    /** Text als Bitfolge (als String aus '0' und '1', damit man es lesen kann). */
    public String encode(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            String code = codes.get(c);
            if (code == null) {
                throw new IllegalArgumentException("Zeichen nicht im Code: " + c);
            }
            sb.append(code);
        }
        return sb.toString();
    }

    public String decode(String bits) {
        if (root instanceof Leaf l) { // Sonderfall: nur ein Zeichen
            return String.valueOf(l.ch).repeat(bits.length());
        }
        StringBuilder sb = new StringBuilder();
        Node cur = root;
        for (char b : bits.toCharArray()) {
            Inner in = (Inner) cur;
            cur = b == '0' ? in.left : in.right;
            if (cur instanceof Leaf l) {
                sb.append(l.ch);
                cur = root; // nächstes Zeichen beginnt wieder oben
            }
        }
        return sb.toString();
    }
}
