package com.example.algorithm.java.segmentTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SequenceAndQuery22 {
    private static class Node {
        Node left, right;
        long value;

        Node() {
            this.left = this.right = null;
            this.value = 0;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        long[] array = new long[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            array[i] = Long.parseLong(st.nextToken());
        }

        List<Node> versionRoots = new ArrayList<>();
        versionRoots.add(buildSegmentTree(array, 0, n - 1));

        int queryCount = Integer.parseInt(br.readLine());
        StringBuilder output = new StringBuilder();

        while (queryCount-- > 0) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());

            if (type == 1) {
                int index = Integer.parseInt(st.nextToken()) - 1;
                int newValue = Integer.parseInt(st.nextToken());
                Node newRoot = updateSegmentTree(versionRoots.get(versionRoots.size() - 1), 0, n - 1, index, newValue);
                versionRoots.add(newRoot);
            } else {
                int version = Integer.parseInt(st.nextToken());
                int left = Integer.parseInt(st.nextToken()) - 1;
                int right = Integer.parseInt(st.nextToken()) - 1;
                long result = querySegmentTree(versionRoots.get(version), 0, n - 1, left, right);
                output.append(result).append('\n');
            }
        }

        System.out.print(output);
        br.close();
    }

    private static Node buildSegmentTree(long[] array, int left, int right) {
        Node node = new Node();
        if (left == right) {
            node.value = array[left];
            return node;
        }
        int mid = (left + right) / 2;
        node.left = buildSegmentTree(array, left, mid);
        node.right = buildSegmentTree(array, mid + 1, right);
        node.value = node.left.value + node.right.value;
        return node;
    }

    private static Node updateSegmentTree(Node current, int left, int right, int target, int newValue) {
        if (target < left || right < target)
            return current;

        Node newNode = new Node();
        if (left == right) {
            newNode.value = newValue;
            return newNode;
        }

        int mid = (left + right) / 2;
        newNode.left = updateSegmentTree(current.left, left, mid, target, newValue);
        newNode.right = updateSegmentTree(current.right, mid + 1, right, target, newValue);
        newNode.value = newNode.left.value + newNode.right.value;
        return newNode;
    }

    private static long querySegmentTree(Node node, int left, int right, int queryLeft, int queryRight) {
        if (right < queryLeft || queryRight < left)
            return 0;
        if (queryLeft <= left && right <= queryRight)
            return node.value;

        int mid = (left + right) / 2;
        return querySegmentTree(node.left, left, mid, queryLeft, queryRight)
                + querySegmentTree(node.right, mid + 1, right, queryLeft, queryRight);
    }
}

/*
5
1 2 3 4 5
7        
1 2 5
2 0 1 3
2 1 1 3
1 4 2
2 0 2 5
2 1 2 5
2 2 2 5

6
9
14
17
15
*/