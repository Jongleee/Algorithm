package com.example.algorithm.java.heavyLightDecomposition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Tree {
    static int size, index;
    static int[] subtreeSize, parent, depth, top, dfsOrder, segmentTree;
    static List<Integer>[] graph;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        subtreeSize = new int[n];
        parent = new int[n];
        depth = new int[n];
        top = new int[n];
        dfsOrder = new int[n];
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();

        for (int i = 1; i < n; i++) {
            int p = Integer.parseInt(br.readLine()) - 1;
            graph[p].add(i);
            parent[i] = p;
        }

        computeSubtreeSize(0);
        decomposeTree(0);

        initializeSegmentTree();

        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken());

            boolean result = queryPath(b, c);
            bw.write(result ? "YES\n" : "NO\n");

            if (d == 1) {
                updateSegmentTree(result ? dfsOrder[b] : dfsOrder[c]);
            }
        }

        bw.flush();
    }

    static int computeSubtreeSize(int node) {
        subtreeSize[node] = 1;
        for (int i = 0; i < graph[node].size(); i++) {
            int child = graph[node].get(i);
            depth[child] = depth[node] + 1;
            parent[child] = node;
            subtreeSize[node] += computeSubtreeSize(child);
            if (subtreeSize[child] > subtreeSize[graph[node].get(0)]) {
                Collections.swap(graph[node], 0, i);
            }
        }
        return subtreeSize[node];
    }

    static void decomposeTree(int node) {
        dfsOrder[node] = index++;
        for (int child : graph[node]) {
            top[child] = (child == graph[node].get(0)) ? top[node] : child;
            decomposeTree(child);
        }
    }

    static void initializeSegmentTree() {
        size = 1;
        while (size < index)
            size <<= 1;
        segmentTree = new int[size << 1];
        Arrays.fill(segmentTree, 1);
    }

    static void updateSegmentTree(int position) {
        int i = position + size;
        segmentTree[i] = 0;
        while ((i >>= 1) > 0) {
            segmentTree[i] = Math.min(segmentTree[i << 1], segmentTree[(i << 1) | 1]);
        }
    }

    static int querySegmentTree(int left, int right) {
        int result = 1;
        for (left += size, right += size; left <= right; left >>= 1, right >>= 1) {
            if ((left & 1) == 1)
                result = Math.min(result, segmentTree[left++]);
            if ((right & 1) == 0)
                result = Math.min(result, segmentTree[right--]);
        }
        return result;
    }

    static boolean queryPath(int u, int v) {
        int result = 1;
        while (top[u] != top[v]) {
            if (depth[top[u]] > depth[top[v]]) {
                int temp = u;
                u = v;
                v = temp;
            }
            result = Math.min(result, querySegmentTree(dfsOrder[top[v]], dfsOrder[v]));
            v = parent[top[v]];
        }
        if (depth[u] > depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }
        return Math.min(result, querySegmentTree(dfsOrder[u] + 1, dfsOrder[v])) == 1;
    }
}

/*
11 7
7
4
1
9
11
1
11
1
3
7
11 9 1
8 5 0
3 9 0
6 3 1
10 9 1
3 10 1
1 4 1

YES
NO
YES
NO
NO
YES
YES


3 4
1
1
2 3 1
1 3 0
2 3 1
1 3 1

YES
YES
NO
NO
*/