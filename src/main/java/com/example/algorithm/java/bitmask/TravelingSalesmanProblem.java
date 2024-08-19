package com.example.algorithm.java.bitmask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class TravelingSalesmanProblem {
    private static final int INF = 15000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                int value = Integer.parseInt(st.nextToken());
                matrix[i][j] = (i != j && value == 0) ? INF : value;
            }
        }

        int[][] dp = new int[n][1 << (n - 1)];
        for (int i = 1; i < n; i++) {
            dp[i][0] = matrix[i][0];
        }

        int minDistance = INF;
        int fullBitmask = (1 << (n - 1)) - 1;
        for (int i = 1; i < n; i++) {
            int bitmask = fullBitmask & ~(1 << (i - 1));
            minDistance = Math.min(minDistance, tsp(dp, matrix, i, bitmask, n) + matrix[0][i]);
        }

        System.out.println(minDistance);
        br.close();
    }

    private static int tsp(int[][] dp, int[][] matrix, int current, int bitmask, int n) {
        if (dp[current][bitmask] != 0) {
            return dp[current][bitmask];
        }

        int minDistance = INF;
        for (int i = 1; i < n; i++) {
            if ((bitmask & (1 << (i - 1))) == 0) continue;

            int nextBitmask = bitmask & ~(1 << (i - 1));
            dp[i][nextBitmask] = tsp(dp, matrix, i, nextBitmask, n);
            minDistance = Math.min(minDistance, matrix[current][i] + dp[i][nextBitmask]);
        }

        dp[current][bitmask] = minDistance;
        return minDistance;
    }
}

/*
4
0 10 15 20
5 0 9 10
6 13 0 12
8 8 9 0

35
*/