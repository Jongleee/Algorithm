package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class ShortestPaths {
    static class Pair {
        int node;
        long cost;
        int index;

        Pair(int node, long cost) {
            this(node, cost, -1);
        }

        Pair(int node, long cost, int index) {
            this.node = node;
            this.cost = cost;
            this.index = index;
        }
    }

    static class SegmentTree {
        int size, start;
        long[] tree;

        SegmentTree(int n) {
            size = n;
            start = 1;
            while (start < n)
                start <<= 1;
            tree = new long[start << 1];
            Arrays.fill(tree, Long.MAX_VALUE);
        }

        public void updateMin(int node, int queryLeft, int queryRight, int nodeLeft, int nodeRight, long value) {
            if (queryRight < nodeLeft || nodeRight < queryLeft)
                return;

            if (queryLeft <= nodeLeft && nodeRight <= queryRight) {
                tree[node] = Math.min(tree[node], value);
                return;
            }

            int mid = (nodeLeft + nodeRight) >> 1;
            updateMin(node << 1, queryLeft, queryRight, nodeLeft, mid, value);
            updateMin(node << 1 | 1, queryLeft, queryRight, mid + 1, nodeRight, value);
        }

        public void propagate() {
            for (int i = 1; i < start; i++) {
                tree[i << 1] = Math.min(tree[i << 1], tree[i]);
                tree[i << 1 | 1] = Math.min(tree[i << 1 | 1], tree[i]);
            }
        }

        public long getMin(int index) {
            long result = tree[start + index];
            return result == Long.MAX_VALUE ? -1 : result;
        }
    }

    public static void main(String[] args) throws IOException {
        final int MAX = 2200;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        List<List<Pair>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        int[] f1 = new int[m];
        int[] f2 = new int[m];
        int[] costs = new int[m];
        boolean[] luckyEdge = new boolean[m];
        boolean[][] luckyPath = new boolean[n + 1][n + 1];
        boolean[] isLucky = new boolean[MAX];
        List<Integer> luckyNodes = new ArrayList<>();
        int[] order = new int[MAX];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph.get(u).add(new Pair(v, cost, i));
            graph.get(v).add(new Pair(u, cost, i));

            f1[i] = u;
            f2[i] = v;
            costs[i] = cost;
        }

        st = new StringTokenizer(br.readLine());
        int k = Integer.parseInt(st.nextToken());
        for (int i = 0; i < k; i++) {
            int node = Integer.parseInt(st.nextToken());
            isLucky[node] = true;
            luckyNodes.add(node);
            order[node] = i + 1;
        }

        for (int i = 0; i + 1 < luckyNodes.size(); i++) {
            int u = luckyNodes.get(i);
            int v = luckyNodes.get(i + 1);
            luckyPath[u][v] = luckyPath[v][u] = true;
        }

        for (int i = 0; i < m; i++) {
            luckyEdge[i] = luckyPath[f1[i]][f2[i]];
        }

        long[] leftDistances = new long[MAX];
        int[] leftStable = dijkstraAndGetStable(a, graph, m, f1, f2, costs, luckyEdge, isLucky, leftDistances);

        long[] rightDistances = new long[MAX];
        int[] rightStable = dijkstraAndGetStable(b, graph, m, f1, f2, costs, luckyEdge, isLucky, rightDistances);

        SegmentTree segTree = new SegmentTree(n);

        for (int i = 0; i < m; i++) {
            if (luckyEdge[i])
                continue;

            int u = f1[i], v = f2[i];
            updateSegmentTree(u, v, costs[i], leftDistances, rightDistances, leftStable, rightStable, order, segTree);
            updateSegmentTree(v, u, costs[i], leftDistances, rightDistances, leftStable, rightStable, order, segTree);
        }

        segTree.propagate();
        for (int i = 1; i < luckyNodes.size(); i++) {
            bw.write(segTree.getMin(i) + "\n");
        }
        bw.flush();
    }

    private static void updateSegmentTree(int u, int v, int cost, long[] left, long[] right,
            int[] lStable, int[] rStable, int[] order, SegmentTree segTree) {
        long totalCost = left[u] + right[v] + cost;
        int l = order[lStable[u]];
        int r = order[rStable[v]];
        if (l + 1 <= r) {
            segTree.updateMin(1, l + 1, r, 1, segTree.start, totalCost);
        }
    }

    private static int[] dijkstraAndGetStable(int start, List<List<Pair>> graph, int m,
            int[] f1, int[] f2, int[] costs,
            boolean[] luckyEdge, boolean[] isLucky, long[] distance) {

        int[] prevEdge = new int[distance.length];
        Arrays.fill(distance, Long.MAX_VALUE);
        Arrays.fill(prevEdge, -1);

        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingLong(p -> p.cost));
        pq.offer(new Pair(start, 0));
        distance[start] = 0;

        while (!pq.isEmpty()) {
            Pair current = pq.poll();
            if (current.cost != distance[current.node])
                continue;

            for (Pair neighbor : graph.get(current.node)) {
                long newCost = current.cost + neighbor.cost;
                if (distance[neighbor.node] > newCost) {
                    distance[neighbor.node] = newCost;
                    prevEdge[neighbor.node] = neighbor.index;
                    pq.offer(new Pair(neighbor.node, newCost));
                } else if (distance[neighbor.node] == newCost && luckyEdge[neighbor.index]) {
                    prevEdge[neighbor.node] = neighbor.index;
                }
            }
        }

        List<List<Pair>> tree = new ArrayList<>();
        for (int i = 0; i < distance.length; i++)
            tree.add(new ArrayList<>());

        for (int idx : prevEdge) {
            if (idx == -1)
                continue;
            tree.get(f1[idx]).add(new Pair(f2[idx], costs[idx]));
            tree.get(f2[idx]).add(new Pair(f1[idx], costs[idx]));
        }

        int[] stable = new int[distance.length];
        dfsSetStable(start, -1, start, tree, isLucky, stable);
        return stable;
    }

    private static void dfsSetStable(int current, int parent, int stableNode,
            List<List<Pair>> tree, boolean[] isLucky, int[] stable) {
        if (isLucky[current])
            stableNode = current;
        stable[current] = stableNode;

        for (Pair neighbor : tree.get(current)) {
            if (neighbor.node != parent) {
                dfsSetStable(neighbor.node, current, stableNode, tree, isLucky, stable);
            }
        }
    }
}

/*
5 6 1 5
1 2 1
2 3 3
2 5 100
3 4 3
3 5 5
4 5 3
4 1 2 3 5

-1
101
10
*/