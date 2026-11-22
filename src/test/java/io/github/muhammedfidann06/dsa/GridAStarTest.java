package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.graph.GridAStar;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GridAStarTest {

    @Test
    void kleinesLabyrinth() {
        String[] maze = {
                "...#....",
                ".#.#.##.",
                ".#...#..",
                ".####.#.",
                "......#."};
        GridAStar.Result res = GridAStar.shortestPath(maze, 0, 0, 4, 7);
        assertEquals(bfs(maze, 0, 0, 4, 7), res.length());
        assertTrue(res.length() > 0);
    }

    @Test
    void unerreichbarUndWand() {
        String[] grid = {".#.", ".#.", ".#."};
        assertEquals(-1, GridAStar.shortestPath(grid, 0, 0, 0, 2).length());
        assertEquals(-1, GridAStar.shortestPath(grid, 0, 1, 0, 0).length()); // Start in der Wand
        assertEquals(0, GridAStar.shortestPath(grid, 2, 2, 2, 2).length());
    }

    @Test
    void offenesFeldUntersuchtNichtAlleFelder() {
        String[] open = new String[30];
        Arrays.fill(open, ".".repeat(30));
        GridAStar.Result res = GridAStar.shortestPath(open, 0, 0, 29, 29);
        assertEquals(58, res.length());
        assertTrue(res.expanded() < 30 * 30, "A* sollte nicht alle 900 Felder untersuchen");
    }

    @Test
    void zufallsGitterGegenBfs() {
        SplittableRandom r = new SplittableRandom(21);
        for (int t = 0; t < 200; t++) {
            int rows = 2 + r.nextInt(12);
            int cols = 2 + r.nextInt(12);
            String[] grid = new String[rows];
            for (int i = 0; i < rows; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < cols; j++) {
                    sb.append(r.nextInt(100) < 30 ? '#' : '.');
                }
                grid[i] = sb.toString();
            }
            int sr = r.nextInt(rows);
            int sc = r.nextInt(cols);
            int gr = r.nextInt(rows);
            int gc = r.nextInt(cols);
            assertEquals(bfs(grid, sr, sc, gr, gc), GridAStar.shortestPath(grid, sr, sc, gr, gc).length());
        }
    }

    /** Einfache Breitensuche als Referenz. */
    private static int bfs(String[] grid, int sr, int sc, int gr, int gc) {
        if (grid[sr].charAt(sc) == '#' || grid[gr].charAt(gc) == '#') {
            return -1;
        }
        int rows = grid.length;
        int cols = grid[0].length();
        int[][] dist = new int[rows][cols];
        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }
        ArrayDeque<int[]> q = new ArrayDeque<>();
        dist[sr][sc] = 0;
        q.add(new int[]{sr, sc});
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int[] d : dirs) {
                int nr = cur[0] + d[0];
                int nc = cur[1] + d[1];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr].charAt(nc) != '#' && dist[nr][nc] < 0) {
                    dist[nr][nc] = dist[cur[0]][cur[1]] + 1;
                    q.add(new int[]{nr, nc});
                }
            }
        }
        return dist[gr][gc];
    }
}
