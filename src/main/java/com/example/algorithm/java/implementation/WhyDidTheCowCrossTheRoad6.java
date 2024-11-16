package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class WhyDidTheCowCrossTheRoad6 {
    private static final int[] dx = { -1, 1, 0, 0 };
    private static final int[] dy = { 0, 0, -1, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        boolean[][][] roads = new boolean[n + 1][n + 1][4];
        boolean[][] visited = new boolean[n + 1][n + 1];
        boolean[][] cows = new boolean[n + 1][n + 1];

        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());
            addRoad(roads, x1, y1, x2, y2);
        }

        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            cows[x][y] = true;
        }

        int result = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (!visited[i][j]) {
                    int connectedCows = countConnectedCows(i, j, n, visited, cows, roads);
                    result += connectedCows * (k - connectedCows);
                }
            }
        }

        System.out.println(result / 2);
    }

    private static void addRoad(boolean[][][] roads, int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            if (y1 > y2) {
                roads[x1][y1][2] = true;
                roads[x2][y2][3] = true;
            } else {
                roads[x1][y1][3] = true;
                roads[x2][y2][2] = true;
            }
        } else {
            if (x1 > x2) {
                roads[x1][y1][0] = true;
                roads[x2][y2][1] = true;
            } else {
                roads[x1][y1][1] = true;
                roads[x2][y2][0] = true;
            }
        }
    }

    private static int countConnectedCows(int x, int y, int n, boolean[][] visited, boolean[][] cows,
            boolean[][][] roads) {
        visited[x][y] = true;
        int count = cows[x][y] ? 1 : 0;

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx < 1 || ny < 1 || nx > n || ny > n)
                continue;
            if (visited[nx][ny] || roads[x][y][i])
                continue;

            count += countConnectedCows(nx, ny, n, visited, cows, roads);
        }

        return count;
    }
}

/*
3 3 3
2 2 2 3
3 3 3 2
3 3 2 3
3 3
2 2
2 3

2
*/