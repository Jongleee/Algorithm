package com.example.algorithm.java.implement;

import java.util.Arrays;

public class TableHashFunction {
    public static int solution(int[][] data, int column, int rowBegin, int rowEnd) {
        column--;
        rowBegin--;

        int finalColumn = column;
        Arrays.sort(data, (o1, o2) -> {
            if (o1[finalColumn] == o2[finalColumn])
                return o2[0] - o1[0];
            return o1[finalColumn] - o2[finalColumn];
        });

        int result = 0;

        for (int i = rowBegin; i < rowEnd; i++) {
            int sumSi = calculateSumSi(data[i], i + 1);
            result ^= sumSi;
        }

        return result;
    }

    private static int calculateSumSi(int[] row, int rowNumber) {
        int sum = 0;
        for (int i = 0; i < row.length; i++) {
            sum += row[i] % rowNumber;
        }
        return sum;
    }

    public static void main(String[] args) {
        int[][] data = { { 2, 2, 6 }, { 1, 5, 10 }, { 4, 2, 9 }, { 3, 8, 3 } };
        System.out.println(solution(data, 2, 2, 3));//4
    }
}
