package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PopulationMovement {
    static int[] dr = {1, 0, -1, 0};
    static int[] dc = {0, -1, 0, 1};
    static int n;
    static int l;
    static int r;
    static int[][] map;
    static int[][] visitMap;
    static int[] unionLand;
    static int count;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        n = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        visitMap = new int[n][n];
        unionLand = new int[n * n];
        count = 0;

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                visitMap[i][j] = -1;
            }
        }

        int answer = 0;
        while (true) {
            
            int unionIndex = 0;

            if (isMoved(unionIndex, false)) {
                answer++;
            } else {
                break;
            }
            resetMap();
        }
        System.out.println(answer);
    }

    private static boolean isMoved(int unionIndex, boolean hasMoved) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (visitMap[i][j] == -1) {
                    unionLand[unionIndex] = 0;
                    searchUnion(i, j, unionIndex);
                    if (count > 1) {
                        hasMoved = true;
                    }
                    unionLand[unionIndex] /= count;
                    unionIndex++;
                    count = 0;
                }
            }
        }
        return hasMoved;
    }

    private static void searchUnion(int x, int y, int union) {
        count++;
        visitMap[x][y] = union;
        unionLand[union] += map[x][y];

        for (int i = 0; i < 4; i++) {
            int nr = x + dr[i];
            int nc = y + dc[i];
            if (!isWithinBoundary(nr, nc) || visitMap[nr][nc] != -1) {
                continue;
            }
            int diff = Math.abs(map[x][y] - map[nr][nc]);
            if (diff >= l && diff <= r) {
                searchUnion(nr, nc, union);
            }
        }
    }

    private static boolean isWithinBoundary(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }

    private static void resetMap() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                map[i][j] = unionLand[visitMap[i][j]];
                visitMap[i][j] = -1;
            }
        }
    }
}

/*
2 20 50
50 30
20 40

1


2 40 50
50 30
20 40

0


2 20 50
50 30
30 40

1
 */