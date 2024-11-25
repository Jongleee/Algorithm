package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class ManiaDePar {
    private static class Edge {
        int to;
        long weight;

        Edge(int to, long weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    private static class State implements Comparable<State> {
        int node;
        long cost;
        int evenOdd;

        State(int node, long cost, int evenOdd) {
            this.node = node;
            this.cost = cost;
            this.evenOdd = evenOdd;
        }

        @Override
        public int compareTo(State other) {
            return Long.compare(this.cost, other.cost);
        }
    }

    static final long INF = Long.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int nodes = Integer.parseInt(tokenizer.nextToken());
        int edgesCount = Integer.parseInt(tokenizer.nextToken());

        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= nodes; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edgesCount; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int from = Integer.parseInt(tokenizer.nextToken());
            int to = Integer.parseInt(tokenizer.nextToken());
            long weight = Long.parseLong(tokenizer.nextToken());
            graph.get(from).add(new Edge(to, weight));
            graph.get(to).add(new Edge(from, weight));
        }

        long result = dijkstra(graph, nodes);
        System.out.println(result == INF ? "-1" : result);
    }

    private static long dijkstra(List<List<Edge>> graph, int target) {
        PriorityQueue<State> queue = new PriorityQueue<>();
        long[][] distance = new long[2][graph.size()];
        Arrays.fill(distance[0], INF);
        Arrays.fill(distance[1], INF);

        queue.offer(new State(1, 0, 0));
        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (distance[current.evenOdd][current.node] <= current.cost)
                continue;
            distance[current.evenOdd][current.node] = current.cost;

            for (Edge edge : graph.get(current.node)) {
                int nextEvenOdd = (current.evenOdd + 1) % 2;
                if (distance[nextEvenOdd][edge.to] > current.cost + edge.weight) {
                    queue.offer(new State(edge.to, current.cost + edge.weight, nextEvenOdd));
                }
            }
        }
        return distance[0][target];
    }
}

/*
4 4
1 2 2
2 3 1
2 4 10
3 4 6

12


5 6
1 2 3
2 3 5
3 5 2
5 1 8
2 4 1
4 5 4

-1
*/