package com.example.algorithm.java.prac;

class Solution9 {
    public static long solution(long n) {
        // 제곱근의 값을 구함
        Double sqrt = Math.sqrt(n);
        // 정수가 맞는지 판별하여 제곱해줌
        if (sqrt == sqrt.intValue()) {
            return (long) Math.pow(sqrt + 1, 2);
        } else
            return -1;
    }

    public static void main(String[] args) {
        System.out.println(solution(256));
    }
    //289
}
