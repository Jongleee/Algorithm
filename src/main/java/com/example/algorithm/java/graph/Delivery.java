package com.example.algorithm.java.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Delivery {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] distance = new int[n + 1];
        Arrays.fill(distance, Integer.MAX_VALUE);

        Node[] graph = new Node[n + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            graph[u] = new Node(v, w, graph[u]);
            graph[v] = new Node(u, w, graph[v]);
        }

        dijkstra(n, graph, distance);
        System.out.println(distance[n]);
    }

    private static void dijkstra(int n, Node[] graph, int[] distance) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        distance[1] = 0;
        pq.offer(new Node(1, 0, null));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (current.to == n)
                break;
            if (distance[current.to] < current.weight)
                continue;

            for (Node neighbor = graph[current.to]; neighbor != null; neighbor = neighbor.next) {
                if (distance[neighbor.to] > distance[current.to] + neighbor.weight) {
                    distance[neighbor.to] = distance[current.to] + neighbor.weight;
                    pq.offer(new Node(neighbor.to, distance[neighbor.to], null));
                }
            }
        }
    }

    static class Node implements Comparable<Node> {
        int to;
        int weight;
        Node next;

        public Node(int to, int weight, Node next) {
            this.to = to;
            this.weight = weight;
            this.next = next;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.weight, other.weight);
        }
    }
}
/*
6 8
4 5 3
2 4 0
4 1 4
2 1 1
5 6 1
3 6 2
3 2 6
3 4 4

5
 */