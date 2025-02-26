package com.example.algorithm.java.networkFlow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SorrowOfDivision {
    private static final int INF = 1_000_000_000;
    private static int n, source, sink;
    private static int[][] capacity, flow, adjList;
    private static int[] level, work;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        source = n + 1;
        sink = n + 2;
        capacity = new int[n + 3][n + 3];
        flow = new int[n + 3][n + 3];
        adjList = new int[n + 3][n + 3];
        level = new int[n + 3];
        work = new int[n + 3];
        int[] listSize = new int[n + 3];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            int team = Integer.parseInt(st.nextToken());
            if (team == 1) {
                adjList[source][listSize[source]++] = i;
                adjList[i][listSize[i]++] = source;
                capacity[source][i] = INF;
            } else if (team == 2) {
                adjList[i][listSize[i]++] = sink;
                adjList[sink][listSize[sink]++] = i;
                capacity[i][sink] = INF;
            }
        }

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                if (i == j) {
                    st.nextToken();
                    continue;
                }
                capacity[i][j] = Integer.parseInt(st.nextToken());
                adjList[i][listSize[i]++] = j;
            }
        }

        int maxFlow = dinicMaxFlow();
        System.out.println(maxFlow);
        findMinCut();
    }

    private static boolean bfs() {
        Arrays.fill(level, -1);
        level[source] = 0;
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.addLast(source);

        while (!queue.isEmpty()) {
            int cur = queue.removeFirst();
            if (cur == sink)
                return true;

            for (int next : adjList[cur]) {
                if (level[next] == -1 && capacity[cur][next] > flow[cur][next]) {
                    level[next] = level[cur] + 1;
                    queue.addLast(next);
                }
            }
        }
        return level[sink] != -1;
    }

    private static int dfs(int cur, int curFlow) {
        if (cur == sink)
            return curFlow;

        for (int i = work[cur]; i < adjList[cur].length; work[cur] = ++i) {
            int next = adjList[cur][i];
            if (level[next] == level[cur] + 1 && capacity[cur][next] > flow[cur][next]) {
                int minFlow = dfs(next, Math.min(curFlow, capacity[cur][next] - flow[cur][next]));
                if (minFlow > 0) {
                    flow[cur][next] += minFlow;
                    flow[next][cur] -= minFlow;
                    return minFlow;
                }
            }
        }
        return 0;
    }

    private static int dinicMaxFlow() {
        int totalFlow = 0;
        while (bfs()) {
            Arrays.fill(work, 0);
            int flowValue;
            while ((flowValue = dfs(source, INF)) > 0) {
                totalFlow += flowValue;
            }
        }
        return totalFlow;
    }

    private static void findMinCut() {
        boolean[] visited = new boolean[n + 3];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.addLast(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int cur = queue.removeFirst();
            for (int next : adjList[cur]) {
                if (!visited[next] && capacity[cur][next] > flow[cur][next]) {
                    visited[next] = true;
                    queue.addLast(next);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (visited[i])
                sb.append(i).append(" ");
        }
        sb.append("\n");

        for (int i = 1; i <= n; i++) {
            if (!visited[i])
                sb.append(i).append(" ");
        }

        System.out.println(sb);
    }
}

/*
5
0 1 0 2 2
0 1 1 1 1
1 0 1 1 1
1 1 0 1 1
1 1 1 0 1
1 1 1 1 0

4
2
1 3 4 5
*/