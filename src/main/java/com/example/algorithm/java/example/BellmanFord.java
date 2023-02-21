package com.example.algorithm.java.example;

import java.util.Arrays;

class Edge {
    int src;
    int dest;
    int weight;

    public Edge() {
        this.src = this.dest = this.weight = 0;
    }
}

class Graph {
    private static final int INF = Integer.MAX_VALUE;

    private int vertexCount;
    private Edge[] edges;

    public Graph(int vertexCount, int edgeCount) {
        this.vertexCount = vertexCount;
        this.edges = new Edge[edgeCount];
        for (int i = 0; i < edgeCount; i++) {
            this.edges[i] = new Edge();
        }
    }

    public void bellmanFord(int sourceVertex) {
        // Step 1: 원점에서 정점으로 가는 모든 거리를 INF로 초기화
        int[] distances = new int[vertexCount];
        Arrays.fill(distances, INF);
        distances[sourceVertex] = 0;

        // Step 2: 모든 정점을 방문하여 거리 업데이트
        for (int i = 1; i < vertexCount; i++) {
            for (Edge edge : edges) {
                int u = edge.src;
                int v = edge.dest;
                int weight = edge.weight;
                if (distances[u] != INF && distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                }
            }
        }

        // Step 3: 한번 더 반복하면서 음수 사이클을 찾아줌
        for (Edge edge : edges) {
            int u = edge.src;
            int v = edge.dest;
            int weight = edge.weight;
            if (distances[u] != INF && distances[u] + weight < distances[v]) {
                System.out.println("음수 사이클 존재");
                return;
            }
        }

        printDistances(distances);
    }

    void printDistances(int[] distances) {
        System.out.println("Vertex Distance from Source");
        for (int i = 0; i < vertexCount; i++) {
            System.out.println(i + "\t\t" + distances[i]);
        }
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