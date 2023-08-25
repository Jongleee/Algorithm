package com.example.algorithm.java.math;

import java.util.stream.IntStream;

public class InnerProduc {
    public int solution(int[] a, int[] b) {
        return IntStream.range(0, a.length)
                .map(i -> a[i] * b[i])
                .sum();
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(3, solution(new int[] { 1, 2, 3, 4 }, new int[] { -3, -1, 0, 2 }));
    //     Assertions.assertEquals(-2, solution(new int[] { -1,0,1 }, new int[] {1,0, -1}));
    // }
}
