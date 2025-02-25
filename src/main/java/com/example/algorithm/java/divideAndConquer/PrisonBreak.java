package com.example.algorithm.java.divideAndConquer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PrisonBreak {
    private static final long INF = Long.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int totalLocations = Integer.parseInt(st.nextToken());
        int totalGroups = Math.min(totalLocations, Integer.parseInt(st.nextToken()));

        long[] prefixSum = new long[totalLocations];
        long[][] dpTable = new long[totalGroups][totalLocations];

        st = new StringTokenizer(br.readLine());
        prefixSum[0] = Integer.parseInt(st.nextToken());
        dpTable[0][0] = prefixSum[0];

        initializeFirstGroup(prefixSum, dpTable, st);

        for (int group = 1; group < totalGroups; group++) {
            computeOptimalDivision(group, group, totalLocations - 1, group - 1, totalLocations - 1, prefixSum, dpTable);
        }

        System.out.print(dpTable[totalGroups - 1][totalLocations - 1]);
    }

    private static void initializeFirstGroup(long[] prefixSum, long[][] dpTable, StringTokenizer tokenizer) {
        for (int location = 1; location < prefixSum.length; location++) {
            prefixSum[location] = prefixSum[location - 1] + Integer.parseInt(tokenizer.nextToken());
            dpTable[0][location] = (location + 1) * prefixSum[location];
        }
    }

    private static void computeOptimalDivision(int group, int start, int end, int leftBound, int rightBound,
            long[] prefixSum, long[][] dpTable) {
        if (start > end)
            return;

        int mid = (start + end) / 2;
        int upperBound = Math.min(mid - 1, rightBound);
        int optimalSplit = leftBound;
        dpTable[group][mid] = INF;

        long minCost = INF;
        for (int split = leftBound; split <= upperBound; split++) {
            long currentCost = dpTable[group - 1][split] + (mid - split) * (prefixSum[mid] - prefixSum[split]);
            if (currentCost < minCost) {
                minCost = currentCost;
                optimalSplit = split;
            }
        }
        dpTable[group][mid] = minCost;

        computeOptimalDivision(group, start, mid - 1, leftBound, optimalSplit, prefixSum, dpTable);
        computeOptimalDivision(group, mid + 1, end, optimalSplit, rightBound, prefixSum, dpTable);
    }
}

/*
6 3
11 11 11 24 26 100

299
*/