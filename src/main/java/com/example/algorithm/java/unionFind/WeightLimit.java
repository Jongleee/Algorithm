package com.example.algorithm.java.unionFind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class WeightLimit {
    private static class Edge implements Comparable<Edge> {
        int s, e, cost;

        public Edge(int s, int e, int cost) {
            this.s = s;
            this.e = e;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(o.cost, this.cost);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        int[] parents = new int[n + 1];

        for (int i = 1; i <= n; i++)
            parents[i] = i;

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            pq.offer(new Edge(a, b, cost));
        }

        st = new StringTokenizer(br.readLine(), " ");
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        System.out.println(findMaxWeight(pq, parents, start, end));
    }

    private static int findMaxWeight(PriorityQueue<Edge> pq, int[] parents, int start, int end) {
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            if (union(parents, edge.s, edge.e) && isConnected(parents, start, end)) {
                return edge.cost;
            }
        }
        return 0;
    }

    private static boolean union(int[] parents, int a, int b) {
        int rootA = find(parents, a);
        int rootB = find(parents, b);
        if (rootA != rootB) {
            parents[rootB] = rootA;
            return true;
        }
        return false;
    }

    private static int find(int[] parents, int v) {
        if (parents[v] == v)
            return v;
        parents[v] = find(parents, parents[v]);
        return parents[v];
    }

    private static boolean isConnected(int[] parents, int a, int b) {
        return find(parents, a) == find(parents, b);
    }
}

/*
3 3
1 2 2
3 1 3
2 3 2
1 3

3
*/