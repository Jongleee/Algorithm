package com.example.algorithm.java.example;

import java.util.Arrays;

class Edge {
    int src;
    int dest;
    int weight;

    Edge() {
        src = dest = weight = 0;
    }
}

class Graph {
    private static final int INF = Integer.MAX_VALUE;

    private int vertex;
    private Edge[] edges;

    Graph(int v, int e) {
        vertex = v;
        edges = new Edge[e];
        for (int i = 0; i < e; ++i)
            edges[i] = new Edge();
    }

    void bellmanFord(int src) {
        // Step 1: 원점에서 정점으로 가는 모든 거리를 INF로 초기화
        int[] dist = new int[vertex];
        Arrays.fill(dist, INF);
        dist[src] = 0;

        // Step 2: 모든 정점을 방문하여 거리 업데이트
        for (int i = 1; i < vertex; ++i) {
            for (Edge edge : edges) {
                int u = edge.src;
                int v = edge.dest;
                int weight = edge.weight;
                if (dist[u] != INF && dist[u] + weight < dist[v])
                    dist[v] = dist[u] + weight;
            }
        }

        // Step 3: 한번 더 반복하면서 음수 사이클을 찾아줌
        for (Edge edge : edges) {
            int u = edge.src;
            int v = edge.dest;
            int weight = edge.weight;
            if (dist[u] != INF && dist[u] + weight < dist[v]) {
                System.out.println("음수 사이클 존재");
                return;
            }
        }

        printDistances(dist);
    }

    void printDistances(int[] dist) {
        System.out.println("Vertex Distance from Source");
        for (int i = 0; i < vertex; ++i)
            System.out.println(i + "\t\t" + dist[i]);
    }

    public static void main(String[] args) {
        int vertex = 5; // Number of vertices in graph
        int edge = 8; // Number of edges in graph

        Graph graph = new Graph(vertex, edge);

        // add edge 0-1 (or A-B in above figure)
        graph.edges[0].src = 0;
        graph.edges[0].dest = 1;
        graph.edges[0].weight = -1;

        // add edge 0-2 (or A-C in above figure)
        graph.edges[1].src = 0;
        graph.edges[1].dest = 2;
        graph.edges[1].weight = 4;

        // add edge 1-2 (or B-C in above figure)
        graph.edges[2].src = 1;
        graph.edges[2].dest = 2;
        graph.edges[2].weight = 3;

        // add edge 1-3 (or B-D in above figure)
        graph.edges[3].src = 1;
        graph.edges[3].dest = 3;
        graph.edges[3].weight = 2;

        // add edge 1-4 (or A-E in above figure)
        graph.edges[4].src = 1;
        graph.edges[4].dest = 4;
        graph.edges[4].weight = 2;// 음수사이클 : -2

        // add edge 3-2 (or D-C in above figure)
        graph.edges[5].src = 3;
        graph.edges[5].dest = 2;
        graph.edges[5].weight = 5;

        // add edge 3-1 (or D-B in above figure)
        graph.edges[6].src = 3;
        graph.edges[6].dest = 1;
        graph.edges[6].weight = 1;

        // add edge 4-3 (or E-D in above figure)
        graph.edges[7].src = 4;
        graph.edges[7].dest = 3;
        graph.edges[7].weight = -3;

        graph.bellmanFord(0);
    }
}