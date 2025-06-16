package com.example.algorithm.java.segmentTree.lazyPropagation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class RangeGcd {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] values = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            values[i] = Integer.parseInt(st.nextToken());
        }

        long[] lazy = new long[n << 2];
        long[] tree = new long[n << 2];
        int lazyEnd = n - 1;
        int treeEnd = n - 2;

        initializeLazy(lazy, values, 1, 0, lazyEnd);
        initializeTree(tree, values, 1, 0, treeEnd);

        int queryCount = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while (queryCount-- > 0) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            int left = Integer.parseInt(st.nextToken()) - 1;
            int right = Integer.parseInt(st.nextToken()) - 1;
            if (type == 0) {
                if (left == right) {
                    sb.append(queryLazy(lazy, 1, 0, lazyEnd, left)).append('\n');
                } else {
                    long val = queryLazy(lazy, 1, 0, lazyEnd, left);
                    long diffGcd = queryGcd(tree, 1, 0, treeEnd, left, right - 1);
                    sb.append(gcd(val, Math.abs(diffGcd))).append('\n');
                }
            } else {
                updateLazy(lazy, 1, 0, lazyEnd, left, right, type);
                if (left > 0) {
                    updateTree(tree, 1, 0, treeEnd, left - 1, -type);
                }
                if (right < lazyEnd) {
                    updateTree(tree, 1, 0, treeEnd, right, type);
                }
            }
        }
        System.out.print(sb);
    }

    private static void initializeLazy(long[] lazy, int[] values, int index, int start, int end) {
        if (start == end) {
            lazy[index] = values[start];
            return;
        }
        int mid = (start + end) >> 1;
        initializeLazy(lazy, values, index << 1, start, mid);
        initializeLazy(lazy, values, index << 1 | 1, mid + 1, end);
    }

    private static long initializeTree(long[] tree, int[] values, int index, int start, int end) {
        if (start == end) {
            return tree[index] = values[start] - values[start + 1];
        }
        int mid = (start + end) >> 1;
        long left = initializeTree(tree, values, index << 1, start, mid);
        long right = initializeTree(tree, values, index << 1 | 1, mid + 1, end);
        return tree[index] = gcd(left, right);
    }

    private static long queryLazy(long[] lazy, int index, int start, int end, int pos) {
        if (start == end)
            return lazy[index];
        if (lazy[index] != 0) {
            lazy[index << 1] += lazy[index];
            lazy[index << 1 | 1] += lazy[index];
            lazy[index] = 0;
        }
        int mid = (start + end) >> 1;
        if (pos <= mid)
            return queryLazy(lazy, index << 1, start, mid, pos);
        return queryLazy(lazy, index << 1 | 1, mid + 1, end, pos);
    }

    private static long queryGcd(long[] tree, int index, int start, int end, int left, int right) {
        if (left <= start && end <= right)
            return tree[index];
        int mid = (start + end) >> 1;
        if (right <= mid)
            return queryGcd(tree, index << 1, start, mid, left, right);
        if (left > mid)
            return queryGcd(tree, index << 1 | 1, mid + 1, end, left, right);
        return gcd(
                queryGcd(tree, index << 1, start, mid, left, right),
                queryGcd(tree, index << 1 | 1, mid + 1, end, left, right));
    }

    private static void updateLazy(long[] lazy, int index, int start, int end, int left, int right, int delta) {
        if (right < start || end < left)
            return;
        if (left <= start && end <= right) {
            lazy[index] += delta;
            return;
        }
        int mid = (start + end) >> 1;
        updateLazy(lazy, index << 1, start, mid, left, right, delta);
        updateLazy(lazy, index << 1 | 1, mid + 1, end, left, right, delta);
    }

    private static long updateTree(long[] tree, int index, int start, int end, int pos, int delta) {
        if (start == end)
            return tree[index] += delta;
        int mid = (start + end) >> 1;
        if (pos <= mid) {
            return tree[index] = gcd(updateTree(tree, index << 1, start, mid, pos, delta), tree[index << 1 | 1]);
        } else {
            return tree[index] = gcd(tree[index << 1], updateTree(tree, index << 1 | 1, mid + 1, end, pos, delta));
        }
    }

    private static long gcd(long a, long b) {
        while (b != 0) {
            long tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }
}

/*
4
6 3 38 49
5
0 1 3
9 2 2
0 1 2
6 3 3
0 3 4

1
6
1
*/