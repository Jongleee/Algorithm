package com.example.algorithm.java.dynamicProgramming;

public class Thievery {
    public int solution(int[] money) {
        int n = money.length;
        int[] dp1 = new int[n];
        int[] dp2 = new int[n];

        dp1[0] = money[0];
        dp1[1] = dp1[0];
        dp2[0] = 0;
        dp2[1] = money[1];
        for (int i = 2; i < n; i++) {
            dp1[i] = Math.max(dp1[i - 2] + money[i], dp1[i - 1]);
            dp2[i] = Math.max(dp2[i - 2] + money[i], dp2[i - 1]);
        }

        return Math.max(dp1[n - 2], dp2[n - 1]);
    }

    // @Test
    // void 정답() {
    //     int[] money = { 1, 2, 3, 1 };

    //     Assertions.assertEquals(4, solution(money));
    // }
}
