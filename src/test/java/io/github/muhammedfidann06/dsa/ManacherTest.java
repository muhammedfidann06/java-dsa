package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.string.Manacher;
import org.junit.jupiter.api.Test;

import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ManacherTest {

    @Test
    void beispiele() {
        assertEquals("anana", Manacher.longestPalindrome("bananas"));
        assertEquals("abba", Manacher.longestPalindrome("xabbay"));
        assertEquals("a", Manacher.longestPalindrome("a"));
        assertEquals("", Manacher.longestPalindrome(""));
        assertEquals("racecar", Manacher.longestPalindrome("racecar"));
    }

    @Test
    void anzahlPalindrome() {
        assertEquals(3, Manacher.countPalindromes("abc"));  // a, b, c
        assertEquals(6, Manacher.countPalindromes("aaa"));  // a×3, aa×2, aaa
        assertEquals(0, Manacher.countPalindromes(""));
    }

    @Test
    void zufallsVergleichMitBruteForce() {
        SplittableRandom r = new SplittableRandom(11);
        for (int t = 0; t < 300; t++) {
            StringBuilder sb = new StringBuilder();
            int len = r.nextInt(25);
            for (int i = 0; i < len; i++) {
                sb.append((char) ('a' + r.nextInt(3)));
            }
            String s = sb.toString();
            int bestLen = 0;
            long count = 0;
            for (int i = 0; i < s.length(); i++) {
                for (int j = i + 1; j <= s.length(); j++) {
                    if (isPalindrome(s.substring(i, j))) {
                        count++;
                        bestLen = Math.max(bestLen, j - i);
                    }
                }
            }
            String got = Manacher.longestPalindrome(s);
            assertEquals(bestLen, got.length(), s);
            assertTrue(isPalindrome(got) && s.contains(got), s);
            assertEquals(count, Manacher.countPalindromes(s), s);
        }
    }

    private static boolean isPalindrome(String s) {
        return new StringBuilder(s).reverse().toString().equals(s);
    }
}
