package com.example.algorithm.java.segmentTree.lazyPropagation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class IAmHappy {
    static int length;
    static long[][] tree;
    static byte[][] lazy;
    static long[] treeValue;
    static final long MOD = 998244353;
    static char[] chars;
    static long[] power10;
    static long[] tempTree;
    static long tempTreeValue;
    static BufferedWriter bw;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        chars = br.readLine().toCharArray();
        length = chars.length;

        init();

        power10[0] = 1;
        for (int i = 0; i < length; i++) {
            power10[i + 1] = power10[i] * 10 % MOD;
        }

        build(0, length - 1, 1);

        int q = Integer.parseInt(br.readLine());
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            int left = Integer.parseInt(st.nextToken()) - 1;
            int right = Integer.parseInt(st.nextToken()) - 1;

            if (command == 1) {
                byte from = Byte.parseByte(st.nextToken());
                byte to = Byte.parseByte(st.nextToken());
                if (from != to) {
                    modify(0, length - 1, 1, left, right, from, to);
                }
            } else {
                long result = query(0, length - 1, 1, left, right);
                bw.write(result + "\n");
            }
        }
        bw.flush();
        bw.close();
    }

    static void init() {
        int max = 1 << 20;
        tree = new long[2 * max][10];
        treeValue = new long[2 * max];
        lazy = new byte[max][10];
        power10 = new long[length + 1];
        for (int i = 1; i < max; i++) {
            for (byte j = 1; j < 10; j++) {
                lazy[i][j] = j;
            }
        }
        tempTree = new long[10];
    }

    static long merge(long a, long b, int bLen) {
        return (power10[bLen] * a + b) % MOD;
    }

    static void build(int left, int right, int node) {
        if (left == right) {
            int digit = chars[left] - '0';
            tree[node][digit] = 1;
            treeValue[node] = digit;
            return;
        }
        int mid = (left + right) / 2;
        build(left, mid, node * 2);
        build(mid + 1, right, node * 2 + 1);
        for (int d = 0; d < 10; d++) {
            tree[node][d] = merge(tree[node * 2][d], tree[node * 2 + 1][d], right - mid);
        }
        treeValue[node] = (power10[right - mid] * treeValue[node * 2] + treeValue[node * 2 + 1]) % MOD;
    }

    static void pushDown(boolean isSingle, int node, int child) {
        if (!isSingle) {
            for (int d = 0; d < 10; d++) {
                lazy[child][d] = lazy[node][lazy[child][d]];
            }
        }
        Arrays.fill(tempTree, 0);
        tempTreeValue = 0;
        for (int d = 0; d < 10; d++) {
            if (tree[child][d] > 0) {
                int mappedDigit = lazy[node][d];
                tempTree[mappedDigit] += tree[child][d];
                tempTreeValue += tree[child][d] * mappedDigit;
            }
        }
        treeValue[child] = tempTreeValue % MOD;
        for (int d = 0; d < 10; d++) {
            tree[child][d] = tempTree[d] % MOD;
        }
    }

    static void modify(int left, int right, int node, int queryLeft, int queryRight, byte fromDigit, byte toDigit) {
        if (left == queryLeft && right == queryRight) {
            if (tree[node][fromDigit] > 0) {
                treeValue[node] = (treeValue[node] + tree[node][fromDigit] * (toDigit - fromDigit) + 10 * MOD) % MOD;
                tree[node][toDigit] = (tree[node][toDigit] + tree[node][fromDigit]) % MOD;
                tree[node][fromDigit] = 0;
            }
            if (left != right) {
                for (int d = 0; d < 10; d++) {
                    if (lazy[node][d] == fromDigit) {
                        lazy[node][d] = toDigit;
                    }
                }
            }
            return;
        }
        int mid = (left + right) / 2;
        int leftChild = node * 2;
        int rightChild = node * 2 + 1;

        pushDown(left == mid, node, leftChild);
        pushDown(mid + 1 == right, node, rightChild);

        if (queryRight <= mid) {
            modify(left, mid, leftChild, queryLeft, queryRight, fromDigit, toDigit);
        } else if (queryLeft > mid) {
            modify(mid + 1, right, rightChild, queryLeft, queryRight, fromDigit, toDigit);
        } else {
            modify(left, mid, leftChild, queryLeft, mid, fromDigit, toDigit);
            modify(mid + 1, right, rightChild, mid + 1, queryRight, fromDigit, toDigit);
        }

        treeValue[node] = (power10[right - mid] * treeValue[leftChild] + treeValue[rightChild]) % MOD;
        for (int d = 0; d < 10; d++) {
            lazy[node][d] = (byte) d;
            tree[node][d] = merge(tree[leftChild][d], tree[rightChild][d], right - mid);
        }
    }

    static long query(int left, int right, int node, int queryLeft, int queryRight) {
        if (left == queryLeft && right == queryRight) {
            return treeValue[node];
        }
        int mid = (left + right) / 2;
        int leftChild = node * 2;
        int rightChild = node * 2 + 1;

        pushDown(left == mid, node, leftChild);
        pushDown(mid + 1 == right, node, rightChild);

        long result;
        if (queryRight <= mid) {
            result = query(left, mid, leftChild, queryLeft, queryRight);
        } else if (queryLeft > mid) {
            result = query(mid + 1, right, rightChild, queryLeft, queryRight);
        } else {
            long leftResult = query(left, mid, leftChild, queryLeft, mid);
            long rightResult = query(mid + 1, right, rightChild, mid + 1, queryRight);
            result = (power10[queryRight - mid] * leftResult + rightResult) % MOD;
        }

        treeValue[node] = (power10[right - mid] * treeValue[leftChild] + treeValue[rightChild]) % MOD;
        for (int d = 0; d < 10; d++) {
            lazy[node][d] = (byte) d;
            tree[node][d] = merge(tree[leftChild][d], tree[rightChild][d], right - mid);
        }
        return result;
    }
}

/*
5886899678
7
2 6 9
1 2 9 8 5
1 1 3 5 0
2 1 6
1 1 10 0 2
1 5 8 8 1
2 1 10

9967
659
230110972
*/