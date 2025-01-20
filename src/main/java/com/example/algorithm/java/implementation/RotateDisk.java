package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class RotateDisk {
    static class Pair {
        int row, col;

        Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        int[][] board = new int[n + 2][m];
        int[] rotationOffsets = new int[n + 2];

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        while (t-- > 0) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int direction = Integer.parseInt(st.nextToken()) == 0 ? 1 : -1;
            int k = Integer.parseInt(st.nextToken());

            rotateDisks(rotationOffsets, x, direction, k, m, n);
            ArrayList<Pair> toRemove = findPairsToRemove(board, rotationOffsets, n, m);

            if (toRemove.isEmpty()) {
                adjustNumbers(board, n, m);
            } else {
                removeNumbers(board, toRemove);
            }
        }

        System.out.println(calculateSum(board, n, m));
    }

    private static void rotateDisks(int[] offsets, int x, int direction, int k, int m, int n) {
        for (int i = x; i <= n; i += x) {
            offsets[i] = (offsets[i] + direction * k + m) % m;
        }
    }

    private static ArrayList<Pair> findPairsToRemove(int[][] board, int[] offsets, int n, int m) {
        ArrayList<Pair> toRemove = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 0)
                    continue;

                if (board[i][j] == board[i][(j + 1) % m] ||
                        board[i][j] == board[i][(j - 1 + m) % m] ||
                        (i < n && board[i][j] == board[i + 1][(j + offsets[i] - offsets[i + 1] + m) % m]) ||
                        (i > 1 && board[i][j] == board[i - 1][(j + offsets[i] - offsets[i - 1] + m) % m])) {
                    toRemove.add(new Pair(i, j));
                }
            }
        }

        return toRemove;
    }

    private static void adjustNumbers(int[][] board, int n, int m) {
        int sum = 0, count = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] > 0) {
                    sum += board[i][j];
                    count++;
                }
            }
        }

        if (count == 0)
            return;

        process(board, n, m, (double) sum / count);
    }

    private static void process(int[][] board, int n, int m, double average) {
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] > 0) {
                    if (board[i][j] > average)
                        board[i][j]--;
                    else if (board[i][j] < average)
                        board[i][j]++;
                }
            }
        }
    }

    private static void removeNumbers(int[][] board, ArrayList<Pair> toRemove) {
        for (Pair pair : toRemove) {
            board[pair.row][pair.col] = 0;
        }
    }

    private static int calculateSum(int[][] board, int n, int m) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < m; j++) {
                sum += board[i][j];
            }
        }
        return sum;
    }
}

/*
4 4 1
1 1 2 3
5 2 4 2
3 1 3 5
2 1 3 2
2 0 1

30


4 4 3
1 1 2 3
5 2 4 2
3 1 3 5
2 1 3 2
2 0 1
3 1 3
2 0 2

22
*/