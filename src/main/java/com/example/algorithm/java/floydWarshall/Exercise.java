package com.example.algorithm.java.floydWarshall;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Exercise {
    private static final int INF = 63000000;
    private static int n;
    private static int[][] distance;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        distance = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(distance[i], INF);
            distance[i][i] = 0;
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken()) - 1;
            int end = Integer.parseInt(st.nextToken()) - 1;
            int weight = Integer.parseInt(st.nextToken());
            distance[start][end] = weight;
        }

        floydWarshall();

        int minCycle = findMinimumCycle();

        System.out.println(minCycle == INF ? -1 : minCycle);
    }

    private static void floydWarshall() {
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
                }
            }
        }
    }

    private static int findMinimumCycle() {
        int minCycle = INF;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (distance[i][j] < INF && distance[j][i] < INF) {
                    minCycle = Math.min(minCycle, distance[i][j] + distance[j][i]);
                }
            }
        }
        return minCycle;
    }
}


/*
3 4
1 2 1
3 2 1
1 3 5
2 3 2

3
*/