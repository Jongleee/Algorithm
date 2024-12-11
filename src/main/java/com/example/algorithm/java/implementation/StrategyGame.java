package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class StrategyGame {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int playerCount = Integer.parseInt(st.nextToken());
        int rounds = Integer.parseInt(st.nextToken());

        int[] scores = calculateScores(br, playerCount, rounds);
        int winner = findWinner(scores);

        System.out.println(winner);
    }

    private static int[] calculateScores(BufferedReader br, int playerCount, int rounds) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] scores = new int[playerCount + 1];
        for (int i = 0; i < playerCount * rounds; i++) {
            int score = Integer.parseInt(st.nextToken());
            scores[(i % playerCount) + 1] += score;
        }
        return scores;
    }

    private static int findWinner(int[] scores) {
        int maxScore = -1;
        int winner = 0;
        for (int i = 1; i < scores.length; i++) {
            if (scores[i] >= maxScore) {
                maxScore = scores[i];
                winner = i;
            }
        }
        return winner;
    }
}

/*
3 3
1 1 1 1 2 2 2 3 3

3


2 3
0 0 1 0 2 0

1
*/