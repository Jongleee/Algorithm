package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MirrorInstallation {
    static final int[][] DIRECTIONS = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

    static class Point {
        public final int row;
        public final int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public static boolean isValid(int row, int col, int size) {
            return row >= 0 && row < size && col >= 0 && col < size;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(br.readLine());

        char[][] map = new char[size][];
        List<Point> targets = new ArrayList<>(2);
        for (int i = 0; i < size; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; targets.size() < 2 && j < size; j++) {
                if (map[i][j] == '#') {
                    targets.add(new Point(i, j));
                }
            }
        }

        int result = bfs(map, targets);
        System.out.println(result);
    }

    private static int bfs(char[][] map, List<Point> targets) {
        int size = map.length;
        Queue<Point> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[size][size];

        Point start = targets.get(0);
        Point end = targets.get(1);
        queue.add(start);
        visited[start.row][start.col] = true;

        int depth = 0;

        while (!queue.isEmpty()) {
            int stepSize = queue.size();

            for (int i = 0; i < stepSize; i++) {
                Point current = queue.poll();

                for (int[] direction : DIRECTIONS) {
                    int row = current.row;
                    int col = current.col;

                    while (true) {
                        row += direction[0];
                        col += direction[1];

                        if (!Point.isValid(row, col, size) || map[row][col] == '*')
                            break;

                        if (row == end.row && col == end.col)
                            return depth;

                        if (!visited[row][col]) {
                            visited[row][col] = true;
                            if (map[row][col] == '!') {
                                queue.add(new Point(row, col));
                            }
                        }
                    }
                }
            }

            depth++;
        }

        return Integer.MAX_VALUE;
    }
}

/*
5
***#*
*.!.*
*!.!*
*.!.*
*#***

2
*/