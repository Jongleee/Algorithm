package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SameTower {
    private static final int MAX_HEIGHT = 250000;
    private static final int[][] dp = new int[2][MAX_HEIGHT + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        initializeDp();
        int n = Integer.parseInt(br.readLine());
        int[] blockHeights = parseBlockHeights(br.readLine(), n);

        int result = calculateMaxHeight(blockHeights);
        bw.write(String.valueOf(result > 0 ? result : -1));

        br.close();
        bw.close();
    }

    private static void initializeDp() {
        Arrays.fill(dp[0], -1);
        Arrays.fill(dp[1], -1);
        dp[0][0] = 0;
    }

    private static int[] parseBlockHeights(String line, int n) {
        int[] blockHeights = new int[n];
        StringTokenizer st = new StringTokenizer(line);
        for (int i = 0; i < n; i++) {
            blockHeights[i] = Integer.parseInt(st.nextToken());
        }
        return blockHeights;
    }

    private static int calculateMaxHeight(int[] blockHeights) {
        int currentIndex = 0;
        for (int height : blockHeights) {
            int nextIndex = currentIndex ^ 1;
            Arrays.fill(dp[nextIndex], -1);
            updateDpState(currentIndex, nextIndex, height);
            currentIndex = nextIndex;
        }
        return dp[currentIndex][0];
    }

    private static void updateDpState(int from, int to, int height) {
        for (int diff = 0; diff <= MAX_HEIGHT; diff++) {
            int currentValue = dp[from][diff];
            if (currentValue < 0)
                continue;

            dp[to][diff] = Math.max(dp[to][diff], currentValue);

            int taller = diff + height;
            if (taller <= MAX_HEIGHT) {
                dp[to][taller] = Math.max(dp[to][taller], currentValue + height);
            }

            int shorter = Math.abs(diff - height);
            if (shorter <= MAX_HEIGHT) {
                int addedHeight = diff < height ? shorter : 0;
                dp[to][shorter] = Math.max(dp[to][shorter], currentValue + addedHeight);
            }
        }
    }
}

/*
3
10 9 2

-1

9
14 3 20 15 15 14 24 23 15

64
*/