package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DanceDanceRevolution {
    private static final int MAX = 5;

    public static void main(String[] args) throws IOException {
        final int diff = '0';
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        int length = input.length() - 1;

        if (length == 0) {
            System.out.print("0");
            return;
        }

        int size = length >> 1;
        int[] chart = new int[size];
        for (int i = 0; i < size; i++) {
            chart[i] = input.charAt(i << 1) - diff;
        }

        int[][] indexMap = new int[MAX][MAX];
        int index = 0;
        for (int i = 0; i < MAX; i++) {
            for (int j = i + 1; j < MAX; j++) {
                indexMap[i][j] = index++;
            }
        }

        int[][] dp = new int[index][size];
        System.out.print(getMinimumMoves(0, chart[0], 1, chart, size, indexMap, dp) + 2);
    }

    private static int getMinimumMoves(int left, int right, int depth, int[] chart, int size, int[][] indexMap,
            int[][] dp) {
        if (depth == size) {
            return 0;
        }

        int idx = indexMap[Math.min(left, right)][Math.max(left, right)];
        if (dp[idx][depth] != 0) {
            return dp[idx][depth];
        }

        int note = chart[depth];
        if (note == left || note == right) {
            dp[idx][depth] = getMinimumMoves(left, right, depth + 1, chart, size, indexMap, dp) + 1;
            return dp[idx][depth];
        }

        int moveLeft = getMinimumMoves(note, right, depth + 1, chart, size, indexMap, dp) + getMoveCost(left, note);
        int moveRight = getMinimumMoves(left, note, depth + 1, chart, size, indexMap, dp) + getMoveCost(right, note);
        dp[idx][depth] = Math.min(moveLeft, moveRight);
        return dp[idx][depth];
    }

    private static int getMoveCost(int from, int to) {
        if (from == 0) {
            return 2;
        }
        return (Math.abs(from - to) & 1) == 1 ? 3 : 4;
    }
}

/*
1 2 2 4 0

8
*/