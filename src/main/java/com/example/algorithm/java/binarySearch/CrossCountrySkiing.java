package com.example.algorithm.java.binarySearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class CrossCountrySkiing {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int rows = Integer.parseInt(st.nextToken());
        int cols = Integer.parseInt(st.nextToken());

        int[][] board = new int[rows][cols];
        List<int[]> waypoints = new ArrayList<>();
        int maxHeight = 0;

        for (int i = 0; i < rows; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < cols; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
                maxHeight = Math.max(maxHeight, board[i][j]);
            }
        }

        for (int i = 0; i < rows; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < cols; j++) {
                if (Integer.parseInt(st.nextToken()) == 1) {
                    waypoints.add(new int[] { i, j });
                }
            }
        }

        int result = findMinimumDifference(board, waypoints, rows, cols, maxHeight);
        bw.write(result + "\n");

        bw.flush();
        bw.close();
        br.close();
    }

    private static int findMinimumDifference(int[][] board, List<int[]> waypoints, int rows, int cols, int maxHeight) {
        int low = 0, high = maxHeight;
        while (low < high) {
            int mid = (low + high) / 2;
            if (canConnectAllWaypoints(board, waypoints, rows, cols, mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    private static boolean canConnectAllWaypoints(int[][] board, List<int[]> waypoints, int rows, int cols,
            int maxDiff) {
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new ArrayDeque<>();

        int[] start = waypoints.get(0);
        queue.offer(start);
        visited[start[0]][start[1]] = true;

        int[] dRow = { -1, 1, 0, 0 };
        int[] dCol = { 0, 0, -1, 1 };

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            for (int i = 0; i < 4; i++) {
                int newRow = current[0] + dRow[i];
                int newCol = current[1] + dCol[i];

                if (newRow >= 0 && newCol >= 0 && newRow < rows && newCol < cols
                        && !visited[newRow][newCol]
                        && Math.abs(board[current[0]][current[1]] - board[newRow][newCol]) <= maxDiff) {
                    visited[newRow][newCol] = true;
                    queue.offer(new int[] { newRow, newCol });
                }
            }
        }

        for (int[] waypoint : waypoints) {
            if (!visited[waypoint[0]][waypoint[1]]) {
                return false;
            }
        }
        return true;
    }
}

/*
3 5
20 21 18 99 5
19 22 20 16 26
18 17 40 60 80
1 0 0 0 1
0 0 0 0 0
0 0 0 0 1

21
*/