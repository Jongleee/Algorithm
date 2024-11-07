package com.example.algorithm.java.networkFlow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class PassionateWarrior4 {
    private static int v;
    private static int[][] capacity;
    private static int[][] flow;
    private static int[] level;
    private static int[] work;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        v = n + m + 3;

        capacity = new int[v][v];
        flow = new int[v][v];
        level = new int[v];
        work = new int[v];

        capacity[0][v - 2] = k;
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            int count = Integer.parseInt(st.nextToken());
            capacity[0][i] = 1;
            capacity[v - 2][i] = k;
            for (int j = 0; j < count; j++) {
                int job = Integer.parseInt(st.nextToken());
                capacity[i][n + job] = 1;
            }
        }

        for (int i = 1; i <= m; i++) {
            capacity[n + i][v - 1] = 1;
        }

        System.out.println(calculateMaxFlow());
    }

    private static int calculateMaxFlow() {
        int totalFlow = 0;
        while (bfs()) {
            Arrays.fill(work, 0);
            int flow;
            while ((flow = dfs(0, v - 1, Integer.MAX_VALUE)) > 0) {
                totalFlow += flow;
            }
        }
        return totalFlow;
    }

    private static boolean bfs() {
        Arrays.fill(level, -1);
        level[0] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int i = 0; i < v; i++) {
                if (level[i] == -1 && capacity[node][i] > flow[node][i]) {
                    level[i] = level[node] + 1;
                    queue.add(i);
                }
            }
        }
        return level[v - 1] != -1;
    }

    private static int dfs(int current, int destination, int flowLimit) {
        if (current == destination)
            return flowLimit;
        for (int i = work[current]; i < v; i++) {
            if (level[current] + 1 == level[i] && capacity[current][i] > flow[current][i]) {
                int flowResult = dfs(i, destination, Math.min(flowLimit, capacity[current][i] - flow[current][i]));
                if (flowResult > 0) {
                    flow[current][i] += flowResult;
                    flow[i][current] -= flowResult;
                    work[current] = i;
                    return flowResult;
                }
            }
        }
        work[current] = v;
        return 0;
    }
}

/*
5 5 3
5 1 2 3 4 5
1 1
1 1
1 1
2 1 5

5


5 5 5
5 1 2 3 4 5
1 1
1 1
1 1
2 1 5

5
*/