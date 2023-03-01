package com.example.algorithm.java.practice;

public class TwinBuilding {
    public static int solution(int n, int count) {
        final int MOD = 1000000007;
        if (n == count)
            return 1;
        long[][] dp = new long[n + 1][count + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= count; j++) {
                dp[i][j] = (dp[i - 1][j - 1] + 2 * (i - 1) * dp[i - 1][j]) % MOD;
            }
        }
        return (int) dp[n][count];
    }

    public static void main(String[] args) {
        System.out.println(solution(50, 25));
    }
}
