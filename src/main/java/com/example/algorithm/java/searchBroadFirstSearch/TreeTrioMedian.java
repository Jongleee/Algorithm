package com.example.algorithm.java.searchBroadFirstSearch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@SuppressWarnings("unchecked")
public class TreeTrioMedian {
    public static int solution(int n, int[][] edges) {
        List<Integer>[] adjList = buildAdjacencyList(n, edges);

        int[] distances = bfs(adjList, 1, n);
        int start = findStartNode(distances);
        distances = bfs(adjList, start, n);
        int maxDistance = findMaxDistance(distances);
        int cnt = count(distances, maxDistance);

        if (cnt >= 2) {
            return maxDistance;
        }

        distances = bfs(adjList, start, n);
        maxDistance = findMaxDistance(distances);
        cnt = count(distances, maxDistance);

        return cnt >= 2 ? maxDistance : maxDistance - 1;
    }

    private static List<Integer>[] buildAdjacencyList(int n, int[][] edges) {
        List<Integer>[] adjList = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            adjList[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            adjList[u].add(v);
            adjList[v].add(u);
        }
        return adjList;
    }

    private static int[] bfs(List<Integer>[] adjList, int start, int n) {
        boolean[] visited = new boolean[n + 1];
        int[] distances = new int[n + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;
        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            for (int neighbor : adjList[currentNode]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                    distances[neighbor] = distances[currentNode] + 1;
                }
            }
        }
        return distances;
    }

    private static int findStartNode(int[] distances) {
        int start = 1;
        for (int i = 2; i < distances.length; i++) {
            if (distances[i] > distances[start]) {
                start = i;
            }
        }
        return start;
    }

    private static int findMaxDistance(int[] distances) {
        int maxDistance = 0;
        for (int distance : distances) {
            maxDistance = Math.max(maxDistance, distance);
        }
        return maxDistance;
    }

    private static int count(int[] distances, int targetDistance) {
        int count = 0;
        for (int distance : distances) {
            if (distance == targetDistance) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int n1 = 4;
        int[][] e1 = { { 1, 2 }, { 2, 3 }, { 3, 4 } };
        int n2 = 5;
        int[][] e2 = { { 1, 5 }, { 2, 5 }, { 3, 5 }, { 4, 5 } };
        System.out.println(solution(n1, e1));
        System.out.println(solution(n2, e2));
    }
}
