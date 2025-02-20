package com.example.algorithm.java.divideAndConquer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BranchAssignment {
    private static final long INF = 1L << 60;
    private static long[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        List<List<long[]>> graph = new ArrayList<>();
        List<List<long[]>> reversedGraph = new ArrayList<>();
        initializeGraphs(graph, reversedGraph, n);

        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            graph.get(u).add(new long[] { v, w });
            reversedGraph.get(v).add(new long[] { u, w });
        }

        long[] d = dijkstra(b + 1, graph, n);
        long[] rd = dijkstra(b + 1, reversedGraph, n);

        long[] val = computeValArray(d, rd, b);
        long[] sum = computePrefixSum(val, b);

        dp = new long[s + 1][b + 1];
        int[][] opt = new int[s + 1][b + 1];
        initializeDP(opt, sum, b);

        for (int i = 2; i <= s; i++)
            divideAndConquer(opt, sum, i, i, b, 0, b);

        System.out.println(dp[s][b]);
    }

    private static void initializeGraphs(List<List<long[]>> graph, List<List<long[]>> reversedGraph, int n) {
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
            reversedGraph.add(new ArrayList<>());
        }
    }

    private static long[] dijkstra(int start, List<List<long[]>> g, int n) {
        long[] dist = new long[n + 1];
        Arrays.fill(dist, INF);
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        pq.add(new long[] { 0, start });
        dist[start] = 0;

        while (!pq.isEmpty()) {
            long[] current = pq.poll();
            long cost = current[0];
            int node = (int) current[1];

            if (cost > dist[node])
                continue;
            for (long[] edge : g.get(node)) {
                int next = (int) edge[0];
                long newCost = cost + edge[1];
                if (newCost < dist[next]) {
                    dist[next] = newCost;
                    pq.add(new long[] { newCost, next });
                }
            }
        }
        return dist;
    }

    private static long[] computeValArray(long[] d, long[] rd, int b) {
        long[] val = new long[b + 1];
        for (int i = 1; i <= b; i++) {
            val[i] = d[i] + rd[i];
        }
        Arrays.sort(val, 1, b + 1);
        return val;
    }

    private static long[] computePrefixSum(long[] val, int b) {
        long[] sum = new long[b + 1];
        for (int i = 1; i <= b; i++) {
            sum[i] = sum[i - 1] + val[i];
        }
        return sum;
    }

    private static void initializeDP(int[][] opt, long[] sum, int b) {
        for (int i = 1; i <= b; i++) {
            dp[1][i] = sum[i] * (i - 1);
            opt[1][i] = 1;
        }
    }

    private static void divideAndConquer(int[][] opt, long[] sum, int t, int s, int e, int l, int r) {
        if (s > e)
            return;
        int m = (s + e) >> 1;
        dp[t][m] = INF;
        opt[t][m] = -1;

        int start = Math.max(l, t - 1);
        int end = Math.min(m - 1, r);

        for (int k = start; k <= end; k++) {
            long current = dp[t - 1][k] + (sum[m] - sum[k]) * (m - k - 1);
            if (current < dp[t][m]) {
                dp[t][m] = current;
                opt[t][m] = k;
            }
        }

        divideAndConquer(opt, sum, t, s, m - 1, l, opt[t][m]);
        divideAndConquer(opt, sum, t, m + 1, e, opt[t][m], r);
    }
}

/*
5 4 2 10
5 2 1
2 5 1
3 5 5
4 5 10
1 5 1
2 3 1
3 2 5
2 4 5
2 1 1
3 4 2

24


5 4 2 10
5 2 1
2 5 1
3 5 5
4 5 0
1 5 1
2 3 1
3 2 5
2 4 5
2 1 1
3 4 2

13
*/