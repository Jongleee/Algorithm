package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GatherBalls {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        char[] balls = br.readLine().toCharArray();

        int redCount = countColor(balls, 'R');
        int blueCount = n - redCount;

        int leftStreak = countStreak(balls, 0, 1);
        int rightStreak = countStreak(balls, n - 1, -1);

        int minMoves = Math.min(redCount, blueCount);
        minMoves = Math.min(minMoves, calculateMoves(balls, 'R', redCount, leftStreak, rightStreak));
        minMoves = Math.min(minMoves, calculateMoves(balls, 'B', blueCount, leftStreak, rightStreak));

        System.out.println(minMoves);
    }

    private static int countColor(char[] balls, char color) {
        int count = 0;
        for (char ball : balls) {
            if (ball == color) {
                count++;
            }
        }
        return count;
    }

    private static int countStreak(char[] balls, int start, int step) {
        int count = 0;
        char initialColor = balls[start];
        for (int i = start; i >= 0 && i < balls.length; i += step) {
            if (balls[i] == initialColor) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    private static int calculateMoves(char[] balls, char color, int colorCount, int leftStreak, int rightStreak) {
        int moves = colorCount;
        if (balls[0] == color) {
            moves = Math.min(moves, colorCount - leftStreak);
        }
        if (balls[balls.length - 1] == color) {
            moves = Math.min(moves, colorCount - rightStreak);
        }
        return moves;
    }
}

/*
9
RBBBRBRRR

2


8
BBRBBBBR

1
 */
