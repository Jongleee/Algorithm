package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class WizardSharkAndRainstorm {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][n];
        boolean[][] hasCloud = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        initializeClouds(hasCloud, n);

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int direction = Integer.parseInt(st.nextToken()) - 1;
            int move = Integer.parseInt(st.nextToken());

            moveClouds(hasCloud, direction, move, n);
            rain(map, hasCloud, n);
            waterCopy(map, hasCloud, n);
            createNewClouds(hasCloud, map, n);
        }

        System.out.println(calculateWaterSum(map));
    }

    private static void initializeClouds(boolean[][] hasCloud, int n) {
        hasCloud[n - 1][0] = true;
        hasCloud[n - 1][1] = true;
        hasCloud[n - 2][0] = true;
        hasCloud[n - 2][1] = true;
    }

    private static void moveClouds(boolean[][] hasCloud, int direction, int move, int n) {
        int[] dr = { 0, -1, -1, -1, 0, 1, 1, 1 };
        int[] dc = { -1, -1, 0, 1, 1, 1, 0, -1 };

        boolean[][] nextCloud = new boolean[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (hasCloud[r][c]) {
                    int nr = (r + move * dr[direction] + 51 * n) % n;
                    int nc = (c + move * dc[direction] + 51 * n) % n;
                    nextCloud[nr][nc] = true;
                }
            }
        }
        System.arraycopy(nextCloud, 0, hasCloud, 0, n);
    }

    private static void rain(int[][] map, boolean[][] hasCloud, int n) {
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (hasCloud[r][c]) {
                    map[r][c]++;
                }
            }
        }
    }

    private static void waterCopy(int[][] map, boolean[][] hasCloud, int n) {
        int[] dr = { -1, -1, 1, 1 };
        int[] dc = { -1, 1, -1, 1 };

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (hasCloud[r][c]) {
                    int count = 0;
                    for (int k = 0; k < 4; k++) {
                        int nr = r + dr[k];
                        int nc = c + dc[k];
                        if (condition(map, n, nr, nc)) {
                            count++;
                        }
                    }
                    map[r][c] += count;
                }
            }
        }
    }

    private static boolean condition(int[][] map, int n, int nr, int nc) {
        return nr >= 0 && nr < n && nc >= 0 && nc < n && map[nr][nc] > 0;
    }

    private static void createNewClouds(boolean[][] hasCloud, int[][] map, int n) {
        boolean[][] newClouds = new boolean[n][n];

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (!hasCloud[r][c] && map[r][c] >= 2) {
                    map[r][c] -= 2;
                    newClouds[r][c] = true;
                }
            }
        }
        System.arraycopy(newClouds, 0, hasCloud, 0, n);
    }

    private static int calculateWaterSum(int[][] map) {
        int sum = 0;
        for (int[] row : map) {
            for (int water : row) {
                sum += water;
            }
        }
        return sum;
    }
}

/*
5 4
0 0 1 0 2
2 3 2 1 0
4 3 2 9 0
1 0 2 9 0
8 8 2 1 0
1 3
3 4
8 1
4 8

77


5 8
100 100 100 100 100
100 100 100 100 100
100 100 100 100 100
100 100 100 100 100
100 100 100 100 100
8 1
7 1
6 1
5 1
4 1
3 1
2 1
1 1

2657
*/