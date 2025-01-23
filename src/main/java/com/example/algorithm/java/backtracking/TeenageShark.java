package com.example.algorithm.java.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class TeenageShark {
    private static final int[] DX = { -1, -1, 0, 1, 1, 1, 0, -1 };
    private static final int[] DY = { 0, -1, -1, -1, 0, 1, 1, 1 };
    private static final int SIZE = 4;
    private static int maxScore = 0;

    static class Fish {
        int x, y, id, direction;
        boolean isAlive;

        Fish(int x, int y, int id, int direction) {
            this.x = x;
            this.y = y;
            this.id = id;
            this.direction = direction;
            this.isAlive = true;
        }

        Fish(int x, int y, int id, int direction, boolean isAlive) {
            this.x = x;
            this.y = y;
            this.id = id;
            this.direction = direction;
            this.isAlive = isAlive;
        }
    }

    static class Shark {
        int x, y, direction, score;

        Shark(int x, int y, int direction, int score) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.score = score;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[][] map = new int[SIZE][SIZE];
        Fish[] fishes = new Fish[17];

        for (int i = 0; i < SIZE; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < SIZE; j++) {
                int id = Integer.parseInt(st.nextToken());
                int direction = Integer.parseInt(st.nextToken()) - 1;
                fishes[id] = new Fish(i, j, id, direction);
                map[i][j] = id;
            }
        }

        Fish firstFish = fishes[map[0][0]];
        Shark shark = new Shark(0, 0, firstFish.direction, firstFish.id);
        firstFish.isAlive = false;
        map[0][0] = -1;

        explore(map, shark, fishes);
        System.out.println(maxScore);
    }

    private static void explore(int[][] map, Shark shark, Fish[] fishes) {
        maxScore = Math.max(maxScore, shark.score);

        moveAllFishes(map, fishes);
        for (int step = 1; step < SIZE; step++) {
            int nx = shark.x + DX[shark.direction] * step;
            int ny = shark.y + DY[shark.direction] * step;

            if (isInBounds(nx, ny) && map[nx][ny] > 0) {
                int[][] newMap = copyMap(map);
                Fish[] newFishes = copyFishes(fishes);

                Fish eatenFish = newFishes[map[nx][ny]];
                Shark newShark = new Shark(eatenFish.x, eatenFish.y, eatenFish.direction, shark.score + eatenFish.id);

                newMap[shark.x][shark.y] = 0;
                newMap[eatenFish.x][eatenFish.y] = -1;
                eatenFish.isAlive = false;

                explore(newMap, newShark, newFishes);
            }
        }
    }

    private static void moveAllFishes(int[][] map, Fish[] fishes) {
        for (int id = 1; id <= 16; id++) {
            Fish fish = fishes[id];
            if (!fish.isAlive)
                continue;

            for (int rotation = 0; rotation < 8; rotation++) {
                int direction = (fish.direction + rotation) % 8;
                int nx = fish.x + DX[direction];
                int ny = fish.y + DY[direction];

                if (isInBounds(nx, ny) && map[nx][ny] != -1) {
                    fish.direction = direction;
                    swapFish(map, fishes, fish, nx, ny);
                    break;
                }
            }
        }
    }

    private static void swapFish(int[][] map, Fish[] fishes, Fish fish, int nx, int ny) {
        int targetId = map[nx][ny];
        if (targetId != 0) {
            Fish targetFish = fishes[targetId];
            targetFish.x = fish.x;
            targetFish.y = fish.y;
            map[fish.x][fish.y] = targetId;
        } else {
            map[fish.x][fish.y] = 0;
        }

        fish.x = nx;
        fish.y = ny;
        map[nx][ny] = fish.id;
    }

    private static int[][] copyMap(int[][] map) {
        int[][] newMap = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(map[i], 0, newMap[i], 0, SIZE);
        }
        return newMap;
    }

    private static Fish[] copyFishes(Fish[] fishes) {
        Fish[] newFishes = new Fish[17];
        for (int i = 1; i <= 16; i++) {
            Fish fish = fishes[i];
            newFishes[i] = new Fish(fish.x, fish.y, fish.id, fish.direction, fish.isAlive);
        }
        return newFishes;
    }

    private static boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < SIZE && y < SIZE;
    }
}

/*
12 6 14 5 4 5 6 7
15 1 11 7 3 7 7 5
10 3 8 3 16 6 1 1
5 8 2 7 13 6 9 2

76


2 6 10 8 6 7 9 4
1 7 16 6 4 2 5 8
3 7 8 6 7 6 14 8
12 7 15 4 11 3 13 3

39
*/