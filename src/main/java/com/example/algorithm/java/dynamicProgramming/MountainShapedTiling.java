package com.example.algorithm.java.dynamicProgramming;

public class MountainShapedTiling {
    private static final int MOD = 10007;
    
    public int solution(int n, int[] tops) {
        int[][] dp = new int[n + 1][2];
        dp[0][1] = 1;
        
        for (int i = 0; i < n; i++) {
            int top = tops[i];
            int k = i + 1;
            dp[k][0] = (dp[i][0] + dp[i][1]) % MOD;
            if (top == 0) {
                dp[k][1] = (dp[i][0] + dp[i][1] * 2) % MOD;
            } else {
                dp[k][1] = (dp[i][0] * 2 + dp[i][1] * 3) % MOD;
            }
        }
        
        return (dp[n][0] + dp[n][1]) % MOD;
    }

    // @Test
    // void 정답() {
    //     int[] n = { 4, 2, 10 };
    //     int[][] tops = { { 1, 1, 0, 1 },
    //             { 0, 1 },
    //             { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

    //     int[] result = { 149, 11, 7704 };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertEquals(result[i], solution(n[i], tops[i]));
    //     }
    // }
}
