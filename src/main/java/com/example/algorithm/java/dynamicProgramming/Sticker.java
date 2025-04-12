package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Sticker {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseCount = Integer.parseInt(br.readLine());

        for (int t = 0; t < testCaseCount; t++) {
            int columnCount = Integer.parseInt(br.readLine());
            int[][] stickers = readStickerArray(br, columnCount);
            int[][] dp = computeMaxStickerScore(stickers, columnCount);
            System.out.println(Math.max(dp[0][columnCount], dp[1][columnCount]));
        }
    }

    private static int[][] readStickerArray(BufferedReader br, int columnCount) throws IOException {
        int[][] stickers = new int[2][columnCount + 1];
        for (int row = 0; row < 2; row++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int col = 1; col <= columnCount; col++) {
                stickers[row][col] = Integer.parseInt(st.nextToken());
            }
        }
        return stickers;
    }

    private static int[][] computeMaxStickerScore(int[][] stickers, int columnCount) {
        int[][] dp = new int[2][columnCount + 1];
        dp[0][1] = stickers[0][1];
        dp[1][1] = stickers[1][1];

        for (int col = 2; col <= columnCount; col++) {
            dp[0][col] = Math.max(dp[1][col - 1], dp[1][col - 2]) + stickers[0][col];
            dp[1][col] = Math.max(dp[0][col - 1], dp[0][col - 2]) + stickers[1][col];
        }

        return dp;
    }
}

/*
2
5
50 10 100 20 40
30 50 70 10 60
7
10 30 10 50 100 20 40
20 40 30 50 60 20 80

260
290
*/