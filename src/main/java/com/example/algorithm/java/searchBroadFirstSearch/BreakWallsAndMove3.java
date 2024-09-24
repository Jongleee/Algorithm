package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BreakWallsAndMove3 {
    private int n;
    private int m;
    private int k;
    private char[][] grid;
    private int[][] visited;
    private final int[] dx = { 1, -1, 0, 0 };
    private final int[] dy = { 0, 0, 1, -1 };
    private int answer = -1;

    public static void main(String[] args) throws IOException {
        BreakWallsAndMove3 main = new BreakWallsAndMove3();
        main.run();
    }

    private void run() throws IOException {
        input();
        bfs();
        System.out.println(answer);
    }

    private void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        grid = new char[n][m];
        visited = new int[n][m];

        for (int i = 0; i < n; i++) {
            grid[i] = br.readLine().toCharArray();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                visited[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    private void bfs() {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] { 0, 0, 0, 1 });
        visited[0][0] = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            if (current[0] == n - 1 && current[1] == m - 1) {
                answer = current[3];
                return;
            }

            for (int i = 0; i < 4; i++) {
                int nx = current[0] + dx[i];
                int ny = current[1] + dy[i];

                if (nx < 0 || ny < 0 || nx >= n || ny >= m) {
                    continue;
                }

                if (visited[nx][ny] <= current[2]) {
                    continue;
                }

                if (grid[nx][ny] == '1') {
                    if (current[2] >= k || visited[nx][ny] <= current[2] + 1) {
                        continue;
                    }

                    if (current[3] % 2 == 0) {
                        queue.add(new int[] { current[0], current[1], current[2], current[3] + 1 });
                    } else {
                        visited[nx][ny] = current[2] + 1;
                        queue.add(new int[] { nx, ny, current[2] + 1, current[3] + 1 });
                    }
                } else {
                    visited[nx][ny] = current[2];
                    queue.add(new int[] { nx, ny, current[2], current[3] + 1 });
                }
            }
        }
    }
}

/*
1 4 1
0010

5


6 4 1
0100
1110
1000
0000
0111
0000

15


6 4 2
0100
1110
1000
0000
0111
0000

9
*/