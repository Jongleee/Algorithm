package com.example.algorithm.java.searchBroadFirstSearch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@SuppressWarnings("unchecked")
public class TreeTrioMedian {
    public int solution(int n, int[][] edges) {
        List<Integer>[] adjList = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            adjList[i] = new ArrayList<>();
        }
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            adjList[u].add(v);
            adjList[v].add(u);
        }
        int[] result = bfs(adjList, 1, n);
        int s = findStart(n, result);
        result = bfs(adjList, s, n);
        s = findStart(n, result);
        int max = findMax(result);
        int cnt = calculateCnt(result, max);
        if (cnt >= 2) {
            return max;
        }

        result = bfs(adjList, s, n);
        max = findMax(result);
        cnt = calculateCnt(result, max);

        return cnt >= 2 ? max : max - 1;
    }

    private int findStart(int n, int[] result) {
        int s = 1;
        for (int i = 1; i <= n; i++) {
            if (result[i] > result[s])
                s = i;
        }
        return s;
    }

    private int findMax(int[] result) {
        int max = 0;
        for (int i : result) {
            max = Math.max(max, i);
        }
        return max;
    }

    private int calculateCnt(int[] result, int max) {
        int cnt = 0;
        for (int i : result) {
            if (max == i)
                cnt++;
        }
        return cnt;
    }

    private int[] bfs(List<Integer>[] adjList, int start, int n) {
        boolean[] visited = new boolean[n + 1];
        int[] distances = new int[n + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;
        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            for (int i = 0; i < adjList[currentNode].size(); i++) {
                int neighbor = adjList[currentNode].get(i);
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                    distances[neighbor] = distances[currentNode] + 1;
                }
            }
        }
        return distances;
    }

    // @Test
    // public void 정답() {
    //     int n1 = 4;
    //     int[][] e1 = { { 1, 2 }, { 2, 3 }, { 3, 4 } };
    //     int n2 = 5;
    //     int[][] e2 = { { 1, 5 }, { 2, 5 }, { 3, 5 }, { 4, 5 } };
    //     Assertions.assertEquals(2, solution(n1, e1));
    //     Assertions.assertEquals(4, solution(n2, e2));
    // }
}
