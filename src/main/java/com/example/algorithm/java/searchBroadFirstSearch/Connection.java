package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Connection {
    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static final String IMPOSSIBLE = "IMPOSSIBLE";
    static Point[][] prev;
    static int[][] visited;
    static int n;
    static int m;

    static final int[] DX = { -1, 1, 0, 0 };
    static final int[] DY = { 0, 0, -1, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        Point[] a = new Point[2];
        Point[] b = new Point[2];

        for (int i = 0; i < 2; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            a[i] = new Point(y, x);
        }

        for (int i = 0; i < 2; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            b[i] = new Point(y, x);
        }

        int answer = calculateMinimumDistance(a, b);
        answer = Math.min(answer, calculateMinimumDistance(b, a));

        if (answer == Integer.MAX_VALUE) {
            System.out.println(IMPOSSIBLE);
        } else {
            System.out.println(answer);
        }
    }

    private static int calculateMinimumDistance(Point[] p1, Point[] p2) {
        visited = new int[n + 1][m + 1];
        visited[p2[0].x][p2[0].y] = visited[p2[1].x][p2[1].y] = 1;
        prev = new Point[n + 1][m + 1];

        bfs(p1);
        int aDistance = visited[p1[1].x][p1[1].y];

        if (aDistance == 0)
            return Integer.MAX_VALUE;

        visited = new int[n + 1][m + 1];
        Point current = prev[p1[1].x][p1[1].y];

        while (current != null && (current.x != p1[0].x || current.y != p1[0].y)) {
            visited[current.x][current.y] = 1;
            current = prev[current.x][current.y];
        }

        visited[p1[0].x][p1[0].y] = visited[p1[1].x][p1[1].y] = 1;
        bfs(p2);
        int bDistance = visited[p2[1].x][p2[1].y];

        return bDistance == 0 ? Integer.MAX_VALUE : aDistance + bDistance;
    }

    private static void bfs(Point[] points) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(points[0]);

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            for (int i = 0; i < 4; i++) {
                int newX = current.x + DX[i];
                int newY = current.y + DY[i];

                if (!isInBounds(newX, newY) || visited[newX][newY] != 0) {
                    continue;
                }

                queue.add(new Point(newX, newY));
                visited[newX][newY] = visited[current.x][current.y] + 1;
                prev[newX][newY] = current;

                if (newX == points[1].x && newY == points[1].y) {
                    return;
                }
            }
        }
    }

    private static boolean isInBounds(int x, int y) {
        return x >= 0 && x <= n && y >= 0 && y <= m;
    }
}

/*
6 3
2 3
4 0
0 2
6 1

IMPOSSIBLE


6 6
2 1
5 4
4 0
4 5

15
*/