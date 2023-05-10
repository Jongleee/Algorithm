package com.example.algorithm.java.math;

public class MarkDot {
    public long solution(int k, int d) {
        long answer = 0;

        for (long i = 0; i <= d / k; i++) {
            long x = i * k;
            long y = (long) Math.sqrt((double) d * d - x * x) / k;

            answer += y + 1;
        }

        return answer;
    }
}
