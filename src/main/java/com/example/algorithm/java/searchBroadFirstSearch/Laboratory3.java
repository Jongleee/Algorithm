package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Laboratory3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] lab = new int[n][n];
        List<int[]> viruses = new ArrayList<>();
        boolean hasEmpty = false;

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                lab[i][j] = Integer.parseInt(st.nextToken());
                if (lab[i][j] == 2) {
                    viruses.add(new int[] { i, j });
                }
                if (lab[i][j] == 0) {
                    hasEmpty = true;
                }
            }
        }

        System.out.println(hasEmpty ? findMinimumTime(lab, viruses, n, m) : 0);
    }

    private static int findMinimumTime(int[][] lab, List<int[]> viruses, int n, int m) {
        int virusCount = viruses.size();
        int[] permutation = createPermutation(virusCount, m);
        int[][][] times = calculateTimes(lab, viruses, n, virusCount);
        return testAllCombinations(lab, times, permutation, n);
    }

    private static int[] createPermutation(int virusCount, int m) {
        int[] permutation = new int[virusCount];
        for (int i = virusCount - m; i < virusCount; i++) {
            permutation[i] = 1;
        }
        return permutation;
    }

    private static int[][][] calculateTimes(int[][] lab, List<int[]> viruses, int n, int virusCount) {
        int[][][] times = new int[n][n][virusCount];
        int[] dy = { 1, -1, 0, 0 };
        int[] dx = { 0, 0, 1, -1 };

        for (int v = 0; v < virusCount; v++) {
            Queue<int[]> queue = new ArrayDeque<>();
            int[] start = viruses.get(v);
            queue.add(new int[] { start[0], start[1], 1 });
            times[start[0]][start[1]][v] = 1;

            while (!queue.isEmpty()) {
                int[] curr = queue.poll();
                for (int dir = 0; dir < 4; dir++) {
                    int ny = curr[0] + dy[dir];
                    int nx = curr[1] + dx[dir];

                    if (isValid(ny, nx, n) && times[ny][nx][v] == 0 && lab[ny][nx] != 1) {
                        times[ny][nx][v] = curr[2] + 1;
                        queue.add(new int[] { ny, nx, curr[2] + 1 });
                    }
                }
            }
        }

        return times;
    }

    private static int testAllCombinations(int[][] lab, int[][][] times, int[] permutation, int n) {
        int minTime = Integer.MAX_VALUE;

        do {
            int maxTime = 0;
            boolean reachable = true;

            outer: for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (lab[i][j] == 0) {
                        int shortest = getShortest(times, permutation, i, j);
                        if (shortest == Integer.MAX_VALUE) {
                            reachable = false;
                            break outer;
                        }
                        maxTime = Math.max(maxTime, shortest);
                    }
                }
            }

            if (reachable) {
                minTime = Math.min(minTime, maxTime - 1);
            }
        } while (nextPermutation(permutation));

        return minTime == Integer.MAX_VALUE ? -1 : minTime;
    }

    private static int getShortest(int[][][] times, int[] permutation, int i, int j) {
        int shortest = Integer.MAX_VALUE;
        for (int v = 0; v < permutation.length; v++) {
            if (permutation[v] == 1 && times[i][j][v] > 0) {
                shortest = Math.min(shortest, times[i][j][v]);
            }
        }
        return shortest;
    }

    private static boolean nextPermutation(int[] p) {
        int i = p.length - 1;
        while (i > 0 && p[i - 1] >= p[i])
            i--;
        if (i == 0)
            return false;

        int j = p.length - 1;
        while (p[i - 1] >= p[j])
            j--;

        swap(p, i - 1, j);
        reverse(p, i, p.length - 1);
        return true;
    }

    private static void swap(int[] p, int i, int j) {
        int temp = p[i];
        p[i] = p[j];
        p[j] = temp;
    }

    private static void reverse(int[] p, int i, int j) {
        while (i < j) {
            swap(p, i++, j--);
        }
    }

    private static boolean isValid(int y, int x, int n) {
        return y >= 0 && y < n && x >= 0 && x < n;
    }
}

/*
7 5
2 0 2 0 1 1 0
0 0 1 0 1 2 0
0 1 1 2 1 0 0
2 1 0 0 0 0 2
0 0 0 2 0 1 1
0 1 0 0 0 0 0
2 1 0 0 2 0 2

3


7 2
2 0 2 0 1 1 0
0 0 1 0 1 0 0
0 1 1 1 1 0 0
2 1 0 0 0 0 2
1 0 0 0 0 1 1
0 1 0 0 0 0 0
2 1 0 0 2 0 2

-1
*/