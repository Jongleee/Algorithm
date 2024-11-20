package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NewGame2 {
    static int n, k;
    static int[][] board;
    static int[] directions;
    static int[] positions;
    static int[] dx = { 0, 0, -1, 1 };
    static int[] dy = { 1, -1, 0, 0 };
    static List<Integer>[][] stacks;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] tokens = br.readLine().split(" ");
        n = Integer.parseInt(tokens[0]);
        k = Integer.parseInt(tokens[1]);
        board = new int[n][n];
        directions = new int[k + 1];
        positions = new int[k + 1];
        stacks = new List[n][n];

        for (int i = 0; i < n; i++) {
            tokens = br.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(tokens[j]);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                stacks[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < k; i++) {
            tokens = br.readLine().split(" ");
            int row = Integer.parseInt(tokens[0]) - 1;
            int col = Integer.parseInt(tokens[1]) - 1;
            int dir = Integer.parseInt(tokens[2]) - 1;
            directions[i + 1] = dir;
            positions[i + 1] = row * n + col;
            stacks[row][col].add(i + 1);
        }

        int turn = 1;
        while (turn <= 1000) {
            for (int i = 0; i < k; i++) {
                int piece = i + 1;
                int dir = directions[piece];
                int x = positions[piece] / n;
                int y = positions[piece] % n;
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                int stackSize = stacks[x][y].size();

                if (!isValid(nx, ny) || board[nx][ny] == 2) {
                    dir = reverseDirection(dir);
                    directions[piece] = dir;
                    nx = x + dx[dir];
                    ny = y + dy[dir];
                }

                if (isValid(nx, ny)) {
                    if (board[nx][ny] == 1) {
                        moveRed(x, y, nx, ny, piece, stackSize);
                    } else if (board[nx][ny] == 0) {
                        moveWhite(x, y, nx, ny, piece, stackSize);
                    }
                    if (stacks[nx][ny].size() > 3) {
                        System.out.println(turn);
                        return;
                    }
                }
            }
            turn++;
        }
        System.out.println(-1);
    }

    static void moveRed(int x, int y, int nx, int ny, int piece, int stackSize) {
        for (int i = stackSize - 1; i >= 0; i--) {
            int currPiece = stacks[x][y].get(i);
            stacks[nx][ny].add(currPiece);
            stacks[x][y].remove(i);
            positions[currPiece] = nx * n + ny;
            if (currPiece == piece)
                break;
        }
    }

    static void moveWhite(int x, int y, int nx, int ny, int piece, int stackSize) {
        for (int i = 0; i < stackSize; i++) {
            int currPiece = stacks[x][y].get(i);
            if (currPiece == piece) {
                while (i < stacks[x][y].size()) {
                    currPiece = stacks[x][y].get(i);
                    stacks[nx][ny].add(currPiece);
                    stacks[x][y].remove(i);
                    positions[currPiece] = nx * n + ny;
                }
                break;
            }
        }
    }

    static boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < n && y < n;
    }

    static int reverseDirection(int dir) {
        if (dir == 0)
            return 1;
        if (dir == 1)
            return 0;
        if (dir == 2)
            return 3;
        return 2;
    }
}

/*
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

7


4 4
0 0 0 0
0 0 0 0
0 0 0 0
0 0 0 0
1 1 1
1 2 1
1 3 1
3 3 3

2
*/