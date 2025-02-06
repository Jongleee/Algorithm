package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class WizardSharkAndDuplication {
    private static final int N = 4;
    private static final int[] fishDx = { 0, -1, -1, -1, 0, 1, 1, 1 };
    private static final int[] fishDy = { -1, -1, 0, 1, 1, 1, 0, -1 };
    private static final int[] sharkDx = { 0, 1, 0, -1 };
    private static final int[] sharkDy = { 1, 0, -1, 0 };

    private static int[][][] board = new int[N][N][8];
    private static int[][][] tempBoard = new int[N][N][8];
    private static int[][] smell = new int[N][N];
    private static int sharkX;
    private static int sharkY;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int fishCount = Integer.parseInt(st.nextToken());
        int rounds = Integer.parseInt(st.nextToken());

        for (int i = 0; i < fishCount; i++) {
            st = new StringTokenizer(br.readLine());
            int fx = Integer.parseInt(st.nextToken()) - 1;
            int fy = Integer.parseInt(st.nextToken()) - 1;
            int direction = Integer.parseInt(st.nextToken()) - 1;
            board[fx][fy][direction]++;
        }

        st = new StringTokenizer(br.readLine());
        sharkX = Integer.parseInt(st.nextToken()) - 1;
        sharkY = Integer.parseInt(st.nextToken()) - 1;

        for (int i = 0; i < rounds; i++) {
            play();
        }

        System.out.println(totalFish());
    }

    private static void play() {
        copyBoard();
        moveFish();
        decrementSmell();
        moveShark();
        duplicateFish();
    }

    private static void copyBoard() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.arraycopy(board[i][j], 0, tempBoard[i][j], 0, 8);
                board[i][j] = new int[8];
            }
        }
    }

    private static void moveFish() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int d = 0; d < 8; d++) {
                    if (tempBoard[i][j][d] == 0)
                        continue;

                    int newDirection = findValidFishDirection(i, j, d);
                    if (newDirection == -1) {
                        board[i][j][d] += tempBoard[i][j][d];
                        continue;
                    }

                    int newX = i + fishDx[newDirection];
                    int newY = j + fishDy[newDirection];
                    board[newX][newY][newDirection] += tempBoard[i][j][d];
                }
            }
        }
    }

    private static int findValidFishDirection(int x, int y, int d) {
        for (int i = 0; i < 8; i++) {
            int newDir = (d - i + 8) % 8;
            int newX = x + fishDx[newDir];
            int newY = y + fishDy[newDir];

            if (!isValidMove(newX, newY) || smell[newX][newY] > 0||(newX == sharkX && newY == sharkY)) {
                continue;
            }
            return newDir;
        }
        return -1;
    }

    private static void decrementSmell() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (smell[i][j] > 0)
                    smell[i][j]--;
            }
        }
    }

    private static int moveShark() {
        int[] bestPath = findBestSharkPath(sharkX, sharkY);

        for (int dir : bestPath) {
            sharkX += sharkDx[dir];
            sharkY += sharkDy[dir];

            if (hasFish(sharkX, sharkY)) {
                removeFish(sharkX, sharkY);
                smell[sharkX][sharkY] = 2;
            }
        }
        return sharkX;
    }

    private static int[] findBestSharkPath(int sx, int sy) {
        int maxFish = 0;
        int[] bestPath = new int[3];

        for (int i = 0; i < 4; i++) {
            maxFish = getMaxFish(sx, sy, maxFish, bestPath, i);
        }
        return bestPath;
    }

    private static int getMaxFish(int sx, int sy, int maxFish, int[] bestPath, int i) {
        int nx1 = sx + sharkDx[i];
        int ny1 = sy + sharkDy[i];
        if (!isValidMove(nx1, ny1))
            return maxFish;

        int fish1 = countFish(nx1, ny1);

        for (int j = 0; j < 4; j++) {
            int nx2 = nx1 + sharkDx[j];
            int ny2 = ny1 + sharkDy[j];
            if (!isValidMove(nx2, ny2))
                continue;

            int fish2 = countFish(nx2, ny2);

            for (int k = 0; k < 4; k++) {
                int nx3 = nx2 + sharkDx[k];
                int ny3 = ny2 + sharkDy[k];
                if (!isValidMove(nx3, ny3))
                    continue;

                int fish3 = getFish3(nx1, ny1, nx3, ny3);
                int totalFish = fish1 + fish2 + fish3;

                if (totalFish >= maxFish) {
                    maxFish = totalFish;
                    bestPath[0] = i;
                    bestPath[1] = j;
                    bestPath[2] = k;
                }
            }
        }
        return maxFish;
    }

    private static int getFish3(int nx1, int ny1, int nx3, int ny3) {
        return (nx1 == nx3 && ny1 == ny3) ? 0 : countFish(nx3, ny3);
    }

    private static int countFish(int x, int y) {
        int count = 0;
        for (int d = 0; d < 8; d++) {
            count += board[x][y][d];
        }
        return count;
    }

    private static boolean hasFish(int x, int y) {
        return countFish(x, y) > 0;
    }

    private static void removeFish(int x, int y) {
        board[x][y] = new int[8];
    }

    private static void duplicateFish() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int d = 0; d < 8; d++) {
                    board[i][j][d] += tempBoard[i][j][d];
                }
            }
        }
    }

    private static int totalFish() {
        int total = 0;
        for (int[][] row : board) {
            for (int[] cell : row) {
                for (int fish : cell) {
                    total += fish;
                }
            }
        }
        return total;
    }

    private static boolean isValidMove(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }
}

/*
5 26
4 3 5
1 3 5
2 4 2
2 1 6
3 4 4
4 2

640240


8 100
1 1 1
1 1 2
1 1 3
1 1 4
1 1 5
1 1 6
1 1 7
1 1 8
1 1

8
*/