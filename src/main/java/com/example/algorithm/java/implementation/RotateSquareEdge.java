package com.example.algorithm.java.implementation;

public class RotateSquareEdge {
    public int[] solution(int rows, int columns, int[][] queries) {
        int[][] square = new int[rows][columns];
        int value = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                square[i][j] = value++;
            }
        }
        return rotateNums(square, queries);
    }

    public int[] rotateNums(int[][] square, int[][] queries) {
        int[] answer = new int[queries.length];
        int index = 0;

        for (int[] query : queries) {
            int x1 = query[0] - 1;
            int y1 = query[1] - 1;
            int x2 = query[2] - 1;
            int y2 = query[3] - 1;
            int min = Integer.MAX_VALUE;

            int prev = square[x1][y1];
            min = Math.min(min, prev);

            for (int i = x1; i < x2; i++) {
                square[i][y1] = square[i + 1][y1];
                min = Math.min(min, square[i][y1]);
            }

            for (int i = y1; i < y2; i++) {
                square[x2][i] = square[x2][i + 1];
                min = Math.min(min, square[x2][i]);
            }

            for (int i = x2; i > x1; i--) {
                square[i][y2] = square[i - 1][y2];
                min = Math.min(min, square[i][y2]);
            }

            for (int i = y2; i > y1 + 1; i--) {
                square[x1][i] = square[x1][i - 1];
                min = Math.min(min, square[x1][i]);
            }

            square[x1][y1 + 1] = prev;
            answer[index++] = min;
        }

        return answer;
    }

    // @Test
    // void 정답() {
    //     int[] rows = { 6, 3, 100 };
    //     int[] columns = { 6, 3, 97 };

    //     int[][][] queries = { { { 2, 2, 5, 4 }, { 3, 3, 6, 6 }, { 5, 1, 6, 3 } },
    //             { { 1, 1, 2, 2 }, { 1, 2, 2, 3 }, { 2, 1, 3, 2 }, { 2, 2, 3, 3 } },
    //             { { 1, 1, 100, 97 } } };

    //     int[][] result = { { 8, 10, 25 }, { 1, 1, 5, 3 }, { 1 } };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertArrayEquals(result[i], solution(rows[i], columns[i], queries[i]));
    //     }
    // }
}
