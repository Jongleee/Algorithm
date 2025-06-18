package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class JoiNationalEvent {
    private static final int INF = Integer.MAX_VALUE >>> 1;
    private static final char LINE_BREAK = '\n';

    private static class Edge implements Comparable<Edge> {
        int u, v, weight;
        Edge next;

        Edge(int u, int v, int weight, Edge next) {
            this.u = u;
            this.v = v;
            this.weight = weight;
            this.next = next;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(o.weight, this.weight);
        }
    }

    private static class Node implements Comparable<Node> {
        int node, weight;

        Node(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    private static class Query {
        int u, v, left = 0, right, ans;
        Query next;

        Query(int u, int v, int upperBound, Query next) {
            this.u = u;
            this.v = v;
            this.right = upperBound;
            this.next = next;
        }

        void validate(int mid, int[] dist, int[] parent, Edge[] edges, Query[] queriesAt) {
            if (find(u, parent) == find(v, parent)) {
                right = mid;
            } else {
                left = mid + 1;
            }
            queriesAt[mid] = next;
            if (left >= right) {
                ans = (right == 0) ? Math.min(dist[u], dist[v]) : edges[right].weight;
            } else {
                int nextMid = (left + right) >>> 1;
                next = queriesAt[nextMid];
                queriesAt[nextMid] = this;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        Edge[] edges = new Edge[m + 1];
        int[] dist = runDijkstra(br, n, m, k, edges);

        Query[] result = new Query[q];
        Query[] queriesAt = new Query[m + 1];
        int mid = (m + 1) >>> 1;

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            queriesAt[mid] = new Query(u, v, m + 1, queriesAt[mid]);
            result[i] = queriesAt[mid];
        }

        for (int i = 1; i <= m; i++) {
            edges[i].weight = Math.min(dist[edges[i].u], dist[edges[i].v]);
        }
        Arrays.sort(edges, 1, m + 1);
        edges[0] = new Edge(1, 1, 0, null);

        int[] initRoots = new int[n + 1];
        int[] parent = new int[n + 1];
        int completed = 0;

        while (completed < q) {
            System.arraycopy(initRoots, 1, parent, 1, n);
            for (int i = 0; i <= m; i++) {
                union(edges[i].u, edges[i].v, parent);
                while (queriesAt[i] != null) {
                    Query current = queriesAt[i];
                    queriesAt[i] = current.next;
                    current.validate(i, dist, parent, edges, queriesAt);
                    if (current.left >= current.right) {
                        completed++;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Query query : result) {
            sb.append(query.ans).append(LINE_BREAK);
        }
        System.out.print(sb);
    }

    private static int[] runDijkstra(BufferedReader br, int n, int m, int k, Edge[] edges) throws IOException {
        Edge[] adj = new Edge[n + 1];
        StringTokenizer st;
        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            adj[u] = new Edge(u, v, w, adj[u]);
            adj[v] = new Edge(v, u, w, adj[v]);
            edges[i] = adj[u];
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, INF);
        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (int i = 0; i < k; i++) {
            int node = Integer.parseInt(br.readLine());
            dist[node] = 0;
            pq.offer(new Node(node, 0));
        }

        boolean[] visited = new boolean[n + 1];
        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            int node = curr.node;
            if (visited[node])
                continue;
            visited[node] = true;
            for (Edge edge = adj[node]; edge != null; edge = edge.next) {
                if (!visited[edge.v] && dist[node] + edge.weight < dist[edge.v]) {
                    dist[edge.v] = dist[node] + edge.weight;
                    pq.offer(new Node(edge.v, dist[edge.v]));
                }
            }
        }
        return dist;
    }

    private static int find(int v, int[] parent) {
        if (parent[v] <= 0)
            return v;
        return parent[v] = find(parent[v], parent);
    }

    private static void union(int u, int v, int[] parent) {
        u = find(u, parent);
        v = find(v, parent);
        if (u == v)
            return;
        if (parent[u] > parent[v]) {
            parent[u] = v;
        } else {
            if (parent[u] == parent[v])
                parent[u]--;
            parent[v] = u;
        }
    }
}

/*
12 17 2 5
1 3 6
1 6 7
2 3 8
2 4 4
2 8 11
2 12 2
3 6 3
3 7 8
3 11 2
4 12 2
5 10 3
6 10 5
8 9 6
8 12 7
9 10 6
11 9 10
12 9 5
8
7
2 6
5 2
1 10
8 9
9 4

8
8
11
0
6


6 6 2 3
1 2 5
2 3 4
2 4 6
3 5 9
4 5 3
5 6 7
1
6
3 4
5 2
1 4

7
5
0
*/