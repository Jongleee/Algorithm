package com.example.algorithm.java.math;

public class NNumberSpacedByX {
    public long[] solution(int x, int n) {
        long[] answer = new long[n];
        long value = x;
        for (int i = 0; i < n; i++) {
            answer[i] = value;
            value += x;
        }
        return answer;
    }

    // @Test
    // void 정답() {
    //     int[] x = { 2, 4, -4 };
    //     int[] n = { 5, 3, 2 };
    //     long[][] result = { { 2, 4, 6, 8, 10 }, { 4, 8, 12 }, { -4, -8 } };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertArrayEquals(result[i], solution(x[i], n[i]));
    //     }
    // }
}
