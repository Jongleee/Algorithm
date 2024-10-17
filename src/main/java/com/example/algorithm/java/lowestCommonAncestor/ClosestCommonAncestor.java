package com.example.algorithm.java.lowestCommonAncestor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ClosestCommonAncestor {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseCount = Integer.parseInt(br.readLine());

        for (int t = 0; t < testCaseCount; t++) {
            int nodeCount = Integer.parseInt(br.readLine());

            boolean[] visited = new boolean[nodeCount + 1];
            int[] parentTree = new int[nodeCount + 1];

            for (int i = 0; i < nodeCount - 1; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int parentNode = Integer.parseInt(st.nextToken());
                int childNode = Integer.parseInt(st.nextToken());
                parentTree[childNode] = parentNode;
            }

            int root = findRoot(parentTree);

            StringTokenizer st = new StringTokenizer(br.readLine());
            int nodeA = Integer.parseInt(st.nextToken());
            int nodeB = Integer.parseInt(st.nextToken());

            markAncestors(nodeA, root, parentTree, visited);
            System.out.println(findLCA(nodeB, parentTree, visited));
        }
    }

    private static int findRoot(int[] parentTree) {
        for (int i = 1; i < parentTree.length; i++) {
            if (parentTree[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    private static void markAncestors(int node, int root, int[] parentTree, boolean[] visited) {
        while (node != root) {
            visited[node] = true;
            node = parentTree[node];
        }
        visited[root] = true;
    }

    private static int findLCA(int node, int[] parentTree, boolean[] visited) {
        while (!visited[node]) {
            node = parentTree[node];
        }
        return node;
    }
}

/*
2
16
1 14
8 5
10 16
5 9
4 6
8 4
4 10
1 13
6 15
10 11
6 7
10 2
16 3
8 1
16 12
16 7
5
2 3
3 4
3 1
1 5
3 5

4
3
*/