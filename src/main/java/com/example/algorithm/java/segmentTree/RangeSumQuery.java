package com.example.algorithm.java.segmentTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class RangeSumQuery {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int size = 1;
        while (size < n)
            size *= 2;
        long[] tree = new long[size * 2];

        for (int i = 0; i < n; i++) {
            tree[size + i] = Long.parseLong(br.readLine());
        }

        buildTree(tree, size);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m + k; i++) {
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());

            if (command == 1) {
                int index = Integer.parseInt(st.nextToken());
                long value = Long.parseLong(st.nextToken());
                updateTree(tree, size, index, value);
            } else {
                int left = Integer.parseInt(st.nextToken());
                int right = Integer.parseInt(st.nextToken());
                sb.append(queryTree(tree, size, left, right)).append('\n');
            }
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    private static void buildTree(long[] tree, int size) {
        for (int i = size - 1; i > 0; i--) {
            tree[i] = tree[i * 2] + tree[i * 2 + 1];
        }
    }

    private static void updateTree(long[] tree, int size, int index, long value) {
        int pos = size + index - 1;
        long diff = value - tree[pos];
        while (pos > 0) {
            tree[pos] += diff;
            pos /= 2;
        }
    }

    private static long queryTree(long[] tree, int size, int left, int right) {
        long sum = 0;
        int l = left + size - 1;
        int r = right + size - 1;

        while (l <= r) {
            if ((l & 1) == 1)
                sum += tree[l++];
            if ((r & 1) == 0)
                sum += tree[r--];
            l /= 2;
            r /= 2;
        }
        return sum;
    }
}

/*
5 2 2
1
2
3
4
5
1 3 6
2 2 5
1 5 2
2 3 5

17
12
*/