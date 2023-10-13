package com.example.algorithm.java.recursive;

public class CantorSetBitstring {
    public int solution(int n, long l, long r) {
        return countOnesInRange(n, l, r, 1);
    }

    public int countOnesInRange(int n, long start, long end, long index) {
        if (n == 0) {
            return 1;
        }

        int numOnes = 0;
        long subIntervalSize = (long) Math.pow(5, (double) n - 1);

        for (int i = 0; i < 5; i++) {
            long subStart = index + subIntervalSize * i;
            long subEnd = index + subIntervalSize * (i + 1) - 1;

            if (i == 2 || end < subStart || start > subEnd) {
                continue;
            }

            numOnes += countOnesInRange(n - 1, start, end, subStart);
        }

        return numOnes;
    }

    // @Test
    // void 정답() {
    //     Assertions.assertEquals(8, solution(2,4,17));
    //     Assertions.assertEquals(7, solution(2,4,16));
    // }
}
