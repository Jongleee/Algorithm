package com.example.algorithm.java.divideAndConquerOptimization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.StringTokenizer;

public class Vacation {
    static class SegmentTree {
        long[] tree;
        int[] count;
        int size;
        List<Integer> values;

        SegmentTree(int n, List<Integer> values) {
            this.size = n;
            this.values = values;
            tree = new long[n * 4];
            count = new int[n * 4];
        }

        void init() {
            Arrays.fill(tree, 0);
            Arrays.fill(count, 0);
        }

        void update(int node, int start, int end, int index, int value) {
            if (index < start || index > end)
                return;
            count[node] += value;
            tree[node] += (long) value * values.get(index);
            if (start == end)
                return;
            int mid = (start + end) >> 1;
            update(node * 2, start, mid, index, value);
            update(node * 2 + 1, mid + 1, end, index, value);
        }

        long queryMaxKSum(int node, int start, int end, int k) {
            if (k == 0)
                return 0;
            if (start == end)
                return (long) Math.min(count[node], k) * values.get(start);
            int mid = (start + end) >> 1;
            if (count[node * 2 + 1] >= k) {
                return queryMaxKSum(node * 2 + 1, mid + 1, end, k);
            } else {
                return tree[node * 2 + 1] + queryMaxKSum(node * 2, start, mid, k - count[node * 2 + 1]);
            }
        }
    }

    static int n, startIndex, d;
    static int[] a;
    static boolean[] selected;
    static List<Integer> compressed;
    static SegmentTree segmentTree;
    static long answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        startIndex = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());

        a = new int[n];
        compressed = new ArrayList<>();

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
            compressed.add(a[i]);
        }

        Collections.sort(compressed);
        compressed = new ArrayList<>(new LinkedHashSet<>(compressed));
        for (int i = 0; i < n; i++) {
            a[i] = Collections.binarySearch(compressed, a[i]);
        }

        segmentTree = new SegmentTree(compressed.size(), compressed);
        selected = new boolean[n];

        solve();

        for (int i = 0, j = n - 1; i < j; i++, j--) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        startIndex = n - 1 - startIndex;

        solve();

        System.out.println(answer);
    }

    static void solve() {
        segmentTree.init();
        Arrays.fill(selected, false);
        int left = Math.max(0, startIndex - d);
        divideAndConquer(left, startIndex, startIndex, n - 1);
    }

    static void update(int index, int value) {
        if ((value == 1) == selected[index])
            return;
        selected[index] ^= true;
        segmentTree.update(1, 0, compressed.size() - 1, a[index], value);
    }

    static long query(int k) {
        return segmentTree.queryMaxKSum(1, 0, compressed.size() - 1, k);
    }

    static void divideAndConquer(int s, int e, int kMin, int kMax) {
        if (s > e)
            return;
        int mid = (s + e) >> 1;
        for (int i = mid; i <= e; i++)
            update(i, 1);

        int kLimit = Math.min(kMax, 2 * mid + d - startIndex);
        long localMax = 0;
        int bestIndex = kMin;

        for (int i = kMin; i <= kLimit; i++) {
            update(i, 1);
            long temp = query(d - (startIndex - mid) - (i - mid));
            if (temp > localMax) {
                localMax = temp;
                bestIndex = i;
            }
        }

        answer = Math.max(answer, localMax);

        for (int i = kLimit; i >= bestIndex; i--)
            update(i, -1);

        if (mid < e) {
            int half = (mid + 1 + e) >> 1;
            for (int i = mid; i <= half; i++)
                update(i, -1);
            divideAndConquer(mid + 1, e, bestIndex, kMax);
        }

        for (int i = kMax; i >= kMin; i--)
            update(i, -1);

        if (s < mid) {
            int half = (s + mid - 1) >> 1;
            for (int i = mid; i < Math.min(kMin, e + 1); i++)
                update(i, 1);
            divideAndConquer(s, mid - 1, kMin, bestIndex);
            for (int i = half; i <= e; i++)
                update(i, -1);
        }

        for (int i = mid; i <= e; i++)
            update(i, -1);
        for (int i = kMin; i <= bestIndex; i++)
            update(i, -1);
    }
}

/*
5 2 7
10 2 20 30 1

60
*/