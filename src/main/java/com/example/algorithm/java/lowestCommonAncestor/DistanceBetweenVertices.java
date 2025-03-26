package com.example.algorithm.java.lowestCommonAncestor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class DistanceBetweenVertices {
    static class Node {
        int to, cost;

        public Node(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        List<Node>[] tree = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++)
            tree[i] = new ArrayList<>();

        for (int i = 1; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            tree[a].add(new Node(b, cost));
            tree[b].add(new Node(a, cost));
        }

        int[][] parents = new int[17][n + 1];
        int[] depth = new int[n + 1];
        int[] costs = new int[n + 1];
        Arrays.fill(depth, -1);

        initializeTree(tree, parents, depth, costs);
        buildSparseTable(parents, n);

        int m = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (m-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            sb.append(calculateDistance(a, b, parents, depth, costs)).append("\n");
        }
        System.out.print(sb);
    }

    private static void initializeTree(List<Node>[] tree, int[][] parents, int[] depth, int[] costs) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        depth[1] = 0;
        parents[0][1] = 1;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (Node next : tree[node]) {
                if (depth[next.to] == -1) {
                    depth[next.to] = depth[node] + 1;
                    costs[next.to] = costs[node] + next.cost;
                    parents[0][next.to] = node;
                    queue.add(next.to);
                }
            }
        }
    }

    private static void buildSparseTable(int[][] parents, int n) {
        for (int i = 1; i < 17; i++) {
            for (int j = 1; j <= n; j++) {
                parents[i][j] = parents[i - 1][parents[i - 1][j]];
            }
        }
    }

    private static int findLCA(int a, int b, int[][] parents, int[] depth) {
        if (depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }
        for (int i = 16; i >= 0; i--) {
            if (depth[parents[i][a]] >= depth[b]) {
                a = parents[i][a];
            }
        }
        if (a == b)
            return a;
        for (int i = 16; i >= 0; i--) {
            if (parents[i][a] != parents[i][b]) {
                a = parents[i][a];
                b = parents[i][b];
            }
        }
        return parents[0][a];
    }

    private static int calculateDistance(int a, int b, int[][] parents, int[] depth, int[] costs) {
        int lca = findLCA(a, b, parents, depth);
        return costs[a] + costs[b] - 2 * costs[lca];
    }
}

/*
7
1 6 13
6 3 9 
3 5 7
4 1 3
2 4 20
4 7 2
3
1 6
1 4
2 6

13
3
36
*/