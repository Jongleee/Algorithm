package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class FineDustGoodbye {
    private static final int[][] DIRECTIONS = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
    private static int[][] grid, tempGrid;
    private static int rows, cols;
    private static int purifierTop = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        rows = Integer.parseInt(st.nextToken());
        cols = Integer.parseInt(st.nextToken());
        int time = Integer.parseInt(st.nextToken());
        grid = new int[rows][cols];
        tempGrid = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < cols; j++) {
                int value = Integer.parseInt(st.nextToken());
                if (j == 0 && purifierTop == -1 && value == -1) {
                    purifierTop = i;
                }
                grid[i][j] = value;
            }
        }
        for (int turn = 1; turn <= time; turn++) {
            for (int[] arr : tempGrid) {
                Arrays.fill(arr, 0);
            }
            spreadDust();
            swapGrids();
            circulateAir();
        }
        System.out.println(getTotalDust());
    }

    private static void swapGrids() {
        int[][] temp = tempGrid;
        tempGrid = grid;
        grid = temp;
    }

    private static void spreadDust() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                spreadFromCell(i, j);
            }
        }
    }

    private static void spreadFromCell(int row, int col) {
        int value = grid[row][col];
        if (value == -1)
            tempGrid[row][col] = -1;
        if (value <= 0)
            return;
        int spreadAmount = value / 5;
        if (spreadAmount > 0) {
            for (int[] direction : DIRECTIONS) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];
                if (isValidPosition(newRow, newCol) && grid[newRow][newCol] != -1) {
                    value -= spreadAmount;
                    tempGrid[newRow][newCol] += spreadAmount;
                }
            }
        }
        tempGrid[row][col] += value;
    }

    private static void circulateAir() {
        int[] state = { purifierTop, 1, grid[purifierTop][1] };
        grid[purifierTop][1] = 0;
        for (int direction = 1, count = 0; count < 4; direction = (direction + 3) % 4, count++) {
            circulateAirInLine(state, direction);
        }
        state = new int[] { purifierTop + 1, 1, grid[purifierTop + 1][1] };
        grid[purifierTop + 1][1] = 0;
        for (int direction = 1, count = 0; count < 4; direction = (direction + 1) % 4, count++) {
            circulateAirInLine(state, direction);
        }
    }

    private static void circulateAirInLine(int[] state, int direction) {
        while (true) {
            int newRow = state[0] + DIRECTIONS[direction][0];
            int newCol = state[1] + DIRECTIONS[direction][1];
            if (!isValidPosition(newRow, newCol) || grid[newRow][newCol] == -1)
                break;
            int tempValue = grid[newRow][newCol];
            grid[newRow][newCol] = state[2];
            state[0] = newRow;
            state[1] = newCol;
            state[2] = tempValue;
        }
    }

    private static boolean isValidPosition(int row, int col) {
        return row >= 0 && col >= 0 && row < rows && col < cols;
    }

    private static int getTotalDust() {
        int totalDust = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] != -1) {
                    totalDust += grid[i][j];
                }
            }
        }
        return totalDust;
    }
}

/*
7 8 1
0 0 0 0 0 0 0 9
0 0 0 0 3 0 0 8
-1 0 5 0 0 0 22 0
-1 8 0 0 0 0 0 0
0 0 0 0 0 10 43 0
0 0 5 0 15 0 0 0
0 0 40 0 0 0 20 0

188
*/