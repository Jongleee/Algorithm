package com.example.algorithm.java.searchBroadFirstSearch;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Ballerino {
    private static final int[][] directions = {
            { -2, 1 }, { -1, 2 }, { 1, 2 }, { 2, 1 },
            { -2, -1 }, { -1, -2 }, { 1, -2 }, { 2, -1 }
    };

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int row = Integer.parseInt(st.nextToken());
        int col = Integer.parseInt(st.nextToken());

        int[][] map = new int[row][col];
        long[][] distance = new long[row][col];
        long[][] pathCount = new long[row][col];
        List<Point>[][] nextMove = new ArrayList[row][col];

        int startY = -1, startX = -1;
        int endY = -1, endX = -1;

        for (int y = 0; y < row; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < col; x++) {
                map[y][x] = Integer.parseInt(st.nextToken());
                nextMove[y][x] = new ArrayList<>();
                if (map[y][x] == 3) {
                    startY = y;
                    startX = x;
                    map[y][x] = 0;
                } else if (map[y][x] == 4) {
                    endY = y;
                    endX = x;
                    map[y][x] = 0;
                }
            }
        }

        for (int y = 0; y < row; y++) {
            for (int x = 0; x < col; x++) {
                if (map[y][x] != 2) {
                    buildGraph(y, x, map, nextMove, row, col);
                }
            }
        }

        for (int i = 0; i < row; i++) {
            Arrays.fill(distance[i], -1);
        }

        bfs(startX, startY, nextMove, distance, pathCount, row, col);

        if (distance[endY][endX] == -1) {
            System.out.println("-1");
        } else {
            System.out.println((distance[endY][endX] - 1) + " " + pathCount[endY][endX]);
        }
    }

    private static void buildGraph(int sy, int sx, int[][] map, List<Point>[][] nextMove, int row, int col) {
        boolean[][] visited = new boolean[row][col];
        Queue<Point> queue = new ArrayDeque<>();
        queue.offer(new Point(sx, sy));
        visited[sy][sx] = true;

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            for (int d = 0; d < 8; d++) {
                int ny = current.y + directions[d][0];
                int nx = current.x + directions[d][1];

                if (ny < 0 || nx < 0 || ny >= row || nx >= col)
                    continue;
                if (map[ny][nx] == 2 || visited[ny][nx])
                    continue;

                visited[ny][nx] = true;

                if (map[ny][nx] != 1 && !(ny == sy && nx == sx)) {
                    nextMove[sy][sx].add(new Point(nx, ny));
                } else if (map[ny][nx] == 1) {
                    queue.offer(new Point(nx, ny));
                }
            }
        }
    }

    private static void bfs(int sx, int sy, List<Point>[][] nextMove, long[][] distance, long[][] pathCount, int row,
            int col) {
        Queue<Point> queue = new ArrayDeque<>();
        queue.offer(new Point(sx, sy));
        distance[sy][sx] = 0;
        pathCount[sy][sx] = 1;

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            for (Point next : nextMove[current.y][current.x]) {
                if (distance[next.y][next.x] == -1) {
                    distance[next.y][next.x] = distance[current.y][current.x] + 1;
                    pathCount[next.y][next.x] = pathCount[current.y][current.x];
                    queue.offer(new Point(next.x, next.y));
                } else if (distance[next.y][next.x] == distance[current.y][current.x] + 1) {
                    pathCount[next.y][next.x] += pathCount[current.y][current.x];
                }
            }
        }
    }
}

/*
4 5
1 0 0 0 0
3 0 0 0 0
0 0 2 0 0
0 0 0 4 0

2 3
*/