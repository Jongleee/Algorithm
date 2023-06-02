package com.example.algorithm.java.dynamicProgramming;

public class ConvertNumber {
    public int solution(int x, int y, int n) {
        int[] dp = new int[y + 1];

        for (int i = x; i < y + 1; i++) {
            if (i != x && dp[i] == 0) {
                dp[i] = -1;
                continue;
            }

            int doubleValue = i * 2;
            if (doubleValue <= y) {
                dp[doubleValue] = updateValue(dp[doubleValue], dp[i] + 1);
            }

            int tripleValue = i * 3;
            if (tripleValue <= y) {
                dp[tripleValue] = updateValue(dp[tripleValue], dp[i] + 1);
            }

            int sumValue = i + n;
            if (sumValue <= y) {
                dp[sumValue] = updateValue(dp[sumValue], dp[i] + 1);
            }
        }

        return dp[y];
    }

    private int updateValue(int currentValue, int newValue) {
        return (currentValue == 0) ? newValue : Math.min(currentValue, newValue);
    }
}
