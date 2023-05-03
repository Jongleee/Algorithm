package com.example.algorithm.java.recursive;

public class DivideNumberCard {
    public static int solution(int[] arrayA, int[] arrayB) {
        int length = arrayA.length;
        int gcdA = arrayA[0];
        int gcdB = arrayB[0];

        for (int i = 1; i < length; i++) {
            gcdA = gcd(gcdA, arrayA[i]);
            gcdB = gcd(gcdB, arrayB[i]);
        }

        for (int i = length- 1; i >= 0; i--) {
            if (arrayA[i] % gcdB == 0) {
                gcdB = 1;
                break;
            }
        }

        for (int i = length- 1; i >= 0; i--) {
            if (arrayB[i] % gcdA == 0) {
                gcdA = 1;
                break;
            }
        }

        if (gcdA == gcdB) {
            return 0;
        } else {
            return Math.max(gcdA, gcdB);
        }
    }

    public static int gcd(int a, int b) {
        if (a == 0) {
            return b;
        }
        return gcd(b % a, a);
    }

    public static void main(String[] args) {
        System.out.println(solution(new int [] {14, 35, 119}, new int[]	{18, 30, 102}));//7
    }
}
