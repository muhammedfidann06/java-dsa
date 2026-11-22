package io.github.muhammedfidann06.dsa.string;

import java.util.ArrayList;
import java.util.List;

/**
 * Z-Algorithmus: für jede Position die Länge des längsten gemeinsamen Präfixes mit dem Anfang.
 *
 * <p>Beispiel "aabxaab" -> [7, 1, 0, 0, 3, 1, 0]. Läuft in O(n), weil das "Z-Fenster" [l, r)
 * nur nach rechts wandert und bekannte Werte wiederverwendet werden.
 */
public final class ZAlgorithm {

    private ZAlgorithm() {
    }

    /** z[i] = Länge des längsten gemeinsamen Präfixes von s und s.substring(i). z[0] = n. */
    public static int[] zArray(String s) {
        int n = s.length();
        int[] z = new int[n];
        if (n == 0) {
            return z;
        }
        z[0] = n;
        int l = 0;
        int r = 0; // [l, r) = am weitesten rechts reichendes Fenster, das mit dem Präfix übereinstimmt
        for (int i = 1; i < n; i++) {
            if (i < r) {
                z[i] = Math.min(r - i, z[i - l]); // Wert aus dem Fenster übernehmen
            }
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
                z[i]++;
            }
            if (i + z[i] > r) {
                l = i;
                r = i + z[i];
            }
        }
        return z;
    }

    /** Alle Startpositionen von pattern in text (auch überlappend). O(n + m). */
    public static List<Integer> search(String text, String pattern) {
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("leeres Muster");
        }
        int m = pattern.length();
        // Ohne Trennzeichen: ab Position m liegt der Text, z >= m bedeutet einen Treffer.
        int[] z = zArray(pattern + text);
        List<Integer> hits = new ArrayList<>();
        for (int i = m; i < z.length; i++) {
            if (z[i] >= m) {
                hits.add(i - m);
            }
        }
        return hits;
    }
}
