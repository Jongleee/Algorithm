package com.example.algorithm.java.divideAndConquer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Tree {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            int[] preOrder = new int[n];
            int[] inOrder = new int[n];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++)
                preOrder[i] = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++)
                inOrder[i] = Integer.parseInt(st.nextToken());

            buildPostOrder(preOrder, inOrder, 0, 0, n, sb);
            sb.append("\n");
        }

        System.out.print(sb);
    }

    private static void buildPostOrder(int[] preOrder, int[] inOrder, int rootIdx, int start, int end,
            StringBuilder sb) {
        if (start >= end)
            return;

        int root = preOrder[rootIdx];
        int rootPos = findRootIndex(inOrder, start, end, root);

        buildPostOrder(preOrder, inOrder, rootIdx + 1, start, rootPos, sb);
        buildPostOrder(preOrder, inOrder, rootIdx + 1 + rootPos - start, rootPos + 1, end, sb);

        sb.append(root).append(" ");
    }

    private static int findRootIndex(int[] inOrder, int start, int end, int root) {
        for (int i = start; i < end; i++) {
            if (inOrder[i] == root)
                return i;
        }
        return -1;
    }
}

/*
2
4
3 2 1 4
2 3 4 1
8
3 6 5 4 8 7 1 2
5 6 8 4 3 1 2 7

2 4 1 3 
5 8 4 6 2 1 7 3
*/