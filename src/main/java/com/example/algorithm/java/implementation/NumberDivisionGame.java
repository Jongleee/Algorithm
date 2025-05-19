package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class NumberDivisionGame {
    private static final int SIZE = 1_000_001;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int playerCount = Integer.parseInt(br.readLine());

        int[] indexByValue = new int[SIZE];
        int[] scores = new int[playerCount + 1];

        int maxCardValue = fillIndexMapAndGetMax(br, playerCount, indexByValue);
        calculateScores(indexByValue, scores, maxCardValue);
        printScores(scores);
    }

    private static int fillIndexMapAndGetMax(BufferedReader br, int playerCount, int[] indexByValue)
            throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int maxValue = 0;
        for (int i = 1; i <= playerCount; i++) {
            int card = Integer.parseInt(st.nextToken());
            indexByValue[card] = i;
            maxValue = Math.max(maxValue, card);
        }
        return maxValue;
    }

    private static void calculateScores(int[] indexByValue, int[] scores, int maxValue) {
        for (int value = 1; value <= maxValue; value++) {
            int playerIndex = indexByValue[value];
            if (playerIndex == 0)
                continue;

            for (int multiple = value << 1; multiple <= maxValue; multiple += value) {
                int opponentIndex = indexByValue[multiple];
                if (opponentIndex == 0)
                    continue;

                scores[playerIndex]++;
                scores[opponentIndex]--;
            }
        }
    }

    private static void printScores(int[] scores) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < scores.length; i++) {
            sb.append(scores[i]).append(' ');
        }
        System.out.println(sb);
    }
}

/*
3
3 4 12

1 1 -2 


4
7 23 8 6

0 0 0 0 
*/