package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SharkElementarySchool {
    private static final int[] DY = { -1, 0, 1, 0 };
    private static final int[] DX = { 0, 1, 0, -1 };

    private static class Seat implements Comparable<Seat> {
        int y, x, likedCount, emptyCount;

        Seat(int y, int x, int likedCount, int emptyCount) {
            this.y = y;
            this.x = x;
            this.likedCount = likedCount;
            this.emptyCount = emptyCount;
        }

        @Override
        public int compareTo(Seat other) {
            if (likedCount != other.likedCount)
                return Integer.compare(other.likedCount, likedCount);
            if (emptyCount != other.emptyCount)
                return Integer.compare(other.emptyCount, emptyCount);
            if (y != other.y)
                return Integer.compare(y, other.y);
            return Integer.compare(x, other.x);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[][] like = new int[n * n + 1][4];
        int[][] grid = new int[n][n];
        int[] order = new int[n * n];

        initialize(br, order, like);
        placeStudents(n, order, like, grid);
        System.out.println(calculateSatisfaction(n, grid, like));
    }

    private static void initialize(BufferedReader br, int[] order, int[][] like) throws IOException {
        for (int i = 0; i < order.length; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            order[i] = Integer.parseInt(st.nextToken());
            for (int j = 0; j < 4; j++) {
                like[order[i]][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private static void placeStudents(int n, int[] order, int[][] like, int[][] grid) {
        for (int student : order) {
            Seat bestSeat = findBestSeat(n, grid, like[student]);
            grid[bestSeat.y][bestSeat.x] = student;
        }
    }

    private static Seat findBestSeat(int n, int[][] grid, int[] likedStudents) {
        Seat bestSeat = new Seat(-1, -1, -1, -1);

        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                if (grid[y][x] > 0)
                    continue;

                int likedCount = countAdjacentLiked(n, grid, y, x, likedStudents);
                int emptyCount = countEmptySpaces(n, grid, y, x);

                Seat currentSeat = new Seat(y, x, likedCount, emptyCount);
                if (bestSeat.compareTo(currentSeat) > 0) {
                    bestSeat = currentSeat;
                }
            }
        }
        return bestSeat;
    }

    private static int countAdjacentLiked(int n, int[][] grid, int y, int x, int[] likedStudents) {
        int count = 0;
        for (int d = 0; d < 4; d++) {
            int ny = y + DY[d];
            int nx = x + DX[d];
            if (isValid(n, ny, nx) && contains(likedStudents, grid[ny][nx])) {
                count++;
            }
        }
        return count;
    }

    private static int countEmptySpaces(int n, int[][] grid, int y, int x) {
        int count = 0;
        for (int d = 0; d < 4; d++) {
            int ny = y + DY[d];
            int nx = x + DX[d];
            if (isValid(n, ny, nx) && grid[ny][nx] == 0) {
                count++;
            }
        }
        return count;
    }

    private static boolean isValid(int n, int y, int x) {
        return y >= 0 && x >= 0 && y < n && x < n;
    }

    private static boolean contains(int[] arr, int num) {
        for (int val : arr) {
            if (val == num)
                return true;
        }
        return false;
    }

    private static int calculateSatisfaction(int n, int[][] grid, int[][] like) {
        int sum = 0;
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                int likedCount = countAdjacentLiked(n, grid, y, x, like[grid[y][x]]);
                if (likedCount > 0) {
                    sum += (int) Math.pow(10, (double) likedCount - 1);
                }
            }
        }
        return sum;
    }
}

/*
3
4 2 5 1 7
2 1 9 4 5
5 8 1 4 3
1 2 9 3 4
7 2 3 4 8
9 8 4 5 7
6 5 2 3 4
8 4 9 2 1
3 9 2 1 4

1053


3
4 2 5 1 7
3 1 9 4 5
9 8 1 2 3
8 1 9 3 4
7 2 3 4 8
1 9 2 5 7
6 5 2 3 4
5 1 9 2 8
2 9 3 1 4

54
*/