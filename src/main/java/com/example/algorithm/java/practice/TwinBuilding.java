package com.example.algorithm.java.practice;

public class TwinBuilding {
    public int solution(int n, int count) {
        if (n == count) {
            return 1;
        }

        long[][] dp = new long[n + 1][count + 1];
        dp[0][0] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= count; j++) {
                dp[i][j] = calculateDpValue(dp, i, j);
            }
        }

        return (int) dp[n][count];
    }

    private long calculateDpValue(long[][] dp, int i, int j) {
        return (dp[i - 1][j - 1] + 2L * (i - 1) * dp[i - 1][j]) % 1000000007;
    }

    // @Test
    // void 정답() {
    //     Assertions.assertEquals(799483221, solution(50, 25));
    //     Assertions.assertEquals(8, solution(3, 1));
    //     Assertions.assertEquals(6, solution(3, 2));
    //     Assertions.assertEquals(1, solution(3, 3));
    // }
}
