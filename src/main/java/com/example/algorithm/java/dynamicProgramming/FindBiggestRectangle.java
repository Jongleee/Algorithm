package com.example.algorithm.java.dynamicProgramming;

public class FindBiggestRectangle {

    public static int solution(int[][] board) {
        int[][] dp = new int[board.length + 1][board[0].length + 1];
        int maxLength = 0;
        for (int i = 1; i <= board.length; i++) {
            for (int j = 1; j <= board[0].length; j++) {
                if (board[i - 1][j - 1] == 0)
                    continue;
                int min = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
                dp[i][j] = min + 1;
                maxLength = Math.max(maxLength, min + 1);
            }
        }
        return maxLength * maxLength;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[][] { { 0, 0, 0, 0 } }));
    }
}
