package com.example.algorithm.java.knapsack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OrdinaryKnapsack {
    static int n;
    static int k;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        n = Integer.parseInt(input[0]);
        k = Integer.parseInt(input[1]);
        dp = new int[k + 1];

        for (int i = 0; i < n; i++) {
            input = br.readLine().split(" ");
            int weight = Integer.parseInt(input[0]);
            int value = Integer.parseInt(input[1]);
            fillKnapsack(weight, value);
        }

        System.out.println(dp[k]);
    }

    public static void fillKnapsack(int weight, int value) {
        for (int i = k; i >= weight; i--) {
            if (dp[i] < dp[i - weight] + value) {
                dp[i] = dp[i - weight] + value;
            }
        }
    }
}

/*
4 7
6 13
4 8
3 6
5 12

14
 */