package com.example.algorithm.java.dynamicProgramming;

public class AbangardeTiling {
    static final int MOD = 1000000007;

    public int solution(int n) {
        long[] dp = new long[100002];
        long[] sum = new long[100002];

        dp[0] = dp[1] = 1;
        dp[2] = 3;
        dp[3] = 10;
        dp[4] = 23;
        dp[5] = 62;
        dp[6] = 170;

        sum[0] = sum[1] = 1;
        sum[2] = 3;
        sum[3] = 11;
        sum[4] = 24;
        sum[5] = 65;
        sum[6] = 181;

        for (int i = 7; i <= n; i++) {
            dp[i] = dp[i - 1] % MOD;
            dp[i] += (dp[i - 2] * 2) % MOD;
            dp[i] += (dp[i - 3] * 5) % MOD;
            dp[i] += (sum[i - 4] * 2) % MOD;
            dp[i] += (sum[i - 5] * 2) % MOD;
            dp[i] += (sum[i - 6] * 4) % MOD;

            dp[i] %= MOD;

            sum[i] = (dp[i] + sum[i - 3]) % MOD;
        }

        return (int) dp[n];
    }

    // @Test
    // public void 정답() {
    // Assertions.assertEquals(3,solution(2));

    // Assertions.assertEquals(10,solution(3));
    // }
}
