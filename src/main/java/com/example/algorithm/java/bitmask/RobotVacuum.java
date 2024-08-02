package com.example.algorithm.java.bitmask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class RobotVacuum {
    static int rows;
    static int cols;
    static int dustCount;
    static int[][] dist;
    static char[][] map;
    static boolean[][] visited;
    static List<int[]> dustList;
    static Queue<int[]> queue;
    static int[][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder result = new StringBuilder();
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());
            cols = Integer.parseInt(st.nextToken());
            rows = Integer.parseInt(st.nextToken());
            if (cols == 0 && rows == 0) break;

            initialize(br);

            if (!calculateDistances()) {
                result.append(-1).append("\n");
                continue;
            }

            initializeDP();
            result.append(tsp(0, 1 << 0)).append("\n");
        }
        System.out.println(result.toString());
    }

    private static void initialize(BufferedReader br) throws Exception {
        map = new char[rows][cols];
        visited = new boolean[rows][cols];
        dustList = new ArrayList<>();
        queue = new LinkedList<>();
        dustCount = 0;
        int dustIdx = 1;

        for (int i = 0; i < rows; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == 'o') {
                    map[i][j] = '0';
                    dustList.add(0, new int[]{i, j});
                    dustCount++;
                } else if (map[i][j] == '*') {
                    map[i][j] = (char) (dustIdx++ + '0');
                    dustList.add(new int[]{i, j});
                    dustCount++;
                }
            }
        }
        dist = new int[dustCount][dustCount];
    }

    private static boolean calculateDistances() {
        for (int i = 0; i < dustCount; i++) {
            if (!bfs(i)) {
                return false;
            }
        }
        return true;
    }

    private static boolean bfs(int pos) {
        queue.clear();
        for (int i = 0; i < rows; i++) Arrays.fill(visited[i], false);
        int[] start = dustList.get(pos);
        queue.offer(start);
        visited[start[0]][start[1]] = true;
        int steps = 0;
        int foundDust = 0;
        int ny;
        int nx;
        while (!queue.isEmpty()) {
            int qSize = queue.size();
            steps++;
            for (int i = 0; i < qSize; i++) {
                int[] current = queue.poll();
                for (int d = 0; d < 4; d++) {
                    ny = current[0] + dy[d];
                    nx = current[1] + dx[d];
                    if (outOfBounds(ny, nx) || visited[ny][nx] || map[ny][nx] == 'x') continue;
                    if (map[ny][nx] != '.') {
                        dist[pos][map[ny][nx] - '0'] = steps;
                        if (++foundDust == dustCount - 1) return true;
                    }
                    queue.offer(new int[]{ny, nx});
                    visited[ny][nx] = true;
                }
            }
        }
        return false;
    }

    private static boolean outOfBounds(int y, int x) {
        return y < 0 || x < 0 || y >= rows || x >= cols;
    }

    private static void initializeDP() {
        dp = new int[dustCount][1 << dustCount];
        for (int i = 0; i < dustCount; i++)
            Arrays.fill(dp[i], -1);
    }

    private static int tsp(int cur, int visited) {
        if (dp[cur][visited] != -1) return dp[cur][visited];
        if (visited == (1 << dustCount) - 1) return 0;
        dp[cur][visited] = Integer.MAX_VALUE;
        for (int i = 0; i < dustCount; i++) {
            if ((visited & (1 << i)) > 0) continue;
            dp[cur][visited] = Math.min(dp[cur][visited], dist[cur][i] + tsp(i, visited | (1 << i)));
        }
        return dp[cur][visited];
    }

    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, 1, 0, -1};
}


/*
7 5
.......
.o...*.
.......
.*...*.
.......
15 13
.......x.......
...o...x....*..
.......x.......
.......x.......
.......x.......
...............
xxxxx.....xxxxx
...............
.......x.......
.......x.......
.......x.......
..*....x....*..
.......x.......
10 10
..........
..o.......
..........
..........
..........
.....xxxxx
.....x....
.....x.*..
.....x....
.....x....
0 0

8
49
-1
*/