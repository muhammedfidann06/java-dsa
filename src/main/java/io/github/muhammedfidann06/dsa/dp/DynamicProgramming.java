package io.github.muhammedfidann06.dsa.dp;

/**
 * Klassische Aufgaben der dynamischen Programmierung.
 *
 * <p>Idee: Ein großes Problem in überlappende Teilprobleme zerlegen und jedes nur
 * EINMAL lösen (Tabelle). Dadurch wird aus exponentieller Zeit polynomielle.
 */
public final class DynamicProgramming {

    private DynamicProgramming() {
    }

    /**
     * Levenshtein-Distanz: minimale Anzahl von Einfügen, Löschen, Ersetzen.
     * Beispiel: "kitten" -> "sitting" = 3. O(n·m) Zeit, O(m) Speicher (nur zwei Zeilen).
     */
    public static int editDistance(String a, String b) {
        int[] prev = new int[b.length() + 1];
        int[] cur = new int[b.length() + 1];
        for (int j = 0; j <= b.length(); j++) {
            prev[j] = j; // aus "" wird b[0..j] durch j-mal Einfügen
        }
        for (int i = 1; i <= a.length(); i++) {
            cur[0] = i;
            for (int j = 1; j <= b.length(); j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                cur[j] = Math.min(Math.min(prev[j] + 1, cur[j - 1] + 1), prev[j - 1] + cost);
            }
            int[] tmp = prev;
            prev = cur;
            cur = tmp;
        }
        return prev[b.length()];
    }

    /**
     * Längste gemeinsame Teilfolge (nicht zusammenhängend), z. B. für diff-Programme.
     * "ABCBDAB" und "BDCABA" -> Länge 4, z. B. "BCBA".
     */
    public static String longestCommonSubsequence(String a, String b) {
        int[][] t = new int[a.length() + 1][b.length() + 1];
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                t[i][j] = a.charAt(i - 1) == b.charAt(j - 1)
                        ? t[i - 1][j - 1] + 1
                        : Math.max(t[i - 1][j], t[i][j - 1]);
            }
        }
        // Rückwärts durch die Tabelle gehen, um eine Lösung zu bauen
        StringBuilder sb = new StringBuilder();
        int i = a.length();
        int j = b.length();
        while (i > 0 && j > 0) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                sb.append(a.charAt(i - 1));
                i--;
                j--;
            } else if (t[i - 1][j] >= t[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        return sb.reverse().toString();
    }

    /**
     * 0/1-Rucksack: maximaler Wert mit Gesamtgewicht ≤ capacity. Jeder Gegenstand höchstens einmal.
     * O(n·W) – "pseudo-polynomiell", weil W eine Zahl ist und keine Eingabelänge.
     */
    public static int knapsack(int[] weights, int[] values, int capacity) {
        if (weights.length != values.length) {
            throw new IllegalArgumentException("weights und values müssen gleich lang sein");
        }
        int[] best = new int[capacity + 1];
        for (int k = 0; k < weights.length; k++) {
            // Rückwärts, damit jeder Gegenstand nur einmal benutzt wird
            for (int w = capacity; w >= weights[k]; w--) {
                best[w] = Math.max(best[w], best[w - weights[k]] + values[k]);
            }
        }
        return best[capacity];
    }
}
