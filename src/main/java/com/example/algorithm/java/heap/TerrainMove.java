package com.example.algorithm.java.heap;

import java.util.PriorityQueue;

public class TerrainMove {
    public static int solution(int[][] land, int height) {
        int n = land.length;

        boolean[][] visited = new boolean[n][n];
        int[][] move = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        int visitCount = 0;
        int maxCount = n * n;
        int value = 0;

        queue.offer(new int[] { 0, 0, 0 });

        while (visitCount < maxCount) {
            int[] curr = queue.poll();
            int val = curr[0];
            int x = curr[1];
            int y = curr[2];

            if (visited[x][y]) {
                continue;
            }
            visited[x][y] = true;

            visitCount++;
            value += val;

            int currentHeight = land[x][y];

            for (int[] d : move) {
                int nx = x + d[0];
                int ny = y + d[1];

                if (nx < 0 || ny < 0 || nx >= n || ny >= n) {
                    continue;
                }

                int nextHeight = land[nx][ny];

                if (Math.abs(nextHeight - currentHeight) > height) {
                    queue.add(new int[] { Math.abs(nextHeight - currentHeight), nx, ny });
                } else {
                    queue.add(new int[] { 0, nx, ny });
                }
            }
        }
        return value;
    }

    public static void main(String[] args) {
        int[][] n1 = { { 1, 4, 8, 10 }, { 5, 5, 5, 5 }, { 10, 10, 10, 10 }, { 10, 10, 10, 20 } };
        int h1 = 3;
        int[][] n2 = { { 10, 11, 10, 11 }, { 2, 21, 20, 10 }, { 1, 20, 21, 11 }, { 2, 1, 2, 1 } };
        int h2 = 1;
        System.out.println(solution(n1, h1));
        System.out.println(solution(n2, h2));
    }
}
