package com.example.algorithm.java.networkFlow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class PassionateWarrior2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int numCows = Integer.parseInt(st.nextToken());
        int numSheds = Integer.parseInt(st.nextToken());
        int numVertices = numCows + numSheds + 2;

        int[][] capacity = new int[numVertices][numVertices];
        int[][] flow = new int[numVertices][numVertices];
        int[] level = new int[numVertices];
        int[] work = new int[numVertices];

        for (int i = 1; i <= numCows; i++) {
            st = new StringTokenizer(br.readLine());
            int connections = Integer.parseInt(st.nextToken());
            capacity[0][i] = 2;
            for (int j = 0; j < connections; j++) {
                int shed = Integer.parseInt(st.nextToken());
                capacity[i][numCows + shed] = 1;
            }
        }

        for (int i = 1; i <= numSheds; i++) {
            capacity[numCows + i][numVertices - 1] = 1;
        }

        int maxFlow = 0;
        while (bfs(capacity, flow, level, numVertices)) {
            Arrays.fill(work, 0);
            int currentFlow;
            while ((currentFlow = dfs(0, numVertices - 1, Integer.MAX_VALUE, capacity, flow, level, work)) > 0) {
                maxFlow += currentFlow;
            }
        }

        bw.write(Integer.toString(maxFlow));
        bw.flush();
        bw.close();
        br.close();
    }

    private static boolean bfs(int[][] capacity, int[][] flow, int[] level, int numVertices) {
        Arrays.fill(level, -1);
        level[0] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int next = 0; next < numVertices; next++) {
                if (level[next] == -1 && capacity[current][next] - flow[current][next] > 0) {
                    level[next] = level[current] + 1;
                    queue.add(next);
                }
            }
        }
        return level[numVertices - 1] != -1;
    }

    private static int dfs(int current, int dest, int flow, int[][] capacity, int[][] flowMatrix, int[] level,
            int[] work) {
        if (current == dest)
            return flow;

        for (int i = work[current]; i < capacity.length; i++) {
            if (level[current] + 1 == level[i] && capacity[current][i] - flowMatrix[current][i] > 0) {
                int minFlow = dfs(i, dest, Math.min(flow, capacity[current][i] - flowMatrix[current][i]), capacity,
                        flowMatrix, level, work);
                if (minFlow > 0) {
                    flowMatrix[current][i] += minFlow;
                    flowMatrix[i][current] -= minFlow;
                    work[current] = i;
                    return minFlow;
                }
            }
        }
        work[current] = capacity.length;
        return 0;
    }
}

/*
5 5
2 1 2
2 1 2
2 1 2
2 4 5
0

4
*/