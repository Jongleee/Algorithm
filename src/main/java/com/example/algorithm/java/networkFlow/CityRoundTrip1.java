package com.example.algorithm.java.networkFlow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class CityRoundTrip1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int numNodes = Integer.parseInt(st.nextToken());
        int numEdges = Integer.parseInt(st.nextToken());

        int[][] capacity = new int[numNodes + 1][numNodes + 1];
        int[][] flow = new int[numNodes + 1][numNodes + 1];
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= numNodes; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < numEdges; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            graph.get(u).add(v);
            graph.get(v).add(u);
            capacity[u][v] = 1;
        }

        int maxFlow = getMaxFlow(graph, capacity, flow, numNodes);
        System.out.println(maxFlow);
    }

    private static int getMaxFlow(List<List<Integer>> graph, int[][] capacity, int[][] flow, int numNodes) {
        int totalFlow = 0;
        int[] parent = new int[numNodes + 1];

        while (bfs(graph, capacity, flow, parent)) {
            int currentFlow = Integer.MAX_VALUE;
            for (int v = 2; v != 1; v = parent[v]) {
                int u = parent[v];
                currentFlow = Math.min(currentFlow, capacity[u][v] - flow[u][v]);
            }

            for (int v = 2; v != 1; v = parent[v]) {
                int u = parent[v];
                flow[u][v] += currentFlow;
                flow[v][u] -= currentFlow;
            }

            totalFlow += currentFlow;
        }
        return totalFlow;
    }

    private static boolean bfs(List<List<Integer>> graph, int[][] capacity, int[][] flow, int[] parent) {
        Arrays.fill(parent, -1);
        parent[1] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int next : graph.get(current)) {
                if (parent[next] == -1 && capacity[current][next] > flow[current][next]) {
                    parent[next] = current;
                    if (next == 2)
                        return true;
                    queue.offer(next);
                }
            }
        }
        return false;
    }
}

/*
6 7
1 3
3 2
1 4
4 2
1 5
5 6
6 2

3


7 8
1 3
1 4
3 5
4 5
5 6
5 7
6 2
7 2

2
*/