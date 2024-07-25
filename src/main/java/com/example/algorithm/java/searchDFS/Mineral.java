package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Mineral {
    static int rows;
    static int cols;
    static char[][] cave;
    static boolean[][] onAir;
    static int[][] directions = { { 0, -1 }, { -1, 0 }, { 0, 1 }, { 1, 0 } };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer tokens = new StringTokenizer(br.readLine());
        rows = Integer.parseInt(tokens.nextToken());
        cols = Integer.parseInt(tokens.nextToken());

        cave = new char[rows][cols];
        for (int row = 0; row < rows; row++) {
            String line = br.readLine();
            for (int col = 0; col < cols; col++) {
                cave[row][col] = line.charAt(col);
            }
        }

        int numThrows = Integer.parseInt(br.readLine());
        tokens = new StringTokenizer(br.readLine());
        for (int i = 0; i < numThrows; i++) {
            int level = Integer.parseInt(tokens.nextToken());
            throwBar(rows - level, i);
            dropCluster();
        }

        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                sb.append(cave[row][col]);
            }
            sb.append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    static void throwBar(int level, int throwCount) {
        if (throwCount % 2 == 0) {
            for (int col = 0; col < cols; col++) {
                if (cave[level][col] == 'x') {
                    cave[level][col] = '.';
                    break;
                }
            }
        } else {
            for (int col = cols - 1; col >= 0; col--) {
                if (cave[level][col] == 'x') {
                    cave[level][col] = '.';
                    break;
                }
            }
        }
    }

    static void dropCluster() {
        onAir = new boolean[rows][cols];
        for (int col = 0; col < cols; col++) {
            if (cave[rows - 1][col] == 'x' && !onAir[rows - 1][col]) {
                onAir[rows - 1][col] = true;
                findConnectedMinerals(rows - 1, col);
            }
        }

        int minDrop = Integer.MAX_VALUE;
        for (int col = cols - 1; col >= 0; col--) {
            minDrop = Math.min(minDrop, findMinDropDistance(col));
        }

        if (minDrop == Integer.MAX_VALUE)
            return;

        for (int row = rows - 2; row >= 0; row--) {
            for (int col = 0; col < cols; col++) {
                if (cave[row][col] == 'x' && !onAir[row][col]) {
                    cave[row + minDrop][col] = 'x';
                    cave[row][col] = '.';
                }
            }
        }
    }

    static int findMinDropDistance(int col) {
        int dropIdx = -1;
        int groundIdx = -1;
        boolean isDroppable = false;

        for (int row = rows - 1; row >= 0; row--) {
            if (cave[row][col] == 'x') {
                if (onAir[row][col]) {
                    groundIdx = row;
                } else {
                    if (groundIdx == -1) {
                        dropIdx = row;
                        groundIdx = rows - 1;
                    } else {
                        isDroppable = true;
                        dropIdx = row;
                    }
                    break;
                }
            }
        }

        if (dropIdx == -1 || groundIdx == -1)
            return Integer.MAX_VALUE;
        return isDroppable ? groundIdx - dropIdx - 1 : groundIdx - dropIdx;
    }

    static void findConnectedMinerals(int row, int col) {
        for (int[] delta : directions) {
            int newRow = row + delta[0];
            int newCol = col + delta[1];
            if (isValid(newRow, newCol) && cave[newRow][newCol] == 'x' && !onAir[newRow][newCol]) {
                onAir[newRow][newCol] = true;
                findConnectedMinerals(newRow, newCol);
            }
        }
    }

    static boolean isValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
}

/*
5 6
......
..xx..
..x...
..xx..
.xxxx.
1
3

......
......
..xx..
..xx..
.xxxx.


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
*/