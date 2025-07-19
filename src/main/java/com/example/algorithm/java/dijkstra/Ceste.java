package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Ceste {
    private static class Edge {
        int to, time, cost;

        Edge(int to, int time, int cost) {
            this.to = to;
            this.time = time;
            this.cost = cost;
        }
    }

    private static class State implements Comparable<State> {
        int totalCost, totalTime, node;

        State(int totalCost, int totalTime, int node) {
            this.totalCost = totalCost;
            this.totalTime = totalTime;
            this.node = node;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.totalCost, other.totalCost);
        }
    }

    public static void main(String[] args) throws IOException {
        final long INF = (long) 1e17;
        final int MAX = 2010 * 2010;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            graph.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            graph.get(a).add(new Edge(b, c, d));
            graph.get(b).add(new Edge(a, c, d));
        }

        long[] minProduct = new long[n + 1];
        int[] minTime = new int[n + 1];
        Arrays.fill(minProduct, INF);
        Arrays.fill(minTime, Integer.MAX_VALUE);

        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.offer(new State(0, 0, 1));

        while (!pq.isEmpty()) {
            State curr = pq.poll();

            if ((long) curr.totalTime * curr.totalCost < minProduct[curr.node]) {
                minProduct[curr.node] = (long) curr.totalTime * curr.totalCost;
            }

            if (curr.totalTime >= minTime[curr.node])
                continue;
            minTime[curr.node] = curr.totalTime;

            for (Edge edge : graph.get(curr.node)) {
                int nextTime = curr.totalTime + edge.time;
                int nextCost = curr.totalCost + edge.cost;
                if (nextTime < MAX) {
                    pq.offer(new State(nextCost, nextTime, edge.to));
                }
            }
        }

        for (int i = 2; i <= n; i++) {
            System.out.println(minProduct[i] == INF ? -1 : minProduct[i]);
        }
    }
}

/*
4 5
1 2 1 7
3 1 3 2
2 4 5 2
2 3 1 1
2 4 7 1

7
6
44


4 4
1 2 2 4
3 4 4 1
4 2 1 1
1 3 3 1

8
3
14
*/