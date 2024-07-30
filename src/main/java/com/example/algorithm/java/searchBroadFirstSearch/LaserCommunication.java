package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;

public class LaserCommunication {
    static class Point {
        int x;
        int y;
        int direction;

        Point(int x, int y, int direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        int height = Integer.parseInt(input[0]);
        int width = Integer.parseInt(input[1]);
        char[][] map = new char[width][height];
        int startX1 = -1;
        int startY1 = -1;
        int startX2 = -1;
        int startY2 = -1;

        for (int i = 0; i < width; i++) {
            String line = br.readLine();
            for (int j = 0; j < height; j++) {
                map[i][j] = line.charAt(j);
                if (map[i][j] == 'C') {
                    if (startX1 == -1) {
                        startX1 = i;
                        startY1 = j;
                    } else {
                        startX2 = i;
                        startY2 = j;
                    }
                    map[i][j] = '.';
                }
            }
        }

        int[][] check = new int[width][height];
        Deque<Point> queue = new LinkedList<>();
        int[] dx = { -1, 1, 0, 0 };
        int[] dy = { 0, 0, -1, 1 };
        check[startX1][startY1] = 1;
        queue.add(new Point(startX1, startY1, -1));

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            for (int i = 0; i < 4; i++) {
                if (current.direction == i)
                    continue;

                int nx = current.x;
                int ny = current.y;
                int count = check[current.x][current.y] + 1;

                while (true) {
                    nx += dx[i];
                    ny += dy[i];

                    if (nx < 0 || nx >= width || ny < 0 || ny >= height || map[nx][ny] != '.')
                        break;

                    if (check[nx][ny] == 0) {
                        queue.add(new Point(nx, ny, i));
                        check[nx][ny] = count;
                    }

                    if (check[nx][ny] != 0 && check[nx][ny] < count)
                        break;
                }
            }
        }

        System.out.println(check[startX2][startY2] - 2);
    }
}

/*
7 8
.......
......C
......*
*****.*
....*..
....*..
.C..*..
.......

3
*/