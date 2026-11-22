package io.github.muhammedfidann06.dsa.graph;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * A*-Suche auf einem Gitter ('#' = Wand, alles andere frei, Schritte nach oben/unten/links/rechts).
 *
 * <p>Wie Dijkstra, aber die Warteschlange ist nach g + h sortiert: g = bisherige Kosten,
 * h = Schätzung bis zum Ziel (hier Manhattan-Distanz). Weil h nie überschätzt, ist der Weg
 * trotzdem kürzest – A* untersucht aber meist viel weniger Felder.
 */
public final class GridAStar {

    /** Länge des kürzesten Wegs (−1 = unerreichbar) und Anzahl der untersuchten Felder. */
    public record Result(int length, int expanded) {
    }

    private static final int[] DR = {-1, 1, 0, 0};
    private static final int[] DC = {0, 0, -1, 1};

    private GridAStar() {
    }

    public static Result shortestPath(String[] grid, int startRow, int startCol, int goalRow, int goalCol) {
        if (!free(grid, startRow, startCol) || !free(grid, goalRow, goalCol)) {
            return new Result(-1, 0);
        }
        int[][] g = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            g[i] = new int[grid[i].length()];
            Arrays.fill(g[i], Integer.MAX_VALUE);
        }
        g[startRow][startCol] = 0;
        // Einträge: {f = g + h, g, zeile, spalte}; bei gleichem f zuerst das mit größerem g (näher am Ziel)
        PriorityQueue<int[]> open = new PriorityQueue<>(
                (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(b[1], a[1]));
        open.add(new int[]{manhattan(startRow, startCol, goalRow, goalCol), 0, startRow, startCol});
        int expanded = 0;
        while (!open.isEmpty()) {
            int[] cur = open.poll();
            int d = cur[1];
            int r = cur[2];
            int c = cur[3];
            if (d > g[r][c]) {
                continue; // veralteter Eintrag
            }
            expanded++;
            if (r == goalRow && c == goalCol) {
                return new Result(d, expanded);
            }
            for (int k = 0; k < 4; k++) {
                int nr = r + DR[k];
                int nc = c + DC[k];
                if (free(grid, nr, nc) && d + 1 < g[nr][nc]) {
                    g[nr][nc] = d + 1;
                    open.add(new int[]{d + 1 + manhattan(nr, nc, goalRow, goalCol), d + 1, nr, nc});
                }
            }
        }
        return new Result(-1, expanded);
    }

    private static boolean free(String[] grid, int r, int c) {
        return r >= 0 && r < grid.length && c >= 0 && c < grid[r].length() && grid[r].charAt(c) != '#';
    }

    private static int manhattan(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);
    }
}
