package com.example.algorithm.java.segmentTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MinimumMaximum {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] arr = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        int size = 1;
        while (size < n)
            size *= 2;
        size *= 2;

        int[] minTree = new int[size];
        int[] maxTree = new int[size];

        buildTree(arr, minTree, maxTree, 1, 1, n);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int minVal = queryMin(minTree, 1, 1, n, a, b);
            int maxVal = queryMax(maxTree, 1, 1, n, a, b);
            sb.append(minVal).append(" ").append(maxVal).append("\n");
        }
        System.out.print(sb);
    }

    private static void buildTree(int[] arr, int[] minTree, int[] maxTree, int node, int start, int end) {
        if (start == end) {
            minTree[node] = arr[start];
            maxTree[node] = arr[start];
        } else {
            int mid = (start + end) / 2;
            buildTree(arr, minTree, maxTree, node * 2, start, mid);
            buildTree(arr, minTree, maxTree, node * 2 + 1, mid + 1, end);
            minTree[node] = Math.min(minTree[node * 2], minTree[node * 2 + 1]);
            maxTree[node] = Math.max(maxTree[node * 2], maxTree[node * 2 + 1]);
        }
    }

    private static int queryMin(int[] minTree, int node, int start, int end, int left, int right) {
        if (right < start || end < left)
            return Integer.MAX_VALUE;
        if (left <= start && end <= right)
            return minTree[node];
        int mid = (start + end) / 2;
        return Math.min(queryMin(minTree, node * 2, start, mid, left, right),
                queryMin(minTree, node * 2 + 1, mid + 1, end, left, right));
    }

    private static int queryMax(int[] maxTree, int node, int start, int end, int left, int right) {
        if (right < start || end < left)
            return Integer.MIN_VALUE;
        if (left <= start && end <= right)
            return maxTree[node];
        int mid = (start + end) / 2;
        return Math.max(queryMax(maxTree, node * 2, start, mid, left, right),
                queryMax(maxTree, node * 2 + 1, mid + 1, end, left, right));
    }
}

/*
10 4
75
30
100
38
50
51
52
20
81
5
1 10
3 5
6 9
8 10

5 100
38 100
20 81
5 81
*/