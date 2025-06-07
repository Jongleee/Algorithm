package com.example.algorithm.java.divideAndConquerOptimization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class TWLand {
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[][] u = new int[n + 1][n + 1];
        int[][] d1 = new int[n + 1][n + 1];
        int[][] d2 = new int[n + 1][n + 1];
        int[][] dp = new int[k + 1][n + 1];
        int[][] p = new int[k + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                u[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        preprocess(n, u, d1, d2);

        for (int i = 1; i <= n; i++) {
            dp[1][i] = d2[1][i];
        }

        for (int t = 2; t <= k; t++) {
            divideAndConquer(t, 1, n, 0, n - 1, dp, d2, p);
        }

        System.out.println(dp[k][n]);
    }

    private static void preprocess(int n, int[][] u, int[][] d1, int[][] d2) {
        for (int i = 1; i <= n; i++) {
            d1[i][1] = u[i][1];
            for (int j = 2; j <= n; j++) {
                d1[i][j] = d1[i][j - 1] + u[i][j];
            }
        }

        for (int i = 1; i <= n; i++) {
            d2[i][i] = 0;
            for (int j = i + 1; j <= n; j++) {
                d2[i][j] = d2[i][j - 1] + (d1[j][j] - d1[j][i - 1]);
            }
        }
    }

    private static void divideAndConquer(int t, int start, int end, int minPartition, int maxPartition,
            int[][] dp, int[][] d2, int[][] p) {
        if (start > end)
            return;

        int mid = (start + end) / 2;
        dp[t][mid] = INF;

        int bestPartition = -1;
        int upperBound = Math.min(maxPartition, mid - 1);
        for (int sep = minPartition; sep <= upperBound; sep++) {
            int cost = dp[t - 1][sep] + d2[sep + 1][mid];
            if (cost < dp[t][mid]) {
                dp[t][mid] = cost;
                bestPartition = sep;
            }
        }

        if (bestPartition != -1) {
            p[t][mid] = bestPartition;
            divideAndConquer(t, start, mid - 1, minPartition, bestPartition, dp, d2, p);
            divideAndConquer(t, mid + 1, end, bestPartition, maxPartition, dp, d2, p);
        }
    }
}

/*
8 3
0 1 1 1 1 1 1 1
1 0 1 1 1 1 1 1
1 1 0 1 1 1 1 1
1 1 1 0 1 1 1 1
1 1 1 1 0 1 1 1
1 1 1 1 1 0 1 1
1 1 1 1 1 1 0 1
1 1 1 1 1 1 1 0

7


5 2
0 0 1 1 1
0 0 1 1 1
1 1 0 0 0
1 1 0 0 0
1 1 0 0 0

0
*/