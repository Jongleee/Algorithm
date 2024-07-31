package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MergeFiles {
    private static int size;
    private static long[][] dp;
    private static long[] cumulativeSum;
    private static int[][] optIndices;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        int[] costs;
        StringBuilder sb = new StringBuilder();

        for (int t = 0; t < testCase; t++) {
            size = Integer.parseInt(br.readLine());
            costs = new int[size + 1];
            dp = new long[size + 2][size + 2];
            cumulativeSum = new long[size + 1];
            optIndices = new int[size + 1][size + 1];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= size; i++) {
                costs[i] = Integer.parseInt(st.nextToken());
                cumulativeSum[i] = cumulativeSum[i - 1] + costs[i];
            }

            calculateMinCost();
            sb.append(dp[1][size]).append('\n');
        }

        System.out.print(sb);
    }

    private static void calculateMinCost() {
        for (int i = 1; i <= size; i++) {
            optIndices[i][i] = i;
        }

        for (int start = size - 1; start >= 1; start--) {
            for (int end = start + 1; end <= size; end++) {
                if (dp[start][end] == 0)
                    dp[start][end] = Long.MAX_VALUE;

                for (int k = optIndices[start][end - 1]; k <= optIndices[start + 1][end] && k < end; k++) {
                    long cost = dp[start][k] + dp[k + 1][end] + cumulativeSum[end] - cumulativeSum[start - 1];
                    if (dp[start][end] > cost) {
                        dp[start][end] = cost;
                        optIndices[start][end] = k;
                    }
                }
            }
        }
    }
}

/*
2
4
40 30 30 50
15
1 21 3 4 5 35 5 4 3 5 98 21 14 17 32

300
864
*/