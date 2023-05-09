package com.example.algorithm.java.math;

public class NormalSquare {
    public static long solution(int w, int h) {
        long area = (long) w * h;
        long gcd = calculateGcd(w, h);
        long overlappedArea = (w / gcd) + (h / gcd) - 1;
        return area - gcd * overlappedArea;
    }

    public static long calculateGcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return calculateGcd(b, a % b);
    }

    public static void main(String[] args) {
        System.out.println(solution(8, 12));// 80
    }
}
