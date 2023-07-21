package com.example.algorithm.java.prefixSum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Camping {
    public int solution(int n, int[][] data) {
        int[][] compressedData = compressCoordinates(n, data);

        int[][] prefixSum = new int[n][n];
        for (int i = 0; i < n; i++) {
            prefixSum[compressedData[i][0]][compressedData[i][1]] = 1;
        }

        prefixSum(n, prefixSum);

        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (compressedData[i][0] == compressedData[j][0] || compressedData[i][1] == compressedData[j][1])
                    continue;

                int startX = Math.min(compressedData[i][0], compressedData[j][0]);
                int startY = Math.min(compressedData[i][1], compressedData[j][1]);
                int endX = Math.max(compressedData[i][0], compressedData[j][0]);
                int endY = Math.max(compressedData[i][1], compressedData[j][1]);
                
                int cnt = calculateDistance(startX, startY, endX, endY, prefixSum);
                if (cnt == 0)
                    ans++;
            }
        }

        return ans;
    }

    static int[][] compressCoordinates(int n, int[][] data) {
        ArrayList<Integer> xList = new ArrayList<>();
        ArrayList<Integer> yList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            xList.add(data[i][0]);
            yList.add(data[i][1]);
        }

        ArrayList<Integer> uniqueXList = new ArrayList<>(new HashSet<>(xList));
        ArrayList<Integer> uniqueYList = new ArrayList<>(new HashSet<>(yList));

        Collections.sort(uniqueXList);
        Collections.sort(uniqueYList);

        int[][] compressedData = new int[n][2];

        for (int i = 0; i < n; i++) {
            int x = uniqueXList.indexOf(data[i][0]);
            int y = uniqueYList.indexOf(data[i][1]);

            compressedData[i][0] = x;
            compressedData[i][1] = y;
        }

        return compressedData;
    }

    private static void prefixSum(int n, int[][] prefixSum) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                prefixSum[i][j] += (i - 1 >= 0 ? prefixSum[i - 1][j] : 0)
                        + (j - 1 >= 0 ? prefixSum[i][j - 1] : 0)
                        - (i - 1 >= 0 && j - 1 >= 0 ? prefixSum[i - 1][j - 1] : 0);
            }
        }
    }

    static int calculateDistance(int startX, int startY, int endX, int endY, int[][] prefixSum) {

        if (startX + 1 > endX - 1 || startY + 1 > endY - 1)
            return 0;

        return prefixSum[endX - 1][endY - 1] - prefixSum[endX - 1][startY] - prefixSum[startX][endY - 1]
                + prefixSum[startX][startY];
    }
    
    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(3, solution(4, new int[][] { { 0, 0 }, { 1, 1 }, { 0, 2 }, { 2, 0 } }));
    // }
}
