package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class WarehousePolygon {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int n = Integer.parseInt(br.readLine());
        int[] heights = new int[1001];
        int start = Integer.MAX_VALUE;
        int end = 0;

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            heights[l] = h;
            start = Math.min(start, l);
            end = Math.max(end, l);
        }

        int maxIdx = findMaxHeightIndex(heights, start, end);
        int totalArea = calculateArea(heights, start, end, maxIdx);

        System.out.println(totalArea);
    }

    private static int findMaxHeightIndex(int[] heights, int start, int end) {
        int max = 0;
        int maxIdx = 0;
        for (int i = start; i <= end; i++) {
            if (heights[i] > max) {
                max = heights[i];
                maxIdx = i;
            }
        }
        return maxIdx;
    }

    private static int calculateArea(int[] heights, int start, int end, int maxIdx) {
        int totalArea = 0;

        int curHeight = 0;
        for (int i = start; i < maxIdx; i++) {
            curHeight = Math.max(curHeight, heights[i]);
            totalArea += curHeight;
        }

        curHeight = 0;
        for (int i = end; i > maxIdx; i--) {
            curHeight = Math.max(curHeight, heights[i]);
            totalArea += curHeight;
        }

        totalArea += heights[maxIdx];
        return totalArea;
    }
}

/*
7
2 4
11 4
15 8
4 6
5 3
8 10
13 6

98
 */