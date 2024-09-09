package com.example.algorithm.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int[] postOrder;
    private static int[] inOrderIndex;
    private static StringBuilder result = new StringBuilder();

    public static void main(String[] args) throws IOException {
        int[] inOrder;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      
        int n = Integer.parseInt(br.readLine());

        inOrder = new int[n + 1];
        postOrder = new int[n + 1];
        inOrderIndex = new int[n + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            inOrder[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            postOrder[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= n; i++) {
            inOrderIndex[inOrder[i]] = i;
        }

        constructPreOrder(1, n, 1, n);
        System.out.println(result.toString());
    }

    private static void constructPreOrder(int inOrderStart, int inOrderEnd, int postOrderStart, int postOrderEnd) {
        if (inOrderStart > inOrderEnd || postOrderStart > postOrderEnd) {
            return;
        }

        int root = postOrder[postOrderEnd];
        int rootIndexInOrder = inOrderIndex[root];
        result.append(root).append(" ");

        int leftTreeSize = rootIndexInOrder - inOrderStart;

        constructPreOrder(inOrderStart, rootIndexInOrder - 1, postOrderStart, postOrderStart + leftTreeSize - 1);
        constructPreOrder(rootIndexInOrder + 1, inOrderEnd, postOrderStart + leftTreeSize, postOrderEnd - 1);
    }
}

/*
3
1 2 3
1 3 2

2 1 3 
*/