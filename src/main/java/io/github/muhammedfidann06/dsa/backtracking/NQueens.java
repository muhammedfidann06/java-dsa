package io.github.muhammedfidann06.dsa.backtracking;

import java.util.Optional;

/**
 * Das n-Damen-Problem mit Backtracking: n Damen so auf ein n×n-Brett, dass keine eine andere schlägt.
 *
 * <p>Pro Zeile genau eine Dame. Belegte Spalten und Diagonalen merken wir uns als Bitmasken –
 * so ist "ist dieses Feld frei?" eine einzige UND-Verknüpfung.
 */
public final class NQueens {

    private NQueens() {
    }

    /** Anzahl aller Lösungen (n = 8 -> 92). */
    public static long count(int n) {
        check(n);
        return place(n, 0, 0, 0, 0);
    }

    private static long place(int n, int row, int cols, int diag1, int diag2) {
        if (row == n) {
            return 1;
        }
        int full = (1 << n) - 1;
        int free = full & ~(cols | diag1 | diag2);
        long total = 0;
        while (free != 0) {
            int bit = free & -free; // niedrigstes freies Feld
            free -= bit;
            // Diagonalen wandern in der nächsten Zeile eine Spalte nach links bzw. rechts
            total += place(n, row + 1, cols | bit, ((diag1 | bit) << 1) & full, (diag2 | bit) >> 1);
        }
        return total;
    }

    /** Eine Lösung: queens[zeile] = spalte. Leer, wenn es keine gibt (n = 2, 3). */
    public static Optional<int[]> solve(int n) {
        check(n);
        int[] queens = new int[n];
        return solveRow(queens, 0, new boolean[n], new boolean[2 * n], new boolean[2 * n])
                ? Optional.of(queens) : Optional.empty();
    }

    private static boolean solveRow(int[] queens, int row, boolean[] col, boolean[] d1, boolean[] d2) {
        int n = queens.length;
        if (row == n) {
            return true;
        }
        for (int c = 0; c < n; c++) {
            int a = row + c;          // "/"-Diagonale
            int b = row - c + n;      // "\"-Diagonale
            if (col[c] || d1[a] || d2[b]) {
                continue;
            }
            queens[row] = c;
            col[c] = d1[a] = d2[b] = true;
            if (solveRow(queens, row + 1, col, d1, d2)) {
                return true;
            }
            col[c] = d1[a] = d2[b] = false; // zurücknehmen = Backtracking
        }
        return false;
    }

    private static void check(int n) {
        if (n < 0 || n > 30) {
            throw new IllegalArgumentException("n muss zwischen 0 und 30 liegen");
        }
    }
}
