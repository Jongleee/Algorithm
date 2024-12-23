package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Cheese {
    static class Point {
        int row, col, day;

        Point(int row, int col, int day) {
            this.row = row;
            this.col = col;
            this.day = day;
        }
    }

    private static final int[] dRow = { 0, 1, 0, -1 };
    private static final int[] dCol = { 1, 0, -1, 0 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int row = Integer.parseInt(st.nextToken());
        int col = Integer.parseInt(st.nextToken());

        boolean[][] isCheese = new boolean[row][col];

        for (int r = 0; r < row; r++) {
            String[] line = br.readLine().split(" ");
            for (int c = 0; c < col; c++) {
                if (line[c].equals("1")) {
                    isCheese[r][c] = true;
                }
            }
        }

        int result = meltCheese(row, col, isCheese);
        System.out.println(result);
    }

    private static int meltCheese(int row, int col, boolean[][] isCheese) {
        Deque<Point> queue = initializeQueue(row, col);
        int[][] contact = new int[row][col];
        boolean[][] visited = new boolean[row][col];

        int days = 0;

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (visited[current.row][current.col]) {
                continue;
            }
            visited[current.row][current.col] = true;
            days = current.day;

            for (int dir = 0; dir < 4; dir++) {
                int nextRow = current.row + dRow[dir];
                int nextCol = current.col + dCol[dir];

                if (isOutOfBounds(nextRow, nextCol, row, col) || visited[nextRow][nextCol]) {
                    continue;
                }
                if (!isCheese[nextRow][nextCol]) {
                    queue.addFirst(new Point(nextRow, nextCol, current.day));
                } else if (++contact[nextRow][nextCol] > 1) {
                    queue.addLast(new Point(nextRow, nextCol, current.day + 1));
                }
            }
        }
        return days;
    }

    private static Deque<Point> initializeQueue(int row, int col) {
        Deque<Point> queue = new ArrayDeque<>();
        for (int c = 0; c < col; c++) {
            queue.add(new Point(0, c, 0));
            queue.add(new Point(row - 1, c, 0));
        }
        for (int r = 1; r < row - 1; r++) {
            queue.add(new Point(r, 0, 0));
            queue.add(new Point(r, col - 1, 0));
        }
        return queue;
    }

    private static boolean isOutOfBounds(int r, int c, int row, int col) {
        return r < 0 || c < 0 || r >= row || c >= col;
    }
}

/*
8 9
0 0 0 0 0 0 0 0 0
0 0 0 1 1 0 0 0 0
0 0 0 1 1 0 1 1 0
0 0 1 1 1 1 1 1 0
0 0 1 1 1 1 1 0 0
0 0 1 1 0 1 1 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0

4
*/