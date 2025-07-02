package com.example.algorithm.java.segmentTree.persistent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class DistinctNumbersAndQuery2 {
    static class Node {
        int val;
        Node left, right;

        void init(int start, int end) {
            if (start == end)
                return;
            int mid = (start + end) >> 1;
            left = new Node();
            right = new Node();
            left.init(start, mid);
            right.init(mid + 1, end);
        }

        Node delete(int start, int end, int idx) {
            Node node = new Node();
            if (start != end) {
                int mid = (start + end) >> 1;
                if (idx <= mid) {
                    node.left = left.delete(start, mid, idx);
                    node.right = right;
                } else {
                    node.left = left;
                    node.right = right.delete(mid + 1, end, idx);
                }
            }
            node.val = val - 1;
            return node;
        }

        Node add(int start, int end, int idx) {
            Node node = new Node();
            if (start != end) {
                int mid = (start + end) >> 1;
                if (idx <= mid) {
                    node.left = left.add(start, mid, idx);
                    node.right = right;
                } else {
                    node.left = left;
                    node.right = right.add(mid + 1, end, idx);
                }
            }
            node.val = val + 1;
            return node;
        }

        Node update(int start, int end, int delIdx, int addIdx) {
            if (delIdx == -1)
                return add(start, end, addIdx);
            Node node = new Node();
            if (start != end) {
                int mid = (start + end) >> 1;
                if (addIdx <= mid && delIdx <= mid) {
                    node.left = left.update(start, mid, delIdx, addIdx);
                    node.right = right;
                } else if (addIdx > mid && delIdx > mid) {
                    node.left = left;
                    node.right = right.update(mid + 1, end, delIdx, addIdx);
                } else {
                    node.left = left.delete(start, mid, delIdx);
                    node.right = right.add(mid + 1, end, addIdx);
                }
            }
            node.val = val;
            return node;
        }

        int query(int start, int end, int l, int r) {
            if (l > end || r < start)
                return 0;
            if (l <= start && r >= end)
                return val;
            int mid = (start + end) >> 1;
            return left.query(start, mid, l, r) + right.query(mid + 1, end, l, r);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int[] prev = getPrev(arr);
        int end = n - 1;
        Node[] roots = new Node[n + 1];
        roots[0] = new Node();
        roots[0].init(0, end);

        for (int i = 0; i < n; i++) {
            roots[i + 1] = roots[i].update(0, end, prev[i], i);
        }

        int q = Integer.parseInt(br.readLine());
        int res = 0;
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            int l = res + x;

            if (l > r || r > n) {
                res = 0;
            } else {
                res = roots[r].query(0, end, l - 1, r - 1);
            }
            sb.append(res).append('\n');
        }

        bw.write(sb.toString());
        bw.flush();
    }

    private static int[] getPrev(int[] arr) {
        int n = arr.length;
        int[] prev = new int[n];
        int[] sorted = Arrays.stream(arr).distinct().sorted().toArray();
        int[] lastIndex = new int[sorted.length];
        Arrays.fill(lastIndex, -1);

        for (int i = 0; i < n; i++) {
            int idx = Arrays.binarySearch(sorted, arr[i]);
            prev[i] = lastIndex[idx];
            lastIndex[idx] = i;
        }
        return prev;
    }
}

/*
10
1 3 2 1 3 1 3 2 1 3 
10
8 9
2 7
4 8
1 6
1 7
-1 10
0 8
-2 10
1 7
-1 7

2
2
3
2
3
3
3
3
2
3
*/