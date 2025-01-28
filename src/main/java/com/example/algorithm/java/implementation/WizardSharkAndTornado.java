package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class WizardSharkAndTornado {
    static class TornadoSimulation {
        private final int[][] grid;
        private final int size;
        private int sandOutside;

        private static final int[] dx = { -1, 1, 0, 0 };
        private static final int[] dy = { 0, 0, -1, 1 };
        private static final int[] nextDirection = { 2, 3, 1, 0 };
        private static final int[][] spreadX = {
                { 1, 1, 0, 0, -2, 0, 0, -1, -1, -1 },
                { -1, -1, 0, 0, 2, 0, 0, 1, 1, 1 },
                { -1, 1, -2, 2, 0, -1, 1, -1, 1, 0 },
                { -1, 1, -2, 2, 0, -1, 1, -1, 1, 0 }
        };
        private static final int[][] spreadY = {
                { -1, 1, -2, 2, 0, -1, 1, -1, 1, 0 },
                { -1, 1, -2, 2, 0, -1, 1, -1, 1, 0 },
                { 1, 1, 0, 0, -2, 0, 0, -1, -1, -1 },
                { -1, -1, 0, 0, 2, 0, 0, 1, 1, 1 }
        };
        private static final int[] spreadRate = { 1, 1, 2, 2, 5, 7, 7, 10, 10 };

        public TornadoSimulation(int[][] grid) {
            this.grid = grid;
            this.size = grid.length;
            this.sandOutside = 0;
        }

        public int run() {
            int centerX = size / 2;
            int centerY = size / 2;
            int currentDirection = 2;
            int step = 1;
            int turns = 0;

            while (centerX != 0 || centerY != 0) {
                for (int i = 0; i < step; i++) {
                    int nextX = centerX + dx[currentDirection];
                    int nextY = centerY + dy[currentDirection];

                    moveSand(centerX, centerY, nextX, nextY, currentDirection);

                    centerX = nextX;
                    centerY = nextY;

                    if (centerX == 0 && centerY == 0) {
                        break;
                    }
                }

                turns++;
                currentDirection = nextDirection[currentDirection];

                if (turns == 2) {
                    step++;
                    turns = 0;
                }
            }

            return sandOutside;
        }

        private void moveSand(int fromX, int fromY, int toX, int toY, int direction) {
            int sand = grid[toX][toY] += grid[fromX][fromY];
            grid[fromX][fromY] = 0;
            int remainingSand = sand;

            for (int i = 0; i < 9; i++) {
                int spreadXPos = toX + spreadX[direction][i];
                int spreadYPos = toY + spreadY[direction][i];
                int spreadAmount = sand * spreadRate[i] / 100;

                distributeSand(spreadXPos, spreadYPos, spreadAmount);
                remainingSand -= spreadAmount;
            }

            int alphaX = toX + spreadX[direction][9];
            int alphaY = toY + spreadY[direction][9];
            distributeSand(alphaX, alphaY, remainingSand);
            grid[toX][toY] = 0;
        }

        private void distributeSand(int x, int y, int amount) {
            if (x < 0 || x >= size || y < 0 || y >= size) {
                sandOutside += amount;
            } else {
                grid[x][y] += amount;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int gridSize = Integer.parseInt(br.readLine());
        int[][] grid = new int[gridSize][gridSize];

        for (int i = 0; i < gridSize; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        TornadoSimulation simulation = new TornadoSimulation(grid);
        System.out.println(simulation.run());
    }
}

/*
5
0 0 0 0 0
0 0 0 0 0
0 10 0 0 0
0 0 0 0 0
0 0 0 0 0

10


5
100 200 300 400 200
300 243 432 334 555
999 111 0 999 333
888 777 222 333 900
100 200 300 400 500

7501
*/