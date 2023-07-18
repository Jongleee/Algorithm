package com.example.algorithm.java.dynamicProgramming;

public class FibonacciNumbers {
    public long solution(int n) {
        long[] dp = new long[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i < dp.length; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % 1234567;
        }

        return dp[n];
    }
    
    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(2, solution(3));
    //     Assertions.assertEquals(5, solution(5));
    // }
}
