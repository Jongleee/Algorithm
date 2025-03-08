package com.example.algorithm.java.segmentTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class AmazingStudent {
    static class SegmentTree {
        private final int[] tree;
        private final int size;

        public SegmentTree(int n) {
            size = n;
            tree = new int[2 * size];
            Arrays.fill(tree, Integer.MAX_VALUE);
        }

        public void update(int index, int value) {
            index += size - 1;
            tree[index] = value;

            while (index > 1) {
                index /= 2;
                tree[index] = Math.min(tree[2 * index], tree[2 * index + 1]);
            }
        }

        public int query(int left, int right) {
            left += size - 1;
            right += size - 1;
            int minValue = Integer.MAX_VALUE;

            while (left <= right) {
                if (left % 2 == 1)
                    minValue = Math.min(minValue, tree[left++]);
                if (right % 2 == 0)
                    minValue = Math.min(minValue, tree[right--]);
                left /= 2;
                right /= 2;
            }

            return minValue;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int[] firstRank = new int[n + 1];
        int[] secondRank = new int[n + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            firstRank[Integer.parseInt(st.nextToken())] = i;
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            secondRank[Integer.parseInt(st.nextToken())] = i;
        }

        int result = processRanks(br, n, firstRank, secondRank);

        bw.write(Integer.toString(result));
        bw.flush();
        bw.close();
    }

    private static int processRanks(BufferedReader br, int n, int[] firstRank, int[] secondRank) throws IOException {
        int segmentTreeSize = initializeSegmentTreeSize(n);
        SegmentTree segmentTree = new SegmentTree(segmentTreeSize);

        int result = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int value = Integer.parseInt(st.nextToken());
            int index = secondRank[value];

            if (segmentTree.query(1, index - 1) > firstRank[value]) {
                result++;
            }
            segmentTree.update(index, firstRank[value]);
        }
        return result;
    }

    private static int initializeSegmentTreeSize(int n) {
        int size = 1;
        while (size < n) {
            size *= 2;
        }
        return size;
    }
}

/*
10
2 5 3 8 10 7 1 6 9 4
1 2 3 4 5 6 7 8 9 10
3 8 7 10 5 4 1 2 6 9

4
*/