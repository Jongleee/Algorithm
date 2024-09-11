package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class LCA2 {
    static int n;
    static List<Integer>[] adj;
    static int[] depth;
    static int[][] dp;
    static int maxLog;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder result = new StringBuilder();

        n = Integer.parseInt(br.readLine());

        adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adj[a].add(b);
            adj[b].add(a);
        }

        maxLog = (int) (Math.log(n) / Math.log(2)) + 1;
        dp = new int[n + 1][maxLog];
        depth = new int[n + 1];

        dfs(1, 0);

        for (int j = 1; j < maxLog; j++) {
            for (int i = 1; i <= n; i++) {
                dp[i][j] = dp[dp[i][j - 1]][j - 1];
            }
        }

        int queries = Integer.parseInt(br.readLine());
        for (int i = 0; i < queries; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            result.append(lca(a, b)).append("\n");
        }

        bw.write(result.toString());
        bw.flush();
    }

    private static void dfs(int node, int parent) {
        depth[node] = depth[parent] + 1;
        dp[node][0] = parent;

        for (int next : adj[node]) {
            if (next != parent) {
                dfs(next, node);
            }
        }
    }

    private static int lca(int a, int b) {
        if (depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }

        int diff = depth[a] - depth[b];
        for (int i = 0; i < maxLog; i++) {
            if ((diff & (1 << i)) > 0) {
                a = dp[a][i];
            }
        }

        if (a == b) {
            return a;
        }

        for (int i = maxLog - 1; i >= 0; i--) {
            if (dp[a][i] != dp[b][i]) {
                a = dp[a][i];
                b = dp[b][i];
            }
        }

        return dp[a][0];
    }
}

/*
15
1 2
1 3
2 4
3 7
6 2
3 8
4 9
2 5
5 11
7 13
10 4
11 15
12 5
14 7
6
6 11
10 9
2 6
7 6
8 13
8 15

2
4
2
1
3
1
*/