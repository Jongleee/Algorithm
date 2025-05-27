package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class TreeAndPathLength {
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        if (n == 1) {
            System.out.println(s == 0 ? 1 : 0);
            return;
        }

        int[] combies = buildCombies(n);
        int[][] dp = initDp(n, s);
        dp[0][0] = 1;

        System.out.println(getDp(n - 2, s, combies, dp));
    }

    private static int[] buildCombies(int n) {
        int[] combies = new int[n - 1];
        for (int i = 1; i < n - 1; i++) {
            combies[i] = i * (i + 1) / 2;
        }
        return combies;
    }

    private static int[][] initDp(int n, int s) {
        int[][] dp = new int[n + 1][s + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= s; j++) {
                dp[i][j] = INF;
            }
        }
        return dp;
    }

    private static int getDp(int n, int s, int[] combies, int[][] dp) {
        if (dp[n][s] != INF) {
            return dp[n][s];
        }

        dp[n][s] = 0;
        for (int i = 1; i <= n && combies[i] <= s; i++) {
            if (getDp(n - i, s - combies[i], combies, dp) != 0) {
                dp[n][s] = 1;
                break;
            }
        }
        return dp[n][s];
    }
}

/*
5 4

1


3 2

0
*/