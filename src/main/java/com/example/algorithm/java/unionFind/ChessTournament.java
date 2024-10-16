package com.example.algorithm.java.unionFind;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ChessTournament {
    private static final char LEFT = '>';
    private static final char RIGHT = '<';
    private static final String CONSISTENT = "consistent";
    private static final String INCONSISTENT = "inconsistent";

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int nodeCount = Integer.parseInt(st.nextToken());
        int edgeCount = Integer.parseInt(st.nextToken());

        ArrayList<Integer>[] tree = new ArrayList[nodeCount];
        ArrayList<Integer>[] path = new ArrayList[nodeCount];
        int[] parent = new int[nodeCount];
        int[] visit = new int[nodeCount];

        for (int i = 0; i < nodeCount; i++) {
            tree[i] = new ArrayList<>();
            path[i] = new ArrayList<>();
            parent[i] = -1;
        }

        for (int i = 0; i < edgeCount; i++) {
            st = new StringTokenizer(br.readLine());
            int node1 = Integer.parseInt(st.nextToken());
            char relation = st.nextToken().charAt(0);
            int node2 = Integer.parseInt(st.nextToken());

            if (relation == LEFT) {
                tree[node1].add(node2);
            } else if (relation == RIGHT) {
                tree[node2].add(node1);
            } else {
                merge(find(node1, parent), find(node2, parent), parent);
            }
        }

        for (int i = 0; i < nodeCount; i++) {
            for (int child : tree[i]) {
                int root1 = find(i, parent);
                int root2 = find(child, parent);
                path[root1].add(root2);
            }
        }

        System.out.println(judgeConsistency(nodeCount, path, visit) ? CONSISTENT : INCONSISTENT);
    }

    private static int find(int node, int[] parent) {
        if (parent[node] < 0) {
            return node;
        }
        parent[node] = find(parent[node], parent);
        return parent[node];
    }

    private static void merge(int root1, int root2, int[] parent) {
        if (root1 == root2) {
            return;
        }
        if (parent[root1] < parent[root2]) {
            parent[root1] += parent[root2];
            parent[root2] = root1;
        } else {
            parent[root2] += parent[root1];
            parent[root1] = root2;
        }
    }

    private static boolean judgeConsistency(int nodeCount, ArrayList<Integer>[] path, int[] visit) {
        for (int i = 0; i < nodeCount; i++) {
            if (visit[i] == 0 && isCycle(i, path, visit)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isCycle(int node, ArrayList<Integer>[] path, int[] visit) {
        if (visit[node] == -1) {
            return true;
        }
        if (visit[node] == 1) {
            return false;
        }

        visit[node] = -1;
        for (int next : path[node]) {
            if (isCycle(next, path, visit)) {
                return true;
            }
        }

        visit[node] = 1;
        return false;
    }
}

/*
6 5
0 > 1
1 > 2
3 = 4
4 = 5
5 > 3

inconsistent


5 5
0 = 1
1 = 2
3 = 4
0 > 3
1 > 4

consistent
*/