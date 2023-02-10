package com.example.algorithm.java.dynamicProgramming;

public class Knapsack {

    public static int knapSack(int weight, int[] weights, int[] val, int n) {
        int i;
        int w;
        int[][] dp = new int[n + 1][weight + 1];

        for (i = 0; i <= n; i++) {
            for (w = 0; w <= weight; w++) {
                if (i == 0 || w == 0)
                    dp[i][w] = 0;
                else if (weights[i - 1] <= w)
                    dp[i][w] = Math.max(val[i - 1] + dp[i - 1][w - weights[i - 1]], dp[i - 1][w]);
                else
                    dp[i][w] = dp[i - 1][w];
            }
        }

        return dp[n][weight];
    }

    public static void main(String[] args) {
        int[] val = new int[] {60, 100, 120};
        int[] weights = new int[] {10, 20, 30};
        int weight = 50;
        int n = val.length;
        System.out.println(knapSack(weight, weights, val, n));
    }
}