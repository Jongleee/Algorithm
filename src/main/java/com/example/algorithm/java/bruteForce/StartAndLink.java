package com.example.algorithm.java.bruteForce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class StartAndLink {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] sumRow = new int[n];
        int[] sumColumn = new int[n];
        int totalSum = 0;

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                int value = Integer.parseInt(st.nextToken());
                sumRow[i] += value;
                sumColumn[j] += value;
                totalSum += value;
            }
        }

        int result = findMinDifference(n, sumRow, sumColumn, totalSum);
        System.out.println(result);
    }

    private static int findMinDifference(int n, int[] sumRow, int[] sumColumn, int totalSum) {
        Result result = new Result(Integer.MAX_VALUE);
        calculateCombination(0, 0, n, totalSum, sumRow, sumColumn, result);
        return result.min;
    }

    private static void calculateCombination(int index, int count, int n, int remainingSum,
            int[] sumRow, int[] sumColumn, Result result) {
        if (count == n / 2) {
            result.min = Math.min(result.min, Math.abs(remainingSum));
            return;
        }
        if (index == n) {
            return;
        }

        calculateCombination(index + 1, count + 1, n, remainingSum - sumRow[index] - sumColumn[index], sumRow,
                sumColumn, result);
        calculateCombination(index + 1, count, n, remainingSum, sumRow, sumColumn, result);
    }

    private static class Result {
        int min;

        Result(int min) {
            this.min = min;
        }
    }
}

/*
8
0 5 4 5 4 5 4 5
4 0 5 1 2 3 4 5
9 8 0 1 2 3 1 2
9 9 9 0 9 9 9 9
1 1 1 1 0 1 1 1
8 7 6 5 4 0 3 2
9 1 9 1 9 1 0 9
6 5 4 3 2 1 9 0

1


6
0 1 2 3 4 5
1 0 2 3 4 5
1 2 0 3 4 5
1 2 3 0 4 5
1 2 3 4 0 5
1 2 3 4 5 0

2
*/