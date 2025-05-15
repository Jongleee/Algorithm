package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PiedPiper {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int rowCount = Integer.parseInt(st.nextToken());
        int colCount = Integer.parseInt(st.nextToken());

        char[][] grid = new char[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            grid[i] = br.readLine().toCharArray();
        }

        int[][] visited = new int[rowCount][colCount];
        int safeZoneCount = 0;
        int visitId = 0;

        int[] dx = { 0, 1, 0, -1 };
        int[] dy = { 1, 0, -1, 0 };

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                if (visited[r][c] == 0) {
                    visitId++;
                    if (dfs(r, c, visitId, grid, visited, dx, dy, rowCount, colCount)) {
                        safeZoneCount++;
                    }
                }
            }
        }

        System.out.println(safeZoneCount);
    }

    private static boolean dfs(int r, int c, int visitId,
            char[][] grid, int[][] visited,
            int[] dx, int[] dy,
            int rowCount, int colCount) {
        if (visited[r][c] == visitId) {
            return true;
        }
        if (visited[r][c] != 0) {
            return false;
        }
        visited[r][c] = visitId;

        int dir = getDirection(grid[r][c]);
        int nr = r + dx[dir];
        int nc = c + dy[dir];

        if (nr < 0 || nr >= rowCount || nc < 0 || nc >= colCount) {
            return false;
        }
        return dfs(nr, nc, visitId, grid, visited, dx, dy, rowCount, colCount);
    }

    private static int getDirection(char ch) {
        switch (ch) {
            case 'R':
                return 0;
            case 'D':
                return 1;
            case 'L':
                return 2;
            case 'U':
                return 3;
            default:
                throw new IllegalArgumentException("Invalid direction: " + ch);
        }
    }
}

/*
3 4
DLLL
DRLU
RRRU

2
*/