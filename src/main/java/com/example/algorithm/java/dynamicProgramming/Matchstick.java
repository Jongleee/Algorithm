package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Matchstick {
    static long[] dp;
    static final int[] ARR = {1, 7, 4, 2, 0, 8};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());

        dp = new long[101];
        Arrays.fill(dp, Long.MAX_VALUE);

        dp[2] = 1;
        dp[3] = 7;
        dp[4] = 4;
        dp[5] = 2;
        dp[6] = 6;
        dp[7] = 8;
        dp[8] = 10;

        for (int i = 9; i <= 100; i++) {
            for (int j = 2; j <= 7; j++) {
                String temp = String.valueOf(dp[i - j]) + ARR[j - 2];
                dp[i] = Math.min(Long.parseLong(temp), dp[i]);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(br.readLine());
            sb.append(dp[n]).append(" ");
            if (n % 2 == 0) {
                sb.append(getMaxNumber(n / 2));
            } else {
                sb.append("7").append(getMaxNumber((n - 3) / 2));
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
    }

    private static String getMaxNumber(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append("1");
        }
        return sb.toString();
    }
}

/*
4
3
6
7
15

7 7
6 111
8 711
108 7111111
 */