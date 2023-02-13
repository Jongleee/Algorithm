package com.example.algorithm.java.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {
    private static List<List<Integer>> graph;
    private static boolean[] visited;

    public static void bfs(int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int node = queue.remove();
            System.out.print(node + " ");

            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    queue.add(neighbor);
                    visited[neighbor] = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        graph = new ArrayList<>();
        graph.add(List.of(1, 2));
        graph.add(List.of(0, 3));
        graph.add(List.of(0, 3));
        graph.add(List.of(1, 2, 4));
        graph.add(List.of(3));

        visited = new boolean[graph.size()];

        bfs(0);
    }
}
