package com.example.algorithm.java.practice;

import java.util.PriorityQueue;

public class BuildingTrack {

    private static class Car implements Comparable<Car> {
        int x;
        int y;
        int dir;
        int sum;

        public Car(int x, int y, int dir, int sum) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.sum = sum;
        }

        @Override
        public int compareTo(Car o) {
            return this.sum - o.sum;
        }
    }

    private static int n;
    private static int[] dx = {0, 1, 0, -1};
    private static int[] dy = {1, 0, -1, 0};

    public static int solution(int[][] board) {
        n = board.length;
        return build(0, 0, board);
    }

    private static int build(int i, int j, int[][] board) {
        int answer = 0;

        int[][][] minSum = new int[n][n][4];

        minSum[i][j][3] = minSum[i][j][2] = minSum[i][j][1] = minSum[i][j][0] = -1;
        PriorityQueue<Car> queue = new PriorityQueue<>();
        extracted(i, j, board, minSum, queue);

        while (!queue.isEmpty()) {
            Car tmp = queue.poll();
            int x = tmp.x;
            int y = tmp.y;
            int dir = tmp.dir;
            int sum = tmp.sum;

            if (x == n - 1 && y == n - 1) {
                answer = sum;
                break;
            }
            movingCar(board, minSum, queue, x, y, dir, sum);
        }
        return answer;
    }

    private static void movingCar(int[][] board, int[][][] minSum, PriorityQueue<Car> queue, int x, int y, int dir,
            int sum) {
        for (int d = 0; d < 4; d++) {
            int row = x + dx[d];
            int col = y + dy[d];
            int tempSum = sum + (d == dir ? 100 : 600);
            if (row < 0 || row >= n || col < 0 || col >= n || board[row][col] == 1) continue;
            if (minSum[row][col][d] != 0 && minSum[row][col][d] <= tempSum) continue;
            queue.add(new Car(row, col, d, tempSum));
            minSum[row][col][d] = tempSum;
        }
    }

    private static void extracted(int i, int j, int[][] board, int[][][] minSum, PriorityQueue<Car> queue) {
        if (board[i][j + 1] == 0) {
            queue.add(new Car(i, j + 1, 0, 100));
            minSum[i][j + 1][0] = 100;
        }
        if (board[i + 1][j] == 0) {
            queue.add(new Car(i + 1, j, 1, 100));
            minSum[i + 1][j][1] = 100;
        }
    }
    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{0,0,0,0,0,0,0,1},{0,0,0,0,0,0,0,0},{0,0,0,0,0,1,0,0},{0,0,0,0,1,0,0,0},{0,0,0,1,0,0,0,1},{0,0,1,0,0,0,1,0},{0,1,0,0,0,1,0,0},{1,0,0,0,0,0,0,0}}));
    }
}
