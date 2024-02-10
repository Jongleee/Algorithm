package com.example.algorithm.java.dynamicProgramming;

public class OptimalMatrixMultiplication {
    public int solution(int[][] matrixSizes) {
        int numMatrices = matrixSizes.length;
        int[][] dp = new int[numMatrices][numMatrices];

        for (int i = 0; i < numMatrices; i++) {
            for (int j = 0; j < numMatrices; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int len = 0; len < numMatrices; len++) {
            for (int start = 0; start < numMatrices - len; start++) {
                int end = start + len;
                if (start == end) {
                    dp[start][end] = 0;
                } else {
                    for (int k = start; k < end; k++) {
                        dp[start][end] = Math.min(dp[start][end], dp[start][k] + dp[k + 1][end] +
                                matrixSizes[start][0] * matrixSizes[k][1] * matrixSizes[end][1]);
                    }
                }
            }
        }

        return dp[0][numMatrices - 1];
    }

    // @Test
    // void 정답() {
    //     Assertions.assertEquals(270, solution(new int[][] { { 5, 3 }, { 3, 10 }, { 10, 6 } }));
    // }
}
