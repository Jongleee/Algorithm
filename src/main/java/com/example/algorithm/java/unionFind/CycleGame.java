package com.example.algorithm.java.unionFind;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CycleGame {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int nodeCount = Integer.parseInt(st.nextToken());
        int edgeCount = Integer.parseInt(st.nextToken());

        int[] rank = new int[nodeCount];
        int[] parent = new int[nodeCount];
        Arrays.fill(rank, 1);
        Arrays.fill(parent, -1);

        int cycleIndex = 0;
        for (int i = 0; i < edgeCount; i++) {
            st = new StringTokenizer(br.readLine());
            int nodeA = Integer.parseInt(st.nextToken());
            int nodeB = Integer.parseInt(st.nextToken());

            if (union(parent, rank, nodeA, nodeB)) {
                cycleIndex = i + 1;
                break;
            }
        }

        bw.write(String.valueOf(cycleIndex));
        bw.flush();
        bw.close();
        br.close();
    }

    private static int find(int[] parent, int node) {
        if (parent[node] < 0) {
            return node;
        }
        return parent[node] = find(parent, parent[node]);
    }

    private static boolean union(int[] parent, int[] rank, int nodeA, int nodeB) {
        int rootA = find(parent, nodeA);
        int rootB = find(parent, nodeB);

        if (rootA == rootB) {
            return true;
        }

        if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
        } else if (rank[rootA] < rank[rootB]) {
            parent[rootA] = rootB;
        } else {
            parent[rootA] = rootB;
            rank[rootB]++;
        }

        return false;
    }
}

/*
6 5
0 1
1 2
2 3
5 4
0 4

0

6 5
0 1
1 2
1 3
0 3

4
*/