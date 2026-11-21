package io.github.muhammedfidann06.dsa.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Präfixbaum (Trie) für Wörter – z. B. für Autovervollständigung.
 *
 * <p>Jeder Knoten steht für einen Buchstaben. Ein Wort ist ein Weg von der Wurzel nach unten.
 * insert, contains und startsWith brauchen O(L) Zeit, L = Länge des Wortes –
 * egal wie viele Wörter gespeichert sind.
 */
public class Trie {

    private static final class Node {
        // TreeMap: Kinder alphabetisch sortiert -> Vorschläge kommen sortiert heraus
        final Map<Character, Node> children = new TreeMap<>();
        boolean endOfWord;
    }

    private final Node root = new Node();
    private int size;

    public void insert(String word) {
        Node cur = root;
        for (char ch : word.toCharArray()) {
            cur = cur.children.computeIfAbsent(ch, c -> new Node());
        }
        if (!cur.endOfWord) {
            cur.endOfWord = true;
            size++;
        }
    }

    public boolean contains(String word) {
        Node n = find(word);
        return n != null && n.endOfWord;
    }

    public boolean startsWith(String prefix) {
        return find(prefix) != null;
    }

    /** Alle Wörter mit diesem Anfang, alphabetisch, höchstens {@code limit} Stück. */
    public List<String> suggest(String prefix, int limit) {
        List<String> out = new ArrayList<>();
        Node start = find(prefix);
        if (start != null) {
            collect(start, new StringBuilder(prefix), out, limit);
        }
        return out;
    }

    public int size() {
        return size;
    }

    private Node find(String prefix) {
        Node cur = root;
        for (char ch : prefix.toCharArray()) {
            cur = cur.children.get(ch);
            if (cur == null) {
                return null;
            }
        }
        return cur;
    }

    // Tiefensuche: Buchstaben anhängen, rekursiv weiter, dann wieder entfernen (Backtracking)
    private void collect(Node node, StringBuilder prefix, List<String> out, int limit) {
        if (out.size() >= limit) {
            return;
        }
        if (node.endOfWord) {
            out.add(prefix.toString());
        }
        for (Map.Entry<Character, Node> e : node.children.entrySet()) {
            prefix.append(e.getKey());
            collect(e.getValue(), prefix, out, limit);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
}
