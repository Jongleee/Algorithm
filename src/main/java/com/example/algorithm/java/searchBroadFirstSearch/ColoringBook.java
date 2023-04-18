package com.example.algorithm.java.searchBroadFirstSearch;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class Point {
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

public class ColoringBook {

    private static final int[] DX = { -1, 0, 1, 0 };
    private static final int[] DY = { 0, 1, 0, -1 };

    private static boolean[][] visited;

    public static int[] solution(int m, int n, int[][] picture) {
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

    private static int bfs(int m, int n, int x, int y, int[][] picture) {
        int size = 1;
        int target = picture[x][y];
        Queue<Point> queue = new LinkedList<>();

        visited[x][y] = true;
        queue.offer(new Point(x, y));

        while (!queue.isEmpty()) {
            Point p = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = p.getX() + DX[i];
                int ny = p.getY() + DY[i];

                if (onPicture(m, n, nx, ny) && picture[nx][ny] == target && !visited[nx][ny]) {
                    queue.offer(new Point(nx, ny));
                    visited[nx][ny] = true;
                    size++;
                }
            }
        }

        return size;
    }

    private static boolean onPicture(int m, int n, int nx, int ny) {
        return nx >= 0 && nx < m && ny >= 0 && ny < n;
    }

    public static void main(String[] args) {
        int[][] p1 = { { 1, 1, 1, 0 }, { 1, 2, 2, 0 }, { 1, 0, 0, 1 }, { 0, 0, 0, 1 }, { 0, 0, 0, 3 }, { 0, 0, 0, 3 } };
        System.out.println(Arrays.toString(solution(6, 4, p1)));
    }
}