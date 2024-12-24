package com.example.algorithm.java.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Game2048Easy {
    private static int n;
    private static int max = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        int[][] map = new int[n][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        backtracking(0, map);
        System.out.println(max);
    }

    private static void backtracking(int depth, int[][] map) {
        if (depth == 5) {
            updateMax(map);
            return;
        }

        for (int direction = 0; direction < 4; direction++) {
            int[][] newMap = deepCopy(map);
            swipe(newMap, direction);
            backtracking(depth + 1, newMap);
        }
    }

    private static void swipe(int[][] map, int direction) {
        switch (direction) {
            case 0:
                moveUp(map);
                break;
            case 1:
                moveDown(map);
                break;
            case 2:
                moveLeft(map);
                break;
            case 3:
                moveRight(map);
                break;
            default:
                break;
        }
    }

    private static void moveUp(int[][] map) {
        for (int col = 0; col < n; col++) {
            int index = 0, block = 0;
            for (int row = 0; row < n; row++) {
                if (map[row][col] != 0) {
                    if (block == map[row][col]) {
                        map[index - 1][col] *= 2;
                        block = 0;
                    } else {
                        block = map[row][col];
                        map[index++][col] = block;
                    }
                    if (index - 1 != row) {
                        map[row][col] = 0;
                    }
                }
            }
        }
    }

    private static void moveDown(int[][] map) {
        for (int col = 0; col < n; col++) {
            int index = n - 1, block = 0;
            for (int row = n - 1; row >= 0; row--) {
                if (map[row][col] != 0) {
                    if (block == map[row][col]) {
                        map[index + 1][col] *= 2;
                        block = 0;
                    } else {
                        block = map[row][col];
                        map[index--][col] = block;
                    }
                    if (index + 1 != row) {
                        map[row][col] = 0;
                    }
                }
            }
        }
    }

    private static void moveLeft(int[][] map) {
        for (int row = 0; row < n; row++) {
            int index = 0, block = 0;
            for (int col = 0; col < n; col++) {
                if (map[row][col] != 0) {
                    if (block == map[row][col]) {
                        map[row][index - 1] *= 2;
                        block = 0;
                    } else {
                        block = map[row][col];
                        map[row][index++] = block;
                    }
                    if (index - 1 != col) {
                        map[row][col] = 0;
                    }
                }
            }
        }
    }

    private static void moveRight(int[][] map) {
        for (int row = 0; row < n; row++) {
            int index = n - 1, block = 0;
            for (int col = n - 1; col >= 0; col--) {
                if (map[row][col] != 0) {
                    if (block == map[row][col]) {
                        map[row][index + 1] *= 2;
                        block = 0;
                    } else {
                        block = map[row][col];
                        map[row][index--] = block;
                    }
                    if (index + 1 != col) {
                        map[row][col] = 0;
                    }
                }
            }
        }
    }

    private static void updateMax(int[][] map) {
        for (int[] row : map) {
            for (int value : row) {
                if (max < value)
                    max = value;
            }
        }
    }

    private static int[][] deepCopy(int[][] map) {
        int[][] newMap = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(map[i], 0, newMap[i], 0, n);
        }
        return newMap;
    }
}

/*
3
2 2 2
4 4 4
8 8 8

16
*/