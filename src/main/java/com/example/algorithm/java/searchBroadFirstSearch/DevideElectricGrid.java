package com.example.algorithm.java.searchBroadFirstSearch;

import java.util.LinkedList;
import java.util.Queue;

public class DevideElectricGrid {
    
    private int[][] arr;

    public int solution(int n, int[][] wires) {
        int answer = n;
        arr = new int[n + 1][n + 1];

        for (int i = 0; i < wires.length; i++) {
            int x = wires[i][0];
            int y = wires[i][1];
            arr[x][y] = 1;
            arr[y][x] = 1;
        }

        for (int i = 0; i < wires.length; i++) {
            int x = wires[i][0];
            int y = wires[i][1];

            arr[x][y] = 0;
            arr[y][x] = 0;

            answer = Math.min(answer, calculateMinDifference(n, x));

            arr[x][y] = 1;
            arr[y][x] = 1;
        }

        return answer;
    }

    private int calculateMinDifference(int n, int start) {
        int[] visit = new int[n + 1];
        int cnt = 1;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);

        while (!queue.isEmpty()) {
            int point = queue.poll();
            visit[point] = 1;

            for (int i = 1; i <= n; i++) {
                if (visit[i] == 1) {
                    continue;
                }
                if (arr[point][i] == 1) {
                    queue.offer(i);
                    cnt++;
                }
            }
        }

        return Math.abs(n - 2 * cnt);
    }
}
