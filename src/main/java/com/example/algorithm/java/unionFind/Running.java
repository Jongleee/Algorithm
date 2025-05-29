package com.example.algorithm.java.unionFind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Running {
    private static final long MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int nodeCount = Integer.parseInt(st.nextToken());
        int edgeCount = Integer.parseInt(st.nextToken());

        int[] from = new int[edgeCount];
        int[] to = new int[edgeCount];

        for (int i = 0; i < edgeCount; i++) {
            st = new StringTokenizer(br.readLine());
            from[i] = Integer.parseInt(st.nextToken()) + 1;
            to[i] = Integer.parseInt(st.nextToken()) + 1;
        }

        long[] powerOfThree = computePowers(edgeCount);
        int[] parent = initializeParent(nodeCount);
        long answer = processEdges(from, to, parent, powerOfThree, nodeCount);

        System.out.println(answer);
    }

    private static long[] computePowers(int size) {
        long[] powers = new long[size + 1];
        powers[0] = 1;
        for (int i = 1; i <= size; i++) {
            powers[i] = (powers[i - 1] * 3) % MOD;
        }
        return powers;
    }

    private static int[] initializeParent(int size) {
        int[] parent = new int[size + 1];
        for (int i = 1; i <= size; i++) {
            parent[i] = i;
        }
        return parent;
    }

    private static long processEdges(int[] from, int[] to, int[] parent, long[] powers, int nodeCount) {
        long result = 0;
        for (int i = from.length - 1; i >= 0; i--) {
            int u = from[i];
            int v = to[i];

            int rootU = find(u, parent);
            int rootV = find(v, parent);

            if (rootU == rootV)
                continue;

            if (rootU > rootV) {
                int temp = rootU;
                rootU = rootV;
                rootV = temp;
            }

            if (rootU == 1 && rootV == nodeCount) {
                result = (result + powers[i]) % MOD;
            } else if (rootU == 1) {
                parent[rootV] = rootU;
            } else {
                parent[rootU] = rootV;
            }
        }
        return result;
    }

    private static int find(int x, int[] parent) {
        if (parent[x] == x)
            return x;
        return parent[x] = find(parent[x], parent);
    }
}

/*
6 9
1 3
1 2
2 3
0 1
4 5
3 5
0 2
1 4
4 3

39


5 0

0
*/