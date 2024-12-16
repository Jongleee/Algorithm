package com.example.algorithm.java.bitmask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SpaceExplorer {
    private static int[][] road;
    private static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        road = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                road[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        floydWarshall(n);

        dp = new int[1 << (n + 1)];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[1 << k] = 0;

        int answer = dfs(1 << k, k, n, Integer.MAX_VALUE);
        System.out.println(answer);
    }

    private static void floydWarshall(int n) {
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    road[i][j] = Math.min(road[i][j], road[i][k] + road[k][j]);
                }
            }
        }
    }

    private static int dfs(int visited, int currentCity, int n, int answer) {
        if (dp[visited] >= answer)
            return answer;

        if (visited == (1 << n) - 1) {
            return Math.min(answer, dp[(1 << n) - 1]);
        }

        for (int nextCity = 0; nextCity < n; nextCity++) {
            int nextVisited = visited | (1 << nextCity);
            if (nextVisited != visited) {
                dp[nextVisited] = dp[visited] + road[currentCity][nextCity];
                answer = dfs(nextVisited, nextCity, n, answer);
            }
        }
        return answer;
    }
}

/*
3 0
0 30 1
1 0 29
28 1 0

2


4 1
0 83 38 7
15 0 30 83
67 99 0 44
14 46 81 0

74
*/