package com.example.algorithm.java.bruteForce;

import java.util.LinkedList;
import java.util.Queue;

public class DevideElectricGrid {
    private int[][] adjacencyMatrix;

    public int solution(int n, int[][] wires) {
        int answer = n;
        adjacencyMatrix = new int[n + 1][n + 1];

        initializeAdjacencyMatrix(wires);

        for (int i = 0; i < wires.length; i++) {
            int x = wires[i][0];
            int y = wires[i][1];

            removeWire(x, y);
            answer = Math.min(answer, calculateMinDifference(n, x));
            addWire(x, y);
        }

        return answer;
    }

    private void initializeAdjacencyMatrix(int[][] wires) {
        for (int[] wire : wires) {
            int x = wire[0];
            int y = wire[1];
            adjacencyMatrix[x][y] = 1;
            adjacencyMatrix[y][x] = 1;
        }
    }

    private void removeWire(int x, int y) {
        adjacencyMatrix[x][y] = 0;
        adjacencyMatrix[y][x] = 0;
    }

    private void addWire(int x, int y) {
        adjacencyMatrix[x][y] = 1;
        adjacencyMatrix[y][x] = 1;
    }

    private int calculateMinDifference(int n, int start) {
        int[] visited = new int[n + 1];
        int cnt = 1;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);

        while (!queue.isEmpty()) {
            int point = queue.poll();
            visited[point] = 1;

            for (int i = 1; i <= n; i++) {
                if (visited[i] == 1) {
                    continue;
                }
                if (adjacencyMatrix[point][i] == 1) {
                    queue.offer(i);
                    cnt++;
                }
            }
        }

        return Math.abs(n - 2 * cnt);
    }

    // @Test
    // public void 정답() {
    //     int[][] w1 = { {1,3},{2,3},{3,4},{4,5},{4,6},{4,7},{7,8},{7,9}};
    //     int[][] w2 = { {1,2},{2,3},{3,4}};
    //     Assertions.assertEquals(3, solution(9,w1));
    //     Assertions.assertEquals(0, solution(4,w2));
    // }
}
