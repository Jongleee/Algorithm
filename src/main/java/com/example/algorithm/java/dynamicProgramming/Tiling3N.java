package com.example.algorithm.java.dynamicProgramming;

public class Tiling3N {
    public static int solution(int n) {
        final int MOD = 1000000007;
        int index = n / 2;
        if (n % 2 != 0)
            return 0;
        long[] dp = new long[n + 1];
        dp[1] = 3;
        dp[2] = 11;
        for (int i = 3; i <= index; i++) {
            dp[i] = (4 * dp[i - 1] % MOD - dp[i - 2] + MOD) % MOD;
        }
        return (int) dp[index];
    }

    public static void main(String[] args) {
        System.out.println(solution(12));
        System.out.println(solution(14));
        System.out.println(solution(16));
    }
}
