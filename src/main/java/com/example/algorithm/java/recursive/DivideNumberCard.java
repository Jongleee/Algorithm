package com.example.algorithm.java.recursive;

public class DivideNumberCard {
    public int solution(int[] arrayA, int[] arrayB) {
        int gcdA = calculateGCD(arrayA);
        int gcdB = calculateGCD(arrayB);

        gcdA = updateGCD(gcdA, arrayB);
        gcdB = updateGCD(gcdB, arrayA);

        if (gcdA == gcdB) {
            return 0;
        } else {
            return Math.max(gcdA, gcdB);
        }
    }

    public int calculateGCD(int[] arr) {
        int gcd = arr[0];
        for (int i = 1; i < arr.length; i++) {
            gcd = calculateGCD(gcd, arr[i]);
        }
        return gcd;
    }

    public int calculateGCD(int a, int b) {
        if (a == 0) {
            return b;
        }
        return calculateGCD(b % a, a);
    }

    public int updateGCD(int gcd, int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] % gcd == 0) {
                gcd = 1;
                break;
            }
        }
        return gcd;
    }

    // @Test
    // void 정답() {
    //     int[] a1 = { 10, 17 };
    //     int[] b1 = { 5, 20 };
    //     int[] a2 = { 10, 20 };
    //     int[] b2 = { 5, 17 };
    //     int[] a3 = { 14, 35, 119 };
    //     int[] b3 = { 18, 30, 102 };
    //     Assertions.assertEquals(0, solution(a1, b1));
    //     Assertions.assertEquals(10, solution(a2, b2));
    //     Assertions.assertEquals(7, solution(a3, b3));
    // }
}
