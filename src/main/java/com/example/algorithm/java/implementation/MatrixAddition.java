package com.example.algorithm.java.implementation;

public class MatrixAddition {
    public int[][] solution(int[][] arr1, int[][] arr2) {
        int rows = arr1.length;
        int cols = arr1[0].length;
        int[][] result = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = arr1[i][j] + arr2[i][j];
            }
        }

        return result;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertArrayEquals(new int[][] { { 4, 6 }, { 7, 9 } },
    //             solution(new int[][] { { 1, 2 }, { 2, 3 } }, new int[][] { { 3, 4 }, { 5, 6 } }));
    //     Assertions.assertArrayEquals(new int[][] { { 4 }, { 6 } },
    //             solution(new int[][] { { 1 }, { 2 } }, new int[][] { { 3 }, { 4 } }));
    // }
}
