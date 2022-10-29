package com.example.algorithm;

import java.util.ArrayList;

public class MakeZeroEverything {
    int[] visit;
    long[] temp;
    ArrayList<Integer>[] adj;
    long answer;

    public long solution(int[] a, int[][] edges) {
        //new int[]{-5, 0, 2, 1, 2},new int[][]{{0, 1}, {3, 4}, {2, 3}, {0, 3}}
        int sum = 0;
        visit = new int[a.length];
        adj = new ArrayList[a.length];
        temp = new long[a.length];
        for (int i = 0; i < a.length; i++) {
            adj[i] = new ArrayList<>();
            sum += a[i];
            temp[i] = a[i];
        }
        if (sum != 0) return -1;
        for (int i = 0; i < edges.length; i++) {
            adj[edges[i][0]].add(edges[i][1]);
            adj[edges[i][1]].add(edges[i][0]);
        }
        dfs(0);
        return answer;
    }

    public long dfs(int i) {
        visit[i] = 1;
        for (int j = 0; j < adj[i].size(); j++) {
            int next = adj[i].get(j);
            if (visit[next] == 0) {
                temp[i] += dfs(next);
            }
        }
        this.answer += Math.abs(temp[i]);
        return temp[i];
    }

}
