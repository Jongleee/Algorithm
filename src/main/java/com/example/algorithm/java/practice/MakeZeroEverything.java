package com.example.algorithm.java.practice;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class MakeZeroEverything {
    private int[] visit;
    private long[] temp;
    private ArrayList<Integer>[] adj;
    private long answer;

    public long solution(int[] a, int[][] edges) {
        int sum = 0;
        int n = a.length;
        visit = new int[n];
        adj = new ArrayList[n];
        temp = new long[n];
        answer = 0;

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
            sum += a[i];
            temp[i] = a[i];
        }

        if (sum != 0) {
            return -1;
        }

        buildGraph(edges);

        dfs(0);

        return answer;
    }

    private void buildGraph(int[][] edges) {
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            adj[u].add(v);
            adj[v].add(u);
        }
    }

    private long dfs(int i) {
        visit[i] = 1;
        for (int j = 0; j < adj[i].size(); j++) {
            int next = adj[i].get(j);
            if (visit[next] == 0) {
                temp[i] += dfs(next);
            }
        }
        answer += Math.abs(temp[i]);
        return temp[i];
    }

    // @Test
    // void 정답() {
    //     int[] a1 = { -5, 0, 2, 1, 2 };
    //     int[][] e1 = { { 0, 1 }, { 3, 4 }, { 2, 3 }, { 0, 3 } };
    //     int[] a2 = { 0, 1, 0 };
    //     int[][] e2 = { { 0, 1 }, { 1, 2 } };
    //     Assertions.assertEquals(9, solution(a1, e1));
    //     Assertions.assertEquals(-1, solution(a2, e2));
}
