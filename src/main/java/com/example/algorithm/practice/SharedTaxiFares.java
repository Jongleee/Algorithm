package com.example.algorithm.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class SharedTaxiFares {
    public static void main(String[] args) {
        System.out.println(solution(6, 4, 6, 2, new int[][] { { 4, 1, 10 }, { 3, 5, 24 }, { 5, 6, 2 }, { 3, 1, 41 },
                { 5, 1, 24 }, { 4, 6, 50 }, { 2, 4, 66 }, { 2, 3, 22 }, { 1, 6, 25 } }));
    }

    static final int MAX = 20000001;
    static ArrayList<ArrayList<Edge>> graph;

    static class Edge implements Comparable<Edge> {
        int index;
        int cost;

        Edge(int index, int cost) {
            this.index = index;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge e) {
            return this.cost - e.cost;
        }


    }

    public static int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = MAX;

        graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] i : fares) {
            graph.get(i[0]).add(new Edge(i[1], i[2]));
            graph.get(i[1]).add(new Edge(i[0], i[2]));
        }

        int[] startA = new int[n + 1];
        int[] startB = new int[n + 1];
        int[] start = new int[n + 1];

        Arrays.fill(startA, MAX);
        Arrays.fill(startB, MAX);
        Arrays.fill(start, MAX);

        startA = dijkstra(a, startA);
        startB = dijkstra(b, startB);
        start = dijkstra(s, start);

        for (int i = 1; i <= n; i++)
            answer = Math.min(answer, startA[i] + startB[i] + start[i]);
        return answer;
    }

    static int[] dijkstra(int start, int[] costs) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.offer(new Edge(start, 0));
        costs[start] = 0;

        while (!pq.isEmpty()) {
            Edge now = pq.poll();

            if (now.cost > costs[now.index])
                continue;

            ArrayList<Edge> edges = graph.get(now.index);
            for (Edge edge : edges) {
                int cost = costs[now.index] + edge.cost;

                if (cost < costs[edge.index]) {
                    costs[edge.index] = cost;
                    pq.offer(new Edge(edge.index, cost));
                }
            }
        }
        return costs;
    }
}
