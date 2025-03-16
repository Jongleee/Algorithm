package com.example.algorithm.java.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Sudoku {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[][] board = new int[9][9];

        for (int i = 0; i < 9; i++) {
            char[] row = br.readLine().toCharArray();
            for (int j = 0; j < 9; j++) {
                board[i][j] = row[j] - '0';
            }
        }

        dfs(board, 0);

        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int num : row) {
                sb.append(num);
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }

    private static boolean dfs(int[][] board, int depth) {
        if (depth == 81) {
            return true;
        }

        int row = depth / 9;
        int col = depth % 9;

        if (board[row][col] != 0) {
            return dfs(board, depth + 1);
        }

        for (int num = 1; num <= 9; num++) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;
                if (dfs(board, depth + 1)) {
                    return true;
                }
                board[row][col] = 0;
            }
        }

        return false;
    }

    private static boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == num || board[row][i] == num) {
                return false;
            }
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }
}

/*
103000509
002109400
000704000
300502006
060000050
700803004
000401000
009205800
804000107

143628579
572139468
986754231
391542786
468917352
725863914
237481695
619275843
854396127
*/