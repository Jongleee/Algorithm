package com.example.algorithm.java.segmentTree.liChao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class StarTrek {
    static class Node {
        Node left, right;
        long start, end, mid;
        Line line;

        Node(long start, long end) {
            this.start = start;
            this.end = end;
            this.mid = (start + end) >> 1;
        }

        void createChildren() {
            left = new Node(start, mid);
            right = new Node(mid + 1, end);
            left.line = right.line = line;
        }
    }

    static class Line {
        long slope, intercept;

        Line(long slope, long intercept) {
            this.slope = slope;
            this.intercept = intercept;
        }

        long valueAt(long x) {
            return slope * x + intercept;
        }
    }

    static class LiChaoTree {
        private final long MAX = 1_000_000_000L;
        Node root;

        LiChaoTree() {
            root = new Node(0, MAX);
        }

        long query(Node node, long x) {
            if (node.start == node.end) {
                return node.line.valueAt(x);
            }

            long currentVal = node.line.valueAt(x);
            if (x <= node.mid) {
                if (node.left == null) {
                    return currentVal;
                }
                return Math.min(currentVal, query(node.left, x));
            } else {
                if (node.right == null) {
                    return currentVal;
                }
                return Math.min(currentVal, query(node.right, x));
            }
        }

        void update(Node node, Line newLine) {
            if (node.line == null) {
                node.line = newLine;
                return;
            }

            boolean leftBetter = node.line.valueAt(node.start) >= newLine.valueAt(node.start);
            boolean rightBetter = node.line.valueAt(node.end) >= newLine.valueAt(node.end);

            if (leftBetter && rightBetter) {
                node.line = newLine;
                return;
            }
            if (leftBetter == rightBetter) {
                return;
            }

            if (node.left == null) {
                node.createChildren();
            }

            update(node.left, newLine);
            update(node.right, newLine);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        long[] prefixDistance = new long[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i < n; i++) {
            prefixDistance[i] = prefixDistance[i - 1] + Long.parseLong(st.nextToken());
        }

        long[] prepTime = new long[n];
        long[] speed = new long[n];
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            prepTime[i] = Long.parseLong(st.nextToken());
            speed[i] = Long.parseLong(st.nextToken());
        }

        LiChaoTree liChaoTree = new LiChaoTree();
        liChaoTree.update(liChaoTree.root, new Line(speed[0], prepTime[0]));

        long answer = 0;
        for (int i = 1; i < n; i++) {
            answer = liChaoTree.query(liChaoTree.root, prefixDistance[i]);
            liChaoTree.update(liChaoTree.root,
                    new Line(speed[i], -speed[i] * prefixDistance[i] + prepTime[i] + answer));
        }

        System.out.println(answer);
    }
}

/*
4
10 10 10
0 5
10 3
5 2

115


5
5 10 4 8
3 6
8 3
4 8
15 4

107
*/