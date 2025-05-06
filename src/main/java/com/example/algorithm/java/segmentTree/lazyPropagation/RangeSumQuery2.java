package com.example.algorithm.java.segmentTree.lazyPropagation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class RangeSumQuery2 {
    static class SegmentTree {
        private final long[] tree;
        private final long[] lazy;
        private final int size;

        SegmentTree(long[] input) {
            int n = input.length;
            size = n;
            tree = new long[4 * n];
            lazy = new long[4 * n];
            build(input, 1, 0, n - 1);
        }

        private void build(long[] input, int node, int start, int end) {
            if (start == end) {
                tree[node] = input[start];
                return;
            }
            int mid = (start + end) / 2;
            build(input, node * 2, start, mid);
            build(input, node * 2 + 1, mid + 1, end);
            tree[node] = tree[node * 2] + tree[node * 2 + 1];
        }

        private void propagate(int node, int start, int end) {
            if (lazy[node] != 0) {
                tree[node] += (end - start + 1) * lazy[node];
                if (start < end) {
                    lazy[node * 2] += lazy[node];
                    lazy[node * 2 + 1] += lazy[node];
                }
                lazy[node] = 0;
            }
        }

        void update(int left, int right, long val) {
            update(1, 0, size - 1, left, right, val);
        }

        private void update(int node, int start, int end, int left, int right, long val) {
            propagate(node, start, end);
            if (end < left || start > right)
                return;
            if (left <= start && end <= right) {
                lazy[node] += val;
                propagate(node, start, end);
                return;
            }
            int mid = (start + end) / 2;
            update(node * 2, start, mid, left, right, val);
            update(node * 2 + 1, mid + 1, end, left, right, val);
            tree[node] = tree[node * 2] + tree[node * 2 + 1];
        }

        long query(int left, int right) {
            return query(1, 0, size - 1, left, right);
        }

        private long query(int node, int start, int end, int left, int right) {
            propagate(node, start, end);
            if (end < left || start > right)
                return 0;
            if (left <= start && end <= right)
                return tree[node];
            int mid = (start + end) / 2;
            return query(node * 2, start, mid, left, right) +
                    query(node * 2 + 1, mid + 1, end, left, right);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }

        SegmentTree segmentTree = new SegmentTree(arr);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < m + k; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            if (type == 1) {
                long d = Long.parseLong(st.nextToken());
                segmentTree.update(b, c, d);
            } else {
                sb.append(segmentTree.query(b, c)).append('\n');
            }
        }

        System.out.print(sb);
    }
}

/*
5 2 2
1
2
3
4
5
1 3 4 6
2 2 5
1 1 3 -2
2 2 5

26
22
*/