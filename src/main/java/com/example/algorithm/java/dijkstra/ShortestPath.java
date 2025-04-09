package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class ShortestPath {
    private static class Edge implements Comparable<Edge> {
        int to;
        int weight;
        Edge next;

        Edge(int to, int weight, Edge next) {
            this.to = to;
            this.weight = weight;
            this.next = next;
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int vertexCount = Integer.parseInt(st.nextToken());
        int edgeCount = Integer.parseInt(st.nextToken());
        int start = Integer.parseInt(br.readLine());

        Edge[] graph = new Edge[vertexCount + 1];
        int[] dist = new int[vertexCount + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        for (int i = 0; i < edgeCount; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            graph[from] = new Edge(to, weight, graph[from]);
        }

        dijkstra(graph, dist, start);

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= vertexCount; i++) {
            sb.append(dist[i] == Integer.MAX_VALUE ? "INF" : dist[i]).append("\n");
        }
        System.out.print(sb);
    }

    private static void dijkstra(Edge[] graph, int[] dist, int start) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        dist[start] = 0;
        pq.add(new Edge(start, 0, null));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            if (dist[current.to] < current.weight) {
                continue;
            }

            for (Edge next = graph[current.to]; next != null; next = next.next) {
                int newDist = current.weight + next.weight;
                if (dist[next.to] > newDist) {
                    dist[next.to] = newDist;
                    pq.add(new Edge(next.to, newDist, null));
                }
            }
        }
    }
}

/*
5 6
1
5 1 1
1 2 2
1 3 3
2 3 4
2 4 5
3 4 6

0
2
3
7
INF
*/