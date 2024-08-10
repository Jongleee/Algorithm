package com.example.algorithm.java.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GasPipe {
    private static int rows;
    private static int cols;
    private static char[][] map;
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};
    private static final int TOP = 0;
    private static final int BOTTOM = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] dimensions = br.readLine().split(" ");
        rows = Integer.parseInt(dimensions[0]);
        cols = Integer.parseInt(dimensions[1]);
        map = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            map[i] = br.readLine().toCharArray();
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == '.' && checkPipe(i, j)) {
                    return;
                }
            }
        }
    }

    private static boolean checkPipe(int x, int y) {
        boolean[] connected = new boolean[4];
        connected[TOP] = isInRange(x + dx[TOP], y + dy[TOP]) && canComeFromBottom(map[x + dx[TOP]][y + dy[TOP]]);
        connected[BOTTOM] = isInRange(x + dx[BOTTOM], y + dy[BOTTOM]) && canComeFromTop(map[x + dx[BOTTOM]][y + dy[BOTTOM]]);
        connected[LEFT] = isInRange(x + dx[LEFT], y + dy[LEFT]) && canComeFromRight(map[x + dx[LEFT]][y + dy[LEFT]]);
        connected[RIGHT] = isInRange(x + dx[RIGHT], y + dy[RIGHT]) && canComeFromLeft(map[x + dx[RIGHT]][y + dy[RIGHT]]);

        if (connected[TOP] && connected[BOTTOM] && connected[LEFT] && connected[RIGHT])
            System.out.println((x + 1) + " " + (y + 1) + " +");
        else if (connected[TOP] && connected[BOTTOM])
            System.out.println((x + 1) + " " + (y + 1) + " |");
        else if (connected[TOP] && connected[LEFT])
            System.out.println((x + 1) + " " + (y + 1) + " 3");
        else if (connected[TOP] && connected[RIGHT])
            System.out.println((x + 1) + " " + (y + 1) + " 2");
        else if (connected[BOTTOM] && connected[LEFT])
            System.out.println((x + 1) + " " + (y + 1) + " 4");
        else if (connected[BOTTOM] && connected[RIGHT])
            System.out.println((x + 1) + " " + (y + 1) + " 1");
        else if (connected[LEFT] && connected[RIGHT])
            System.out.println((x + 1) + " " + (y + 1) + " -");
        else
            return false;

        return true;
    }

    private static boolean isInRange(int x, int y) {
        return 0 <= x && x < rows && 0 <= y && y < cols;
    }

    private static boolean canComeFromTop(char c) {
        return c == '|' || c == '+' || c == '2' || c == '3';
    }

    private static boolean canComeFromBottom(char c) {
        return c == '|' || c == '+' || c == '1' || c == '4';
    }

    private static boolean canComeFromLeft(char c) {
        return c == '-' || c == '+' || c == '3' || c == '4';
    }

    private static boolean canComeFromRight(char c) {
        return c == '-' || c == '+' || c == '1' || c == '2';
    }
}


/*
3 7
.......
.M-.-Z.
.......

2 4 -


6 10
Z.1----4..
|.|....|..
|..14..M..
2-+++4....
..2323....
..........

3 3 |
*/