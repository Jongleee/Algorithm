package com.example.algorithm.java.practice;

public class NextBiggerNumber {
    public int solution(int n) {
        int num=Integer.bitCount(n);
        for (int i = n+1; i <= 1000000 ; i++) {
            if (Integer.bitCount(i)==num) return i;
        }
        return num;
    }
}
