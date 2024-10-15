package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BreakWallsAndMove {
    static StringBuilder sb = new StringBuilder();

    static int[] dx = { 1, 0, -1, 0 };
    static int[] dy = { 0, 1, 0, -1 };

    static int n;
    static int m;
    static char[][] board;

    static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new char[n][m];
        for (int i = 0; i < n; i++) {
            board[i] = br.readLine().toCharArray();
        }

        int[][] distStart = bfs(0, 0);
        int[][] distEnd = bfs(n - 1, m - 1);

        int ans = distStart[n - 1][m - 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == '1') {
                    ans = Math.min(ans, distStart[i][j] + distEnd[i][j]);
                }
            }
        }
        System.out.println(ans == 1000001 ? -1 : ans + 1);
    }

    static int[][] bfs(int startX, int startY) {
        int[][] dist = new int[n][m];
        for (int[] row : dist) {
            Arrays.fill(row, 1000001);
        }
        ArrayDeque<Position> queue = new ArrayDeque<>();
        queue.add(new Position(startX, startY));
        dist[startX][startY] = 0;

        while (!queue.isEmpty()) {
            Position pos = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = pos.x + dx[i];
                int ny = pos.y + dy[i];
                if (nx < 0 || nx >= n || ny < 0 || ny >= m)
                    continue;
                if (dist[nx][ny] != 1000001)
                    continue;
                dist[nx][ny] = dist[pos.x][pos.y] + 1;
                if (board[nx][ny] == '0')
                    queue.add(new Position(nx, ny));
            }
        }
        return dist;
    }
}

/*
6 4
0100
1110
1000
0000
0111
0000

15


4 4
0111
1111
1111
1110

-1
 */