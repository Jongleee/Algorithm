package com.example.algorithm.java.searchBroadFirstSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FarthestNode {
    @SuppressWarnings("unchecked")
    public int solution(int n, int[][] edge) {
        int answer = 0;

        List<Integer>[] adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edge) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        int[] depths = bfs(adj);

        int maxDepth = Arrays.stream(depths).max().getAsInt();
        answer = (int) Arrays.stream(depths).filter(depth -> depth == maxDepth).count();

        return answer;
    }

    private int[] bfs(List<Integer>[] adj) {
        int n = adj.length - 1;
        int[] depths = new int[n + 1];
        boolean[] visited = new boolean[n + 1];

        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        visited[1] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();

            for (int neighbor : adj[node]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    depths[neighbor] = depths[node] + 1;
                    queue.add(neighbor);
                }
            }
        }

        return depths;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(3,
    //             solution(6, new int[][] { { 3, 6 }, { 4, 3 }, { 3, 2 }, { 1, 3 }, { 1, 2 }, { 2, 4 }, { 5, 2 } }));
    // }
}
