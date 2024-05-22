package com.example.algorithm.java.minimumCostMaximumFlow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ExploreMysteriousRemains {
    class Edge {
        int to, capacity, cost, reverse;

        Edge(int to, int capacity, int cost, int reverse) {
            this.to = to;
            this.capacity = capacity;
            this.cost = cost;
            this.reverse = reverse;
        }
    }

    class MinimumCostMaximumFlow {
        int size;
        int source;
        int sink;
        List<List<Edge>> graph;
        List<Integer> distance, parent, edgeIndex;

        MinimumCostMaximumFlow(int size, int source, int sink) {
            this.size = size;
            this.source = source;
            this.sink = sink;
            graph = new ArrayList<>(size);
            distance = new ArrayList<>(size);
            parent = new ArrayList<>(size);
            edgeIndex = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                graph.add(new ArrayList<>());
                distance.add(0);
                parent.add(0);
                edgeIndex.add(0);
            }
        }

        boolean shortestPathFasterAlgorithm() {
            List<Boolean> inQueue = new ArrayList<>(Collections.nCopies(size, false));
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(source);
            inQueue.set(source, true);
            distance.set(source, 0);
            while (!queue.isEmpty()) {
                int here = queue.poll();
                inQueue.set(here, false);
                for (int i = 0; i < graph.get(here).size(); i++) {
                    Edge edge = graph.get(here).get(i);
                    if (edge.capacity > 0 && distance.get(here) + edge.cost < distance.get(edge.to)) {
                        distance.set(edge.to, distance.get(here) + edge.cost);
                        parent.set(edge.to, here);
                        edgeIndex.set(edge.to, i);
                        if (!inQueue.get(edge.to)) {
                            queue.offer(edge.to);
                            inQueue.set(edge.to, true);
                        }
                    }
                }
            }
            return distance.get(sink) < 0;
        }

        int[] getMinimumCostMaximumFlow() {
            int maxFlow = 0;
            int minCost = 0;
            while (true) {
                Collections.fill(distance, Integer.MAX_VALUE);
                if (!shortestPathFasterAlgorithm()) break;
                int minFlow = Integer.MAX_VALUE;
                int costSum = 0;
                for (int p = sink; p != source; p = parent.get(p)) {
                    Edge edge = graph.get(parent.get(p)).get(edgeIndex.get(p));
                    minFlow = Math.min(minFlow, edge.capacity);
                    costSum += edge.cost;
                }
                for (int p = sink; p != source; p = parent.get(p)) {
                    Edge edge = graph.get(parent.get(p)).get(edgeIndex.get(p));
                    edge.capacity -= minFlow;
                    graph.get(edge.to).get(edge.reverse).capacity += minFlow;
                }
                maxFlow += minFlow;
                minCost += costSum * minFlow;
            }
            return new int[]{maxFlow, minCost};
        }

        void addEdge(int from, int to, int capacity, int cost) {
            graph.get(from).add(new Edge(to, capacity, cost, graph.get(to).size()));
            graph.get(to).add(new Edge(from, 0, -cost, graph.get(from).size() - 1));
        }
    }

    @SuppressWarnings("unchecked")
    public int solution(int n1, int[][] g1, int n2, int[][] g2) {
        List<Integer>[] graph1 = new ArrayList[103];
        List<Integer>[] graph2 = new ArrayList[103];
        for (int i = 0; i < 103; i++) {
            graph1[i] = new ArrayList<>();
            graph2[i] = new ArrayList<>();
        }
        for (int[] edge : g1) {
            int a = edge[0], b = edge[1];
            graph1[a].add(b);
            graph1[b].add(a);
        }
        for (int[] edge : g2) {
            int a = edge[0], b = edge[1];
            graph2[a].add(b);
            graph2[b].add(a);
        }

        int[][] dp = new int[103][103];
        return calculateMinimumCost(1, 0, 1, 0, graph1, graph2, dp);
    }

    private int calculateMinimumCost(int cur1, int dad1, int cur2, int dad2, List<Integer>[] G1, List<Integer>[] G2, int[][] dp) {
        if (dp[cur1][cur2] != 0)
            return dp[cur1][cur2];

        int N1 = G1[cur1].size();
        int N2 = G2[cur2].size();
        int src = N1 + N2, sink = src + 1;
        MinimumCostMaximumFlow mcmf = new MinimumCostMaximumFlow(2 + N1 + N2, src, sink);

        for (int i = 0; i < N1; i++) {
            int u = G1[cur1].get(i);
            if (u == dad1)
                continue;
            for (int j = 0; j < N2; j++) {
                int v = G2[cur2].get(j);
                if (v == dad2)
                    continue;
                int cost = calculateMinimumCost(u, cur1, v, cur2, G1, G2, dp);
                mcmf.addEdge(i, N1 + j, 1, -cost);
            }
            mcmf.addEdge(src, i, 1, 0);
        }

        for (int i = 0; i < N2; i++) {
            mcmf.addEdge(N1 + i, sink, 1, 0);
        }

        int ret = -mcmf.getMinimumCostMaximumFlow()[1] + 1;
        return dp[cur1][cur2] = ret;
    }

    // @Test
    // void 정답() {
    //     int[] n1 = { 8 };
    //     int[][][] g1 = { { { 3, 1 }, { 5, 7 }, { 8, 7 }, { 2, 3 }, { 3, 6 }, { 1, 5 }, { 4, 3 } } };
    //     int[] n2 = { 9 };
    //     int[][][] g2 = { { { 1, 5 }, { 5, 6 }, { 3, 7 }, { 3, 1 }, { 7, 4 }, { 7, 2 }, { 9, 8 }, { 5, 9 } } };
    //     int[] result = { 7 };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertEquals(result[i], solution(n1[i], g1[i], n2[i], g2[i]));
    //     }
    // }
}
