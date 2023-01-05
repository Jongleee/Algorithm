package com.example.algorithm.java.dynamicProgramming;

public class FourArithmeticOperations {
    private static int[] numbers;
    private static String[] operations;
    private static int[][][] dp;

    public static int solution(String[] arr) {
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

    private static int calculate(int start, int end, int sign) {
        if (start == end) {
            dp[start][end][sign] = numbers[start];
            return dp[start][end][sign];
        }
        if (dp[start][end][sign] != -10000000) {
            if (sign == 0 || sign == 1) {
                return dp[start][end][sign];
            }
        }

        dp[start][end][sign] = 0;

        int result = 0;

        if (sign == 0) {
            result = signZero(start, end);
        }

        if (sign == 1) {
            result = signOne(start, end);
        }

        dp[start][end][sign] = result;
        return dp[start][end][sign];
    }

    private static int signOne(int start, int end) {
        int result;
        result = 10000000;
        for (int i = start; i < end; i++) {
            if (operations[i].equals("-")) {
                result = Math.min(result, calculate(start, i, 1) - calculate(i + 1, end, 0));
                continue;
            }
            result = Math.min(result, calculate(start, i, 1) + calculate(i + 1, end, 1));
        }
        return result;
    }

    private static int signZero(int start, int end) {
        int result;
        result = -10000000;
        for (int i = start; i < end; i++) {
            if (operations[i].equals("-")) {
                result = Math.max(result, calculate(start, i, 0) - calculate(i + 1, end, 1));
                continue;
            }
            result = Math.max(result, calculate(start, i, 0) + calculate(i + 1, end, 0));
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(solution(new String[] { "5", "-", "3", "+", "1", "+", "2", "-", "4" }));
    }
}
