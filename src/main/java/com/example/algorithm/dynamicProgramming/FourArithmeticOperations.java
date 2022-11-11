package com.example.algorithm.dynamicProgramming;

public class FourArithmeticOperations {
//    new String[]{"5", "-", "3", "+", "1", "+", "2", "-", "4"}
    private int numbers[];
    private String operations[];
    private int dp[][][];

    public int solution(String arr[]) {
        int n = arr.length / 2;

        dp = new int[200][200][2];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                dp[i][j][0] = -10000000;
                dp[i][j][1] = 10000000;
            }
        }

        numbers = new int[n + 1];
        operations = new String[n];

        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0) {
                numbers[i / 2] = Integer.parseInt(arr[i]);
                continue;
            }
            operations[i / 2] = arr[i];
        }

        return calculate(0, n, 0);
    }

    private int calculate(int start, int end, int sign) {
        if (start == end) {
            dp[start][end][sign] = numbers[start];
            return dp[start][end][sign];
        }

        if (sign == 0 && dp[start][end][sign] != -10000000) {
            return dp[start][end][sign];
        }

        if (sign == 1 && dp[start][end][sign] != 10000000) {
            return dp[start][end][sign];
        }

        dp[start][end][sign] = 0;

        int result = sign == 0 ? -10000000 : 10000000;

        if (sign == 0) {
            for (int i = start; i < end; i++) {
                if (operations[i].equals("-")) {
                    result = Math.max(result, calculate(start, i, 0) - calculate(i + 1, end, 1));
                    continue;
                }
                result = Math.max(result, calculate(start, i, 0) + calculate(i + 1, end, 0));
            }
        }

        if (sign == 1) {
            for (int i = start; i < end; i++) {
                if (operations[i].equals("-")) {
                    result = Math.min(result, calculate(start, i, 1) - calculate(i + 1, end, 0));
                    continue;
                }
                result = Math.min(result, calculate(start, i, 1) + calculate(i + 1, end, 1));
            }
        }

        dp[start][end][sign] = result;
        return dp[start][end][sign];
    }
}
