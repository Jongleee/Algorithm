package com.example.algorithm.java.implement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CookieBodyMeasurement {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(reader.readLine());
        char[][] map = new char[size][size];
        int[] col = new int[size];

        for (int i = 0; i < size; i++) {
            String input = reader.readLine();
            for (int j = 0; j < size; j++) {
                map[i][j] = input.charAt(j);
                if (map[i][j] == '*') {
                    col[i]++;
                }
            }
        }

        int heartCol = 0;
        int heartRow = 0;
        int maxCol = 0;
        for (int i = 0; i < size; i++) {
            if (col[i] > maxCol) {
                maxCol = col[i];
                heartCol = i;
            }
        }
        for (int i = 0; i < size; i++) {
            if (map[heartCol - 1][i] == '*')
                heartRow = i;
        }

        System.out.printf("%d %d\n", heartCol + 1, heartRow + 1);

        int rightHand = 0;
        int leftHand = 0;
        int rightLeg = 0;
        int leftLeg = 0;
        int body = 0;

        for (int i = heartRow - 1; i >= 0; i--) {
            if (map[heartCol][i] == '*')
                leftHand++;
            else
                break;
        }
        for (int i = heartRow + 1; i < size; i++) {
            if (map[heartCol][i] == '*')
                rightHand++;
            else
                break;
        }
        for (int i = heartCol + 1; i < size; i++) {
            if (map[i][heartRow] != '*') {
                heartCol = i;
                break;
            } else
                body++;
        }
        for (int i = heartCol; i < size; i++) {
            if (map[i][heartRow - 1] == '*')
                leftLeg++;
            else
                break;
        }
        for (int i = heartCol; i < size; i++) {
            if (map[i][heartRow + 1] == '*')
                rightLeg++;
            else
                break;
        }

        System.out.printf("%d %d %d %d %d", leftHand, rightHand, body, leftLeg, rightLeg);
    }
}
/*
5
_____
__*__
_***_
__*__
_*_*_

3 3
1 1 1 1 1

10
__________
_____*____
__******__
_____*____
_____*____
_____*____
____*_*___
____*_____
____*_____
____*_____

3 6
3 2 3 4 1
 */