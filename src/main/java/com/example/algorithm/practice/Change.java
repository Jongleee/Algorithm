package com.example.algorithm.practice;

import java.util.Arrays;

public class Change {

    public static void main(String[] args) {
        System.out.println(solution(5, new int[]{1, 2, 5}));
    }

    static int num = 1000000007;

    public static int solution(int n, int[] money) {
        Arrays.sort(money);
        int[] d = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            if (i % money[0] == 0) d[i] = 1;
        }
        for (int i = 1; i < money.length; i++) {
            for (int j = money[i]; j <= n; j++) {
                d[j] += d[j - money[i]] % num;
            }
        }
        return d[n];
    }
}
