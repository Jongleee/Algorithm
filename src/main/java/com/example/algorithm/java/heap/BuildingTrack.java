package com.example.algorithm.java.heap;

import java.util.PriorityQueue;

public class BuildingTrack {
    private class Car implements Comparable<Car> {
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

    private int n;
    private int[] dx = { 0, 1, 0, -1 };
    private int[] dy = { 1, 0, -1, 0 };

    public int solution(int[][] board) {
        n = board.length;
        return findMinimumSum(board);
    }

    private int findMinimumSum(int[][] board) {
        int answer = 0;
        int[][][] minSum = new int[n][n][4];

        PriorityQueue<Car> queue = new PriorityQueue<>();
        addInitialCars(board, minSum, queue);

        while (!queue.isEmpty()) {
            Car currentCar = queue.poll();
            int x = currentCar.x;
            int y = currentCar.y;
            int dir = currentCar.dir;
            int sum = currentCar.sum;

            if (x == n - 1 && y == n - 1) {
                answer = sum;
                break;
            }
            moveCar(board, minSum, queue, x, y, dir, sum);
        }

        return answer;
    }

    private void moveCar(int[][] board, int[][][] minSum, PriorityQueue<Car> queue, int x, int y, int dir, int sum) {
        for (int d = 0; d < 4; d++) {
            int row = x + dx[d];
            int col = y + dy[d];
            int tempSum = sum + (d == dir ? 100 : 600);
            if (!isValidMove(row, col, board))
                continue;
            if (!isNewPathShorter(minSum, row, col, d, tempSum))
                continue;
            queue.add(new Car(row, col, d, tempSum));
            minSum[row][col][d] = tempSum;
        }
    }

    private boolean isValidMove(int row, int col, int[][] board) {
        return row >= 0 && row < n && col >= 0 && col < n && board[row][col] == 0;
    }

    private boolean isNewPathShorter(int[][][] minSum, int row, int col, int direction, int tempSum) {
        return minSum[row][col][direction] == 0 || minSum[row][col][direction] > tempSum;
    }

    private void addInitialCars(int[][] board, int[][][] minSum, PriorityQueue<Car> queue) {
        if (board[0][1] == 0) {
            queue.add(new Car(0, 1, 0, 100));
            minSum[0][1][0] = 100;
        }
        if (board[1][0] == 0) {
            queue.add(new Car(1, 0, 1, 100));
            minSum[1][0][1] = 100;
        }
    }

    // @Test
    // void 정답() {
    //     int[][] b1 = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
    //     int[][] b2 = { { 0, 0, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 1, 0, 0 },
    //             { 0, 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0, 1 }, { 0, 0, 1, 0, 0, 0, 1, 0 },
    //             { 0, 1, 0, 0, 0, 1, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0 } };
    //     Assertions.assertEquals(900, solution(b1));
    //     Assertions.assertEquals(3800, solution(b2));
    // }
}
