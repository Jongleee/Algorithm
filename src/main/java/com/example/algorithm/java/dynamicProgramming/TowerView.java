package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class TowerView {
    static int n;
    static int[] buildings;
    static int[][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        buildings = new int[n];
        for (int i = 0; i < n; i++) {
            buildings[i] = Integer.parseInt(st.nextToken());
        }

        dp = new int[n][3];

        int max = buildings[0];
        for (int i = 1; i < n; i++) {
            if (max < buildings[i]) {
                max = buildings[i];
                continue;
            }

            for (int j = i - 1; j >= 0; j--) {
                if (buildings[j] > buildings[i]) {
                    dp[i][0] = dp[j][0] + 1;
                    dp[i][2] = j + 1;
                    break;
                }
            }
        }

        max = buildings[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            if (buildings[i] > max) {
                max = buildings[i];
                continue;
            }

            for (int j = i + 1; j < n; j++) {
                if (buildings[i] < buildings[j]) {
                    dp[i][1] = dp[j][1] + 1;

                    if (dp[i][0] == 0 || (i - dp[i][2] + 1) > (j - i)) {
                        dp[i][2] = j + 1;
                    }
                    break;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int sum = dp[i][0] + dp[i][1];

            sb.append(sum).append(" ");
            if (sum != 0) {
                sb.append(dp[i][2]);
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }
}

/*
8
3 7 1 6 3 5 1 7

1 2
0
3 2
2 2
4 4
3 4
4 6
0
 */