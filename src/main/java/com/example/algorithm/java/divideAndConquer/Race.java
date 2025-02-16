package com.example.algorithm.java.divideAndConquer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Race {
    private static final int INF = Integer.MAX_VALUE >> 1;

    private static class Node {
        int idx, weight;
        Node next;

        Node(int idx, int weight, Node next) {
            this.idx = idx;
            this.weight = weight;
            this.next = next;
        }
    }

    private static int k;
    private static Node[] adj;
    private static int[] subSize;
    private static boolean[] visited;
    private static int[] minDepth;
    private static int[] subDepth;
    private static ArrayDeque<Integer> copy;
    private static ArrayDeque<Integer> updated;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        adj = new Node[n];

        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());
            adj[u] = new Node(v, l, adj[u]);
            adj[v] = new Node(u, l, adj[v]);
        }

        subSize = new int[n];
        visited = new boolean[n];
        minDepth = new int[k + 1];
        subDepth = new int[k + 1];
        copy = new ArrayDeque<>(n);
        updated = new ArrayDeque<>(n);

        for (int i = 1; i <= k; i++) {
            minDepth[i] = INF;
            subDepth[i] = INF;
        }

        int result = divideAndConquer(0);
        System.out.print(result == INF ? "-1" : result);
    }

    private static int divideAndConquer(int curr) {
        int centroid = getCentroid(curr, -1, getSize(curr, -1) >> 1);
        visited[centroid] = true;

        while (!updated.isEmpty()) {
            minDepth[updated.pollFirst()] = INF;
        }
        while (!copy.isEmpty()) {
            subDepth[copy.pollFirst()] = INF;
        }

        int result = INF;

        for (Node child = adj[centroid]; child != null; child = child.next) {
            if (visited[child.idx])
                continue;
            while (!copy.isEmpty()) {
                int dist = copy.pollFirst();
                if (subDepth[dist] < minDepth[dist]) {
                    minDepth[dist] = subDepth[dist];
                    updated.addLast(dist);
                }
                subDepth[dist] = INF;
            }
            result = Math.min(result,
                    depthFirstSearch(child.idx, centroid, child.weight, 1));
        }

        for (Node child = adj[centroid]; child != null; child = child.next) {
            if (visited[child.idx])
                continue;
            result = Math.min(result, divideAndConquer(child.idx));

        }
        return result;
    }

    private static int getSize(int curr, int parent) {
        subSize[curr] = 1;
        for (Node child = adj[curr]; child != null; child = child.next) {
            if (visited[child.idx] || child.idx == parent) {
                continue;
            }
            subSize[curr] += getSize(child.idx, curr);
        }
        return subSize[curr];
    }

    private static int getCentroid(int curr, int parent, int threshold) {
        for (Node child = adj[curr]; child != null; child = child.next) {
            if (visited[child.idx] || child.idx == parent) {
                continue;
            }
            if (subSize[child.idx] > threshold) {
                return getCentroid(child.idx, curr, threshold);
            }
        }
        return curr;
    }

    private static int depthFirstSearch(int curr, int parent, int dist, int depth) {
        if (dist > k)
            return INF;

        if (depth < subDepth[dist]) {
            subDepth[dist] = depth;
            copy.addLast(dist);
            updated.addLast(dist);
        }

        int result = Math.min(INF, minDepth[k - dist] + depth);

        for (Node child = adj[curr]; child != null; child = child.next) {
            if (visited[child.idx] || child.idx == parent) {
                continue;
            }
            result = Math.min(result,
                    depthFirstSearch(child.idx, curr, dist + child.weight, depth + 1));
        }
        return result;
    }
}

/*
11 12
0 1 3
0 2 4
2 3 5
3 4 4
4 5 6
0 6 3
6 7 2
6 8 5
8 9 6
8 10 7

2


3 3
0 1 1
1 2 1

-1
*/