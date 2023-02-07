package com.example.algorithm.java.practice.searchDFS;

import java.util.List;
import java.util.Stack;

public class DFS {

    class DFSUsingList {
        private boolean[] visited;

        public void dfs(int node, List<List<Integer>> graph) {
            visited[node] = true;
            System.out.print(node + " ");

            for (int i = 0; i < graph.get(node).size(); i++) {
                int neighbor = graph.get(node).get(i);
                if (!visited[neighbor]) {
                    dfs(neighbor, graph);
                }
            }
        }
    }

    class DFSUsingStack {
        private boolean[] visited;

        public void dfs(int node, List<List<Integer>> graph) {
            Stack<Integer> stack = new Stack<>();
            stack.push(node);

            while (!stack.isEmpty()) {
                int currentNode = stack.pop();
                if (!visited[currentNode]) {
                    visited[currentNode] = true;
                    System.out.print(currentNode + " ");
                    for (int i = graph.get(currentNode).size() - 1; i >= 0; i--) {
                        int neighbor = graph.get(currentNode).get(i);
                        if (!visited[neighbor]) {
                            stack.push(neighbor);
                        }
                    }
                }
            }
        }
    }
}
