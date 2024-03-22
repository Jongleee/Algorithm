package com.example.algorithm.java.dynamicProgramming;

import java.util.Arrays;

public class AirConditioner {
    public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {
        int temp = temperature > t2 ? t1 - (temperature - t2) : temperature;
        int INF = 100001;

        t1 -= temp;
        t2 -= temp;

        int n = onboard.length;
        int[][] dp = new int[n][t2 + 2];
        for (int i = 0; i < n; i++)
            Arrays.fill(dp[i], INF);
        dp[0][0] = 0;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= t2 + 1; j++) {
                if (onboard[i] == 1 && (j < t1 || j > t2))
                    continue;
                int min = INF;
                if (j == 0) {
                    min = Math.min(min, dp[i - 1][j]);
                    if (j + 1 <= t2 + 1)
                        min = Math.min(min, dp[i - 1][j + 1]);
                } else {
                    int prev1 = j - 1 >= 0 ? dp[i - 1][j - 1] + a : INF;
                    int prev2 = dp[i - 1][j] + b;
                    int prev3 = j + 1 <= t2 + 1 ? dp[i - 1][j + 1] : INF;
                    min = Math.min(min, Math.min(prev1, Math.min(prev2, prev3)));
                }
                dp[i][j] = min;
            }
        }

        int result = Integer.MAX_VALUE;
        for (int j = 0; j <= t2 + 1; j++)
            result = Math.min(result, dp[n - 1][j]);
        return result;
    }

    // @Test
    // void 정답() {
    //     int[] temperature = { 28, -10, 11, 11 };
    //     int[] t1 = { 18, -5, 8, 8 };
    //     int[] t2 = { 26, 5, 10, 10 };
    //     int[] a = { 10, 5, 10, 10 };
    //     int[] b = { 8, 1, 1, 100 };
    //     int[][] onboard = { { 0, 0, 1, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0, 1, 0 },
    //             { 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1 },
    //             { 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1 } };

    //     int[] result = { 40, 25, 20, 60 };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertEquals(result[i], solution(temperature[i], t1[i], t2[i], a[i], b[i], onboard[i]));
    //     }
    // }
}
