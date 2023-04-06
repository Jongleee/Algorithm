package com.example.algorithm.java.recursive;

public class CantorSetBitstring {
    public static int solution(int n, long l, long r) {
        return countOne(n, l, r, 1);
    }

    public static int countOne(int n, long s, long e, long idx) {
        if (n == 0) {
            return 1;
        }
        int numOnes = 0;
        long subIntervalSize = (long) Math.pow(5, (double) n - 1);
        for (int i = 0; i < 5; i++) {
            if (i == 2 || e < (idx + subIntervalSize * i) || (idx + subIntervalSize * (i + 1) - 1) < s) {
                continue;
            }
            numOnes += countOne(n - 1, s, e, idx + subIntervalSize * i);
        }
        return numOnes;
    }
    public static void main(String[] args) {
        System.out.println(solution(2,	4,	17	));//8
    }
}
