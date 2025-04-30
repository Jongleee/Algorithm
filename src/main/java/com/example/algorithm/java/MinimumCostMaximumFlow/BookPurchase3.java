package com.example.algorithm.java.minimumCostMaximumFlow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BookPurchase3 {
    private static final int INF = 987654321;
    private static final int SRC = 200;
    private static final int SINK = 201;
    private static final int BIAS = 100;
    private static int[][] capacity = new int[202][202];
    private static int[][] flow = new int[202][202];
    private static int[][] cost = new int[202][202];
    private static int[] parent = new int[202];
    private static int[] dist = new int[202];
    private static boolean[] inQueue = new boolean[202];
    private static List<List<Integer>> adj = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 202; i++)
            adj.add(new ArrayList<>());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int c = Integer.parseInt(st.nextToken());
            capacity[BIAS + i][SINK] = c;
            adj.get(BIAS + i).add(SINK);
            adj.get(SINK).add(BIAS + i);
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            int c = Integer.parseInt(st.nextToken());
            capacity[SRC][i] = c;
            adj.get(SRC).add(i);
            adj.get(i).add(SRC);
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                int c = Integer.parseInt(st.nextToken());
                capacity[i][BIAS + j] = c;
                if (c > 0) {
                    adj.get(i).add(BIAS + j);
                    adj.get(BIAS + j).add(i);
                }
            }
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                int c = Integer.parseInt(st.nextToken());
                cost[i][BIAS + j] = c;
                cost[BIAS + j][i] = -c;
            }
        }

        int[] result = minCostMaxFlow();
        System.out.println(result[0]);
        System.out.println(result[1]);
    }

    private static int[] minCostMaxFlow() {
        int totalFlow = 0, totalCost = 0;

        while (spfa()) {
            int minFlow = INF;
            for (int i = SINK; i != SRC; i = parent[i]) {
                minFlow = Math.min(minFlow, capacity[parent[i]][i] - flow[parent[i]][i]);
            }

            for (int i = SINK; i != SRC; i = parent[i]) {
                flow[parent[i]][i] += minFlow;
                flow[i][parent[i]] -= minFlow;
                totalCost += minFlow * cost[parent[i]][i];
            }

            totalFlow += minFlow;
        }

        return new int[] { totalFlow, totalCost };
    }

    private static boolean spfa() {
        Arrays.fill(parent, -1);
        Arrays.fill(dist, INF);
        Arrays.fill(inQueue, false);
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(SRC);
        dist[SRC] = 0;
        inQueue[SRC] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            inQueue[current] = false;

            for (int next : adj.get(current)) {
                if (capacity[current][next] - flow[current][next] > 0 &&
                        dist[next] > dist[current] + cost[current][next]) {
                    dist[next] = dist[current] + cost[current][next];
                    parent[next] = current;
                    if (!inQueue[next]) {
                        queue.offer(next);
                        inQueue[next] = true;
                    }
                }
            }
        }

        return parent[SINK] != -1;
    }
}

/*
4 4
3 2 4 2
5 3 2 1
0 1 1 0
1 0 1 2
2 1 1 1
0 0 2 0
5 6 2 1
3 7 4 1
2 10 3 1
10 20 30 1

8
47
*/