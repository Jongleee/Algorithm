package com.example.algorithm.java.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Monitoring {
    static int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 },
            { 0, 1 }, { 1, 0 } };
    static int n, m;
    static int[][] office;
    static List<CCTV> cctvs;
    static List<int[]> walls;
    static boolean[][] isWatched;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        office = new int[n][m];
        List<Integer> results = new ArrayList<>();
        int blindSpotCount;

        cctvs = new ArrayList<>();
        walls = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < m; j++) {
                office[i][j] = Integer.parseInt(st.nextToken());
                if (office[i][j] == 6) {
                    walls.add(new int[] { i, j });
                } else if (office[i][j] > 0 && office[i][j] < 6) {
                    cctvs.add(new CCTV(office[i][j], i, j));
                }
            }
        }

        for (int direction = 0; direction < 4; direction++) {
            isWatched = new boolean[n][m];
            blindSpotCount = 0;
            Collections.sort(cctvs);
            blindSpotCount = countBlindSpot(blindSpotCount, direction);
            results.add(blindSpotCount);

            blindSpotCount = 0;
            isWatched = new boolean[n][m];
            Collections.reverse(cctvs);
            blindSpotCount = countBlindSpot(blindSpotCount, direction);
            results.add(blindSpotCount);
        }

        System.out.println(Collections.min(results));
    }

    private static int countBlindSpot(int blindSpotCount, int direction) {
        for (CCTV cctv : cctvs) {
            monitor(cctv.row, cctv.col, cctv.type, direction);
        }
        for (int[] wall : walls) {
            isWatched[wall[0]][wall[1]] = true;
        }
        for (boolean[] row : isWatched) {
            for (boolean watched : row) {
                if (!watched)
                    blindSpotCount++;
            }
        }
        return blindSpotCount;
    }

    static class CCTV implements Comparable<CCTV> {
        int type;
        int row;
        int col;

        public CCTV(int type, int row, int col) {
            this.type = type;
            this.row = row;
            this.col = col;
        }

        @Override
        public int compareTo(CCTV o) {
            return this.type - o.type;
        }
    }

    public static void monitor(int r, int c, int cctvType, int startDirection) {
        int maxCoverage = 0;
        int bestDirection = 0;

        switch (cctvType) {
            case 1:
                for (int i = startDirection; i < 4 + startDirection; i++) {
                    int coverage = countCoverage(r, c, i);
                    if (maxCoverage < coverage) {
                        bestDirection = i;
                        maxCoverage = coverage;
                    }
                }
                setWatched(r, c, bestDirection);
                break;

            case 2:
                for (int i = startDirection; i < 2 + startDirection; i++) {
                    int coverage = countCoverage(r, c, i) + countCoverage(r, c, i + 2);
                    if (maxCoverage < coverage) {
                        bestDirection = i;
                        maxCoverage = coverage;
                    }
                }
                setWatched(r, c, bestDirection);
                setWatched(r, c, bestDirection + 2);
                break;

            case 3:
                for (int i = startDirection; i < 4 + startDirection; i++) {
                    int coverage = countCoverage(r, c, i) + countCoverage(r, c, i + 1);
                    if (maxCoverage < coverage) {
                        bestDirection = i;
                        maxCoverage = coverage;
                    }
                }
                setWatched(r, c, bestDirection);
                setWatched(r, c, bestDirection + 1);
                break;

            case 4:
                for (int i = startDirection; i < 4 + startDirection; i++) {
                    int coverage = countCoverage(r, c, i) + countCoverage(r, c, i + 1) + countCoverage(r, c, i + 2);
                    if (maxCoverage < coverage) {
                        bestDirection = i;
                        maxCoverage = coverage;
                    }
                }
                setWatched(r, c, bestDirection);
                setWatched(r, c, bestDirection + 1);
                setWatched(r, c, bestDirection + 2);
                break;

            default:
                setWatched(r, c, 0);
                setWatched(r, c, 1);
                setWatched(r, c, 2);
                setWatched(r, c, 3);
        }
    }

    public static int countCoverage(int r, int c, int direction) {
        int coverage = 0;
        int row = r;
        int col = c;

        while (true) {
            row += directions[direction][0];
            col += directions[direction][1];
            if (row < 0 || col < 0 || row >= n || col >= m)
                break;
            if (office[row][col] == 6)
                break;
            if (office[row][col] == 0 && !isWatched[row][col]) {
                coverage++;
            }
        }
        return coverage;
    }

    public static void setWatched(int r, int c, int direction) {
        int row = r;
        int col = c;
        isWatched[row][col] = true;

        while (true) {
            row += directions[direction][0];
            col += directions[direction][1];
            if (row < 0 || col < 0 || row >= n || col >= m)
                break;
            if (office[row][col] == 6)
                break;
            if (office[row][col] == 0) {
                isWatched[row][col] = true;
            }
        }
    }
}

/*
4 6
0 0 0 0 0 0
0 0 0 0 0 0
0 0 1 0 6 0
0 0 0 0 0 0

20


6 6
0 0 0 0 0 0
0 2 0 0 0 0
0 0 0 0 6 0
0 6 0 0 2 0
0 0 0 0 0 0
0 0 0 0 0 5

15
*/