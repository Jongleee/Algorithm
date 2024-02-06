package com.example.algorithm.java.greedy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class ConnectingIslands {
    public int solution(int n, int[][] costs) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] cost : costs) {
            graph.computeIfAbsent(cost[0], k -> new ArrayList<>()).add(new int[] { cost[1], cost[2] });
            graph.computeIfAbsent(cost[1], k -> new ArrayList<>()).add(new int[] { cost[0], cost[2] });
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(arr -> arr[1]));
        Set<Integer> visited = new HashSet<>();
        int totalCost = 0;

        pq.addAll(graph.get(0));
        visited.add(0);

        while (!pq.isEmpty()) {
            int[] edge = pq.poll();
            int vertex = edge[0];
            int cost = edge[1];

            if (visited.contains(vertex)) {
                continue;
            }

            visited.add(vertex);
            totalCost += cost;

            if (graph.containsKey(vertex)) {
                for (int[] neighbor : graph.get(vertex)) {
                    if (!visited.contains(neighbor[0])) {
                        pq.offer(neighbor);
                    }
                }
            }
        }

        return totalCost;
    }

    // @Test
    // void 정답() {
    //     int n = 4;
    //     int[][] costs = { { 0, 1, 1 }, { 0, 2, 2 }, { 1, 2, 5 }, { 1, 3, 1 }, { 2, 3, 8 } };

    //     Assertions.assertEquals(4, solution(n, costs));
    // }
}
