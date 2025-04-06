package com.example.algorithm.java.bellmanFord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Wormhole {
    static class Edge {
        int from, to, cost;

        public Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int testCaseCount = Integer.parseInt(br.readLine());

        while (testCaseCount-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int nodeCount = Integer.parseInt(st.nextToken());
            int edgeCount = Integer.parseInt(st.nextToken());
            int wormholeCount = Integer.parseInt(st.nextToken());

            Edge[] edges = readEdges(br, edgeCount, wormholeCount);

            boolean hasNegativeCycle = containsNegativeCycle(edges, nodeCount);
            sb.append(hasNegativeCycle ? "YES\n" : "NO\n");
        }

        System.out.print(sb);
    }

    private static Edge[] readEdges(BufferedReader br, int edgeCount, int wormholeCount) throws IOException {
        Edge[] edges = new Edge[2 * edgeCount + wormholeCount];
        int index = 0;

        for (int i = 0; i < edgeCount; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            edges[index++] = new Edge(from, to, cost);
            edges[index++] = new Edge(to, from, cost);
        }

        for (int i = 0; i < wormholeCount; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            edges[index++] = new Edge(from, to, -cost);
        }

        return edges;
    }

    private static boolean containsNegativeCycle(Edge[] edges, int nodeCount) {
        int[] distance = new int[nodeCount + 1];
        Arrays.fill(distance, 0);

        for (int i = 0; i < nodeCount; i++) {
            boolean updated = false;

            for (Edge edge : edges) {
                if (distance[edge.to] > distance[edge.from] + edge.cost) {
                    distance[edge.to] = distance[edge.from] + edge.cost;
                    updated = true;
                    if (i == nodeCount - 1) {
                        return true;
                    }
                }
            }

            if (!updated) {
                break;
            }
        }

        return false;
    }
}

/*
2
3 3 1
1 2 2
1 3 4
2 3 1
3 1 3
3 2 1
1 2 3
2 3 4
3 1 8

NO
YES
*/