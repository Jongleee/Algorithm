package com.example.algorithm.java.practice.sort;

import java.util.Arrays;

public class HIndex {
    public static int solution(int[] citations) {
        Arrays.sort(citations);
        int n = citations.length;
        for (int i = 0; i < n; i++) {
            int h = n - i;
            if (citations[i] >= h) {
                return h;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int[] c1 = { 3, 0, 6, 1, 5 };
        System.out.println(solution(c1));// 3
    }
}
