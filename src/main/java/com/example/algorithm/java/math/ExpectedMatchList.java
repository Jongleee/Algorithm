package com.example.algorithm.java.math;

public class ExpectedMatchList {
    public int solution(int n, int a, int b) {
        int answer = 0;
        while (a != b) {
            a = a / 2 + a % 2;
            b = b / 2 + b % 2;
            answer++;
        }
        return answer;
    }

    // @Test
    // void 정답() {
    //     int[] result = { 3 };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertEquals(result[i], solution(8, 4, 7));
    //     }
    // }
}
