package com.example.algorithm.java.tree.centroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Factories {
    static class Node {
        int to;
        long weight;
        Node next;

        Node(int to, long weight, Node next) {
            this.to = to;
            this.weight = weight;
            this.next = next;
        }
    }

    static final long INF = Long.MAX_VALUE;
    static int n, logN;
    static Node[] graph;
    static int[][] parent;
    static int[] depth, centroidParent, subtreeSize;
    static long[] distFromRoot, minDist;
    static boolean[] visited;
    static Deque<Integer> updated;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        graph = new Node[n];
        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            graph[u] = new Node(v, w, graph[u]);
            graph[v] = new Node(u, w, graph[v]);
        }

        logN = (int) Math.ceil(Math.log(n) / Math.log(2));
        parent = new int[logN + 1][n+1];
        depth = new int[n+1];
        distFromRoot = new long[n+1];
        dfsForLCA(0, n, 0);

        for (int i = 1; i <= logN; i++) {
            for (int j = 0; j < n; j++) {
                parent[i][j] = parent[i - 1][parent[i - 1][j]];
            }
        }

        centroidParent = new int[n];
        subtreeSize = new int[n];
        visited = new boolean[n];
        minDist = new long[n];
        buildCentroidTree(0, n);

        updated = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();

        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            while (!updated.isEmpty()) {
                minDist[updated.pollFirst()] = INF;
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < s; i++) {
                int x = Integer.parseInt(st.nextToken());
                update(x);
            }

            long answer = INF;
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < t; i++) {
                int x = Integer.parseInt(st.nextToken());
                answer = Math.min(answer, query(x));
            }

            sb.append(answer).append('\n');
        }

        System.out.print(sb);
    }

    static void dfsForLCA(int current, int parentNode, long dist) {
        parent[0][current] = parentNode;
        depth[current] = parentNode == n ? 0 : depth[parentNode] + 1;
        distFromRoot[current] = parentNode == n ? 0 : distFromRoot[parentNode] + dist;
        for (Node next = graph[current]; next != null; next = next.next) {
            if (next.to != parentNode) {
                dfsForLCA(next.to, current, next.weight);
            }
        }
    }

    static int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        for (int i = 0, diff = depth[u] - depth[v]; diff > 0; diff >>= 1, i++) {
            if ((diff & 1) == 1)
                u = parent[i][u];
        }

        if (u == v)
            return u;

        for (int i = logN; i >= 0; i--) {
            if (parent[i][u] != parent[i][v]) {
                u = parent[i][u];
                v = parent[i][v];
            }
        }

        return parent[0][u];
    }

    static long getDistance(int u, int v) {
        return distFromRoot[u] + distFromRoot[v] - 2 * distFromRoot[lca(u, v)];
    }

    static void update(int v) {
        for (int u = v; u != n; u = centroidParent[u]) {
            long dist = getDistance(u, v);
            if (dist < minDist[u]) {
                minDist[u] = dist;
                updated.addFirst(u);
            }
        }
    }

    static long query(int v) {
        long res = INF;
        for (int u = v; u != n; u = centroidParent[u]) {
            if (minDist[u] != INF) {
                res = Math.min(res, getDistance(u, v) + minDist[u]);
            }
        }
        return res == INF ? -1 : res;
    }

    static void buildCentroidTree(int current, int parentNode) {
        int centroid = getCentroid(current, -1, getSubtreeSize(current, -1) / 2);
        centroidParent[centroid] = parentNode;
        visited[centroid] = true;
        minDist[centroid] = INF;
        for (Node next = graph[centroid]; next != null; next = next.next) {
            if (!visited[next.to]) {
                buildCentroidTree(next.to, centroid);
            }
        }
    }

    static int getSubtreeSize(int current, int parentNode) {
        subtreeSize[current] = 1;
        for (Node next = graph[current]; next != null; next = next.next) {
            if (!visited[next.to] && next.to != parentNode) {
                subtreeSize[current] += getSubtreeSize(next.to, current);
            }
        }
        return subtreeSize[current];
    }

    static int getCentroid(int current, int parentNode, int threshold) {
        for (Node next = graph[current]; next != null; next = next.next) {
            if (!visited[next.to] && next.to != parentNode && subtreeSize[next.to] > threshold) {
                return getCentroid(next.to, current, threshold);
            }
        }
        return current;
    }
}

/*
7 3
0 1 4
1 2 4
2 3 5
2 4 6
4 5 5
1 6 3
2 2
0 6
3 4
3 2
0 1 3
4 6
1 1
2
5

12
3
11
*/