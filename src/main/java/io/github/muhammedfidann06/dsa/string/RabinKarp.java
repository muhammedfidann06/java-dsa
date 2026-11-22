package io.github.muhammedfidann06.dsa.string;

import java.util.ArrayList;
import java.util.List;

/**
 * Rabin-Karp: Mustersuche mit einem "rollenden" Hash.
 *
 * <p>Statt jedes Fenster Zeichen für Zeichen zu vergleichen, vergleichen wir zuerst Hashwerte.
 * Der Hash des nächsten Fensters wird in O(1) aus dem vorherigen berechnet
 * (ein Zeichen raus, ein Zeichen rein). Nur bei gleichem Hash prüfen wir genau –
 * so gibt es keine falschen Treffer. Durchschnitt O(n + m).
 */
public final class RabinKarp {

    private static final long MOD = 1_000_000_007L; // große Primzahl
    private static final long BASE = 131;

    private RabinKarp() {
    }

    /** Alle Startpositionen (0-basiert) von pattern in text, auch überlappende. */
    public static List<Integer> search(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        if (m == 0) {
            throw new IllegalArgumentException("leeres Muster");
        }
        List<Integer> hits = new ArrayList<>();
        if (m > n) {
            return hits;
        }
        // BASE^(m-1) mod MOD – das Gewicht des Zeichens, das links herausfällt
        long highPower = 1;
        for (int i = 1; i < m; i++) {
            highPower = highPower * BASE % MOD;
        }
        long patternHash = 0;
        long windowHash = 0;
        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * BASE + pattern.charAt(i)) % MOD;
            windowHash = (windowHash * BASE + text.charAt(i)) % MOD;
        }
        for (int start = 0; ; start++) {
            // Gleicher Hash -> genau vergleichen (Hash-Kollisionen sind möglich)
            if (windowHash == patternHash && text.regionMatches(start, pattern, 0, m)) {
                hits.add(start);
            }
            if (start + m >= n) {
                break;
            }
            // Fenster um eins verschieben: linkes Zeichen raus, neues Zeichen rein
            windowHash = (windowHash - text.charAt(start) * highPower % MOD + MOD) % MOD;
            windowHash = (windowHash * BASE + text.charAt(start + m)) % MOD;
        }
        return hits;
    }
}
