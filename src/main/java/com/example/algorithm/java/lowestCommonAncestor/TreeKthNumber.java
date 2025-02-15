package com.example.algorithm.java.lowestCommonAncestor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class TreeKthNumber {
    private static final class Edge {
        int idx;
        Edge next;

        Edge(int idx, Edge next) {
            this.idx = idx;
            this.next = next;
        }
    }

    private static final class Node {
        int val;
        Node left, right;

        void init(int start, int end) {
            if (start == end)
                return;
            int mid = (start + end) >> 1;
            left = new Node();
            right = new Node();
            left.init(start, mid);
            right.init(mid + 1, end);
        }

        Node attach(int start, int end, int num) {
            Node node = new Node();
            if (start != end) {
                int mid = (start + end) >> 1;
                if (num <= mid) {
                    node.left = left.attach(start, mid, num);
                    node.right = right;
                } else {
                    node.left = left;
                    node.right = right.attach(mid + 1, end, num);
                }
            }
            node.val = val + 1;
            return node;
        }
    }

    private static int n, logN;
    private static int[] arr, depth, unique, parents;
    private static int[][] dp;
    private static Edge[] adj;
    private static Node[] trees;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        arr = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        compress();
        adj = new Edge[n + 1];
        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            adj[x] = new Edge(y, adj[x]);
            adj[y] = new Edge(x, adj[y]);
        }
        parents = new int[n + 1];
        logN = (int) Math.ceil(Math.log(n) / Math.log(2));
        dp = new int[logN + 1][n + 1];
        depth = new int[n + 1];
        dfs(1, 0);
        for (int i = 1; i <= logN; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i - 1][dp[i - 1][j]];
            }
        }
        trees = new Node[n + 1];
        trees[0] = new Node();
        trees[0].init(0, n - 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            sb.append(unique[query(
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()))]).append('\n');
        }
        System.out.print(sb);
    }

    private static void compress() {
        unique = new int[n];
        System.arraycopy(arr, 1, unique, 0, n);
        Arrays.sort(unique);
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(unique[i], i);
        }
        for (int i = 1; i <= n; i++) {
            arr[i] = map.get(arr[i]);
        }
    }

    private static void dfs(int curr, int parent) {
        dp[0][curr] = parent;
        depth[curr] = depth[parent] + 1;
        parents[curr] = parent;
        for (Edge child = adj[curr]; child != null; child = child.next) {
            if (child.idx != parent) {
                dfs(child.idx, curr);
            }
        }
    }

    private static int query(int x, int y, int k) {
        int lca = getLca(x, y);
        return query(getTree(x), getTree(y), getTree(lca), getTree(parents[lca]), 0, n - 1, k);
    }

    private static int getLca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }
        for (int diff = depth[u] - depth[v], i = 0; diff != 0; diff >>= 1, i++) {
            if ((diff & 1) == 1) {
                u = dp[i][u];
            }
        }
        if (u == v)
            return u;
        for (int i = logN; i >= 0; i--) {
            if (dp[i][u] != dp[i][v]) {
                u = dp[i][u];
                v = dp[i][v];
            }
        }
        return dp[0][u];
    }

    private static Node getTree(int idx) {
        if (trees[idx] != null)
            return trees[idx];
        trees[idx] = getTree(parents[idx]).attach(0, n - 1, arr[idx]);
        return trees[idx];
    }

    private static int query(Node x, Node y, Node lca, Node par, int start, int end, int k) {
        if (start == end)
            return start;
        int mid = (start + end) >> 1;
        int val = x.left.val + y.left.val - lca.left.val - par.left.val;
        return val >= k ? query(x.left, y.left, lca.left, par.left, start, mid, k)
                : query(x.right, y.right, lca.right, par.right, mid + 1, end, k - val);
    }
}

/*
3 2
1 2 3
1 2
3 1
2 3 1
1 3 2

1
3
*/