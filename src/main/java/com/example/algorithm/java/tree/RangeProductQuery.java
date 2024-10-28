package com.example.algorithm.java.tree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class RangeProductQuery {
    private static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int leafCnt = 1;
        while (leafCnt < n)
            leafCnt *= 2;
        long[] indexedTree = new long[leafCnt * 2];

        for (int i = 0; i < n; i++) {
            indexedTree[leafCnt + i] = Integer.parseInt(br.readLine());
        }

        initializeTree(indexedTree, leafCnt);

        for (int i = 0; i < m + k; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            if (type == 1) {
                updateTree(indexedTree, leafCnt + b - 1, c);
            } else {
                bw.write(query(indexedTree, leafCnt + b - 1, leafCnt + c - 1) + "\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }

    private static void initializeTree(long[] tree, int leafStart) {
        for (int i = leafStart - 1; i > 0; i--) {
            tree[i] = (tree[i * 2] * tree[i * 2 + 1]) % MOD;
        }
    }

    private static void updateTree(long[] tree, int idx, int val) {
        tree[idx] = val;
        while (idx > 1) {
            idx /= 2;
            tree[idx] = (tree[idx * 2] * tree[idx * 2 + 1]) % MOD;
        }
    }

    private static long query(long[] tree, int start, int end) {
        long result = 1;
        while (start <= end) {
            if (start % 2 == 1)
                result = (result * tree[start++]) % MOD;
            if (end % 2 == 0)
                result = (result * tree[end--]) % MOD;
            start /= 2;
            end /= 2;
        }
        return result;
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

240
48


5 2 2
1
2
3
4
5
1 3 0
2 2 5
1 3 6
2 2 5

0
240
*/