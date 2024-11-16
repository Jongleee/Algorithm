package com.example.algorithm.java.implementation;

public class TriangleSnail {
    public int[] solution(int n) {
        int[] answer = new int[n * (n + 1) / 2];

        int[][] triangle = new int[n][n];
        int value = 1;

        int x = -1;
        int y = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i % 3 == 0) {
                    x++;
                } else if (i % 3 == 1) {
                    y++;
                } else if (i % 3 == 2) {
                    x--;
                    y--;
                }
                triangle[x][y] = value++;
            }
        }

        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                if (triangle[i][j] == 0)
                    break;
                answer[index++] = triangle[i][j];
            }
        }

        return answer;
    }

    // @Test
    // void 정답() {
    //     int[] n = { 4, 5, 6 };

    //     int[][] result = { { 1, 2, 9, 3, 10, 8, 4, 5, 6, 7 },
    //             { 1, 2, 12, 3, 13, 11, 4, 14, 15, 10, 5, 6, 7, 8, 9 },
    //             { 1, 2, 15, 3, 16, 14, 4, 17, 21, 13, 5, 18, 19, 20, 12, 6, 7, 8, 9, 10, 11 } };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertArrayEquals(result[i], solution(n[i]));
    //     }
    // }
}
