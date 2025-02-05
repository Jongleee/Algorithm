package com.example.algorithm.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[][] heat;
    static int[] dx = { 0, -1, 0, 1 };
    static int[] dy = { -1, 0, 1, 0 };
    static boolean[][][] wall;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        List<int[]> list = new ArrayList<>();
        List<int[]> heater = new ArrayList<>();
        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            init(st, c, list, heater, i);
        }

        int w = Integer.parseInt(br.readLine());

        wall = getWall(br, r, c, w);

        heat = new int[r][c];
        heatByHeater(r, c, heater);

        int[][] map = new int[r][c];
        for (int t = 1; t <= 100; t++) {
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    map[i][j] += heat[i][j];
                }
            }

            getAdjust(r, c, map);

            outerHeatLoss(r, c, map);

            boolean terminated = getTerminated(k, list, map);
            if (terminated) {
                System.out.println(t);
                return;
            }
        }

        System.out.println(101);
    }

    private static void getAdjust(int r, int c, int[][] map) {
        int[][] adjust = new int[r][c];
        for (int i = 0; i < r - 1; i++) {
            for (int j = 0; j < c; j++) {
                if (!wall[i][j][3]) {
                    int temp = (map[i][j] - map[i + 1][j]) / 4;
                    adjust[i][j] -= temp;
                    adjust[i + 1][j] += temp;
                }
            }
        }
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c - 1; j++) {
                if (!wall[i][j][2]) {
                    int temp = (map[i][j] - map[i][j + 1]) / 4;
                    adjust[i][j] -= temp;
                    adjust[i][j + 1] += temp;
                }
            }
        }
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                map[i][j] += adjust[i][j];
            }
        }
    }

    private static boolean[][][] getWall(BufferedReader br, int r, int c, int w) throws IOException {
        StringTokenizer st;
        boolean[][][] wall = new boolean[r][c][4];
        for (int i = 0; i < w; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int t = Integer.parseInt(st.nextToken());
            if (t == 0) {
                wall[x][y][1] = true;
                wall[x - 1][y][3] = true;
            } else {
                wall[x][y][2] = true;
                wall[x][y + 1][0] = true;
            }
        }
        return wall;
    }

    private static void init(StringTokenizer st, int c, List<int[]> list, List<int[]> heater, int i) {
        for (int j = 0; j < c; j++) {
            int temp = Integer.parseInt(st.nextToken());
            if (temp == 0)
                continue;
            if (temp == 5) {
                list.add(new int[] { i, j });
            } else {
                if (temp == 1) {
                    heater.add(new int[] { i, j, 2 });
                } else if (temp == 2) {
                    heater.add(new int[] { i, j, 0 });
                } else if (temp == 3) {
                    heater.add(new int[] { i, j, 1 });
                } else {
                    heater.add(new int[] { i, j, 3 });
                }
            }
        }
    }

    private static void heatByHeater(int r, int c, List<int[]> heater) {
        for (int i = 0; i < heater.size(); i++) {
            int[] h = heater.get(i);
            int dir = h[2];
            Queue<int[]> queue = new ArrayDeque<>();
            boolean[][] vis = new boolean[r][c];
            heat[h[0] + dx[dir]][h[1] + dy[dir]] += 5;
            queue.add(new int[] { h[0] + dx[dir], h[1] + dy[dir], 5 });

            while (!queue.isEmpty()) {
                int[] cur = queue.poll();
                int curX = cur[0];
                int curY = cur[1];
                if (cur[2] == 1)
                    break;

                leftSide(r, c, dir, queue, vis, cur, curX, curY);

                if (!wall[curX][curY][dir]) {
                    int nx = curX + dx[dir];
                    int ny = curY + dy[dir];
                    if (!(nx < 0 || nx >= r || ny < 0 || ny >= c) && !vis[nx][ny]) {
                        heat[nx][ny] += cur[2] - 1;
                        queue.add(new int[] { nx, ny, cur[2] - 1 });
                        vis[nx][ny] = true;
                    }
                }

                rightSide(r, c, dir, queue, vis, cur, curX, curY);
            }
        }
    }

    private static void leftSide(int r, int c, int dir, Queue<int[]> q, boolean[][] vis, int[] cur,
            int curX, int curY) {
        if (!wall[curX][curY][(dir + 3) % 4]
                && 0 <= curX + dx[(dir + 3) % 4] && curX + dx[(dir + 3) % 4] < r
                && 0 <= curY + dy[(dir + 3) % 4] && curY + dy[(dir + 3) % 4] < c
                && !wall[curX + dx[(dir + 3) % 4]][curY + dy[(dir + 3) % 4]][dir]) {
            int nx = curX + dx[(dir + 3) % 4] + dx[dir];
            int ny = curY + dy[(dir + 3) % 4] + dy[dir];
            if (!(nx < 0 || nx >= r || ny < 0 || ny >= c) && !vis[nx][ny]) {
                heat[nx][ny] += cur[2] - 1;
                q.add(new int[] { nx, ny, cur[2] - 1 });
                vis[nx][ny] = true;
            }
        }
    }

    private static void rightSide(int r, int c, int dir, Queue<int[]> q, boolean[][] vis, int[] cur,
            int curX, int curY) {
        if (!wall[curX][curY][(dir + 1) % 4]
                && 0 <= curX + dx[(dir + 1) % 4] && curX + dx[(dir + 1) % 4] < r
                && 0 <= curY + dy[(dir + 1) % 4] && curY + dy[(dir + 1) % 4] < c
                && !wall[curX + dx[(dir + 1) % 4]][curY + dy[(dir + 1) % 4]][dir]) {
            int nx = curX + dx[(dir + 1) % 4] + dx[dir];
            int ny = curY + dy[(dir + 1) % 4] + dy[dir];
            if (!(nx < 0 || nx >= r || ny < 0 || ny >= c) && !vis[nx][ny]) {
                heat[nx][ny] += cur[2] - 1;
                q.add(new int[] { nx, ny, cur[2] - 1 });
                vis[nx][ny] = true;
            }
        }
    }

    private static void outerHeatLoss(int r, int c, int[][] map) {
        for (int i = 0; i < r; i++) {
            if (map[i][0] > 0) {
                map[i][0]--;
            }
            if (map[i][c - 1] > 0) {
                map[i][c - 1]--;
            }
        }
        for (int i = 1; i < c - 1; i++) {
            if (map[0][i] > 0) {
                map[0][i]--;
            }
            if (map[r - 1][i] > 0) {
                map[r - 1][i]--;
            }
        }
    }

    private static boolean getTerminated(int k, List<int[]> list, int[][] map) {
        boolean terminated = true;
        for (int i = 0; i < list.size(); i++) {
            if (map[list.get(i)[0]][list.get(i)[1]] < k) {
                terminated = false;
            }
        }
        return terminated;
    }
}

/*
7 8 70
0 0 0 0 0 0 0 0
0 0 0 0 4 0 0 0
0 0 0 0 0 0 0 0
0 0 5 5 0 0 0 0
0 0 0 0 0 5 0 0
0 0 0 0 0 0 0 0
0 0 0 0 3 0 0 0
3
4 4 1
5 4 0
5 6 0

53


7 8 100
0 0 0 0 0 0 0 0
5 0 0 0 4 0 0 0
0 0 0 0 0 0 0 0
0 0 5 5 0 0 0 0
0 0 0 0 0 5 0 0
5 0 0 0 0 0 5 0
0 0 0 0 3 0 0 0
0

93
*/