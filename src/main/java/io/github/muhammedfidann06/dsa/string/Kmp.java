package io.github.muhammedfidann06.dsa.string;

import java.util.ArrayList;
import java.util.List;

/**
 * Knuth-Morris-Pratt: ein Muster in einem Text finden – in O(n + m).
 *
 * <p>Die einfache Suche fängt bei einem Fehler wieder ganz von vorne an (schlimmstenfalls O(n·m)).
 * KMP merkt sich mit der Präfix-Funktion, wie viel vom Muster schon passt, und springt.
 */
public final class Kmp {

    private Kmp() {
    }

    /**
     * Präfix-Funktion: pi[i] = Länge des längsten echten Präfixes von pattern[0..i],
     * das auch ein Suffix davon ist. Beispiel "ABAB" -> [0, 0, 1, 2].
     */
    public static int[] prefixFunction(String pattern) {
        int[] pi = new int[pattern.length()];
        int k = 0;
        for (int i = 1; i < pattern.length(); i++) {
            while (k > 0 && pattern.charAt(i) != pattern.charAt(k)) {
                k = pi[k - 1]; // zurückspringen statt von vorne anfangen
            }
            if (pattern.charAt(i) == pattern.charAt(k)) {
                k++;
            }
            pi[i] = k;
        }
        return pi;
    }

    /** Alle Startpositionen (0-basiert) von pattern in text, auch überlappende. */
    public static List<Integer> search(String text, String pattern) {
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("leeres Muster");
        }
        int[] pi = prefixFunction(pattern);
        List<Integer> hits = new ArrayList<>();
        int k = 0;
        for (int i = 0; i < text.length(); i++) {
            while (k > 0 && text.charAt(i) != pattern.charAt(k)) {
                k = pi[k - 1];
            }
            if (text.charAt(i) == pattern.charAt(k)) {
                k++;
            }
            if (k == pattern.length()) {
                hits.add(i - k + 1);
                k = pi[k - 1]; // weitersuchen (Überlappung erlaubt)
            }
        }
        return hits;
    }
}
