package com.example.algorithm.java.segmentTree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Bulldozer {
    private static final int MAX_N = 4002;
    private static final int SEG_SIZE = 1 << 13;
    private static final long INF = (long) 1e13;

    private static class SegmentTree {
        long[] maxTree = new long[SEG_SIZE];
        long[] minTree = new long[SEG_SIZE];
        long[] diffTree = new long[SEG_SIZE];
        int size = SEG_SIZE >> 1;

        void init() {
            Arrays.fill(maxTree, -INF);
            Arrays.fill(minTree, INF);
            Arrays.fill(diffTree, 0);
        }

        void update(int index, long value) {
            int k = index + size - 1;
            maxTree[k] = value;
            minTree[k] = value;
            while (k > 0) {
                k = (k - 1) >> 1;
                maxTree[k] = Math.max(maxTree[2 * k + 1], maxTree[2 * k + 2]);
                minTree[k] = Math.min(minTree[2 * k + 1], minTree[2 * k + 2]);
                diffTree[k] = Math.max(diffTree[2 * k + 1], diffTree[2 * k + 2]);
                diffTree[k] = Math.max(diffTree[k], maxTree[2 * k + 2] - minTree[2 * k + 1]);
            }
        }

        long getMaxDifference() {
            return diffTree[0];
        }
    }

    private static class Point {
        long x, y, weight;

        Point(long x, long y, long weight) {
            this.x = x;
            this.y = y;
            this.weight = weight;
        }
    }

    private static class SwapOperation implements Comparable<SwapOperation> {
        long dx, dy;
        int u, v;

        SwapOperation(long dx, long dy, int u, int v) {
            this.dx = dx;
            this.dy = dy;
            this.u = u;
            this.v = v;
        }

        boolean isSameDirection(SwapOperation other) {
            return this.dx * other.dy == this.dy * other.dx;
        }

        @Override
        public int compareTo(SwapOperation other) {
            long lhs = this.dx * other.dy;
            long rhs = this.dy * other.dx;
            if (lhs == rhs) {
                if (this.u != other.u)
                    return Integer.compare(other.u, this.u);
                return Integer.compare(other.v, this.v);
            }
            return -Long.compare(lhs, rhs);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        long[] x = new long[MAX_N];
        long[] y = new long[MAX_N];
        long[] w = new long[MAX_N];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            x[i] = Long.parseLong(st.nextToken());
            y[i] = Long.parseLong(st.nextToken());
            w[i] = Long.parseLong(st.nextToken());
        }

        List<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            points.add(new Point(x[i], y[i], w[i]));
        }
        points.sort(Comparator.comparingLong((Point p) -> p.x).thenComparingLong(p -> p.y));

        for (int i = 0; i < n; i++) {
            x[i] = points.get(i).x;
            y[i] = points.get(i).y;
            w[i] = points.get(i).weight;
        }

        List<SwapOperation> swaps = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (x[i] < x[j]) {
                    swaps.add(new SwapOperation(y[i] - y[j], x[j] - x[i], Math.min(i, j), Math.max(i, j)));
                }
            }
        }
        swaps.sort(null);

        long[] prefixSum = new long[MAX_N];
        prefixSum[0] = 0;
        for (int i = 1; i <= n; i++) {
            prefixSum[i] = prefixSum[i - 1] + w[i - 1];
        }

        int[] indices = new int[MAX_N];
        for (int i = 0; i < n; i++) {
            indices[i] = i + 1;
        }

        SegmentTree segmentTree = new SegmentTree();
        segmentTree.init();
        for (int i = 0; i <= n; i++) {
            segmentTree.update(i, prefixSum[i]);
        }

        long result = segmentTree.getMaxDifference();
        for (int i = 0; i < swaps.size(); i++) {
            int u = swaps.get(i).u;
            int v = swaps.get(i).v;

            prefixSum[indices[u]] += prefixSum[indices[u] + 1] - 2 * prefixSum[indices[u]] + prefixSum[indices[u] - 1];
            segmentTree.update(indices[u], prefixSum[indices[u]]);
            int temp = indices[u];
            indices[u] = indices[v];
            indices[v] = temp;

            if (i == swaps.size() - 1 || !swaps.get(i).isSameDirection(swaps.get(i + 1))) {
                result = Math.max(result, segmentTree.getMaxDifference());
            }
        }

        System.out.println(result);
    }
}

/*
6
0 0 6
1 0 -2
2 0 8
0 1 -2
1 1 5
2 1 -2

15


15
10 3 30
5 10 -17
4 -5 14
0 -3 -9
-2 3 17
6 9 -19
-9 -6 -14
-2 -3 10
-3 -3 30
8 1 -28
9 -9 -5
7 -5 -24
-8 -10 5
-7 2 20
10 -3 -13

107


2
0 0 -1
1 0 -1

0
*/