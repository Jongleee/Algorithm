package com.example.algorithm.java.implement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Mineral2 {
    private static char[][] map;
    private static int n, m;
    private static int[][] dir = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
    private static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tk = new StringTokenizer(br.readLine());
        n = Integer.parseInt(tk.nextToken());
        m = Integer.parseInt(tk.nextToken());

        map = new char[n + 1][m + 1];
        for (int i = n; i > 0; i--) {
            String line = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j + 1] = line.charAt(j);
            }
        }

        visited = new boolean[n + 1][m + 1];
        int k = Integer.parseInt(br.readLine());
        tk = new StringTokenizer(br.readLine());

        int direction = -1;
        for (int i = 0; i < k; i++) {
            direction *= -1;
            int height = Integer.parseInt(tk.nextToken());
            if (breakMineral(height, direction)) {
                processClusters();
            }
        }

        for (int i = n; i > 0; i--) {
            for (int j = 1; j <= m; j++) {
                bw.write(map[i][j]);
            }
            bw.write("\n");
        }

        bw.flush();
        bw.close();
    }

    private static boolean breakMineral(int height, int direction) {
        int start = (direction == 1) ? 1 : m;
        int end = (direction == 1) ? m + 1 : 0;
        int step = direction;

        for (int j = start; j != end; j += step) {
            if (map[height][j] == 'x') {
                map[height][j] = '.';
                return true;
            }
        }
        return false;
    }

    private static void processClusters() {
        resetVisited();
        for (int i = 1; i <= m; i++) {
            if (map[1][i] == 'x' && !visited[1][i]) {
                dfs(1, i);
            }
        }

        List<int[]> floatingCluster = findFloatingCluster();
        if (!floatingCluster.isEmpty()) {
            dropCluster(floatingCluster);
        }
    }

    private static void resetVisited() {
        for (int i = 1; i <= n; i++) {
            Arrays.fill(visited[i], false);
        }
    }

    private static void dfs(int r, int c) {
        visited[r][c] = true;
        for (int[] d : dir) {
            int nr = r + d[0], nc = c + d[1];
            if (nr > 0 && nr <= n && nc > 0 && nc <= m && map[nr][nc] == 'x' && !visited[nr][nc]) {
                dfs(nr, nc);
            }
        }
    }

    private static List<int[]> findFloatingCluster() {
        List<int[]> cluster = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (map[i][j] == 'x' && !visited[i][j]) {
                    cluster.add(new int[]{i, j});
                    map[i][j] = '.';
                }
            }
        }
        return cluster;
    }

    private static int findMinDropHeight(List<int[]> cluster) {
        int minDrop = n;
        for (int[] cell : cluster) {
            int r = cell[0], c = cell[1];
            int drop = 0;
            while (r - drop > 0 && map[r - drop][c] == '.') drop++;
            minDrop = Math.min(minDrop, drop - 1);
        }
        return minDrop;
    }

    private static void dropCluster(List<int[]> cluster) {
        int dropHeight = findMinDropHeight(cluster);
        for (int[] cell : cluster) {
            map[cell[0] - dropHeight][cell[1]] = 'x';
        }
    }
}

/*
7 6
......
......
xx....
.xx...
..xx..
...xx.
....x.
2
6 4

......
......
......
......
..xx..
xx.xx.
.x..x.


8 8
........
........
...x.xx.
...xxx..
..xxx...
..x.xxx.
..x...x.
.xxx..x.
5
6 6 4 3 1

........
........
........
........
.....x..
..xxxx..
..xxx.x.
..xxxxx.
*/