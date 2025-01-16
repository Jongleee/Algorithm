package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class FishingKing {
    static final int[][] DIRECTIONS = { {}, { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };

    static class Shark {
        int speed;
        int direction;
        int size;

        public Shark(int speed, int direction, int size) {
            this.speed = speed;
            this.direction = direction;
            this.size = size;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());

        int rows = Integer.parseInt(tokenizer.nextToken());
        int cols = Integer.parseInt(tokenizer.nextToken());
        int sharkCount = Integer.parseInt(tokenizer.nextToken());

        Shark[][] board = initializeBoard(br, rows, cols, sharkCount);

        int totalSize = 0;
        for (int col = 0; col < cols; col++) {
            totalSize += catchShark(board, rows, col);
            board = moveSharks(board, rows, cols);
        }

        System.out.println(totalSize);
    }

    private static Shark[][] initializeBoard(BufferedReader br, int rows, int cols, int sharkCount) throws IOException {
        Shark[][] board = new Shark[rows][cols];
        for (int i = 0; i < sharkCount; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(tokenizer.nextToken()) - 1;
            int col = Integer.parseInt(tokenizer.nextToken()) - 1;
            int speed = Integer.parseInt(tokenizer.nextToken());
            int direction = Integer.parseInt(tokenizer.nextToken());
            int size = Integer.parseInt(tokenizer.nextToken());
            board[row][col] = new Shark(speed, direction, size);
        }
        return board;
    }

    private static int catchShark(Shark[][] board, int rows, int col) {
        for (int row = 0; row < rows; row++) {
            if (board[row][col] != null) {
                int size = board[row][col].size;
                board[row][col] = null;
                return size;
            }
        }
        return 0;
    }

    private static Shark[][] moveSharks(Shark[][] board, int rows, int cols) {
        Shark[][] nextBoard = new Shark[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] != null) {
                    moveShark(board[row][col], row, col, nextBoard, rows, cols);
                }
            }
        }
        return nextBoard;
    }

    private static void moveShark(Shark shark, int row, int col, Shark[][] nextBoard, int rows, int cols) {
        int directionRow = DIRECTIONS[shark.direction][0];
        int directionCol = DIRECTIONS[shark.direction][1];
        int nextRow = row + directionRow * shark.speed;

        if (nextRow < 0 || nextRow >= rows) {
            nextRow = Math.abs(nextRow);
            int quotient = (nextRow - 1) / (rows - 1);
            boolean isFlipped = (directionRow < 0) == (quotient % 2 == 0);
            shark.direction = isFlipped ? shark.direction % 2 + 1 : shark.direction;
            int remainder = getRemainder(rows, nextRow);
            nextRow = shark.direction == 1 ? rows - 1 - remainder : remainder;
        }
        int nextCol = col + directionCol * shark.speed;

        if (nextCol < 0 || nextCol >= cols) {
            nextCol = Math.abs(nextCol);
            int quotient = (nextCol - 1) / (cols - 1);
            boolean isFlipped = (directionCol < 0) == (quotient % 2 == 0);
            shark.direction = isFlipped ? (shark.direction) % 2 + 3 : shark.direction;
            int remainder = getRemainder(cols, nextCol);
            nextCol = shark.direction == 4 ? cols - 1 - remainder : remainder;
        }
        if (nextBoard[nextRow][nextCol] != null && shark.size < nextBoard[nextRow][nextCol].size) {
            return;
        }
        nextBoard[nextRow][nextCol] = shark;
    }

    private static int getRemainder(int value, int nextValue) {
        return nextValue % (value - 1) == 0 ? value - 1 : nextValue % (value - 1);
    }
}

/*
100 100 0

0


4 5 4
4 1 3 3 8
1 3 5 2 9
2 4 8 4 1
4 5 0 1 4

22
*/