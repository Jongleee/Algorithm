package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Gear {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[][] gears = new int[4][8];

        for (int i = 0; i < 4; i++) {
            String line = br.readLine();
            for (int j = 0; j < 8; j++) {
                gears[i][j] = line.charAt(j) - '0';
            }
        }

        int k = Integer.parseInt(br.readLine());

        while (k-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int gearNum = Integer.parseInt(st.nextToken()) - 1;
            int direction = Integer.parseInt(st.nextToken());

            int[] rotationDir = determineRotation(gears, gearNum, direction);
            applyRotation(gears, rotationDir);
        }

        System.out.println(calculateScore(gears));
    }

    private static int[] determineRotation(int[][] gears, int gearNum, int direction) {
        int[] rotationDir = new int[4];
        rotationDir[gearNum] = direction;

        for (int i = gearNum - 1; i >= 0; i--) {
            if (gears[i][2] != gears[i + 1][6]) {
                rotationDir[i] = -rotationDir[i + 1];
            } else {
                break;
            }
        }

        for (int i = gearNum + 1; i < 4; i++) {
            if (gears[i][6] != gears[i - 1][2]) {
                rotationDir[i] = -rotationDir[i - 1];
            } else {
                break;
            }
        }

        return rotationDir;
    }

    private static void applyRotation(int[][] gears, int[] rotationDir) {
        for (int i = 0; i < 4; i++) {
            if (rotationDir[i] == 1) {
                rotateClockwise(gears[i]);
            } else if (rotationDir[i] == -1) {
                rotateCounterClockwise(gears[i]);
            }
        }
    }

    private static void rotateClockwise(int[] gear) {
        int temp = gear[7];
        System.arraycopy(gear, 0, gear, 1, 7);
        gear[0] = temp;
    }

    private static void rotateCounterClockwise(int[] gear) {
        int temp = gear[0];
        System.arraycopy(gear, 1, gear, 0, 7);
        gear[7] = temp;
    }

    private static int calculateScore(int[][] gears) {
        int score = 0;
        for (int i = 0; i < 4; i++) {
            if (gears[i][0] == 1) {
                score += (1 << i);
            }
        }
        return score;
    }
}

/*
10001011
10000011
01011011
00111101
5
1 1
2 1
3 1
4 1
1 -1

6


10010011
01010011
11100011
01010101
8
1 1
2 1
3 1
4 1
1 -1
2 -1
3 -1
4 -1

5
*/