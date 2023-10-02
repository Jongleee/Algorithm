package com.example.algorithm.java.searchBroadFirstSearch;

import java.util.LinkedList;
import java.util.Queue;

public class ColoringBook {
    private final int[] dx = { -1, 0, 1, 0 };
    private final int[] dy = { 0, 1, 0, -1 };
    private boolean[][] visited;

    public int[] solution(int m, int n, int[][] picture) {
        int numberOfArea = 0;
        int maxSizeOfOneArea = 0;
        visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] != 0 && !visited[i][j]) {
                    int size = bfs(m, n, i, j, picture);
                    maxSizeOfOneArea = Math.max(maxSizeOfOneArea, size);
                    numberOfArea++;
                }
            }
        }

        return new int[] { numberOfArea, maxSizeOfOneArea };
    }

    private int bfs(int m, int n, int startX, int startY, int[][] picture) {
        int size = 1;
        int targetColor = picture[startX][startY];
        Queue<Point> queue = new LinkedList<>();
        visited[startX][startY] = true;
        queue.offer(new Point(startX, startY));

        while (!queue.isEmpty()) {
            Point currentPoint = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nextX = currentPoint.getX() + dx[i];
                int nextY = currentPoint.getY() + dy[i];

                if (isValidPosition(m, n, nextX, nextY) && picture[nextX][nextY] == targetColor
                        && !visited[nextX][nextY]) {
                    queue.offer(new Point(nextX, nextY));
                    visited[nextX][nextY] = true;
                    size++;
                }
            }
        }

        return size;
    }

    private boolean isValidPosition(int m, int n, int x, int y) {
        return x >= 0 && x < m && y >= 0 && y < n;
    }

    private static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    // @Test
    // public void 정답() {
    //     int[][] p1 = { { 1, 1, 1, 0 }, { 1, 2, 2, 0 }, { 1, 0, 0, 1 }, { 0, 0, 0, 1 }, { 0, 0, 0, 3 }, { 0, 0, 0, 3 } };
    //     Assertions.assertArrayEquals(new int[] { 4, 5 }, solution(6, 4, p1));
    // }
}