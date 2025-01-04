package com.example.algorithm.java.bruteForce;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Slope {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][n];
        int[][] transposeMap = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                int value = Integer.parseInt(st.nextToken());
                map[i][j] = value;
                transposeMap[j][i] = value;
            }
        }

        int result = countValidPaths(map, n, l) + countValidPaths(transposeMap, n, l);
        System.out.println(result);
    }

    private static int countValidPaths(int[][] map, int n, int l) {
        int validPaths = 0;

        for (int i = 0; i < n; i++) {
            if (canPass(map[i], n, l)) {
                validPaths++;
            }
        }
        return validPaths;
    }

    private static boolean canPass(int[] row, int n, int l) {
        int prevLength = 1;

        for (int j = 0; j < n - 1; j++) {
            int diff = row[j + 1] - row[j];

            if (diff == 0) {
                prevLength++;
            } else if (diff == 1) {
                if (prevLength < l) {
                    return false;
                }
                prevLength = 1;
            } else if (diff == -1) {
                if (!canBuildSlope(row, j + 1, l)) {
                    return false;
                }
                j += l - 1;
                prevLength = 0;
            } else {
                return false;
            }
        }
        return true;
    }

    private static boolean canBuildSlope(int[] row, int start, int l) {
        int height = row[start];

        for (int i = start; i < start + l; i++) {
            if (i >= row.length || row[i] != height) {
                return false;
            }
        }
        return true;
    }
}

/*
6 2
3 3 3 3 3 3
2 3 3 3 3 3
2 2 2 3 2 3
1 1 1 2 2 2
1 1 1 3 3 1
1 1 2 3 3 2

3


6 1
3 2 1 1 2 3
3 2 2 1 2 3
3 2 2 2 3 3
3 3 3 3 3 3
3 3 3 3 2 2
3 3 3 3 2 2

11
*/