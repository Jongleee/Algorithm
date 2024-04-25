package com.example.algorithm.java.implement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CookieBodyMeasurement {
    static char[][] map;
    static int size;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        size = Integer.parseInt(reader.readLine());
        map = new char[size][size];
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

        int heartColumn = 0;
        int heartRow = 0;
        int maxCol = 0;
        for (int i = 0; i < size; i++) {
            if (col[i] > maxCol) {
                maxCol = col[i];
                heartColumn = i;
            }
        }
        for (int i = 0; i < size; i++) {
            if (map[heartColumn - 1][i] == '*') {
                heartRow = i;
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(heartColumn + 1).append(' ').append(heartRow + 1).append('\n');

        int leftHand = countBodyParts(heartColumn, heartRow - 1, 0, -1);
        int rightHand = countBodyParts(heartColumn, heartRow + 1, 0, 1);
        int body = countBodyParts(heartColumn + 1, heartRow, 1, 0);
        int leftLeg = countBodyParts(heartColumn + body + 1, heartRow - 1, 1, 0);
        int rightLeg = countBodyParts(heartColumn + body + 1, heartRow + 1, 1, 0);

        sb.append(leftHand).append(' ').append(rightHand).append(' ')
                .append(body).append(' ').append(leftLeg).append(' ').append(rightLeg);

        System.out.print(sb);
    }

    private static int countBodyParts(int row, int col, int rowInc, int colInc) {
        int count = 0;
        while (isOnMap(row, col) && map[row][col] == '*') {
            count++;
            row += rowInc;
            col += colInc;
        }
        return count;
    }

    private static boolean isOnMap(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
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

9
____*____
*********
____*____
____*____
____*____
___*_*___
___*_*___
___*_*___
___*_*___

2 5
4 4 3 4 4
 */