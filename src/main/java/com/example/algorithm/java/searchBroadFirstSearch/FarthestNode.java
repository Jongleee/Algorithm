package com.example.algorithm.java.searchBroadFirstSearch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FarthestNode {
    static int[] visit;
    static int depth = 0;
    static ArrayList<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public static int solution(int n, int[][] edge) {
        int answer = 0;
        visit = new int[n + 1];
        adj = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int i = 0; i < edge.length; i++) {
            adj[edge[i][0]].add(edge[i][1]);
            adj[edge[i][1]].add(edge[i][0]);
        }
        bfs(1, 1);
        for (int i = 0; i <= n; i++) {
            if (depth == visit[i]) answer += 1;
        }
        return answer;
    }

    public static void bfs(int start, int count) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        queue.add(count);
        visit[start] = count;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            int n = queue.poll();
            if (depth < n) depth = n;
            for (int i = 0; i < adj[node].size(); i++) {
                int next = adj[node].get(i);

                if (visit[next] != 0) continue;
                visit[next] = n + 1;
                queue.add(next);
                queue.add(n + 1);
            }
        }
    }
    public static void main(String[] args) {
        System.out.println(solution(6,	new int[][] {{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}}));
    }
    //답 3
}
