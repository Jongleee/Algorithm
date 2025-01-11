package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BabyShark {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[][] map = new int[n][n];
        int fishCount = 0, sharkY = 0, sharkX = 0;

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] > 0 && map[i][j] < 9)
                    fishCount++;
                if (map[i][j] == 9) {
                    sharkY = i;
                    sharkX = j;
                    map[i][j] = 0;
                }
            }
        }

        System.out.println(calculateTime(n, map, fishCount, sharkY, sharkX));
    }

    private static int calculateTime(int n, int[][] map, int fishCount, int sharkY, int sharkX) {
        int sharkSize = 2, totalTime = 0, eatenFish = 0;
        int[] dy = { -1, 0, 1, 0 }, dx = { 0, 1, 0, -1 };

        while (fishCount > 0) {
            int[] target = findClosestFish(n, map, sharkY, sharkX, sharkSize, dy, dx);
            if (target[0] == -1)
                break;

            sharkY = target[0];
            sharkX = target[1];
            totalTime += target[2];
            fishCount--;
            eatenFish++;

            if (eatenFish == sharkSize) {
                sharkSize++;
                eatenFish = 0;
            }
        }

        return totalTime;
    }

    private static int[] findClosestFish(int n, int[][] map, int startY, int startX, int sharkSize, int[] dy,
            int[] dx) {
        boolean[][] visited = new boolean[n][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] { startY, startX });
        visited[startY][startX] = true;

        int distance = 0;
        int targetY = -1, targetX = -1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean foundFish = false;

            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                int y = current[0], x = current[1];

                for (int d = 0; d < 4; d++) {
                    int ny = y + dy[d], nx = x + dx[d];
                    if (ny < 0 || ny >= n || nx < 0 || nx >= n || visited[ny][nx] || map[ny][nx] > sharkSize)
                        continue;

                    visited[ny][nx] = true;

                    if (map[ny][nx] > 0 && map[ny][nx] < sharkSize) {
                        foundFish = true;
                        if (targetY == -1 || ny < targetY || (ny == targetY && nx < targetX)) {
                            targetY = ny;
                            targetX = nx;
                        }
                    }

                    queue.add(new int[] { ny, nx });
                }
            }

            distance++;
            if (foundFish) {
                map[targetY][targetX] = 0;
                return new int[] { targetY, targetX, distance };
            }
        }

        return new int[] { -1, -1, -1 };
    }
}

/*
6
1 1 1 1 1 1
2 2 6 2 2 3
2 2 5 2 2 3
2 2 2 4 6 3
0 0 0 0 0 6
0 0 0 0 0 9

39


6
6 0 6 0 6 1
0 0 0 0 0 2
2 3 4 5 6 6
0 0 0 0 0 2
0 2 0 0 0 0
3 9 3 0 0 1

48
*/