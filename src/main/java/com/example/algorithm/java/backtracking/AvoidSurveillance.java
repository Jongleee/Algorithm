package com.example.algorithm.java.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AvoidSurveillance {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        char[][] board = new char[n][n];
        List<int[]> teachers = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                board[i][j] = st.nextToken().charAt(0);
                if (board[i][j] == 'T') {
                    teachers.add(new int[] { i, j });
                }
            }
        }

        System.out.println(canAvoidDetection(board, teachers, n) ? "YES" : "NO");
    }

    private static boolean canAvoidDetection(char[][] board, List<int[]> teachers, int n) {
        List<int[]> emptySpaces = getEmptySpaces(board, n);
        return placeObstacles(board, teachers, emptySpaces, 0, 0, n);
    }

    private static List<int[]> getEmptySpaces(char[][] board, int n) {
        List<int[]> emptySpaces = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'X') {
                    emptySpaces.add(new int[] { i, j });
                }
            }
        }
        return emptySpaces;
    }

    private static boolean placeObstacles(char[][] board, List<int[]> teachers, List<int[]> emptySpaces, int placed,
            int index, int n) {
        if (placed == 3) {
            return isSafe(board, teachers, n);
        }

        for (int i = index; i < emptySpaces.size(); i++) {
            int[] pos = emptySpaces.get(i);
            board[pos[0]][pos[1]] = 'O';
            if (placeObstacles(board, teachers, emptySpaces, placed + 1, i + 1, n)) {
                return true;
            }
            board[pos[0]][pos[1]] = 'X';
        }

        return false;
    }

    private static boolean isSafe(char[][] board, List<int[]> teachers, int n) {
        int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

        for (int[] teacher : teachers) {
            for (int[] dir : directions) {
                int y = teacher[0];
                int x = teacher[1];
                while (true) {
                    y += dir[0];
                    x += dir[1];
                    if (y < 0 || y >= n || x < 0 || x >= n || board[y][x] == 'O')
                        break;
                    if (board[y][x] == 'S')
                        return false;
                }
            }
        }

        return true;
    }
}

/*
5
X S X X T
T X S X X
X X X X X
X T X X X
X X T X X

YES


4
S S S T
X X X X
X X X X
T T T X

NO
*/