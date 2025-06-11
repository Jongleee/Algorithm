package com.example.algorithm.java.segmentTree.persistent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class XorQuery {
    private static final int MAX_X = 500000;
    private static final char ADD = '1';
    private static final char XOR = '2';
    private static final char DELETE = '3';
    private static final char SMALL = '4';
    private static final char NTH = '5';

    private static final class Node {
        int val;
        Node left;
        Node right;

        void init(int start, int end) {
            if (start == end)
                return;
            int mid = (start + end) >> 1;
            left = new Node();
            right = new Node();
            left.init(start, mid);
            right.init(mid + 1, end);
        }

        Node add(int start, int end, int num) {
            Node node = new Node();
            if (start != end) {
                int mid = (start + end) >> 1;
                if (num <= mid) {
                    node.left = left.add(start, mid, num);
                    node.right = right;
                } else {
                    node.left = left;
                    node.right = right.add(mid + 1, end, num);
                }
            }
            node.val = this.val + 1;
            return node;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int queryCount = Integer.parseInt(br.readLine());
        Node[] trees = new Node[queryCount + 1];
        trees[0] = new Node();

        int max = 1;
        while (max <= MAX_X)
            max <<= 1;
        max--;

        trees[0].init(0, max);

        int currentIndex = 0;
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < queryCount; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            char operation = st.nextToken().charAt(0);
            switch (operation) {
                case ADD: {
                    int num = Integer.parseInt(st.nextToken());
                    trees[currentIndex + 1] = trees[currentIndex].add(0, max, num);
                    currentIndex++;
                    break;
                }
                case XOR: {
                    int left = Integer.parseInt(st.nextToken()) - 1;
                    int right = Integer.parseInt(st.nextToken());
                    int num = Integer.parseInt(st.nextToken());
                    int result = xorQuery(trees[left], trees[right], 0, max, (max + 1) >> 1, num);
                    output.append(result).append('\n');
                    break;
                }
                case DELETE: {
                    int k = Integer.parseInt(st.nextToken());
                    currentIndex -= k;
                    break;
                }
                case SMALL: {
                    int left = Integer.parseInt(st.nextToken()) - 1;
                    int right = Integer.parseInt(st.nextToken());
                    int num = Integer.parseInt(st.nextToken());
                    int result = countSmaller(trees[left], trees[right], 0, max, num);
                    output.append(result).append('\n');
                    break;
                }
                case NTH: {
                    int left = Integer.parseInt(st.nextToken()) - 1;
                    int right = Integer.parseInt(st.nextToken());
                    int k = Integer.parseInt(st.nextToken());
                    int result = kthNumber(trees[left], trees[right], 0, max, k);
                    output.append(result).append('\n');
                    break;
                }
            }
        }
        System.out.print(output);
    }

    private static int xorQuery(Node leftNode, Node rightNode, int start, int end, int bit, int num) {
        if (start == end)
            return start;
        int mid = (start + end) >> 1;
        boolean goLeft = (rightNode.left.val - leftNode.left.val != 0 && (bit & num) != 0)
                || (rightNode.right.val - leftNode.right.val == 0);
        if (goLeft) {
            return xorQuery(leftNode.left, rightNode.left, start, mid, bit >> 1, num);
        }
        return xorQuery(leftNode.right, rightNode.right, mid + 1, end, bit >> 1, num);
    }

    private static int countSmaller(Node leftNode, Node rightNode, int start, int end, int num) {
        if (start > num)
            return 0;
        if (end <= num)
            return rightNode.val - leftNode.val;
        int mid = (start + end) >> 1;
        return countSmaller(leftNode.left, rightNode.left, start, mid, num)
                + countSmaller(leftNode.right, rightNode.right, mid + 1, end, num);
    }

    private static int kthNumber(Node leftNode, Node rightNode, int start, int end, int k) {
        if (start == end)
            return start;
        int mid = (start + end) >> 1;
        int leftCount = rightNode.left.val - leftNode.left.val;
        if (leftCount >= k) {
            return kthNumber(leftNode.left, rightNode.left, start, mid, k);
        }
        return kthNumber(leftNode.right, rightNode.right, mid + 1, end, k - leftCount);
    }
}

/*
16
1 1
1 2
2 1 1 1
2 2 2 1
2 1 2 3
4 1 1 1
4 2 2 1
4 1 2 3
5 1 1 1
5 2 2 1
5 1 2 2
3 1
3 1
1 1
1 2
3 2

1
2
1
1
0
2
1
2
2


10
1 8
5 1 1 1
1 2
2 2 2 7
2 2 2 7
1 1
4 2 2 2
2 1 2 3
4 1 3 5
1 6

8
2
2
1
8
2
*/