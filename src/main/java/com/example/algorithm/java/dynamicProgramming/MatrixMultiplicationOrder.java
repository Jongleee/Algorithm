package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MatrixMultiplicationOrder {
    static int n;
    static int[] dimensions;
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        input();
        System.out.println(solve());
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        dimensions = new int[n + 1];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            dimensions[i] = Integer.parseInt(st.nextToken());
            dimensions[i + 1] = Integer.parseInt(st.nextToken());
        }
    }

    public static int solve() {
        int[][] dp = new int[n + 1][n + 1];

        for (int len = 1; len < n; len++) {
            for (int i = 1; i <= n - len; i++) {
                int j = i + len;
                dp[i][j] = INF;

                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j] + dimensions[i - 1] * dimensions[k] * dimensions[j];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }
        return dp[1][n];
    }
}

/*
3
5 3
3 2
2 6

90
*/