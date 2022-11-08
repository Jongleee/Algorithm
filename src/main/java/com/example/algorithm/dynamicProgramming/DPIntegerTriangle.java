package com.example.algorithm.dynamicProgramming;

public class DPIntegerTriangle {
    public int solution(int[][] triangle) {
        //new int[][]{{7}, {3, 8}, {8, 1, 0}, {2, 7, 4, 4}, {4, 5, 2, 6, 5}}
        int[][] dp = new int[triangle.length][triangle.length];
        dp[0][0] = triangle[0][0];

        int answer = 0;
        for (int i = 1; i < triangle.length; i++) {
            dp[i][0] = dp[i - 1][0] + triangle[i][0];
            dp[i][i] = dp[i - 1][i - 1] + triangle[i][i];

            for (int j = 1; j <= i; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1]) + triangle[i][j];
                answer = Math.max(answer, dp[i][j]);
            }

        }
        return answer;
    }
}
