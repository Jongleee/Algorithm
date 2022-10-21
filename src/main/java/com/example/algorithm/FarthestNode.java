package com.example.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FarthestNode {
    int[] visit;
    int depth = 0;
    ArrayList<Integer>[] adj;

    public int solution(int n, int[][] edge) {
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

    public void bfs(int start, int count) {
        Queue<Integer> queue = new LinkedList();
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
}
