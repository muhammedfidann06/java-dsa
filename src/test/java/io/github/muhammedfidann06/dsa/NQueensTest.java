package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.backtracking.NQueens;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NQueensTest {

    @Test
    void bekannteAnzahlen() {
        // OEIS A000170
        long[] expected = {1, 1, 0, 0, 2, 10, 4, 40, 92, 352, 724};
        for (int n = 0; n < expected.length; n++) {
            assertEquals(expected[n], NQueens.count(n), "n = " + n);
        }
    }

    @Test
    void loesungIstGueltig() {
        for (int n = 1; n <= 12; n++) {
            Optional<int[]> sol = NQueens.solve(n);
            if (n == 2 || n == 3) {
                assertTrue(sol.isEmpty(), "n = " + n);
                continue;
            }
            int[] q = sol.orElseThrow();
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    assertTrue(q[i] != q[j], "gleiche Spalte");
                    assertTrue(Math.abs(q[i] - q[j]) != j - i, "gleiche Diagonale");
                }
            }
        }
    }

    @Test
    void ungueltigesN() {
        assertThrows(IllegalArgumentException.class, () -> NQueens.count(-1));
        assertThrows(IllegalArgumentException.class, () -> NQueens.solve(31));
    }
}
