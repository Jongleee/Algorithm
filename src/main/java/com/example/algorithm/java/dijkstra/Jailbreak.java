package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Jailbreak {
    private static class Pair {
        int x;
        int y;
        int door;

        Pair(int x, int y, int door) {
            this.x = x;
            this.y = y;
            this.door = door;
        }
    }

    private static final int[] DX = { 1, -1, 0, 0 };
    private static final int[] DY = { 0, 0, 1, -1 };
    private static int height;
    private static int width;
    private static final char[][] graph = new char[102][102];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int testCaseCount = Integer.parseInt(br.readLine());

        while (testCaseCount-- > 0) {
            int[][] prisoners = inputAndPreprocessing(br);
            int[][] result1 = bfs(prisoners[0][0], prisoners[0][1]);
            int[][] result2 = bfs(prisoners[1][0], prisoners[1][1]);
            int[][] result3 = bfs(0, 0);

            int answer = Integer.MAX_VALUE;
            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {
                    if (result1[i][j] == -1 || result2[i][j] == -1 || result3[i][j] == -1)
                        continue;
                    int sum = result1[i][j] + result2[i][j] + result3[i][j];
                    if (graph[i][j] == '#')
                        sum -= 2;
                    answer = Math.min(answer, sum);
                }
            }
            sb.append(answer).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }

    private static int[][] inputAndPreprocessing(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        height = Integer.parseInt(st.nextToken());
        width = Integer.parseInt(st.nextToken());

        int[][] prisoners = new int[2][2];
        int find = 0;
        for (int i = 1; i <= height; ++i) {
            String line = br.readLine();
            for (int j = 1; j <= width; ++j) {
                graph[i][j] = line.charAt(j - 1);
                if (graph[i][j] == '$') {
                    graph[i][j] = '.';
                    prisoners[find++] = new int[] { i, j };
                }
            }
        }

        height += 2;
        width += 2;
        for (int i = 0; i < height; ++i) {
            graph[i][0] = '.';
            graph[i][width - 1] = '.';
        }
        for (int j = 0; j < width; ++j) {
            graph[0][j] = '.';
            graph[height - 1][j] = '.';
        }

        return prisoners;
    }

    private static int[][] bfs(int x, int y) {
        Deque<Pair> queue = new ArrayDeque<>();
        queue.add(new Pair(x, y, 0));
        int[][] visited = new int[height][width];
        for (int i = 0; i < height; ++i)
            Arrays.fill(visited[i], -1);
        visited[x][y] = 0;

        while (!queue.isEmpty()) {
            Pair p = queue.poll();

            for (int i = 0; i < 4; ++i) {
                int nx = p.x + DX[i];
                int ny = p.y + DY[i];

                if (nx < 0 || ny < 0 || nx >= height || ny >= width || visited[nx][ny] != -1 || graph[nx][ny] == '*')
                    continue;

                if (graph[nx][ny] == '#') {
                    queue.addLast(new Pair(nx, ny, p.door + 1));
                    visited[nx][ny] = p.door + 1;
                } else {
                    queue.addFirst(new Pair(nx, ny, p.door));
                    visited[nx][ny] = p.door;
                }
            }
        }
        return visited;
    }
}

/*
3
5 9
****#****
*..#.#..*
****.****
*$#.#.#$*
*********
5 11
*#*********
*$*...*...*
*$*.*.*.*.*
*...*...*.*
*********.*
9 9
*#**#**#*
*#**#**#*
*#**#**#*
*#**.**#*
*#*#.#*#*
*$##*##$*
*#*****#*
*.#.#.#.*
*********

4
0
9
*/