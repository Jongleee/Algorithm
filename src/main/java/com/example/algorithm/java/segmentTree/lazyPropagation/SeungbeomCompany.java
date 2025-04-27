package com.example.algorithm.java.segmentTree.lazyPropagation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SeungbeomCompany {
    private static int n, m, indexCounter = 0, size;
    private static int[] newIndex, rangeEnd;
    private static long[] tree, lazy;
    private static List<List<Integer>> childList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        initializeTree();
        readTreeStructure(br);
        prepareSegmentTree();
        processQueries(br, sb);

        bw.write(sb.toString());
        bw.flush();
    }

    private static void initializeTree() {
        childList = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) {
            childList.add(new ArrayList<>());
        }
    }

    private static void readTreeStructure(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        st.nextToken();
        for (int i = 2; i <= n; i++) {
            int parent = Integer.parseInt(st.nextToken());
            childList.get(parent).add(i);
        }
    }

    private static void prepareSegmentTree() {
        newIndex = new int[n + 1];
        rangeEnd = new int[n + 1];
        assignRange(1);

        size = 1;
        while (size < n) {
            size <<= 1;
        }
        tree = new long[size << 1];
        lazy = new long[size << 1];
    }

    private static int assignRange(int node) {
        newIndex[node] = ++indexCounter;
        rangeEnd[node] = indexCounter;
        for (int next : childList.get(node)) {
            rangeEnd[node] = Math.max(rangeEnd[node], assignRange(next));
        }
        return rangeEnd[node];
    }

    private static void processQueries(BufferedReader br, StringBuilder sb) throws IOException {
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int queryType = Integer.parseInt(st.nextToken());
            int node = Integer.parseInt(st.nextToken());

            if (queryType == 1) {
                int value = Integer.parseInt(st.nextToken());
                update(1, 1, size, newIndex[node], rangeEnd[node], value);
            } else {
                sb.append(query(1, 1, size, newIndex[node])).append("\n");
            }
        }
    }

    private static void propagate(int node, int start, int end) {
        if (lazy[node] != 0) {
            tree[node] += (end - start + 1) * lazy[node];
            if (start != end) {
                lazy[node << 1] += lazy[node];
                lazy[node << 1 | 1] += lazy[node];
            }
            lazy[node] = 0;
        }
    }

    private static void update(int node, int start, int end, int left, int right, int diff) {
        propagate(node, start, end);
        if (right < start || end < left) {
            return;
        }
        if (left <= start && end <= right) {
            lazy[node] += diff;
            propagate(node, start, end);
            return;
        }
        int mid = (start + end) >>> 1;
        update(node << 1, start, mid, left, right, diff);
        update(node << 1 | 1, mid + 1, end, left, right, diff);
    }

    private static long query(int node, int start, int end, int idx) {
        propagate(node, start, end);
        if (start == end) {
            return tree[node];
        }
        int mid = (start + end) >>> 1;
        if (idx <= mid) {
            return query(node << 1, start, mid, idx);
        } else {
            return query(node << 1 | 1, mid + 1, end, idx);
        }
    }
}

/*
5 7
-1 4 1 1 1
1 3 10    
2 3
1 1 -7
2 2
1 4 10
2 2
2 1

10
-7
3
-7
*/