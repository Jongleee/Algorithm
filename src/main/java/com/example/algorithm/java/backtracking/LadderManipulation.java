package com.example.algorithm.java.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LadderManipulation {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());

        boolean[][] ladder = new boolean[h + 1][n + 1];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            ladder[y][x] = true;
        }

        System.out.println(solve(n, h, m, ladder));
    }

    private static int solve(int n, int h, int m, boolean[][] ladder) {
        if (countOddLines(ladder, n, h) > 3) {
            return -1;
        }
        for (int limit = m % 2; limit < 4; limit += 2) {
            if (tryAddLadders(n, h, ladder, limit)) {
                return limit;
            }
        }
        return -1;
    }

    private static int countOddLines(boolean[][] ladder, int n, int h) {
        int oddCount = 0;
        for (int x = 1; x < n; x++) {
            boolean isOdd = false;
            for (int y = 1; y <= h; y++) {
                if (ladder[y][x]) {
                    isOdd = !isOdd;
                }
            }
            if (isOdd) {
                oddCount++;
            }
        }
        return oddCount;
    }

    private static boolean tryAddLadders(int n, int h, boolean[][] ladder, int limit) {
        return dfs(n, h, 1, 1, 0, limit, ladder);
    }

    private static boolean dfs(int n, int h, int y, int x, int count, int limit, boolean[][] ladder) {
        if (count == limit) {
            return validateLadders(n, h, ladder);
        }

        for (int nx = x; nx < n; nx++) {
            if (addLadderAndCheck(n, h, y, nx, count, limit, ladder)) {
                return true;
            }
        }

        for (int ny = y + 1; ny <= h; ny++) {
            for (int nx = 1; nx < n; nx++) {
                if (addLadderAndCheck(n, h, ny, nx, count, limit, ladder)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean addLadderAndCheck(int n, int h, int y, int x, int count, int limit, boolean[][] ladder) {
        if (ladder[y][x] || ladder[y][x - 1] || ladder[y][x + 1]) {
            return false;
        }

        ladder[y][x] = true;
        boolean result = dfs(n, h, y, x, count + 1, limit, ladder);
        ladder[y][x] = false;

        return result;
    }

    private static boolean validateLadders(int n, int h, boolean[][] ladder) {
        for (int x = 1; x <= n; x++) {
            int current = x;
            for (int y = 1; y <= h; y++) {
                if (ladder[y][current]) {
                    current++;
                } else if (current > 1 && ladder[y][current - 1]) {
                    current--;
                }
            }
            if (current != x) {
                return false;
            }
        }
        return true;
    }
}

/*
5 12 6
1 1
1 3
2 2
2 4
3 1
3 3
4 2
4 4
5 1
5 3
6 2
6 4

-1


5 6 6
1 1
3 1
5 2
4 3
2 3
1 4

2
*/