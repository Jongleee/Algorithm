package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class PoliceCar {
    private static int n, w;
    private static int[][] accidentPositions;
    private static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        n = Integer.parseInt(br.readLine());
        w = Integer.parseInt(br.readLine());

        accidentPositions = new int[w + 1][2];
        dp = new int[w + 1][w + 1];

        for (int i = 1; i <= w; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            accidentPositions[i][0] = Integer.parseInt(st.nextToken());
            accidentPositions[i][1] = Integer.parseInt(st.nextToken());
        }

        int result = calculateMinDistance(0, 0, 1);
        bw.write(result + "\n");

        trackPoliceMovements(bw);

        bw.flush();
        bw.close();
        br.close();
    }

    private static int calculateMinDistance(int first, int second, int next) {
        if (next > w) {
            return 0;
        }
        if (dp[first][second] != 0) {
            return dp[first][second];
        }

        int firstMove = calculateMinDistance(next, second, next + 1) + calculateDistance(first, next, true);
        int secondMove = calculateMinDistance(first, next, next + 1) + calculateDistance(second, next, false);

        return dp[first][second] = Math.min(firstMove, secondMove);
    }

    private static void trackPoliceMovements(BufferedWriter bw) throws IOException {
        int policeOne = 0, policeTwo = 0;
        for (int i = 1; i <= w; i++) {
            int moveDistance = calculateDistance(policeOne, i, true);
            if (dp[policeOne][policeTwo] - moveDistance == dp[i][policeTwo]) {
                bw.write("1\n");
                policeOne = i;
            } else {
                bw.write("2\n");
                policeTwo = i;
            }
        }
    }

    private static int calculateDistance(int current, int next, boolean isFirstPolice) {
        int currentX = accidentPositions[current][0];
        int currentY = accidentPositions[current][1];
        int nextX = accidentPositions[next][0];
        int nextY = accidentPositions[next][1];

        if (current == 0) {
            if (isFirstPolice) {
                currentX = currentY = 1;
            } else {
                currentX = currentY = n;
            }
        }

        return Math.abs(currentX - nextX) + Math.abs(currentY - nextY);
    }
}

/*
6
3
3 5
5 5
2 3

9
2
2
1
*/