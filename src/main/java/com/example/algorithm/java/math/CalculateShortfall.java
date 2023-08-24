package com.example.algorithm.java.math;

public class CalculateShortfall {
    public long solution(int price, int money, int count) {
        long totalCost = (long) price * count * (count + 1) / 2;
        return Math.max(totalCost - money, 0);
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(10, solution(3,20,4));
    // }
}
