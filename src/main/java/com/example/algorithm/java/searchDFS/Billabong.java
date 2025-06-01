package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Billabong {
    static class Edge {
        int to, weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static class Pair {
        int index;
        long maxDistance;

        Pair(int index, long maxDistance) {
            this.index = index;
            this.maxDistance = maxDistance;
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int nodeCount = Integer.parseInt(st.nextToken());
        int edgeCount = Integer.parseInt(st.nextToken());
        int linkCost = Integer.parseInt(st.nextToken());

        List<Edge>[] graph = new ArrayList[nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < edgeCount; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            graph[from].add(new Edge(to, weight));
            graph[to].add(new Edge(from, weight));
        }

        long[] distances = new long[nodeCount];
        Arrays.fill(distances, -1);

        List<Pair> components = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            if (distances[i] == -1) {
                int u = findFarthestNode(i, i, 0, distances, graph);
                int v = findFarthestNode(u, u, 0, distances, graph);
                int center = propagateMaxDistance(v, v, 0, distances, graph);
                components.add(new Pair(center, distances[center]));
            }
        }

        int largestComponentIndex = findLargestComponentIndex(components);

        for (int i = 0; i < components.size(); i++) {
            if (i != largestComponentIndex) {
                int from = components.get(i).index;
                int to = components.get(largestComponentIndex).index;
                graph[from].add(new Edge(to, linkCost));
                graph[to].add(new Edge(from, linkCost));
            }
        }

        Arrays.fill(distances, -1);
        int start = findFarthestNode(0, 0, 0, distances, graph);
        int end = findFarthestNode(start, start, 0, distances, graph);
        System.out.println(distances[end]);
    }

    private static int findFarthestNode(int current, int parent, long length, long[] distances, List<Edge>[] graph) {
        distances[current] = length;
        int farthest = current;
        for (Edge edge : graph[current]) {
            if (edge.to != parent) {
                int candidate = findFarthestNode(edge.to, current, length + edge.weight, distances, graph);
                if (distances[farthest] < distances[candidate]) {
                    farthest = candidate;
                }
            }
        }
        return farthest;
    }

    private static int propagateMaxDistance(int current, int parent, long length, long[] distances,
            List<Edge>[] graph) {
        int farthest = current;
        distances[current] = Math.max(distances[current], length);
        for (Edge edge : graph[current]) {
            if (edge.to != parent) {
                int candidate = propagateMaxDistance(edge.to, current, length + edge.weight, distances, graph);
                if (distances[farthest] > distances[candidate]) {
                    farthest = candidate;
                }
            }
        }
        return farthest;
    }

    private static int findLargestComponentIndex(List<Pair> components) {
        int index = 0;
        for (int i = 1; i < components.size(); i++) {
            if (components.get(index).maxDistance < components.get(i).maxDistance) {
                index = i;
            }
        }
        return index;
    }
}

/*
12 8 2
0 8 4
8 2 2
2 7 4
5 11 3
5 1 7
1 3 1
1 9 5
10 6 3

18
*/