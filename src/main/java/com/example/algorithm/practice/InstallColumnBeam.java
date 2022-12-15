package com.example.algorithm.practice;

import java.util.Arrays;

public class InstallColumnBeam {
    public static void main(String[] args) {
        System.out.println(
                Arrays.deepToString(solution(5, new int[][] { { 1, 0, 0, 1 }, { 1, 1, 1, 1 }, { 2, 1, 0, 1 },
                        { 2, 2, 1, 1 }, { 5, 0, 0, 1 }, { 5, 1, 0, 1 }, { 4, 2, 1, 1 }, { 3, 2, 1, 1 } })));
    }

    static boolean[][] pillar;
    static boolean[][] bar;

    public static int[][] solution(int n, int[][] build_frame) {
        pillar = new boolean[n + 1][n + 1];
        bar = new boolean[n + 1][n + 1];

        int count = 0;
        for (int i = 0; i < build_frame.length; i++) {
            int x = build_frame[i][0];
            int y = build_frame[i][1];
            int type = build_frame[i][2];
            int action = build_frame[i][3];

            switch (type) {
                case 0:
                    if (action == 1) {
                        if (checkPillar(x, y)) {
                            pillar[x][y] = true;
                            count++;
                        }
                    } else {
                        pillar[x][y] = false;
                        if (canDelete(n) == false)
                            pillar[x][y] = true;
                        else
                            count--;
                    }
                    break;

                case 1:
                    if (action == 1) {
                        if (checkBar(x, y)) { 
                            bar[x][y] = true;
                            count++;
                        }
                    } else { 
                        bar[x][y] = false;
                        if (canDelete(n) == false)
                            bar[x][y] = true;
                        else
                            count--;
                    }
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

    public static boolean canDelete(int n) {
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (pillar[i][j] && checkPillar(i, j) == false)
                    return false; 
                else if (bar[i][j] && checkBar(i, j) == false)
                    return false;
            }
        }
        return true;
    }

    public static boolean checkPillar(int x, int y) {
        if (y == 0)
            return true;
        else if (y > 0 && pillar[x][y - 1])
            return true;
        else if (x > 0 && bar[x - 1][y] || bar[x][y])
            return true;
        return false;
    }

    public static boolean checkBar(int x, int y) {
        if (y > 0 && pillar[x][y - 1] || pillar[x + 1][y - 1])
            return true; 
        else if (x > 0 && bar[x - 1][y] && bar[x + 1][y])
            return true;
        return false;
    }
}