package com.example.algorithm.practice;

public class PassengerHeaven {

    static int mod = 20170805;

    public static void main(String[] args) {
        System.out.println(
                solution(3, 6, new int[][] { { 0, 2, 0, 0, 0, 2 }, { 0, 0, 2, 0, 1, 0 }, { 1, 0, 0, 2, 2, 0 } }));
    }

    public static int solution(int m, int n, int[][] cityMap) {
        int[][][] dp = new int[m + 1][n + 1][2];
        dp[0][0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                switch (cityMap[i][j]) {
                    case 0:
                        dp[i + 1][j][0] += (dp[i][j][0] + dp[i][j][1]) % mod;
                        dp[i][j + 1][1] += (dp[i][j][0] + dp[i][j][1]) % mod;
                        break;
                    case 2:
                        dp[i + 1][j][0] += dp[i][j][0] % mod;
                        dp[i][j + 1][1] += dp[i][j][1] % mod;
                        break;
                    default:
                        break;
                }
            }
        }
        return (dp[m - 1][n - 1][0] + dp[m - 1][n - 1][1]) % mod;
    }
}
