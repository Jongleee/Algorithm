package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class FishbowlArrangement {
    static final int[][] DIRECTIONS = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] fishTank = new int[n];
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < n; i++) {
            fishTank[i] = Integer.parseInt(st.nextToken());
        }

        int operationCount = 0;
        while (true) {
            if (calculateDifference(fishTank) <= k)
                break;
            operationCount++;
            increaseMinFish(fishTank);
            int[][] initialStack = new int[2][fishTank.length - 1];
            stackLeftToUp(fishTank, initialStack);
            int[][] rotatedStack = rotate90Degrees(initialStack);
            balanceFish(rotatedStack);
            fishTank = flattenTank(rotatedStack);
            rotatedStack = rotate180Degrees(fishTank);
            balanceFish(rotatedStack);
            fishTank = flattenTank(rotatedStack);
        }
        System.out.println(operationCount);
    }

    static void increaseMinFish(int[] fishTank) {
        int min = Integer.MAX_VALUE;
        for (int fish : fishTank) {
            min = Math.min(min, fish);
        }
        for (int i = 0; i < fishTank.length; i++) {
            if (fishTank[i] == min)
                fishTank[i]++;
        }
    }

    static void stackLeftToUp(int[] fishTank, int[][] stacked) {
        stacked[0][0] = fishTank[0];
        for (int j = 0; j < stacked[0].length; j++) {
            stacked[1][j] = fishTank[j + 1];
        }
    }

    static int[][] rotate90Degrees(int[][] stacked) {
        int[][] rotated = stacked;
        while (true) {
            int height = 0;
            for (int j = 0; j < rotated[0].length; j++) {
                if (rotated[rotated.length - 2][j] != 0)
                    height++;
                else
                    break;
            }
            if (rotated.length > rotated[0].length - height)
                break;

            rotated = getLeftSide(rotated, height);
        }
        return rotated;
    }

    private static int[][] getLeftSide(int[][] rotated, int height) {
        int newX = height + 1;
        int newY = rotated[0].length - height;
        int[][] temp = new int[newX][newY];
        int startX = rotated.length - 1;
        int startY = 0;
        boolean leftSide = true;

        for (int i = 0; i < newX; i++) {
            for (int j = 0; j < newY; j++) {
                if (leftSide) {
                    if (startX >= 0)
                        temp[i][j] = rotated[startX--][startY];
                } else {
                    temp[i][j] = rotated[startX][startY++];
                }
            }
            startX = rotated.length - 1;
            if (leftSide && ++startY >= height) {
                leftSide = false;
                startY = height;
                startX = rotated.length - 1;
            }
        }
        return temp;
    }

    static void balanceFish(int[][] tank) {
        int rows = tank.length, cols = tank[0].length;
        int[][] adjustments = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int current = tank[i][j];
                if (current == 0)
                    continue;

                moveFish(tank, rows, cols, adjustments, i, j, current);
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tank[i][j] += adjustments[i][j];
            }
        }
    }

    private static void moveFish(int[][] tank, int rows, int cols, int[][] adjustments, int i, int j, int current) {
        for (int d = 0; d < 4; d++) {
            int nx = i + DIRECTIONS[0][d];
            int ny = j + DIRECTIONS[1][d];
            if (0 <= nx && nx < rows && 0 <= ny && ny < cols) {
                if (tank[nx][ny] == 0)
                    continue;
                int diff = Math.abs(current - tank[nx][ny]) / 5;
                if (diff > 0) {
                    if (current < tank[nx][ny])
                        adjustments[i][j] += diff;
                    else
                        adjustments[i][j] -= diff;
                }
            }
        }
    }

    static int[] flattenTank(int[][] tank) {
        Queue<Integer> queue = new LinkedList<>();
        for (int j = 0; j < tank[0].length; j++) {
            for (int i = tank.length - 1; i >= 0; i--) {
                if (tank[i][j] == 0)
                    break;
                queue.add(tank[i][j]);
            }
        }
        int[] answer = new int[queue.size()];
        int idx = 0;
        while (!queue.isEmpty())
            answer[idx++] = queue.poll();
        return answer;
    }

    static int[][] rotate180Degrees(int[] tank) {
        int mid = tank.length / 2;
        int[][] first = new int[2][tank.length - mid];
        boolean left = true;

        process(tank, mid, first, left);

        int newX = first.length * 2;
        int newY = first[0].length - first[0].length / 2;
        int[][] second = new int[newX][newY];
        int targetX = first.length - 1;
        int targetY = first[0].length / 2 - 1;
        left = true;

        for (int i = 0; i < newX; i++) {
            for (int j = 0; j < newY; j++) {
                if (left && targetY >= 0)
                    second[i][j] = first[targetX][targetY--];
                else if (!left && targetY < first[0].length)
                    second[i][j] = first[targetX][targetY++];
            }
            if (left) {
                targetY = first[0].length / 2 - 1;
                if (--targetX < 0) {
                    left = false;
                    targetX = 0;
                    targetY++;
                }
            } else {
                targetX++;
                targetY = first[0].length / 2;
            }
        }
        return second;
    }

    private static void process(int[] tank, int mid, int[][] first, boolean left) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < first[0].length; j++) {
                if (left && mid >= 0)
                    first[i][j] = tank[--mid];
                else
                    first[i][j] = tank[mid++];
            }
            if (mid <= 0) {
                left = false;
                mid = tank.length / 2;
            }
        }
    }

    static int calculateDifference(int[] tank) {
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (int fish : tank) {
            max = Math.max(max, fish);
            min = Math.min(min, fish);
        }
        return max - min;
    }
}

/*
16 0
1 1 10000 1 2 3 10000 9999 10 9 8 10000 5 4 3 1

13


4 0
1 10000 1 10000

10
*/