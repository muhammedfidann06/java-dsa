package io.github.muhammedfidann06.dsa.string;

/**
 * Manacher: das längste Palindrom in einem String finden – in O(n) statt O(n²).
 *
 * <p>Trick 1: Zwischen alle Zeichen kommt ein Trennzeichen, dann haben gerade und ungerade
 * Palindrome beide eine Mitte ("abba" -> "#a#b#b#a#").
 * Trick 2: Ein Palindrom ist symmetrisch – innerhalb des rechtesten bekannten Palindroms
 * kennen wir den Radius schon vom Spiegelpunkt.
 */
public final class Manacher {

    private Manacher() {
    }

    /** Das längste zusammenhängende Palindrom (bei Gleichstand das erste). */
    public static String longestPalindrome(String s) {
        if (s.isEmpty()) {
            return "";
        }
        int[] p = radii(s);
        int best = 0;
        int bestCenter = 0;
        for (int i = 0; i < p.length; i++) {
            if (p[i] > best) {
                best = p[i];
                bestCenter = i;
            }
        }
        // Radius im erweiterten String = Länge im Original
        int start = (bestCenter - best) / 2;
        return s.substring(start, start + best);
    }

    /** Anzahl aller palindromischen Teilstrings (Positionen zählen einzeln). O(n). */
    public static long countPalindromes(String s) {
        long total = 0;
        for (int r : radii(s)) {
            total += (r + 1) / 2; // jede Mitte liefert so viele Palindrome
        }
        return total;
    }

    /** p[i] = Radius des Palindroms um Position i im erweiterten String (Länge 2n+1). */
    private static int[] radii(String s) {
        int m = 2 * s.length() + 1;
        char[] t = new char[m]; // gerade Positionen = Trennzeichen (bleibt '\0')
        for (int i = 0; i < s.length(); i++) {
            t[2 * i + 1] = s.charAt(i);
        }
        int[] p = new int[m];
        int center = 0;
        int right = 0; // rechter Rand des Palindroms, das am weitesten reicht
        for (int i = 0; i < m; i++) {
            if (i < right) {
                p[i] = Math.min(right - i, p[2 * center - i]); // Spiegelpunkt nutzen
            }
            // Gleiche Parität links und rechts -> Trennzeichen treffen nur auf Trennzeichen
            while (i - p[i] - 1 >= 0 && i + p[i] + 1 < m && t[i - p[i] - 1] == t[i + p[i] + 1]) {
                p[i]++;
            }
            if (i + p[i] > right) {
                center = i;
                right = i + p[i];
            }
        }
        return p;
    }
}
