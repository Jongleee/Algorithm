package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class MovingMazeEscape {
    private static final int SIZE = 8;
    private static char[][] grid = new char[SIZE + 1][SIZE + 1];
    private static boolean[][][] visited = new boolean[SIZE + 1][SIZE + 1][SIZE + 1];
    private static Queue<Point> wallQueue = new LinkedList<>();
    private static Queue<Point> personQueue = new LinkedList<>();
    static boolean isEscaped;

    static class Point {
        int row;
        int col;
        int step;

        Point(int row, int col, int step) {
            this.row = row;
            this.col = col;
            this.step = step;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 1; i <= SIZE; i++) {
            char[] line = reader.readLine().toCharArray();
            for (int j = 1; j <= SIZE; j++) {
                grid[i][j] = line[j - 1];
                if (grid[i][j] == '#') {
                    wallQueue.add(new Point(i, j, 0));
                }
            }
        }

        visited[0][SIZE][1] = true;
        personQueue.add(new Point(SIZE, 1, 0));

        while (!isEscaped) {
            if (processPersonMovement())
                break;
            moveWallsDown();
        }

        System.out.println(isEscaped ? 1 : 0);
    }

    private static boolean processPersonMovement() {
        int[] dRow = { -1, 1, 0, 0, -1, -1, 1, 1, 0 };
        int[] dCol = { 0, 0, -1, 1, -1, 1, 1, -1, 0 };

        Queue<Point> tempQueue = new LinkedList<>();

        while (!personQueue.isEmpty()) {
            Point person = personQueue.poll();

            if (grid[person.row][person.col] == '#')
                continue;
            if ((person.row == 1 && person.col == SIZE) || person.step >= SIZE) {
                isEscaped = true;
                break;
            }

            for (int i = 0; i < 9; i++) {
                int newRow = person.row + dRow[i];
                int newCol = person.col + dCol[i];

                if (isOutOfBound(newRow, newCol) || grid[newRow][newCol] == '#'
                        || visited[person.step + 1][newRow][newCol])
                    continue;

                if (grid[newRow - 1][newCol] == '#')
                    continue;

                visited[person.step + 1][newRow][newCol] = true;
                tempQueue.add(new Point(newRow, newCol, person.step + 1));
            }
        }

        personQueue = tempQueue;
        return personQueue.isEmpty();
    }

    private static void moveWallsDown() {
        boolean[][] checked = new boolean[SIZE + 1][SIZE + 1];
        Queue<Point> tempQueue = new LinkedList<>();

        while (!wallQueue.isEmpty()) {
            Point wall = wallQueue.poll();

            if (wall.row < SIZE) {
                if (!checked[wall.row][wall.col]) {
                    checked[wall.row + 1][wall.col] = true;
                    grid[wall.row][wall.col] = '.';
                }

                grid[wall.row + 1][wall.col] = '#';
                tempQueue.add(new Point(wall.row + 1, wall.col, 0));
            } else {
                if (!checked[wall.row][wall.col]) {
                    grid[wall.row][wall.col] = '.';
                }
            }
        }

        wallQueue = tempQueue;
    }

    private static boolean isOutOfBound(int row, int col) {
        return row < 1 || col < 1 || row > SIZE || col > SIZE;
    }
}

/*
........
........
........
........
........
........
........
........

1


........
........
........
........
........
........
##......
........

0
*/