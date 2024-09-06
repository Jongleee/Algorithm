package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

public class TreeDiameter {
    static List<Edge>[] graph;
    static int maxDistance;
    static int farthestNode;

    static class Edge {
        int node;
        int weight;

        Edge(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(br);
        st.nextToken();
        int n = (int) st.nval;

        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            st.nextToken();
            int node = (int) st.nval;

            while (true) {
                st.nextToken();
                int connectedNode = (int) st.nval;
                if (connectedNode < 0)
                    break;

                st.nextToken();
                int weight = (int) st.nval;
                graph[node].add(new Edge(connectedNode, weight));
            }
        }

        dfs(1, -1, 0);
        dfs(farthestNode, -1, 0);

        System.out.println(maxDistance);
    }

    private static void dfs(int currentNode, int parent, int distance) {
        maxDistance = Math.max(distance, maxDistance);
        if (distance == maxDistance) {
            farthestNode = currentNode;
        }

        for (Edge edge : graph[currentNode]) {
            if (edge.node != parent) {
                dfs(edge.node, currentNode, distance + edge.weight);
            }
        }
    }
}

/*
5
1 3 2 -1
2 4 4 -1
3 1 2 4 3 -1
4 2 4 3 3 5 6 -1
5 4 6 -1

11
*/