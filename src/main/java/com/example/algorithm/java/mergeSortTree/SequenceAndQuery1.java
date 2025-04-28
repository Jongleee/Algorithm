package com.example.algorithm.java.mergeSortTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SequenceAndQuery1 {
    private static int n;
    private static int[][] segmentTree;
    private static int[] array;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        array = new int[n + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            array[i] = Integer.parseInt(st.nextToken());
        }

        segmentTree = new int[getTreeSize(n)][];
        buildSegmentTree(1, n, 1);

        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        processQueries(br, m, sb);

        System.out.print(sb);
    }

    private static void buildSegmentTree(int left, int right, int node) {
        if (left == right) {
            segmentTree[node] = new int[] { array[left] };
            return;
        }
        int mid = (left + right) >>> 1;
        buildSegmentTree(left, mid, node << 1);
        buildSegmentTree(mid + 1, right, (node << 1) + 1);
        mergeSegments(left, right, node);
    }

    private static void mergeSegments(int left, int right, int node) {
        segmentTree[node] = new int[right - left + 1];
        int[] leftSegment = segmentTree[node << 1];
        int[] rightSegment = segmentTree[(node << 1) + 1];

        int l = 0, r = 0, idx = 0;
        while (l < leftSegment.length && r < rightSegment.length) {
            if (leftSegment[l] < rightSegment[r]) {
                segmentTree[node][idx++] = leftSegment[l++];
            } else {
                segmentTree[node][idx++] = rightSegment[r++];
            }
        }
        while (l < leftSegment.length) {
            segmentTree[node][idx++] = leftSegment[l++];
        }
        while (r < rightSegment.length) {
            segmentTree[node][idx++] = rightSegment[r++];
        }
    }

    private static void processQueries(BufferedReader br, int m, StringBuilder sb) throws IOException {
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(st.nextToken());
            int des = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            sb.append(querySegmentTree(1, n, 1, src, des, k)).append('\n');
        }
    }

    private static int querySegmentTree(int left, int right, int node, int src, int des, int k) {
        if (right < src || des < left) {
            return 0;
        }
        if (src <= left && right <= des) {
            int total = right - left + 1;
            return total - countLessOrEqual(segmentTree[node], k);
        }
        int mid = (left + right) >>> 1;
        return querySegmentTree(left, mid, node << 1, src, des, k)
                + querySegmentTree(mid + 1, right, (node << 1) + 1, src, des, k);
    }

    private static int countLessOrEqual(int[] arr, int k) {
        int left = 0;
        int right = arr.length - 1;
        int res = arr.length;

        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (arr[mid] > k) {
                res = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return res;
    }

    private static int getTreeSize(int n) {
        return Integer.highestOneBit(n - 1) << 3;
    }
}

/*
5
5 1 2 3 4
3        
2 4 1
4 4 4
1 5 2

2
0
3
*/