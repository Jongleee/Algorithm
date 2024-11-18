package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class IslandTravels {
    static int rows, cols, islandCount = 1, fullVisited = 0;
    static char[][] grid;
    static int[][] dp = new int[1 << 17][16];
    static int[][] islands, routes;
    static int[] dx = { 0, 0, -1, 1 };
    static int[] dy = { -1, 1, 0, 0 };

    static class Pos implements Comparable<Pos> {
        int x, y, dist;

        public Pos(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }

        @Override
        public int compareTo(Pos o) {
            return this.dist - o.dist;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        rows = Integer.parseInt(st.nextToken());
        cols = Integer.parseInt(st.nextToken());
        grid = new char[rows][cols];
        islands = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            String line = br.readLine();
            for (int j = 0; j < cols; j++)
                grid[i][j] = line.charAt(j);
        }

        markIslands();
        calculateRoutes();
        setFullVisited();
        bw.write(tsp(0, 0) + "");
        bw.flush();
        bw.close();
        br.close();
    }

    static int tsp(int visited, int current) {
        if (dp[visited][current] != 0)
            return dp[visited][current];
        if (visited == fullVisited) {
            dp[visited][current] = 0;
            return 0;
        }
        int minDist = Integer.MAX_VALUE;
        for (int i = 1; i <= islandCount; i++) {
            if (current == i || (visited & (1 << (i - 1))) != 0)
                continue;
            minDist = Math.min(minDist, tsp(visited | (1 << (i - 1)), i) + routes[current][i]);
        }
        dp[visited][current] = minDist;

        return minDist;
    }

    static void setFullVisited() {
        for (int i = 0; i < islandCount; i++)
            fullVisited |= (1 << i);
    }

    static void calculateRoutes() {
        routes = new int[islandCount + 1][islandCount + 1];
        int idx = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (islands[i][j] == idx) {
                    bfsRoute(j, i, idx);
                    idx++;
                }
            }
        }
        islandCount = idx - 1;
    }

    static void markIslands() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (islands[i][j] == 0 && grid[i][j] == 'X') {
                    bfsIsland(j, i);
                    islandCount++;
                }
            }
        }
        islandCount--;
    }

    static void bfsRoute(int x, int y, int idx) {
        PriorityQueue<Pos> pq = new PriorityQueue<>();
        boolean[][] visited = new boolean[rows][cols];
        visited[y][x] = true;
        pq.add(new Pos(x, y, 0));

        while (!pq.isEmpty()) {
            Pos curr = pq.poll();
            for (int i = 0; i < 4; i++) {
                int nx = curr.x + dx[i];
                int ny = curr.y + dy[i];
                if (isValid(nx, ny) && !visited[ny][nx]) {
                    visited[ny][nx] = true;
                    if (grid[ny][nx] == 'X') {
                        if (islands[ny][nx] != idx && routes[idx][islands[ny][nx]] == 0)
                            routes[idx][islands[ny][nx]] = curr.dist;
                        pq.add(new Pos(nx, ny, curr.dist));
                    } else if (grid[ny][nx] == 'S') {
                        pq.add(new Pos(nx, ny, curr.dist + 1));
                    }
                }
            }
        }
    }

    static void bfsIsland(int x, int y) {
        Queue<Pos> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        visited[y][x] = true;
        islands[y][x] = islandCount;
        queue.add(new Pos(x, y, islandCount));

        while (!queue.isEmpty()) {
            Pos curr = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = curr.x + dx[i];
                int ny = curr.y + dy[i];
                if (isValid(nx, ny) && !visited[ny][nx]) {
                    if (islands[ny][nx] == 0 && grid[ny][nx] == 'X') {
                        visited[ny][nx] = true;
                        islands[ny][nx] = curr.dist;
                        queue.add(new Pos(nx, ny, curr.dist));
                    }
                }
            }
        }
    }

    static boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < cols && y < rows;
    }
}

/*
5 4
XX.S
.S..
SXSS
S.SX
..SX

3
*/