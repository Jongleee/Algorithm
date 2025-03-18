package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class InvaderChoragi {
    private static final int INF = 1_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCases = Integer.parseInt(br.readLine());

        while (testCases-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int width = Integer.parseInt(st.nextToken());

            int[][] sectors = new int[2][n + 1];
            for (int i = 0; i < 2; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 1; j <= n; j++) {
                    sectors[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            int minDefenders = calculateMinimumDefenders(n, width, sectors);
            System.out.println(minDefenders);
        }
    }

    private static int calculateMinimumDefenders(int n, int width, int[][] sectors) {
        int minResult = INF;

        minResult = Math.min(minResult, handleBaseCase(n, width, sectors));

        if (sectors[0][1] + sectors[0][n] <= width) {
            minResult = Math.min(minResult, handleTopWrapCase(n, width, sectors));
        }

        if (sectors[1][1] + sectors[1][n] <= width) {
            minResult = Math.min(minResult, handleBottomWrapCase(n, width, sectors));
        }

        if (sectors[0][1] + sectors[0][n] <= width && sectors[1][1] + sectors[1][n] <= width) {
            minResult = Math.min(minResult, handleBothWrapCase(n, width, sectors));
        }

        return minResult;
    }

    private static int handleBaseCase(int n, int width, int[][] sectors) {
        int[] totalDP = new int[n + 2];
        int[] upperDP = new int[n + 2];
        int[] lowerDP = new int[n + 2];
        int initialTotal = (sectors[0][1] + sectors[1][1] <= width) ? 1 : 2;
        initializeDPArrays(totalDP, upperDP, lowerDP, n, initialTotal, 1, 1);
        computeDP(n, width, totalDP, upperDP, lowerDP, sectors);
        return totalDP[n];
    }

    private static int handleTopWrapCase(int n, int width, int[][] sectors) {
        int[] totalDP = new int[n + 2];
        int[] upperDP = new int[n + 2];
        int[] lowerDP = new int[n + 2];
        initializeDPArrays(totalDP, upperDP, lowerDP, n, 1, 0, 1);
        computeDP(n, width, totalDP, upperDP, lowerDP, sectors);
        return lowerDP[n] + 1;
    }

    private static int handleBottomWrapCase(int n, int width, int[][] sectors) {
        int[] totalDP = new int[n + 2];
        int[] upperDP = new int[n + 2];
        int[] lowerDP = new int[n + 2];
        initializeDPArrays(totalDP, upperDP, lowerDP, n, 1, 1, 0);
        computeDP(n, width, totalDP, upperDP, lowerDP, sectors);
        return upperDP[n] + 1;
    }

    private static int handleBothWrapCase(int n, int width, int[][] sectors) {
        int[] totalDP = new int[n + 2];
        int[] upperDP = new int[n + 2];
        int[] lowerDP = new int[n + 2];
        initializeDPArrays(totalDP, upperDP, lowerDP, n, 0, 0, 0);
        computeDP(n - 1, width, totalDP, upperDP, lowerDP, sectors);
        return totalDP[n - 1] + 2;
    }

    private static void initializeDPArrays(int[] totalDP, int[] upperDP, int[] lowerDP, int n,
            int initialTotal, int initialUpper, int initialLower) {
        Arrays.fill(totalDP, 1, n + 1, INF);
        Arrays.fill(upperDP, 1, n + 1, INF);
        Arrays.fill(lowerDP, 1, n + 1, INF);
        totalDP[1] = initialTotal;
        upperDP[1] = initialUpper;
        lowerDP[1] = initialLower;
    }

    private static void computeDP(int n, int width, int[] totalDP, int[] upperDP, int[] lowerDP, int[][] sectors) {
        for (int i = 2; i <= n; i++) {
            upperDP[i] = totalDP[i - 1] + 1;
            if (sectors[0][i - 1] + sectors[0][i] <= width) {
                upperDP[i] = Math.min(upperDP[i], lowerDP[i - 1] + 1);
            }

            lowerDP[i] = totalDP[i - 1] + 1;
            if (sectors[1][i - 1] + sectors[1][i] <= width) {
                lowerDP[i] = Math.min(lowerDP[i], upperDP[i - 1] + 1);
            }

            totalDP[i] = Math.min(upperDP[i] + 1, lowerDP[i] + 1);
            if (sectors[0][i] + sectors[1][i] <= width) {
                totalDP[i] = Math.min(totalDP[i], totalDP[i - 1] + 1);
            }

            if (i >= 2 && sectors[0][i - 1] + sectors[0][i] <= width
                    && sectors[1][i - 1] + sectors[1][i] <= width) {
                totalDP[i] = Math.min(totalDP[i], totalDP[i - 2] + 2);
            }
        }
    }
}

/*
1
8 100
70 60 55 43 57 60 44 50
58 40 47 90 45 52 80 40

11
*/