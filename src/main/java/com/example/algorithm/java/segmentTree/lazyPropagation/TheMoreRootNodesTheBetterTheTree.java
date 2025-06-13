package com.example.algorithm.java.segmentTree.lazyPropagation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class TheMoreRootNodesTheBetterTheTree {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int nodeCount = Integer.parseInt(br.readLine());
        List<Integer>[] graph = new ArrayList[nodeCount + 1];
        for (int i = 0; i <= nodeCount; i++)
            graph[i] = new ArrayList<>();

        int[] parent = new int[nodeCount + 1];
        int[] edgeDirection = new int[nodeCount + 1];
        int[] start = new int[nodeCount + 1];
        int[] end = new int[nodeCount + 1];
        boolean[] visited = new boolean[nodeCount + 1];
        List<int[]> directedEdges = new ArrayList<>();

        for (int i = 0; i < nodeCount - 1; i++) {
            String[] parts = br.readLine().split(" ");
            int u = Integer.parseInt(parts[0]);
            int v = Integer.parseInt(parts[2]);
            int dir = parts[1].equals("->") ? 1 : parts[1].equals("<-") ? -1 : 0;
            graph[u].add(v);
            graph[v].add(u);
            if (dir != 0)
                directedEdges.add(new int[] { u, v, dir });
        }

        int[] time = { 1 };
        dfs(1, graph, parent, visited, start, end, time);

        int[] segmentTree = new int[4 * nodeCount];
        int[] lazy = new int[4 * nodeCount];
        buildSegmentTree(1, nodeCount, 1, segmentTree);

        for (int[] edge : directedEdges) {
            applyEdge(edge[0], edge[1], edge[2], parent, edgeDirection, start, end, nodeCount, segmentTree, lazy);
        }

        int queryCount = Integer.parseInt(br.readLine());
        for (int i = 0; i < queryCount; i++) {
            String[] parts = br.readLine().split(" ");
            int u = Integer.parseInt(parts[0]);
            int v = Integer.parseInt(parts[2]);
            int dir = parts[1].equals("->") ? 1 : parts[1].equals("<-") ? -1 : 0;
            applyEdge(u, v, dir, parent, edgeDirection, start, end, nodeCount, segmentTree, lazy);
            bw.write(segmentTree[1] + "\n");
        }

        bw.flush();
        bw.close();
    }

    private static void dfs(int current, List<Integer>[] graph, int[] parent, boolean[] visited, int[] start, int[] end,
            int[] time) {
        visited[current] = true;
        start[current] = time[0]++;
        for (int neighbor : graph[current]) {
            if (!visited[neighbor]) {
                dfs(neighbor, graph, parent, visited, start, end, time);
            } else {
                parent[current] = neighbor;
            }
        }
        end[current] = time[0] - 1;
    }

    private static void buildSegmentTree(int l, int r, int node, int[] tree) {
        if (l == r) {
            tree[node] = 1;
            return;
        }
        int mid = (l + r) / 2;
        buildSegmentTree(l, mid, 2 * node, tree);
        buildSegmentTree(mid + 1, r, 2 * node + 1, tree);
        tree[node] = r - l + 1;
    }

    private static void update(int l, int r, int node, int ql, int qr, int value, int[] tree, int[] lazy) {
        if (ql > qr)
            return;
        if (l == ql && r == qr) {
            lazy[node] += value;
        } else {
            int mid = (l + r) / 2;
            update(l, mid, 2 * node, ql, Math.min(qr, mid), value, tree, lazy);
            update(mid + 1, r, 2 * node + 1, Math.max(ql, mid + 1), qr, value, tree, lazy);
        }

        if (lazy[node] > 0) {
            tree[node] = 0;
        } else if (l == r) {
            tree[node] = 1;
        } else {
            tree[node] = tree[2 * node] + tree[2 * node + 1];
        }
    }

    private static void excludeSubtree(int nodeId, int value, int[] start, int[] end, int[] tree, int[] lazy,
            int nodeCount) {
        if (start[nodeId] > 1) {
            update(1, nodeCount, 1, 1, start[nodeId] - 1, value, tree, lazy);
        }
        if (end[nodeId] < nodeCount) {
            update(1, nodeCount, 1, end[nodeId] + 1, nodeCount, value, tree, lazy);
        }
    }

    private static void applyEdge(int u, int d, int dir, int[] parent, int[] edgeDirection, int[] start, int[] end,
            int nodeCount, int[] tree, int[] lazy) {
        int target = d;
        if (parent[u] == d) {
            dir = -dir;
            target = u;
        }

        if (edgeDirection[target] == dir)
            return;

        if (edgeDirection[target] == 0) {
            if (dir > 0)
                update(1, nodeCount, 1, start[target], end[target], 1, tree, lazy);
            else
                excludeSubtree(target, 1, start, end, tree, lazy, nodeCount);
        } else if (dir == 0) {
            if (edgeDirection[target] > 0)
                update(1, nodeCount, 1, start[target], end[target], -1, tree, lazy);
            else
                excludeSubtree(target, -1, start, end, tree, lazy, nodeCount);
        } else if (dir < 0) {
            update(1, nodeCount, 1, start[target], end[target], -1, tree, lazy);
            excludeSubtree(target, 1, start, end, tree, lazy, nodeCount);
        } else {
            excludeSubtree(target, -1, start, end, tree, lazy, nodeCount);
            update(1, nodeCount, 1, start[target], end[target], 1, tree, lazy);
        }

        edgeDirection[target] = dir;
    }
}

/*
5
1 -- 2
2 -> 3
2 <- 4
3 -- 5
5
2 -- 4
2 -> 4
5 -> 3
2 -- 3
3 -- 5

3
2
0
1
4
*/