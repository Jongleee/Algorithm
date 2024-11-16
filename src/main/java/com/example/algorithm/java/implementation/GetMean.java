package com.example.algorithm.java.implementation;

public class GetMean {
    public double solution(int[] arr) {
        int length = arr.length;
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        return (double) sum / length;
    }

    // @Test
    // void 정답() {
    //     int[][] k = { { 1, 2, 3, 4 }, { 5, 5 } };
    //     double[] result = { 2.5, 5 };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertEquals(result[i], solution(k[i]));
    //     }
    // }
}
