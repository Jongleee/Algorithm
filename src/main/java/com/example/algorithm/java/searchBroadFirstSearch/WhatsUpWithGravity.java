package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class WhatsUpWithGravity {
    private static class Point {
        int y, x, dir, cost;

        Point(int y, int x, int dir, int cost) {
            this.y = y;
            this.x = x;
            this.dir = dir;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int rows = Integer.parseInt(st.nextToken());
        int cols = Integer.parseInt(st.nextToken());

        char[][] map = new char[rows][cols];
        boolean[][][] visited = new boolean[4][rows][cols];
        int startY = 0, startX = 0;

        for (int i = 0; i < rows; i++) {
            String input = br.readLine();
            for (int j = 0; j < cols; j++) {
                map[i][j] = input.charAt(j);
                if (map[i][j] == 'C') {
                    map[i][j] = '.';
                    startY = i;
                    startX = j;
                }
            }
        }

        ArrayDeque<Point> queue = new ArrayDeque<>();
        queue.offer(new Point(startY, startX, 2, 0));
        visited[2][startY][startX] = true;

        int result = -1;
        int[] dirY = { -1, 0, 1, 0 };
        int[] dirX = { 0, 1, 0, -1 };

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (map[current.y][current.x] == 'D') {
                result = current.cost;
                break;
            }

            int nextY = current.y + dirY[current.dir];
            int nextX = current.x + dirX[current.dir];

            if (!isValid(rows, cols, nextY, nextX))
                continue;
            if (visited[current.dir][nextY][nextX])
                continue;

            if (map[nextY][nextX] == '#') {
                moveHorizontally(rows, cols, current, map, visited, queue);
                reverseGravity(current, visited, queue);
                continue;
            }

            visited[current.dir][nextY][nextX] = true;
            queue.offerFirst(new Point(nextY, nextX, current.dir, current.cost));
        }

        System.out.println(result);
    }

    private static void moveHorizontally(int rows, int cols, Point current, char[][] map, boolean[][][] visited,
            ArrayDeque<Point> queue) {
        if (isValid(rows, cols, current.y, current.x - 1) && map[current.y][current.x - 1] != '#'
                && !visited[current.dir][current.y][current.x - 1]) {
            visited[current.dir][current.y][current.x - 1] = true;
            queue.offerFirst(new Point(current.y, current.x - 1, current.dir, current.cost));
        }

        if (isValid(rows, cols, current.y, current.x + 1) && map[current.y][current.x + 1] != '#'
                && !visited[current.dir][current.y][current.x + 1]) {
            visited[current.dir][current.y][current.x + 1] = true;
            queue.offerFirst(new Point(current.y, current.x + 1, current.dir, current.cost));
        }
    }

    private static void reverseGravity(Point current, boolean[][][] visited, ArrayDeque<Point> queue) {
        int reverseDir = (current.dir + 2) % 4;
        if (!visited[reverseDir][current.y][current.x]) {
            visited[reverseDir][current.y][current.x] = true;
            queue.offerLast(new Point(current.y, current.x, reverseDir, current.cost + 1));
        }
    }

    private static boolean isValid(int rows, int cols, int y, int x) {
        return y >= 0 && x >= 0 && y < rows && x < cols;
    }
}

/*
5 5
#####
#...#
#...D
#C...
##.##

3
*/