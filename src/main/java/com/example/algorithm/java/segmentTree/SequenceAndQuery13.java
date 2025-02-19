package com.example.algorithm.java.segmentTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SequenceAndQuery13 {
    private static class SegmentTree {
        private static final int MOD = 1_000_000_007;
        private final int size;
        private final long[] tree, lazyAdd, lazyMul, lazySet;
        private final boolean[] isSet;

        public SegmentTree(int n, int[] arr) {
            size = n;
            tree = new long[4 * n];
            lazyAdd = new long[4 * n];
            lazyMul = new long[4 * n];
            lazySet = new long[4 * n];
            isSet = new boolean[4 * n];
            Arrays.fill(lazyMul, 1);
            build(1, 1, n, arr);
        }

        private void build(int node, int start, int end, int[] arr) {
            if (start == end) {
                tree[node] = arr[start];
                return;
            }
            int mid = (start + end) / 2;
            build(node * 2, start, mid, arr);
            build(node * 2 + 1, mid + 1, end, arr);
            tree[node] = (tree[node * 2] + tree[node * 2 + 1]) % MOD;
        }

        private void applyLazyPropagation(int node, int start, int end) {
            if (isSet[node]) {
                tree[node] = lazySet[node] * (end - start + 1) % MOD;
                if (start != end) {
                    propagateLazy(node, lazySet[node], 0, 1, true);
                }
                isSet[node] = false;
            }
            if (lazyMul[node] != 1) {
                tree[node] = tree[node] * lazyMul[node] % MOD;
                if (start != end) {
                    propagateLazy(node, 0, 0, lazyMul[node], false);
                }
                lazyMul[node] = 1;
            }
            if (lazyAdd[node] != 0) {
                tree[node] = (tree[node] + lazyAdd[node] * (end - start + 1) % MOD) % MOD;
                if (start != end) {
                    propagateLazy(node, 0, lazyAdd[node], 1, false);
                }
                lazyAdd[node] = 0;
            }
        }

        private void propagateLazy(int node, long setVal, long addVal, long mulVal, boolean setFlag) {
            int left = node * 2, right = node * 2 + 1;
            if (setFlag) {
                lazySet[left] = lazySet[right] = setVal;
                isSet[left] = isSet[right] = true;
                lazyAdd[left] = lazyAdd[right] = 0;
                lazyMul[left] = lazyMul[right] = 1;
            } else {
                lazyAdd[left] = (lazyAdd[left] * mulVal + addVal) % MOD;
                lazyAdd[right] = (lazyAdd[right] * mulVal + addVal) % MOD;
                lazyMul[left] = lazyMul[left] * mulVal % MOD;
                lazyMul[right] = lazyMul[right] * mulVal % MOD;
            }
        }

        public void update(int l, int r, int type, int value) {
            updateRange(1, 1, size, l, r, type, value);
        }

        private void updateRange(int node, int start, int end, int l, int r, int type, int value) {
            applyLazyPropagation(node, start, end);
            if (end < l || start > r)
                return;
            if (l <= start && end <= r) {
                if (type == 1)
                    lazyAdd[node] = (lazyAdd[node] + value) % MOD;
                else if (type == 2)
                    lazyMul[node] = lazyMul[node] * value % MOD;
                else {
                    lazySet[node] = value;
                    isSet[node] = true;
                    lazyAdd[node] = 0;
                    lazyMul[node] = 1;
                }
                applyLazyPropagation(node, start, end);
                return;
            }
            int mid = (start + end) / 2;
            updateRange(node * 2, start, mid, l, r, type, value);
            updateRange(node * 2 + 1, mid + 1, end, l, r, type, value);
            tree[node] = (tree[node * 2] + tree[node * 2 + 1]) % MOD;
        }

        public long query(int l, int r) {
            return queryRange(1, 1, size, l, r);
        }

        private long queryRange(int node, int start, int end, int l, int r) {
            applyLazyPropagation(node, start, end);
            if (end < l || start > r)
                return 0;
            if (l <= start && end <= r)
                return tree[node];
            int mid = (start + end) / 2;
            return (queryRange(node * 2, start, mid, l, r) + queryRange(node * 2 + 1, mid + 1, end, l, r)) % MOD;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(br.readLine());

        int[] arr = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        SegmentTree segTree = new SegmentTree(n, arr);
        int m = Integer.parseInt(br.readLine());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            if (type == 4) {
                sb.append(segTree.query(left, right)).append('\n');
            } else {
                int value = Integer.parseInt(st.nextToken());
                segTree.update(left, right, type, value);
            }
        }
        System.out.print(sb);
    }
}

/*
4
1 2 3 4
4
4 1 4
1 1 3 10
2 2 4 2
4 1 4

10
69
*/