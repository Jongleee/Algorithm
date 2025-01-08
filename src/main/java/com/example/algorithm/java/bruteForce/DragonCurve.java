package com.example.algorithm.java.bruteForce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DragonCurve {
    private static final int[] dx = { 1, 0, -1, 0 };
    private static final int[] dy = { 0, -1, 0, 1 };
    private static boolean[][] map = new boolean[101][101];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        while (n-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            int generation = Integer.parseInt(st.nextToken());

            drawDragonCurve(x, y, dir, generation);
        }

        System.out.println(countSquares());
    }

    private static void drawDragonCurve(int x, int y, int direction, int generation) {
        List<Integer> directions = new ArrayList<>();
        directions.add(direction);
        map[y][x] = true;

        x += dx[direction];
        y += dy[direction];
        map[y][x] = true;

        for (int g = 0; g < generation; g++) {
            int size = directions.size();
            for (int i = size - 1; i >= 0; i--) {
                int nextDir = (directions.get(i) + 1) % 4;
                x += dx[nextDir];
                y += dy[nextDir];
                map[y][x] = true;
                directions.add(nextDir);
            }
        }
    }

    private static int countSquares() {
        int count = 0;
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                if (map[y][x] && map[y + 1][x] && map[y][x + 1] && map[y + 1][x + 1]) {
                    count++;
                }
            }
        }
        return count;
    }
}

/*
10
5 5 0 0
5 6 0 0
5 7 0 0
5 8 0 0
5 9 0 0
6 5 0 0
6 6 0 0
6 7 0 0
6 8 0 0
6 9 0 0

8


4
50 50 0 10
50 50 1 10
50 50 2 10
50 50 3 10

1992
*/