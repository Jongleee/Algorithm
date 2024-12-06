package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DecoratingThePastures {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int numVertices = Integer.parseInt(st.nextToken());
        int numEdges = Integer.parseInt(st.nextToken());

        List<List<Integer>> adjacencyList = initializeAdjacencyList(numVertices);

        for (int i = 0; i < numEdges; i++) {
            st = new StringTokenizer(br.readLine());
            int vertexA = Integer.parseInt(st.nextToken());
            int vertexB = Integer.parseInt(st.nextToken());
            adjacencyList.get(vertexA).add(vertexB);
            adjacencyList.get(vertexB).add(vertexA);
        }

        int[] visited = new int[numVertices + 1];
        int maxIndependentSetSize = calculateMaxIndependentSetSize(numVertices, adjacencyList, visited);

        System.out.println(maxIndependentSetSize);
    }

    private static List<List<Integer>> initializeAdjacencyList(int size) {
        List<List<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i <= size; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        return adjacencyList;
    }

    private static int calculateMaxIndependentSetSize(int numVertices, List<List<Integer>> adjacencyList,
            int[] visited) {
        int totalMaxSize = 0;

        for (int vertex = 1; vertex <= numVertices; vertex++) {
            if (visited[vertex] == 0) {
                int[] colorCounts = new int[3];
                if (!isBipartite(vertex, 1, adjacencyList, visited, colorCounts)) {
                    return -1;
                }
                totalMaxSize += Math.max(colorCounts[1], colorCounts[2]);
            }
        }

        return totalMaxSize;
    }

    private static boolean isBipartite(int current, int color, List<List<Integer>> adjacencyList, int[] visited,
            int[] colorCounts) {
        visited[current] = color;
        colorCounts[color]++;

        for (int neighbor : adjacencyList.get(current)) {
            if (visited[neighbor] != 0) {
                if (visited[neighbor] == color) {
                    return false;
                }
            } else if (!isBipartite(neighbor, 3 - color, adjacencyList, visited, colorCounts)) {
                return false;
            }
        }

        return true;
    }
}

/*
4 4
1 2
2 3
3 4
4 1

2
*/