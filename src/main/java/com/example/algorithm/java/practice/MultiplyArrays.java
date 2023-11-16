package com.example.algorithm.java.practice;

public class MultiplyArrays {
    public int[][] solution(int[][] arr1, int[][] arr2) {
        int rowsA = arr1.length;
        int colsA = arr1[0].length;
        int colsB = arr2[0].length;

        int[][] result = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                result[i][j] = calculateElement(arr1, arr2, i, j, colsA);
            }
        }

        return result;
    }

    private int calculateElement(int[][] arr1, int[][] arr2, int row, int col, int colsA) {
        int sum = 0;
        for (int k = 0; k < colsA; k++) {
            sum += arr1[row][k] * arr2[k][col];
        }
        return sum;
    }

    // @Test
    // void 정답() {
    //     int[][] a11 = { { 1, 4 }, { 3, 2 }, { 4, 1 } };
    //     int[][] a21 = { { 3, 3 }, { 3, 3 } };
    //     int[][] a12 = { { 2, 3, 2 }, { 4, 2, 4 }, { 3, 1, 4 } };
    //     int[][] a22 = { { 5, 4, 3 }, { 2, 4, 1 }, { 3, 1, 1 } };

    //     Assertions.assertArrayEquals(new int[][] { { 15, 15 }, { 15, 15 }, { 15, 15 } }, solution(a11, a21));
    //     Assertions.assertArrayEquals(new int[][] { { 22, 22, 11 }, { 36, 28, 18 }, { 29, 20, 14 } }, solution(a12, a22));

    // }
}