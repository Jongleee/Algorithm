package com.example.algorithm.java.practice;

public class LeastCommonMultiple {
    public int solution(int[] arr) {
        int answer = arr[0];
        for (int i = 1; i < arr.length; i++) {
            answer = lcm(answer, arr[i]);
        }
        return answer;
    }

    public int lcm(int x, int y) {
        return x * y / gcd(x, y);
    }

    public int gcd(int x, int y) {
        int a = Math.max(x, y);
        int b = Math.min(x, y);
        while (a % b != 0) {
            int r = a % b;
            a = b;
            b = r;
        }
        return b;
    }

    // @Test
    // void 정답() {
    //     int[] arr1 = { 2,6,8,14 };
    //     int[] arr2 = { 1,2,3};
    //     Assertions.assertEquals(168, solution(arr1));
    //     Assertions.assertEquals(6, solution(arr2));
    // }
}
