package com.example.algorithm.java.shortestPathFasterAlgorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Exploration {
    private static final int INF = 54321;

    private static class Edge {
        int to, weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int src = n + 1;

        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i <= n + 1; i++) {
            adj.add(new ArrayList<>());
        }

        while (m-- > 0) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            adj.get(x).add(new Edge(y, r));
            adj.get(y).add(new Edge(x, -r));
        }

        for (int i = 0; i <= n; i++) {
            if (i > 0) {
                adj.get(i - 1).add(new Edge(i, 1));
                adj.get(i).add(new Edge(i - 1, 0));
            }
            adj.get(src).add(new Edge(i, 0));
        }

        int[] dist = new int[n + 2];
        boolean[] inQueue = new boolean[n + 2];
        for (int i = 0; i <= n + 1; i++)
            dist[i] = INF;

        if (!spfa(src, adj, dist, inQueue, n)) {
            System.out.print("NONE");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(dist[i] == dist[i - 1] ? '-' : '#');
        }
        System.out.print(sb);
    }

    private static boolean spfa(int src, List<List<Edge>> adj, int[] dist, boolean[] inQueue, int n) {
        Queue<Integer> queue = new ArrayDeque<>();
        dist[src] = 0;
        inQueue[src] = true;
        queue.offer(src);

        int iteration = 0;
        while (!queue.isEmpty()) {
            if (++iteration > n + 5)
                return false;
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int u = queue.poll();
                inQueue[u] = false;

                for (Edge edge : adj.get(u)) {
                    int v = edge.to;
                    int newCost = dist[u] + edge.weight;

                    if (newCost < dist[v]) {
                        dist[v] = newCost;
                        if (!inQueue[v]) {
                            inQueue[v] = true;
                            queue.offer(v);
                        }
                    }
                }
            }
        }
        return true;
    }
}

/*
12 7
1 8 4
6 10 4
2 12 6
9 12 2
4 6 1
1 4 1
11 11 0

-#--#-####--


12 2
1 10 1
4 7 3

NONE
*/