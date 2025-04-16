package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class RgbStreet {
    private static final int RED = 0;
    private static final int GREEN = 1;
    private static final int BLUE = 2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int houseCount = Integer.parseInt(br.readLine());
        int[][] cost = readCostArray(br, houseCount);
        calculateMinCost(cost, houseCount);
        System.out.println(getMinFinalCost(cost, houseCount));
    }

    private static int[][] readCostArray(BufferedReader br, int houseCount) throws IOException {
        int[][] cost = new int[houseCount][3];
        for (int i = 0; i < houseCount; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            cost[i][RED] = Integer.parseInt(st.nextToken());
            cost[i][GREEN] = Integer.parseInt(st.nextToken());
            cost[i][BLUE] = Integer.parseInt(st.nextToken());
        }
        return cost;
    }

    private static void calculateMinCost(int[][] cost, int houseCount) {
        for (int i = 1; i < houseCount; i++) {
            cost[i][RED] += Math.min(cost[i - 1][GREEN], cost[i - 1][BLUE]);
            cost[i][GREEN] += Math.min(cost[i - 1][RED], cost[i - 1][BLUE]);
            cost[i][BLUE] += Math.min(cost[i - 1][RED], cost[i - 1][GREEN]);
        }
    }

    private static int getMinFinalCost(int[][] cost, int houseCount) {
        int last = houseCount - 1;
        return Math.min(Math.min(cost[last][RED], cost[last][GREEN]), cost[last][BLUE]);
    }
}

/*
3
1 100 100
100 100 100
1 100 100

102


6
30 19 5
64 77 64
15 19 97
4 71 57
90 86 84
93 32 91

208
*/