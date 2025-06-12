package com.example.algorithm.java.minimumCostMaximumFlow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class OrganizingThings {
    static class Edge {
        int to, capacity;
        double cost;

        Edge(int to, int capacity, double cost) {
            this.to = to;
            this.capacity = capacity;
            this.cost = cost;
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        final int INF = Integer.MAX_VALUE;
        final double EPS = 1e-9;
        final int SCALE = (int) Math.pow(10, 8);

        int totalPoints = Integer.parseInt(br.readLine());
        List<int[]> leftPoints = new ArrayList<>();
        List<int[]> rightPoints = new ArrayList<>();

        for (int i = 0; i < totalPoints; i++) {
            String[] input = br.readLine().split(" ");
            int x = Integer.parseInt(input[0]);
            int y = Integer.parseInt(input[1]);
            if (x > 0)
                rightPoints.add(new int[] { x, y });
            else if (x < 0)
                leftPoints.add(new int[] { x, y });
        }

        int leftSize = leftPoints.size();
        int rightSize = rightPoints.size();
        int n = leftSize + rightSize;
        int nodeCount = 2 * n + 2;
        int source = 0, sink = 1;

        int[][] capacity = new int[nodeCount][nodeCount];
        double[][] cost = new double[nodeCount][nodeCount];
        List<Integer>[] graph = new ArrayList[nodeCount];
        for (int i = 0; i < nodeCount; i++)
            graph[i] = new ArrayList<>();

        for (int i = 0; i < leftSize; i++) {
            int[] left = leftPoints.get(i);
            int u = i + 2, mid = i + 2 + n;

            addEdge(source, u, 1, 0, capacity, cost, graph);
            addEdge(mid, sink, 1, 0, capacity, cost, graph);
            addEdge(u, mid, 1, -left[0], capacity, cost, graph);
            addEdge(mid, u, 0, left[0], capacity, cost, graph);

            for (int j = 0; j < rightSize; j++) {
                int[] right = rightPoints.get(j);
                int v = j + leftSize + 2, midV = v + n;

                double dist = Math.sqrt(Math.pow(left[0] + right[0], 2) + Math.pow(left[1] - right[1], 2)) / 2;
                dist = Math.round(dist * SCALE) / (double) SCALE;

                addEdge(u, midV, 1, dist, capacity, cost, graph);
                addEdge(midV, u, 0, -dist, capacity, cost, graph);
                addEdge(v, mid, 1, dist, capacity, cost, graph);
                addEdge(mid, v, 0, -dist, capacity, cost, graph);
            }
        }

        for (int j = 0; j < rightSize; j++) {
            int[] right = rightPoints.get(j);
            int u = j + leftSize + 2, mid = u + n;

            addEdge(source, u, 1, 0, capacity, cost, graph);
            addEdge(mid, sink, 1, 0, capacity, cost, graph);
            addEdge(u, mid, 1, right[0], capacity, cost, graph);
            addEdge(mid, u, 0, -right[0], capacity, cost, graph);
        }

        double totalCost = 0;
        while (true) {
            double[] dist = new double[nodeCount];
            Arrays.fill(dist, INF);
            dist[source] = 0;

            int[] parent = new int[nodeCount];
            int[] flow = new int[nodeCount];
            boolean[] inQueue = new boolean[nodeCount];
            Queue<Integer> queue = new ArrayDeque<>();
            queue.add(source);
            flow[source] = INF;

            while (!queue.isEmpty()) {
                int u = queue.poll();
                inQueue[u] = false;

                for (int v : graph[u]) {
                    if (capacity[u][v] > 0 && dist[v] > dist[u] + cost[u][v] + EPS) {
                        dist[v] = dist[u] + cost[u][v];
                        parent[v] = u;
                        flow[v] = Math.min(flow[u], capacity[u][v]);
                        if (!inQueue[v]) {
                            queue.add(v);
                            inQueue[v] = true;
                        }
                    }
                }
            }

            if (flow[sink] == 0)
                break;

            int cur = sink;
            while (cur != source) {
                int prev = parent[cur];
                capacity[prev][cur]--;
                capacity[cur][prev]++;
                totalCost += cost[prev][cur];
                cur = prev;
            }
        }

        bw.write(String.format("%.3f", totalCost));
        bw.flush();
        bw.close();
    }

    private static void addEdge(int from, int to, int cap, double cst,
            int[][] capacity, double[][] cost, List<Integer>[] graph) {
        capacity[from][to] = cap;
        cost[from][to] = cst;
        cost[to][from] = -cst;
        graph[from].add(to);
        graph[to].add(from);
    }
}

/*
8
2 2
7 1
9 -4
-10 1
-6 -9
-6 10
8 8
2 -4

15.659
*/