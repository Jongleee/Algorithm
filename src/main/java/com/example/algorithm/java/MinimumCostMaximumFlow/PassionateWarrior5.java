package com.example.algorithm.java.minimumCostMaximumFlow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class PassionateWarrior5 {
    private static class Edge {
        int to, capacity, flow, cost;
        Edge reverse;

        Edge(int to, int capacity, int cost) {
            this.to = to;
            this.capacity = capacity;
            this.cost = cost;
            this.flow = 0;
        }
    }

    private static class Pair implements Comparable<Pair> {
        int index, distance;

        Pair(int index, int[] dist) {
            this.index = index;
            this.distance = dist[index];
        }

        @Override
        public int compareTo(Pair other) {
            return this.distance - other.distance;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int source = 0;
        int sink = n + m + 1;

        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= sink; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 1; i <= n; i++) {
            addEdge(graph, source, i, 1, 0);
        }

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            int workCount = Integer.parseInt(st.nextToken());
            for (int j = 0; j < workCount; j++) {
                int job = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());
                addEdge(graph, i, n + job, 1, cost);
            }
        }

        for (int i = n + 1; i <= n + m; i++) {
            addEdge(graph, i, sink, 1, 0);
        }

        int[] result = minCostMaxFlow(graph, source, sink, sink + 1);
        sb.append(result[0]).append("\n").append(result[1]);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static void addEdge(List<List<Edge>> graph, int from, int to, int capacity, int cost) {
        Edge forward = new Edge(to, capacity, cost);
        Edge backward = new Edge(from, 0, -cost);
        forward.reverse = backward;
        backward.reverse = forward;
        graph.get(from).add(forward);
        graph.get(to).add(backward);
    }

    private static int[] minCostMaxFlow(List<List<Edge>> graph, int source, int sink, int nodeCount) {
        int totalFlow = 0;
        int totalCost = 0;
        int[] prev = new int[nodeCount];
        Edge[] path = new Edge[nodeCount];
        int[] distance = new int[nodeCount];

        while (dijkstra(graph, source, sink, prev, path, distance)) {
            int flow = Integer.MAX_VALUE;
            for (int i = sink; i != source; i = prev[i]) {
                flow = Math.min(flow, path[i].capacity - path[i].flow);
            }
            for (int i = sink; i != source; i = prev[i]) {
                path[i].flow += flow;
                path[i].reverse.flow -= flow;
            }
            totalFlow += flow;
            totalCost += flow * distance[sink];
        }

        return new int[] { totalFlow, totalCost };
    }

    private static boolean dijkstra(List<List<Edge>> graph, int source, int sink, int[] prev, Edge[] path,
            int[] distance) {
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        Arrays.fill(path, null);

        PriorityQueue<Pair> queue = new PriorityQueue<>();
        distance[source] = 0;
        queue.offer(new Pair(source, distance));

        while (!queue.isEmpty()) {
            int current = queue.poll().index;
            if (current == sink)
                return true;

            for (Edge edge : graph.get(current)) {
                if (edge.capacity > edge.flow && distance[edge.to] > distance[current] + edge.cost) {
                    distance[edge.to] = distance[current] + edge.cost;
                    prev[edge.to] = current;
                    path[edge.to] = edge;
                    queue.offer(new Pair(edge.to, distance));
                }
            }
        }
        return false;
    }
}

/*
5 5
2 1 3 2 2
1 1 5    
2 2 1 3 7
3 3 9 4 9 5 9
1 1 0

4
18
*/