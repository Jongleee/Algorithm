package com.example.algorithm.java.practice.sort;

import java.util.Arrays;

public class MakeMinimumValue {
    public int solution(int[] A, int[] B) {
        Arrays.sort(A);
        Arrays.sort(B);
        int answer = 0;
        int length = A.length;
        for (int i = 0; i < length; i++) {
            answer += A[i] * B[length - i - 1];
        }
        return answer;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(29, solution(new int[] { 1, 4, 2 }, new int[] { 5, 4, 4 }));
    //     Assertions.assertEquals(10, solution(new int[] { 1, 2 }, new int[] { 3, 4 }));
    // }
}
