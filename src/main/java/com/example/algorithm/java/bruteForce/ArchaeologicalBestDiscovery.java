package com.example.algorithm.java.bruteForce;

import java.util.Arrays;

public class ArchaeologicalBestDiscovery {
    static int n;

    public static int solution(int[][] clockHands) {
        n = clockHands.length;
        int answer = Integer.MAX_VALUE;

        for (int i = 0; i < Math.pow(4, n); i++) {
            int[][] copyArr = new int[n][n];
            for (int j = 0; j < n; j++) {
                System.arraycopy(clockHands[j], 0, copyArr[j], 0, n);
            }
            int count = 0;
            int a = i;
            for (int j = 0; j < n; j++) {
                int cnt = a % 4;
                a /= 4;
                rotate(copyArr, 0, j, cnt);
                count += cnt;
            }
            for (int row = 1; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    int cnt = (4 - copyArr[row - 1][col]) % 4;
                    rotate(copyArr, row, col, cnt);
                    count += cnt;
                }
            }
            if (Arrays.equals(copyArr[n - 1], new int[n])) {
                return count;
            }
        }
        return answer;
    }

    public static void rotate(int[][] copyArr, int row, int col, int cnt) {
        copyArr[row][col] = (copyArr[row][col] + cnt) % 4;
        if (row > 0)
            copyArr[row - 1][col] = (copyArr[row - 1][col] + cnt) % 4;
        if (col > 0)
            copyArr[row][col - 1] = (copyArr[row][col - 1] + cnt) % 4;
        if (row < n - 1)
            copyArr[row + 1][col] = (copyArr[row + 1][col] + cnt) % 4;
        if (col < n - 1)
            copyArr[row][col + 1] = (copyArr[row][col + 1] + cnt) % 4;
    }

    public static void main(String[] args) {
        int[][] c1 = { { 0, 3, 3, 0 }, { 3, 2, 2, 3 }, { 0, 3, 2, 0 }, { 0, 3, 3, 3 } };
        System.out.println(solution(c1));// 3
    }
}
