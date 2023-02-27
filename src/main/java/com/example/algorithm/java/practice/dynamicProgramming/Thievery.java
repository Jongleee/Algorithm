package com.example.algorithm.java.practice.dynamicProgramming;

public class Thievery {

    public static int solution(int[] money) {
        int n = money.length;
        int[] dp1 = new int[n];
        int[] dp2 = new int[n];

        dp1[0] = money[0];
        dp2[0] = 0;

        for (int i = 1; i < n; i++) {
            dp1[i] = Math.max(i >= 2 ? dp1[i - 2] + money[i] : money[i], dp1[i - 1]);
            dp2[i] = Math.max(i >= 2 ? dp2[i - 2] + money[i] : money[i], dp2[i - 1]);
        }

        return Math.max(dp1[n - 2], dp2[n - 1]);
    }

    public static void main(String[] args) {
        int[] m1 = { 1, 2, 3, 1 };
        System.out.println(solution(m1));
    }
}
