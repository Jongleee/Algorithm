package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class SharkMiddleSchool {
    private static final int[] DX = { -1, 0, 1, 0 };
    private static final int[] DY = { 0, 1, 0, -1 };

    private static int[][] board, group;
    private static int n, score;

    private static class BlockGroup {
        List<int[]> blocks;
        int size, rainbowCount;

        BlockGroup(List<int[]> blocks, int size, int rainbowCount) {
            this.blocks = blocks;
            this.size = size;
            this.rainbowCount = rainbowCount;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        st.nextToken();
        board = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        while (process())
            ;

        System.out.println(score);
    }

    private static boolean process() {
        group = new int[n][n];
        BlockGroup bestGroup = findBestGroup();

        if (bestGroup == null)
            return false;

        removeGroup(bestGroup);
        score += bestGroup.size * bestGroup.size;
        applyGravity();
        rotateBoard();
        applyGravity();

        return true;
    }

    private static BlockGroup findBestGroup() {
        int number = 1, maxSize = 0, maxRainbow = 0;
        BlockGroup bestGroup = null;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (group[i][j] == 0 && board[i][j] > 0) {
                    BlockGroup current = findGroup(i, j, number++);
                    if (current != null && isBetterGroup(current, maxSize, maxRainbow)) {
                        maxSize = current.size;
                        maxRainbow = current.rainbowCount;
                        bestGroup = current;
                    }
                }
            }
        }
        return bestGroup;
    }

    private static BlockGroup findGroup(int x, int y, int number) {
        Queue<int[]> queue = new LinkedList<>();
        List<int[]> blocks = new ArrayList<>(), rainbows = new ArrayList<>();
        int color = board[x][y], count = 1, rainbowCount = 0;

        queue.add(new int[] { x, y });
        blocks.add(new int[] { x, y });
        group[x][y] = number;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int d = 0; d < 4; d++) {
                int nx = cur[0] + DX[d], ny = cur[1] + DY[d];
                if (isInBounds(nx, ny) && group[nx][ny] == 0 && (board[nx][ny] == color || board[nx][ny] == 0)) {
                    queue.add(new int[] { nx, ny });
                    blocks.add(new int[] { nx, ny });
                    group[nx][ny] = number;
                    count++;
                    if (board[nx][ny] == 0) {
                        rainbowCount++;
                        rainbows.add(new int[] { nx, ny });
                    }
                }
            }
        }

        for (int[] rb : rainbows)
            group[rb[0]][rb[1]] = 0;
        return count >= 2 ? new BlockGroup(blocks, count, rainbowCount) : null;
    }

    private static boolean isBetterGroup(BlockGroup current, int maxSize, int maxRainbow) {
        return current.size > maxSize || (current.size == maxSize && current.rainbowCount >= maxRainbow);
    }

    private static void removeGroup(BlockGroup group) {
        for (int[] block : group.blocks) {
            board[block[0]][block[1]] = -2;
        }
    }

    private static void applyGravity() {
        for (int j = 0; j < n; j++) {
            int emptyRow = n - 1;
            for (int i = n - 1; i >= 0; i--) {
                if (board[i][j] == -1) {
                    emptyRow = i - 1;
                } else if (board[i][j] >= 0) {
                    int temp = board[i][j];
                    board[i][j] = -2;
                    board[emptyRow--][j] = temp;
                }
            }
        }
    }

    private static void rotateBoard() {
        int[][] rotated = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotated[n - j - 1][i] = board[i][j];
            }
        }
        board = rotated;
    }

    private static boolean isInBounds(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < n;
    }
}

/*
6 4
2 3 1 0 -1 2
2 -1 4 1 3 3
3 0 4 2 2 1
-1 4 -1 2 3 4
3 -1 4 2 0 3
1 2 2 2 2 1

125


5 3
2 2 -1 3 1
3 3 2 0 -1
0 0 0 1 2
-1 3 1 3 2
0 3 2 2 1

77
*/