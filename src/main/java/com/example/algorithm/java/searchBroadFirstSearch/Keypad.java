package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Keypad {
    private static final int[] dx = { -1, 1, 0, 0 };
    private static final int[] dy = { 0, 0, 1, -1 };

    static class Route {
        int cnt;
        int x;
        int y;

        public Route(int cnt, int x, int y) {
            this.cnt = cnt;
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        char[][] board = new char[n][m];
        for (int i = 0; i < n; i++) {
            board[i] = br.readLine().toCharArray();
        }

        String target = br.readLine();
        int targetLength = target.length();
        int[][][] dp = new int[targetLength][n][m];
        int result = 0;

        Deque<Route> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == target.charAt(targetLength - 1)) {
                    dp[0][i][j] = 1;
                    queue.add(new Route(1, i, j));
                }
            }
        }

        while (!queue.isEmpty()) {
            Route current = queue.poll();
            int cnt = current.cnt;
            int x = current.x;
            int y = current.y;

            if (cnt == targetLength)
                continue;

            for (int step = 1; step <= k; step++) {
                for (int dir = 0; dir < 4; dir++) {
                    int nx = x + dx[dir] * step;
                    int ny = y + dy[dir] * step;

                    if (isValid(nx, ny, n, m) && board[nx][ny] == target.charAt(targetLength - 1 - cnt)) {
                        if (dp[cnt][nx][ny] == 0) {
                            calculateRoutes(cnt, nx, ny, k, n, m, dp);
                            queue.add(new Route(cnt + 1, nx, ny));
                            if (cnt == targetLength - 1) {
                                result += dp[cnt][nx][ny];
                            }
                        }
                    }
                }
            }
        }

        System.out.println(result);
    }

    private static boolean isValid(int x, int y, int n, int m) {
        return x >= 0 && y >= 0 && x < n && y < m;
    }

    private static void calculateRoutes(int cnt, int x, int y, int k, int n, int m, int[][][] dp) {
        for (int step = 1; step <= k; step++) {
            for (int dir = 0; dir < 4; dir++) {
                int nx = x + dx[dir] * step;
                int ny = y + dy[dir] * step;

                if (isValid(nx, ny, n, m)) {
                    dp[cnt][x][y] += dp[cnt - 1][nx][ny];
                }
            }
        }
    }
}

/*
4 4 1
KAKT
XEAS
YRWU
ZBQP
BREAK

3
*/