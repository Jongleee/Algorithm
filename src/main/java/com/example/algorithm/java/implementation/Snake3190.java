package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Snake3190 {
    private static final int WALL = -1;
    private static final int APPLE = 1;
    private static final int EMPTY = 0;
    private static final int LEFT_TURN = 3;
    private static final int RIGHT_TURN = 1;
    private static final int MAX_TIME = 10_100;
    private static final char LEFT = 'L';

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int boardSize = Integer.parseInt(br.readLine());
        int appleCount = Integer.parseInt(br.readLine());

        int[][] board = initializeBoard(boardSize, appleCount, br);
        int directionChanges = Integer.parseInt(br.readLine());
        int[][] moves = getMoves(directionChanges, br);

        int result = simulateGame(board, moves);
        System.out.print(result);
    }

    private static int[][] initializeBoard(int n, int k, BufferedReader br) throws IOException {
        int size = n + 2;
        int[][] board = new int[size][size];

        for (int i = 0; i < size; i++) {
            board[0][i] = WALL;
            board[size - 1][i] = WALL;
            board[i][0] = WALL;
            board[i][size - 1] = WALL;
        }

        for (int i = 0; i < k; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken());
            int col = Integer.parseInt(st.nextToken());
            board[row][col] = APPLE;
        }
        return board;
    }

    private static int[][] getMoves(int l, BufferedReader br) throws IOException {
        int[][] moves = new int[l][2];
        for (int i = 0; i < l; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            moves[i][0] = Integer.parseInt(st.nextToken());
            moves[i][1] = st.nextToken().charAt(0) == LEFT ? LEFT_TURN : RIGHT_TURN;
        }
        return moves;
    }

    private static int simulateGame(int[][] board, int[][] moves) {
        int[] dx = { -1, 0, 1, 0 };
        int[] dy = { 0, 1, 0, -1 };
        int direction = 1;
        int time = 0;
        int x = 1, y = 1;
        int moveIndex = 0;
        int snakeLength = 1;
        int[][] snake = new int[MAX_TIME][2];
        snake[0][0] = x;
        snake[0][1] = y;
        board[x][y] = WALL;

        while (true) {
            time++;
            x += dx[direction];
            y += dy[direction];

            if (board[x][y] == WALL)
                break;

            if (board[x][y] == APPLE) {
                snakeLength++;
            } else {
                int tailX = snake[time - snakeLength][0];
                int tailY = snake[time - snakeLength][1];
                board[tailX][tailY] = EMPTY;
            }

            board[x][y] = WALL;
            snake[time][0] = x;
            snake[time][1] = y;

            if (moveIndex < moves.length && moves[moveIndex][0] == time) {
                direction = (direction + moves[moveIndex][1]) % 4;
                moveIndex++;
            }
        }
        return time;
    }
}

/*
6
3
3 4
2 5
5 3
3
3 D
15 L
17 D

9


10
4
1 2
1 3
1 4
1 5
4
8 D
10 D
11 D
13 L

21
*/