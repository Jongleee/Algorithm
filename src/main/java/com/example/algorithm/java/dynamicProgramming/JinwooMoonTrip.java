package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class JinwooMoonTrip {
    static int n;
    static int m;
    static int[][] matrix;
    static int[][][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        matrix = new int[n][m];
        dp = new int[n][m][3];

        initializeMatrix(br);

        calculateMinimumCost();

        int ans = calculateMinimumFinalCost();

        System.out.println(ans);
    }

    private static void initializeMatrix(BufferedReader br) throws IOException {
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                matrix[i][j] = Integer.parseInt(st.nextToken());
                if (i == 0) {
                    Arrays.fill(dp[i][j], matrix[i][j]);
                } else {
                    Arrays.fill(dp[i][j], Integer.MAX_VALUE);
                }
            }
        }
    }

    private static void calculateMinimumCost() {
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (j != 0) {
                    dp[i][j][0] = Math.min(dp[i - 1][j - 1][1], dp[i - 1][j - 1][2]) + matrix[i][j];
                }
                dp[i][j][1] = Math.min(dp[i - 1][j][0], dp[i - 1][j][2]) + matrix[i][j];
                if (j != m - 1) {
                    dp[i][j][2] = Math.min(dp[i - 1][j + 1][0], dp[i - 1][j + 1][1]) + matrix[i][j];
                }
            }
        }
    }

    private static int calculateMinimumFinalCost() {
        int ans = Integer.MAX_VALUE;
        for (int j = 0; j < m; j++) {
            int minValue = dp[n - 1][j][0];
            for (int k = 1; k < 3; k++) {
                minValue = Math.min(minValue, dp[n - 1][j][k]);
            }
            ans = Math.min(ans, minValue);
        }
        return ans;
    }
}

/*
6 4
5 8 5 1
3 5 8 4
9 77 65 5
2 1 5 2
5 98 1 5
4 95 67 58

29
 */