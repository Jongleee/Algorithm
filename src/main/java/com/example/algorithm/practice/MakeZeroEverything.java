package com.example.algorithm.practice;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class MakeZeroEverything {
    static int[] visit;
    static long[] temp;
    static ArrayList<Integer>[] adj;
    static long answer;

    public static long solution(int[] a, int[][] edges) {
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

    public static long dfs(int i) {
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
    public static void main(String[] args) {
        System.out.println(solution(new int[]{-5, 0, 2, 1, 2},new int[][]{{0, 1}, {3, 4}, {2, 3}, {0, 3}}));
    }

}
