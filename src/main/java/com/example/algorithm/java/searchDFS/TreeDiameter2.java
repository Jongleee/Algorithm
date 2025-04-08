package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class TreeDiameter2 {
    private static class Node {
        public final int idx, distance;
        public final Node next;

        public Node(int idx, int distance, Node next) {
            this.idx = idx;
            this.distance = distance;
            this.next = next;
        }
    }

    private static class Result {
        int maxDepth;
        int maxDiameter;

        Result(int maxDepth, int maxDiameter) {
            this.maxDepth = maxDepth;
            this.maxDiameter = maxDiameter;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int nodeCount = Integer.parseInt(br.readLine());
        Node[] graph = new Node[nodeCount + 1];

        for (int i = 0; i < nodeCount - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int distance = Integer.parseInt(st.nextToken());
            graph[from] = new Node(to, distance, graph[from]);
            graph[to] = new Node(from, distance, graph[to]);
        }

        boolean[] visited = new boolean[nodeCount + 1];
        Result result = dfs(1, graph, visited);
        System.out.println(result.maxDiameter);
    }

    private static Result dfs(int current, Node[] graph, boolean[] visited) {
        visited[current] = true;
        int firstMax = 0;
        int secondMax = 0;
        int maxDiameter = 0;

        for (Node next = graph[current]; next != null; next = next.next) {
            if (!visited[next.idx]) {
                Result childResult = dfs(next.idx, graph, visited);
                int totalDepth = childResult.maxDepth + next.distance;
                maxDiameter = Math.max(maxDiameter, childResult.maxDiameter);

                if (totalDepth > firstMax) {
                    secondMax = firstMax;
                    firstMax = totalDepth;
                } else if (totalDepth > secondMax) {
                    secondMax = totalDepth;
                }
            }
        }

        maxDiameter = Math.max(maxDiameter, firstMax + secondMax);
        return new Result(firstMax, maxDiameter);
    }
}

/*
12
1 2 3
1 3 2
2 4 5
3 5 11
3 6 9
4 7 1
4 8 7
5 9 15
5 10 4
6 11 6
6 12 10

45
*/