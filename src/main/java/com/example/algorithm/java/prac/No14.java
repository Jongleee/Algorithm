package com.example.algorithm.java.prac;

public class No14 {
    public static int solution(int[][] sizes) {
        int biggerSideMax = 0;
        int smallerSideMax = 0;
        for (int[] size : sizes) {
            biggerSideMax = Math.max(biggerSideMax, Math.max(size[0], size[1]));
            smallerSideMax = Math.max(smallerSideMax, Math.min(size[0], size[1]));
        }
        return biggerSideMax * smallerSideMax;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[][] { { 60, 50 }, { 30, 70 }, { 60, 30 }, { 80, 40 } }));
    }
}
