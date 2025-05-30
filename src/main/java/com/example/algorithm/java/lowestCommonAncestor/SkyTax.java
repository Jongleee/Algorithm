package com.example.algorithm.java.lowestCommonAncestor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SkyTax {
    private static final int LOG = 17;
    private static final StringBuilder sb = new StringBuilder();

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= t; testCase++) {
            sb.append("Case #").append(testCase).append(":\n");
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            ArrayList<Integer>[] graph = new ArrayList[n + 1];
            for (int i = 1; i <= n; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int i = 1; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                graph[a].add(b);
                graph[b].add(a);
            }

            int[] size = new int[n + 1];
            int[] depth = new int[n + 1];
            int[][] parent = new int[LOG][n + 1];

            dfs(1, 0, 0, graph, size, depth, parent);
            buildSparseTable(n, parent);

            for (int i = 0; i < q; i++) {
                st = new StringTokenizer(br.readLine());
                int s = Integer.parseInt(st.nextToken());
                int u = Integer.parseInt(st.nextToken());

                if (s == 0) {
                    r = u;
                } else {
                    sb.append(processQuery(n, r, u, size, depth, parent)).append('\n');
                }
            }
        }

        System.out.print(sb);
    }

    private static void dfs(int current, int parentNode, int currentDepth,
            ArrayList<Integer>[] graph, int[] size, int[] depth, int[][] parent) {
        size[current] = 1;
        depth[current] = currentDepth;
        parent[0][current] = parentNode;

        for (int next : graph[current]) {
            if (next != parentNode) {
                dfs(next, current, currentDepth + 1, graph, size, depth, parent);
                size[current] += size[next];
            }
        }
    }

    private static void buildSparseTable(int n, int[][] parent) {
        for (int i = 1; i < LOG; i++) {
            for (int j = 1; j <= n; j++) {
                parent[i][j] = parent[i - 1][parent[i - 1][j]];
            }
        }
    }

    private static int lca(int a, int b, int[] depth, int[][] parent) {
        if (depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }

        int diff = depth[a] - depth[b];
        for (int i = 0; diff > 0; i++) {
            if ((diff & 1) == 1) {
                a = parent[i][a];
            }
            diff >>= 1;
        }

        if (a == b) {
            return a;
        }

        for (int i = LOG - 1; i >= 0; i--) {
            if (parent[i][a] != parent[i][b]) {
                a = parent[i][a];
                b = parent[i][b];
            }
        }

        return parent[0][a];
    }

    private static int processQuery(int n, int r, int u, int[] size, int[] depth, int[][] parent) {
        if (r == u) {
            return n;
        }

        int lcaNode = lca(r, u, depth, parent);
        if (lcaNode == u) {
            int diff = depth[r] - depth[u] - 1;
            int temp = r;
            for (int i = 0; diff > 0; i++) {
                if ((diff & 1) == 1) {
                    temp = parent[i][temp];
                }
                diff >>= 1;
            }
            return n - size[temp];
        } else {
            return size[u];
        }
    }
}

/*
2
5 5 1
1 5  
3 4
3 5
2 1
1 1
1 2
0 5
1 5
1 3
1 5 1
1 1
1 1
0 1
1 1
1 1

Case #1:
5
1
5
2
Case #2:
1
1
1
1
*/