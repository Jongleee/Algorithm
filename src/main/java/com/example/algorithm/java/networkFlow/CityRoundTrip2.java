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

public class CityRoundTrip2 {
    private static final int INF = 10;
    private static int[][] capacity;
    private static int[][] flow;
    private static int[] level;
    private static int[] pointer;
    private static List<Integer>[] graph;

    public static void main(String[] args) throws IOException {
        int nodeCount;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        nodeCount = Integer.parseInt(st.nextToken());
        int edgeCount = Integer.parseInt(st.nextToken());

        initializeGraph((nodeCount << 1) + 1);

        for (int i = 1; i <= nodeCount; i++) {
            addEdge(i, i + nodeCount, 1);
        }
        for (int i = 0; i < edgeCount; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            addEdge(u + nodeCount, v, 1);
            addEdge(v + nodeCount, u, 1);
        }

        int maxFlow = getMaxFlow(nodeCount + 1, 2);
        System.out.println(maxFlow);
    }

    @SuppressWarnings("unchecked")
    private static void initializeGraph(int totalNodes) {
        capacity = new int[totalNodes][totalNodes];
        flow = new int[totalNodes][totalNodes];
        level = new int[totalNodes];
        pointer = new int[totalNodes];
        graph = new ArrayList[totalNodes];
        for (int i = 0; i < totalNodes; i++) {
            graph[i] = new ArrayList<>();
        }
    }

    private static void addEdge(int from, int to, int cap) {
        graph[from].add(to);
        graph[to].add(from);
        capacity[from][to] += cap;
    }

    private static boolean bfs(int source, int sink) {
        Arrays.fill(level, -1);
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);
        level[source] = 0;
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int next : graph[curr]) {
                if (level[next] == -1 && capacity[curr][next] - flow[curr][next] > 0) {
                    level[next] = level[curr] + 1;
                    queue.offer(next);
                }
            }
        }
        return level[sink] != -1;
    }

    private static int dfs(int curr, int sink, int flowCapacity) {
        if (curr == sink)
            return flowCapacity;
        for (; pointer[curr] < graph[curr].size(); pointer[curr]++) {
            int next = graph[curr].get(pointer[curr]);
            if (level[curr] + 1 == level[next] && capacity[curr][next] - flow[curr][next] > 0) {
                int minFlow = dfs(next, sink, Math.min(flowCapacity, capacity[curr][next] - flow[curr][next]));
                if (minFlow > 0) {
                    flow[curr][next] += minFlow;
                    flow[next][curr] -= minFlow;
                    return minFlow;
                }
            }
        }
        return 0;
    }

    private static int getMaxFlow(int source, int sink) {
        int maxFlow = 0;
        while (bfs(source, sink)) {
            Arrays.fill(pointer, 0);
            int flowIncrease;
            while ((flowIncrease = dfs(source, sink, INF)) > 0) {
                maxFlow += flowIncrease;
            }
        }
        return maxFlow;
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

1
*/