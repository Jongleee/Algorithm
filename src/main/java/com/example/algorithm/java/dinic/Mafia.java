package com.example.algorithm.java.dinic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Mafia {
    static class Edge {
        int to, capacity, flow;
        Edge reverse;

        Edge(int to, int capacity) {
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }

        int residualCapacity() {
            return capacity - flow;
        }

        void addFlow(int amount) {
            flow += amount;
            reverse.flow -= amount;
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        final int INF = 1_000_000_007;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int size = n * 2;

        st = new StringTokenizer(br.readLine());
        int source = Integer.parseInt(st.nextToken());
        int sink = n + Integer.parseInt(st.nextToken());

        List<Edge>[] graph = new ArrayList[size + 1];
        for (int i = 1; i <= size; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 1; i <= n; i++) {
            int capacity = Integer.parseInt(br.readLine());
            addEdge(graph, i, n + i, capacity);
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            addEdge(graph, n + u, v, INF);
            addEdge(graph, n + v, u, INF);
        }

        dinic(graph, size, source, sink, INF);

        List<Integer> result = findMinCutSet(graph, n, source);
        StringBuilder sb = new StringBuilder();
        for (int x : result) {
            sb.append(x).append(" ");
        }
        System.out.print(sb);
    }

    private static void addEdge(List<Edge>[] graph, int u, int v, int cap) {
        Edge e1 = new Edge(v, cap);
        Edge e2 = new Edge(u, 0);
        e1.reverse = e2;
        e2.reverse = e1;
        graph[u].add(e1);
        graph[v].add(e2);
    }

    private static void dinic(List<Edge>[] graph, int size, int source, int sink, int INF) {
        int[] level = new int[size + 1];
        int[] work = new int[size + 1];

        while (buildLevelGraph(graph, level, source, sink, size, INF)) {
            Arrays.fill(work, 0);
            while (sendFlow(graph, level, work, source, sink, INF) != 0)
                ;
        }
    }

    private static boolean buildLevelGraph(List<Edge>[] graph, int[] level, int source, int sink, int size, int INF) {
        Arrays.fill(level, INF);
        level[source] = 0;
        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(source);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (Edge e : graph[current]) {
                if (e.residualCapacity() > 0 && level[e.to] == INF) {
                    level[e.to] = level[current] + 1;
                    queue.add(e.to);
                }
            }
        }
        return level[sink] != INF;
    }

    private static int sendFlow(List<Edge>[] graph, int[] level, int[] work, int current, int sink, int flow) {
        if (current == sink)
            return flow;

        for (; work[current] < graph[current].size(); work[current]++) {
            Edge e = graph[current].get(work[current]);
            if (e.residualCapacity() > 0 && level[e.to] == level[current] + 1) {
                int minFlow = sendFlow(graph, level, work, e.to, sink, Math.min(flow, e.residualCapacity()));
                if (minFlow > 0) {
                    e.addFlow(minFlow);
                    return minFlow;
                }
            }
        }
        return 0;
    }

    private static List<Integer> findMinCutSet(List<Edge>[] graph, int n, int source) {
        boolean[] visited = new boolean[graph.length];
        Deque<Integer> queue = new ArrayDeque<>();
        visited[source] = true;
        queue.add(source);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (Edge e : graph[current]) {
                if (!visited[e.to] && e.residualCapacity() > 0) {
                    visited[e.to] = true;
                    queue.add(e.to);
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (visited[i] && !visited[i + n]) {
                result.add(i);
            }
        }
        return result;
    }
}

/*
5 6
5 3
2
4
8
3
10
1 5
1 2
2 4
4 5
2 3
3 4

1 4 
*/