package com.example.algorithm.java.practice;

import java.util.Arrays;

public class InstallColumnBeam {
    public static void main(String[] args) {
        System.out.println(
                Arrays.deepToString(solution(5, new int[][] { { 1, 0, 0, 1 }, { 1, 1, 1, 1 }, { 2, 1, 0, 1 },
                        { 2, 2, 1, 1 }, { 5, 0, 0, 1 }, { 5, 1, 0, 1 }, { 4, 2, 1, 1 }, { 3, 2, 1, 1 } })));
    }

    static boolean[][] pillar;
    static boolean[][] bar;

    public static int[][] solution(int n, int[][] buildFrame) {
        pillar = new boolean[n + 1][n + 1];
        bar = new boolean[n + 1][n + 1];

        int count = 0;
        for (int i = 0; i < buildFrame.length; i++) {
            int x = buildFrame[i][0];
            int y = buildFrame[i][1];
            int type = buildFrame[i][2];
            int action = buildFrame[i][3];

            switch (type) {
                case 0:
                    count = pillarCount(n, count, x, y, action);
                    break;

                case 1:
                    count = barCount(n, count, x, y, action);
                    break;

                default:
                    break;
            }
        }

        int[][] result = new int[count][3];
        int idx = 0;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (pillar[i][j]) {
                    result[idx][0] = i;
                    result[idx][1] = j;
                    result[idx++][2] = 0;
                }
                if (bar[i][j]) {
                    result[idx][0] = i;
                    result[idx][1] = j;
                    result[idx++][2] = 1;
                }
            }
        }
        return result;
    }

    private static int barCount(int n, int count, int x, int y, int action) {
        if (action == 1) {
            if (checkBar(x, y)) { 
                bar[x][y] = true;
                count++;
            }
        } else { 
            bar[x][y] = false;
            if (!canDelete(n))
                bar[x][y] = true;
            else
                count--;
        }
        return count;
    }

    private static int pillarCount(int n, int count, int x, int y, int action) {
        if (action == 1) {
            if (checkPillar(x, y)) {
                pillar[x][y] = true;
                count++;
            }
        } else {
            pillar[x][y] = false;
            if (!canDelete(n))
                pillar[x][y] = true;
            else
                count--;
        }
        return count;
    }

    public static boolean canDelete(int n) {
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (pillar[i][j] && !checkPillar(i, j))
                    return false; 
                if (bar[i][j] && !checkBar(i, j))
                    return false;
            }
        }
        return true;
    }

    public static boolean checkPillar(int x, int y) {
        return (y == 0)||(y > 0 && pillar[x][y - 1])||(x > 0 && bar[x - 1][y] || bar[x][y]);
    }

    public static boolean checkBar(int x, int y) {
        return (y > 0 && pillar[x][y - 1] || pillar[x + 1][y - 1])||(x > 0 && bar[x - 1][y] && bar[x + 1][y]);
    }
}