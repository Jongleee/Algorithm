package com.example.algorithm.java.grundyNumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class WallMakingGame {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int height = Integer.parseInt(st.nextToken());
        int width = Integer.parseInt(st.nextToken());

        int[][] board = new int[height + 1][width + 1];
        int[][][][] grundyMemo = new int[height + 2][height + 2][width + 2][width + 2];

        for (int i = 1; i <= height; i++) {
            String row = br.readLine();
            for (int j = 1; j <= width; j++) {
                board[i][j] = (row.charAt(j - 1) == 'X') ? 1 : 0;
            }
        }

        initializeMemo(grundyMemo);

        int grundy = calculateGrundy(1, height, 1, width, board, grundyMemo, height, width);
        System.out.println(grundy != 0 ? "First" : "Second");
    }

    private static void initializeMemo(int[][][][] memo) {
        for (int[][][] d1 : memo) {
            for (int[][] d2 : d1) {
                for (int[] d3 : d2) {
                    Arrays.fill(d3, -1);
                }
            }
        }
    }

    private static int calculateGrundy(int r1, int r2, int c1, int c2, int[][] board, int[][][][] memo, int height,
            int width) {
        if (r1 < 1 || c1 < 1 || r2 > height || c2 > width || r1 > r2 || c1 > c2)
            return 0;
        if (memo[r1][r2][c1][c2] != -1)
            return memo[r1][r2][c1][c2];

        Set<Integer> grundySet = new HashSet<>();
        for (int i = r1; i <= r2; i++) {
            for (int j = c1; j <= c2; j++) {
                if (board[i][j] == 1)
                    continue;

                int g1 = calculateGrundy(r1, i - 1, c1, j - 1, board, memo, height, width);
                int g2 = calculateGrundy(r1, i - 1, j + 1, c2, board, memo, height, width);
                int g3 = calculateGrundy(i + 1, r2, c1, j - 1, board, memo, height, width);
                int g4 = calculateGrundy(i + 1, r2, j + 1, c2, board, memo, height, width);

                grundySet.add(g1 ^ g2 ^ g3 ^ g4);
            }
        }

        int mex = 0;
        while (grundySet.contains(mex)) {
            mex++;
        }

        return memo[r1][r2][c1][c2] = mex;
    }
}

/*
2 2
..
..

Second


4 5
X....
...X.
.....
.....

First
*/