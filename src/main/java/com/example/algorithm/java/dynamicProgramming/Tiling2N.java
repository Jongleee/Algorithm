package com.example.algorithm.java.dynamicProgramming;

public class Tiling2N {
    public static int solution(int n) {
        final int MOD = 1000000007;
        long[] dp = new long[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] % MOD + dp[i - 2] % MOD;
        }
        return (int) dp[n];
    }

    public static void main(String[] args) {
        System.out.println(solution(3));
        System.out.println(solution(4));
        System.out.println(solution(5));
    }
}
