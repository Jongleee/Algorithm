package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Bomberman {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int rows = Integer.parseInt(st.nextToken());
        int cols = Integer.parseInt(st.nextToken());
        int time = Integer.parseInt(st.nextToken());

        char[][] map = readInitialMap(br, rows, cols);

        if (time <= 1) {
            printMap(map);
            return;
        }

        char[][] resultMap = simulateBombExplosion(map, rows, cols, time);
        printMap(resultMap);
    }

    private static char[][] readInitialMap(BufferedReader br, int rows, int cols) throws IOException {
        char[][] map = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            map[i] = br.readLine().toCharArray();
        }
        return map;
    }

    private static char[][] simulateBombExplosion(char[][] map, int rows, int cols, int time) {
        if (time % 2 == 0) {
            return fillWithBombs(rows, cols);
        } else {
            boolean firstExplosion = (time % 4 == 3);
            return firstExplosion ? performExplosion(map, rows, cols)
                    : performExplosion(performExplosion(map, rows, cols), rows, cols);
        }
    }

    private static char[][] fillWithBombs(int rows, int cols) {
        char[][] filledMap = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                filledMap[i][j] = 'O';
            }
        }
        return filledMap;
    }

    private static char[][] performExplosion(char[][] map, int rows, int cols) {
        char[][] explodedMap = new char[rows][cols];
        int[] dr = { 1, 0, -1, 0 };
        int[] dc = { 0, 1, 0, -1 };

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                explodedMap[i][j] = 'O';
            }
        }

        markMap(map, rows, cols, explodedMap, dr, dc);

        return explodedMap;
    }

    private static void markMap(char[][] map, int rows, int cols, char[][] explodedMap, int[] dr, int[] dc) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == 'O') {
                    explodedMap[i][j] = '.';
                    for (int d = 0; d < 4; d++) {
                        int ni = i + dr[d];
                        int nj = j + dc[d];
                        if (isValidPosition(ni, nj, rows, cols)) {
                            explodedMap[ni][nj] = '.';
                        }
                    }
                }
            }
        }
    }

    private static boolean isValidPosition(int row, int col, int rows, int cols) {
        return row >= 0 && col >= 0 && row < rows && col < cols;
    }

    private static void printMap(char[][] map) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : map) {
            sb.append(row).append("\n");
        }
        System.out.print(sb);
    }
}

/*
6 7 3
.......
...O...
....O..
.......
OO.....
OO.....

OOO.OOO
OO...OO
OOO...O
..OO.OO
...OOOO
...OOOO


6 7 4
.......
...O...
....O..
.......
OO.....
OO.....

OOOOOOO
OOOOOOO
OOOOOOO
OOOOOOO
OOOOOOO
OOOOOOO
*/