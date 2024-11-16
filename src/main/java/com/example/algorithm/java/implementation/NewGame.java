package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class NewGame {
    private static final int WHITE = 0;
    private static final int RED = 1;
    private static final int BLUE = 2;
    private static final int[][] DIRECTIONS = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };
    private static int[][] board;
    private static Deque<Integer>[][] pieceLocations;
    private static int[][] pieceInfo;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int boardSize = Integer.parseInt(st.nextToken());
        int pieceCount = Integer.parseInt(st.nextToken());

        initializeBoard(br, boardSize);
        initializePieces(br, pieceCount);

        int turn = simulateMoves(boardSize, pieceCount);
        System.out.println(turn);
    }

    @SuppressWarnings("unchecked")
    private static void initializeBoard(BufferedReader br, int size) throws IOException {
        board = new int[size][size];
        pieceLocations = new ArrayDeque[size][size];
        for (int i = 0; i < size; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < size; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
                pieceLocations[i][j] = new ArrayDeque<>();
            }
        }
    }

    private static void initializePieces(BufferedReader br, int pieceCount) throws IOException {
        pieceInfo = new int[pieceCount][3];
        for (int i = 0; i < pieceCount; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;
            int dir = Integer.parseInt(st.nextToken()) - 1;
            pieceLocations[row][col].offer(i);
            pieceInfo[i] = new int[] { row, col, dir };
        }
    }

    private static int simulateMoves(int size, int pieceCount) {
        for (int turn = 1; turn <= 1000; turn++) {
            for (int i = 0; i < pieceCount; i++) {
                if (movePiece(i, size))
                    return turn;
            }
        }
        return -1;
    }

    private static boolean movePiece(int pieceIndex, int size) {
        int row = pieceInfo[pieceIndex][0];
        int col = pieceInfo[pieceIndex][1];
        int dir = pieceInfo[pieceIndex][2];

        if (pieceLocations[row][col].peekFirst() != pieceIndex)
            return false;

        int newRow = row + DIRECTIONS[dir][0];
        int newCol = col + DIRECTIONS[dir][1];

        if (isOutOfBounds(newRow, newCol, size) || board[newRow][newCol] == BLUE) {
            dir = changeDirection(dir);
            pieceInfo[pieceIndex][2] = dir;
            newRow = row + DIRECTIONS[dir][0];
            newCol = col + DIRECTIONS[dir][1];

            if (isOutOfBounds(newRow, newCol, size) || board[newRow][newCol] == BLUE) {
                return false;
            }
        }
        return movePieceByColor(row, col, newRow, newCol);
    }

    private static boolean isOutOfBounds(int row, int col, int size) {
        return row < 0 || row >= size || col < 0 || col >= size;
    }

    private static int changeDirection(int dir) {
        return (dir % 2 == 0) ? dir + 1 : dir - 1;
    }

    private static boolean movePieceByColor(int row, int col, int newRow, int newCol) {
        if (board[newRow][newCol] == WHITE) {
            moveToWhite(row, col, newRow, newCol);
        } else if (board[newRow][newCol] == RED) {
            moveToRed(row, col, newRow, newCol);
        }
        return pieceLocations[newRow][newCol].size() >= 4;
    }

    private static void moveToWhite(int row, int col, int newRow, int newCol) {
        while (!pieceLocations[row][col].isEmpty()) {
            int movingPiece = pieceLocations[row][col].pollFirst();
            pieceLocations[newRow][newCol].offer(movingPiece);
            pieceInfo[movingPiece][0] = newRow;
            pieceInfo[movingPiece][1] = newCol;
        }
    }

    private static void moveToRed(int row, int col, int newRow, int newCol) {
        while (!pieceLocations[row][col].isEmpty()) {
            int movingPiece = pieceLocations[row][col].pollLast();
            pieceLocations[newRow][newCol].offer(movingPiece);
            pieceInfo[movingPiece][0] = newRow;
            pieceInfo[movingPiece][1] = newCol;
        }
    }
}

/*
4 4
0 0 2 0
0 0 1 0
0 0 1 2
0 2 0 0
2 1 1
3 2 3
2 2 1
4 1 2

-1


6 10
0 1 2 0 1 1
1 2 0 1 1 0
2 1 0 1 1 0
1 0 1 1 0 2
2 0 1 2 0 1
0 2 1 0 2 1
1 1 1
2 2 2
3 3 4
4 4 1
5 5 3
6 6 2
1 6 3
6 1 2
2 4 3
4 2 1

9
*/