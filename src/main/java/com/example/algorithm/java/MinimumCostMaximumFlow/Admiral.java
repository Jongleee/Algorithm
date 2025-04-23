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

public class Admiral {
    private static class Edge {
        int to, capacity, flow, cost;
        Edge reverse;

        Edge(int to, int capacity, int cost) {
            this.to = to;
            this.capacity = capacity;
            this.cost = cost;
        }
    }

    private static class Node implements Comparable<Node> {
        int index, dist;

        Node(int index, int dist) {
            this.index = index;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node other) {
            return this.dist - other.dist;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        String inputLine;

        while ((inputLine = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(inputLine);
            int v = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());

            int length = 2 * v + 1;
            List<List<Edge>> adj = createGraph(length);

            for (int i = 1; i <= v; i++) {
                addEdge(adj, i, i + v, 1, 0);
            }

            for (int i = 0; i < e; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                addEdge(adj, a + v, b, 1, c);
            }

            int source = 1 + v;
            int sink = v;
            sb.append(minCostMaxFlow(adj, length, source, sink)).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static List<List<Edge>> createGraph(int length) {
        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            adj.add(new ArrayList<>());
        }
        return adj;
    }

    private static void addEdge(List<List<Edge>> adj, int from, int to, int capacity, int cost) {
        Edge forward = new Edge(to, capacity, cost);
        Edge backward = new Edge(from, 0, -cost);
        forward.reverse = backward;
        backward.reverse = forward;
        adj.get(from).add(forward);
        adj.get(to).add(backward);
    }

    private static int minCostMaxFlow(List<List<Edge>> adj, int length, int source, int sink) {
        int totalCost = 0;
        for (int i = 0; i < 2; i++) {
            int[] prev = new int[length];
            Edge[] path = new Edge[length];
            int[] distance = new int[length];
            Arrays.fill(distance, Integer.MAX_VALUE);
            distance[source] = 0;

            PriorityQueue<Node> pq = new PriorityQueue<>();
            pq.add(new Node(source, 0));

            while (!pq.isEmpty()) {
                Node node = pq.poll();
                getCurrent(adj, prev, path, distance, pq, node);
            }

            int flow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = prev[v]) {
                flow = Math.min(flow, path[v].capacity - path[v].flow);
            }

            for (int v = sink; v != source; v = prev[v]) {
                path[v].flow += flow;
                path[v].reverse.flow -= flow;
                totalCost += flow * path[v].cost;
            }
        }
        return totalCost;
    }

    private static void getCurrent(List<List<Edge>> adj, int[] prev, Edge[] path, int[] distance,
            PriorityQueue<Node> pq,
            Node node) {
        int curr = node.index;
        if (node.dist > distance[curr])
            return;

        for (Edge edge : adj.get(curr)) {
            if (edge.capacity > edge.flow && distance[edge.to] > distance[curr] + edge.cost) {
                distance[edge.to] = distance[curr] + edge.cost;
                prev[edge.to] = curr;
                path[edge.to] = edge;
                pq.add(new Node(edge.to, distance[edge.to]));
            }
        }
    }
}

/*
6 11
1 2 23
1 3 12
1 4 99
2 5 17
2 6 73
3 5 3
3 6 21
4 6 8
5 2 33
5 4 5
6 5 20
3 3
1 3 1
1 2 5
2 3 5

86
11
*/