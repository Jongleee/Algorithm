package com.example.algorithm.java.bruteForce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WheresBessie {
    private static final int[][] DIRECTIONS = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
    static char[][] map;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        map = new char[n][];
        for (int i = 0; i < n; i++) {
            map[i] = br.readLine().toCharArray();
        }
        boolean[][][][] counted = new boolean[n][n][n][n];
        System.out.println(countPCLRegions(counted, n));
    }

    private static int countPCLRegions(boolean[][][][] counted, int size) {
        int pclCount = 0;
        for (int height = size - 1; height >= 0; height--) {
            for (int width = size - 1; width >= 0; width--) {
                for (int startRow = 0; startRow + height < size; startRow++) {
                    for (int startCol = 0; startCol + width < size; startCol++) {
                        if (counted[startRow][startCol][startRow + height][startCol + width])
                            continue;
                        if (isPCL(map, startRow, startCol, startRow + height, startCol + width, size)) {
                            markRegion(counted, startRow, startCol, startRow + height, startCol + width);
                            pclCount++;
                        }
                    }
                }
            }
        }
        return pclCount;
    }

    private static void markRegion(boolean[][][][] counted, int startRow, int startCol, int endRow, int endCol) {
        for (int r1 = startRow; r1 <= endRow; r1++) {
            for (int c1 = startCol; c1 <= endCol; c1++) {
                for (int r2 = r1; r2 <= endRow; r2++) {
                    for (int c2 = c1; c2 <= endCol; c2++) {
                        counted[r1][c1][r2][c2] = true;
                    }
                }
            }
        }
    }

    private static boolean isPCL(char[][] map, int startRow, int startCol, int endRow, int endCol, int size) {
        visited = new boolean[size][size];
        int[] colorCounts = new int['Z' - 'A' + 1];
        int distinctColors = 0;

        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                if (!visited[row][col]) {
                    char color = map[row][col];
                    if (colorCounts[color - 'A'] == 0)
                        distinctColors++;
                    if (distinctColors > 2)
                        return false;
                    exploreRegion(startRow, startCol, endRow, endCol, row, col, color);
                    colorCounts[color - 'A']++;
                }
            }
        }
        int firstColor = 0, secondColor = 0;
        for (int count : colorCounts) {
            if (count == 0)
                continue;
            if (firstColor == 0)
                firstColor = count;
            else
                secondColor = count;
        }
        return (firstColor == 1 && secondColor > 1) || (firstColor > 1 && secondColor == 1);
    }

    private static void exploreRegion(int startRow, int startCol, int endRow,
            int endCol, int row, int col, char color) {
        if (row < startRow || row > endRow || col < startCol || col > endCol || visited[row][col]
                || map[row][col] != color) {
            return;
        }
        visited[row][col] = true;
        for (int[] direction : DIRECTIONS) {
            exploreRegion(startRow, startCol, endRow, endCol, row + direction[0], col + direction[1],
                    color);
        }
    }
}

/*
4
ABBC
BBBC
AABB
ABBC

2
*/