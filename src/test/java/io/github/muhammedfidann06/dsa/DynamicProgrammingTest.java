package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.dp.DynamicProgramming;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DynamicProgrammingTest {

    @Test
    void editDistanz() {
        assertEquals(3, DynamicProgramming.editDistance("kitten", "sitting"));
        assertEquals(0, DynamicProgramming.editDistance("abc", "abc"));
        assertEquals(3, DynamicProgramming.editDistance("", "abc"));
        assertEquals(1, DynamicProgramming.editDistance("Hagenberg", "Hagenburg"));
    }

    @Test
    void laengsteGemeinsameTeilfolge() {
        String lcs = DynamicProgramming.longestCommonSubsequence("ABCBDAB", "BDCABA");
        assertEquals(4, lcs.length());
        assertEquals("", DynamicProgramming.longestCommonSubsequence("ABC", "XYZ"));
    }

    @Test
    void rucksack() {
        // Gewichte 1,3,4,5 – Werte 1,4,5,7 – Kapazität 7: beste Wahl 3+4 = Wert 9
        assertEquals(9, DynamicProgramming.knapsack(new int[]{1, 3, 4, 5}, new int[]{1, 4, 5, 7}, 7));
        assertEquals(0, DynamicProgramming.knapsack(new int[]{5}, new int[]{10}, 4));
        assertThrows(IllegalArgumentException.class,
                () -> DynamicProgramming.knapsack(new int[]{1}, new int[]{1, 2}, 3));
    }
}
