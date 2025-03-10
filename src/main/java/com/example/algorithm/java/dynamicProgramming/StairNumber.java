package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StairNumber {
    private static final int MOD = 1000000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int result = solve(n);

        System.out.println(result);
        br.close();
    }

    private static int solve(int n) {
        int[][][] dp = new int[n][10][4];

        initializeDP(dp);
        computeDP(dp, n);

        return calculateResult(dp, n);
    }

    private static void initializeDP(int[][][] dp) {
        dp[0][0][0] = 1;
        for (int i = 1; i < 9; i++) {
            dp[0][i][1] = 1;
        }
        dp[0][9][2] = 1;
    }

    private static void computeDP(int[][][] dp, int n) {
        for (int len = 1; len < n; len++) {
            for (int digit = 0; digit <= 9; digit++) {
                if (digit == 0) {
                    dp[len][0][3] = (dp[len - 1][1][3] + dp[len - 1][1][2]) % MOD;
                    dp[len][0][0] = (dp[len - 1][1][0] + dp[len - 1][1][1]) % MOD;
                } else if (digit == 9) {
                    dp[len][9][3] = (dp[len - 1][8][0] + dp[len - 1][8][3]) % MOD;
                    dp[len][9][2] = (dp[len - 1][8][1] + dp[len - 1][8][2]) % MOD;
                } else {
                    for (int k = 0; k < 4; k++) {
                        dp[len][digit][k] = (dp[len - 1][digit - 1][k] + dp[len - 1][digit + 1][k]) % MOD;
                    }
                }
            }
        }
    }

    private static int calculateResult(int[][][] dp, int n) {
        int result = 0;
        for (int digit = 1; digit < 10; digit++) {
            result = (result + dp[n - 1][digit][3]) % MOD;
        }
        return result;
    }
}

/*
10

1
*/