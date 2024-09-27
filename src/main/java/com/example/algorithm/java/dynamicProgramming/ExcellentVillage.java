package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ExcellentVillage {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int[] population;
    private static List<Integer>[] graph;
    private static int[][] dp;
    private static boolean[] visited;

    public static void main(String[] args) throws IOException {
        initializeVariables();
        visited[1] = true;
        dfs(1);
        System.out.println(Math.max(dp[1][0], dp[1][1]));
    }

    private static void dfs(int node) {
        dp[node][0] = population[node];
        dp[node][1] = 0;

        for (int neighbor : graph[node]) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                dfs(neighbor);
                dp[node][0] += dp[neighbor][1];
                dp[node][1] += Math.max(dp[neighbor][0], dp[neighbor][1]);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void initializeVariables() throws IOException {
        int n;
        StringTokenizer st;
        n = Integer.parseInt(br.readLine().trim());
        population = new int[n + 1];
        graph = new ArrayList[n + 1];
        visited = new boolean[n + 1];
        dp = new int[n + 1][2];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        st = new StringTokenizer(br.readLine().trim());
        for (int i = 1; i <= n; i++) {
            population[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            graph[from].add(to);
            graph[to].add(from);
        }
    }
}

/*
7
1000 3000 4000 1000 2000 2000 7000
1 2
2 3
4 3
4 5
6 2
6 7

14000
*/