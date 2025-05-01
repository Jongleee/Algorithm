package com.example.algorithm.java.dinic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class PassionateGangho3 {
    static int[][] capacity;
    static int[][] flow;
    static int[] level;
    static int[] work;
    static int vertexCount;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int workerCount = Integer.parseInt(st.nextToken());
        int jobCount = Integer.parseInt(st.nextToken());
        int extraCapacity = Integer.parseInt(st.nextToken());

        vertexCount = workerCount + jobCount + 3;
        int source = 0;
        int mid = vertexCount - 2;
        int sink = vertexCount - 1;

        capacity = new int[vertexCount][vertexCount];
        flow = new int[vertexCount][vertexCount];
        level = new int[vertexCount];
        work = new int[vertexCount];

        capacity[source][mid] = extraCapacity;

        for (int i = 1; i <= workerCount; i++) {
            st = new StringTokenizer(br.readLine());
            int taskCount = Integer.parseInt(st.nextToken());
            capacity[source][i] = 1;
            capacity[mid][i] = 1;
            for (int j = 0; j < taskCount; j++) {
                int task = Integer.parseInt(st.nextToken());
                capacity[i][workerCount + task] = 1;
            }
        }

        for (int i = 1; i <= jobCount; i++) {
            capacity[workerCount + i][sink] = 1;
        }

        int maxFlow = computeMaxFlow(source, sink);
        bw.write(String.valueOf(maxFlow));
        bw.flush();
        bw.close();
    }

    private static int computeMaxFlow(int source, int sink) {
        int totalFlow = 0;
        while (bfs(source, sink)) {
            Arrays.fill(work, 0);
            while (true) {
                int currentFlow = dfs(source, sink, Integer.MAX_VALUE);
                if (currentFlow == 0)
                    break;
                totalFlow += currentFlow;
            }
        }
        return totalFlow;
    }

    private static boolean bfs(int source, int sink) {
        Arrays.fill(level, -1);
        level[source] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int next = 0; next < vertexCount; next++) {
                if (level[next] == -1 && capacity[current][next] - flow[current][next] > 0) {
                    level[next] = level[current] + 1;
                    queue.offer(next);
                }
            }
        }

        return level[sink] != -1;
    }

    private static int dfs(int current, int sink, int currentFlow) {
        if (current == sink)
            return currentFlow;

        for (int i = work[current]; i < vertexCount; i++) {
            if (level[current] + 1 != level[i])
                continue;
            if (capacity[current][i] - flow[current][i] <= 0)
                continue;

            int minFlow = Math.min(currentFlow, capacity[current][i] - flow[current][i]);
            int resultFlow = dfs(i, sink, minFlow);

            if (resultFlow > 0) {
                flow[current][i] += resultFlow;
                flow[i][current] -= resultFlow;
                work[current] = i;
                return resultFlow;
            }
        }

        work[current] = vertexCount;
        return 0;
    }
}

/*
5 5 1
3 1 2 3
3 1 2 3
1 5
1 5
1 5

4
*/