package com.example.algorithm.java.prefixSum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Camping {
    public int solution(int n, int[][] data) {
        int[][] compressedData = compressCoordinates(n, data);
        int[][] prefixSum = calculatePrefixSum(n, compressedData);

        int answer = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (compressedData[i][0] == compressedData[j][0] || compressedData[i][1] == compressedData[j][1]) {
                    continue;
                }

                int startX = Math.min(compressedData[i][0], compressedData[j][0]);
                int startY = Math.min(compressedData[i][1], compressedData[j][1]);
                int endX = Math.max(compressedData[i][0], compressedData[j][0]);
                int endY = Math.max(compressedData[i][1], compressedData[j][1]);

                int cnt = calculateRectangleArea(startX, startY, endX, endY, prefixSum);
                if (cnt == 0) {
                    answer++;
                }
            }
        }

        return answer;
    }

    private int[][] compressCoordinates(int n, int[][] data) {
        HashSet<Integer> uniqueXSet = new HashSet<>();
        HashSet<Integer> uniqueYSet = new HashSet<>();

        for (int i = 0; i < n; i++) {
            uniqueXSet.add(data[i][0]);
            uniqueYSet.add(data[i][1]);
        }

        ArrayList<Integer> uniqueXList = new ArrayList<>(uniqueXSet);
        ArrayList<Integer> uniqueYList = new ArrayList<>(uniqueYSet);

        Collections.sort(uniqueXList);
        Collections.sort(uniqueYList);

        int[][] compressedData = new int[n][2];

        for (int i = 0; i < n; i++) {
            compressedData[i][0] = uniqueXList.indexOf(data[i][0]);
            compressedData[i][1] = uniqueYList.indexOf(data[i][1]);
        }

        return compressedData;
    }

    private int[][] calculatePrefixSum(int n, int[][] compressedData) {
        int[][] prefixSum = new int[n][n];
        for (int i = 0; i < n; i++) {
            prefixSum[compressedData[i][0]][compressedData[i][1]] = 1;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                prefixSum[i][j] += (i - 1 >= 0 ? prefixSum[i - 1][j] : 0)
                        + (j - 1 >= 0 ? prefixSum[i][j - 1] : 0)
                        - (i - 1 >= 0 && j - 1 >= 0 ? prefixSum[i - 1][j - 1] : 0);
            }
        }
        return prefixSum;
    }

    private int calculateRectangleArea(int startX, int startY, int endX, int endY, int[][] prefixSum) {
        if (startX + 1 > endX - 1 || startY + 1 > endY - 1) {
            return 0;
        }
        return prefixSum[endX - 1][endY - 1] - prefixSum[endX - 1][startY] - prefixSum[startX][endY - 1]
                + prefixSum[startX][startY];
    }

    // @Test
    // void 정답() {
    //     Assertions.assertEquals(3, solution(4, new int[][] { { 0, 0 }, { 1, 1 }, { 0, 2 }, { 2, 0 } }));
    // }
}
