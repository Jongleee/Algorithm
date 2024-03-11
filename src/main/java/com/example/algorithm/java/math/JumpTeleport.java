package com.example.algorithm.java.math;

public class JumpTeleport {
    public int solution(int n) {
        int ans = 0;

        while (n != 0) {
            if (isEven(n)) {
                n = divideByTwo(n);
            } else {
                n = decrementByOne(n);
                ans++;
            }
        }

        return ans;
    }

    private boolean isEven(int number) {
        return number % 2 == 0;
    }

    private int divideByTwo(int number) {
        return number / 2;
    }

    private int decrementByOne(int number) {
        return number - 1;
    }

    // @Test
    // void 정답() {
    //     int[] N= {5,6,5000};
    //     int[] result = { 2,2,5 };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertEquals(result[i], solution(N[i]));
    //     }
    // }
}
