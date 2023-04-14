package com.example.algorithm.java.bruteForce;

public class IntegerPairsBetweenTwoCircles {
    public static long solution(int r1, int r2) {
        long answer = 0;
        for (long i = 1; i < r2; i++) {
            int num2 = (int) Math.sqrt((double) r2 * r2 - i * i);
            if (i >= r1) {
                answer += 4 * (num2 + 1);
            } else {
                int num1 = (int) Math.sqrt((double) r1 * r1 - i * i);
                answer += 4 * (num2 - num1);
                if (Math.sqrt((double) r1 * r1 - i * i) % 1 == 0) {
                    answer += 4;
                }
            }
        }
        answer += 4;
        return answer;
    }

    public static void main(String[] args) {

        System.out.println(solution(999999, 1000000));// 6281440
    }
}
