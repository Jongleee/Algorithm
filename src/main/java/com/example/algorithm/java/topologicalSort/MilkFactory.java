package com.example.algorithm.java.topologicalSort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MilkFactory {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] outgoingEdges = new int[n];

        countEdges(br, n, outgoingEdges);
        int root = findRoot(outgoingEdges, n);

        System.out.println(root);
    }

    private static void countEdges(BufferedReader br, int n, int[] outgoingEdges) throws IOException {
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            st.nextToken();
            outgoingEdges[a - 1]++;
        }
    }

    private static int findRoot(int[] outgoingEdges, int n) {
        int root = 0;

        for (int i = 0; i < n; i++) {
            if (root != 0 && outgoingEdges[i] == 0) {
                return -1;
            }
            if (outgoingEdges[i] == 0) {
                root = i + 1;
            }
        }
        return root;
    }
}

/*
3
1 2
3 2

2
*/