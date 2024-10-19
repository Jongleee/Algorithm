package com.example.algorithm.java.binaryLifting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TreeAndQuery2 {
    static int n;
    static int m;
    static int[] depth;
    static long[] dist;
    static int[][] parent;
    static ArrayList<Node>[] tree;

    static class Node {
        int target;
        int cost;

        public Node(int target, int cost) {
            this.target = target;
            this.cost = cost;
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        n = Integer.parseInt(br.readLine());
        depth = new int[n + 1];
        dist = new long[n + 1];
        parent = new int[n + 1][18];
        tree = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            tree[a].add(new Node(b, c));
            tree[b].add(new Node(a, c));
        }

        dfs(1, 1);

        for (int i = 1; i < 18; i++) {
            for (int j = 2; j <= n; j++) {
                parent[j][i] = parent[parent[j][i - 1]][i - 1];
            }
        }

        m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int lca = findLCA(u, v);

            if (a == 1) {
                sb.append(dist[u] + dist[v] - 2 * dist[lca]).append("\n");
            } else {
                int k = Integer.parseInt(st.nextToken());
                sb.append(findKthNode(u, v, lca, k)).append("\n");
            }
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }

    static void dfs(int node, int currentDepth) {
        depth[node] = currentDepth;
        for (Node next : tree[node]) {
            if (depth[next.target] == 0) {
                parent[next.target][0] = node;
                dist[next.target] = dist[node] + next.cost;
                dfs(next.target, currentDepth + 1);
            }
        }
    }

    static int findLCA(int a, int b) {
        if (depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }

        for (int i = 17; i >= 0; i--) {
            if ((1 << i) <= depth[a] - depth[b]) {
                a = parent[a][i];
            }
        }

        if (a == b)
            return a;

        for (int i = 17; i >= 0; i--) {
            if (parent[a][i] != parent[b][i]) {
                a = parent[a][i];
                b = parent[b][i];
            }
        }

        return parent[a][0];
    }

    static int findKthNode(int u, int v, int lca, int k) {
        int uDist = depth[u] - depth[lca] + 1;
        if (uDist >= k) {
            return findAncestor(u, k - 1);
        } else {
            return findAncestor(v, depth[v] - depth[lca] - (k - uDist));
        }
    }

    static int findAncestor(int node, int steps) {
        for (int i = 0; steps > 0; i++) {
            if ((steps & 1) != 0) {
                node = parent[node][i];
            }
            steps >>= 1;
        }
        return node;
    }
}

/*
6
1 2 1
2 4 1
2 5 2
1 3 1
3 6 2
2
1 4 6
2 4 6 4

5
3
*/