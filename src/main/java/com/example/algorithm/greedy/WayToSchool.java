package com.example.algorithm.greedy;

public class WayToSchool {
    public int solution(int m, int n, int[][] puddles) {
        //4, 3, new int[][]	{{2, 2}}
        int[][] step = new int[n + 1][m + 1];
        int divisor = 1000000007;
        for (int i = 0; i < puddles.length; i++) {
            step[puddles[i][1]][puddles[i][0]] = -1;
        }
        step[1][1] = 1;
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                if (step[i][j] == -1) continue;
                if (step[i - 1][j] > -1) step[i][j] += step[i - 1][j] % divisor;
                if (step[i][j - 1] > -1) step[i][j] += step[i][j - 1] % divisor;
            }
        }
        return step[n][m] % divisor;
    }
}
