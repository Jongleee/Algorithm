package com.example.algorithm.java.math;

import java.util.stream.IntStream;

public class SumBetweenTwo {
    public long solution(int a, int b) {
        int start = Math.min(a, b);
        int end = Math.max(a, b);

        return IntStream.rangeClosed(start, end).asLongStream().sum();
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(12, solution(3, 5));
    //     Assertions.assertEquals(3, solution(3, 3));
    //     Assertions.assertEquals(12, solution(5, 3));
    // }
}
