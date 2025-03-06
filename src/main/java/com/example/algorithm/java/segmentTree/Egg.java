package com.example.algorithm.java.segmentTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Egg {
    private static final int SIZE = 100000;
    private static final int INITIAL_TREE = SIZE + 1;

    private static class Point implements Comparable<Point> {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point o) {
            return this.x - o.x;
        }
    }

    private static class Node {
        int val, tree;
        Node left, right;

        Node(int tree) {
            this.tree = tree;
        }

        Node(Node node, int tree) {
            this.val = node.val;
            this.tree = tree;
            this.left = node.left;
            this.right = node.right;
        }

        static Node getInitialTree(int start, int end) {
            Node node = new Node(INITIAL_TREE);
            if (start == end)
                return node;
            int mid = (start + end) >>> 1;
            node.left = getInitialTree(start, mid);
            node.right = getInitialTree(mid + 1, end);
            return node;
        }

        Node insert(int start, int end, int idx, int tree) {
            Node node;
            if (this.tree == tree)
                node = this;
            else {
                node = new Node(this, tree);
            }
            if (start == end) {
                node.val++;
                return node;
            }
            int mid = (start + end) >>> 1;
            if (idx <= mid) {
                node.left = node.left.insert(start, mid, idx, tree);
            } else {
                node.right = node.right.insert(mid + 1, end, idx, tree);
            }
            node.val = node.left.val + node.right.val;
            return node;
        }

        static int query(Node l, Node r, int start, int end, int b, int t) {
            if (t < start || end < b)
                return 0;
            if (b <= start && end <= t)
                return r.val - l.val;
            int mid = (start + end) >>> 1;
            return query(l.left, r.left, start, mid, b, t) + query(l.right, r.right, mid + 1, end, b, t);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());

        Node[] trees = new Node[INITIAL_TREE + 1];
        trees[INITIAL_TREE] = Node.getInitialTree(0, SIZE);

        while (t-- > 0) {
            sb.append(processQueries(br, trees)).append('\n');
        }
        System.out.print(sb);
    }

    private static int processQueries(BufferedReader br, Node[] trees) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        PriorityQueue<Point> pq = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            pq.offer(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        Point point = pq.poll();
        for (int i = 0; i < INITIAL_TREE; i++) {
            trees[i] = trees[(i + INITIAL_TREE) % (INITIAL_TREE + 1)];
            while (point != null && point.x == i) {
                trees[i] = trees[i].insert(0, SIZE, point.y, i);
                point = pq.poll();
            }
        }

        int val = 0;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int l = (Integer.parseInt(st.nextToken()) + INITIAL_TREE) % (INITIAL_TREE + 1);
            int r = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            val += Node.query(trees[l], trees[r], 0, SIZE, b, t);
        }
        return val;
    }
}

/*
2
3 1
3 5
2 3
1 1
1 2 1 3
3 2
5 3
2 2
1 1
1 2 1 3
2 5 2 3

2
4
*/