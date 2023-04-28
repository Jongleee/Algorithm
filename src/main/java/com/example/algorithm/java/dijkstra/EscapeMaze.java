package com.example.algorithm.java.dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@SuppressWarnings("unchecked")
public class EscapeMaze {
    static class Edge implements Comparable<Edge> {
        int to;
        int cost;
        int state;

        Edge(int to, int cost, int state) {
            this.to = to;
            this.cost = cost;
            this.state = state;
        }

        @Override
        public int compareTo(Edge o) {
            return this.cost - o.cost;
        }
    }

    public static int solution(int n, int start, int end, int[][] roads, int[] traps) {
        List<Edge>[] adj = new ArrayList[n + 1];
        Map<Integer, Integer> trap = new HashMap<>();
        int answer = Integer.MAX_VALUE;

        init(n, roads, traps, adj, trap);

        int[][] dist = fillDistance(n, answer);

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(start, 0, 0));
        dist[start][0] = 0;

        while (!pq.isEmpty()) {
            Edge cur = pq.poll();
            int curNode = cur.to;
            int curCost = cur.cost;
            int curState = cur.state;

            if (curNode == end) {
                answer = Math.min(answer, curCost);
                continue;
            }

            if (curCost > dist[curNode][curState]) {
                continue;
            }

            for (Edge next : adj[curNode]) {
                int nextNode = next.to;
                int nextCost = next.cost;
                int isReverse = next.state;

                if (isReverse != isConnected(curNode, nextNode, curState, trap)) {
                    continue;
                }

                int nextState = getNextState(curState, nextNode, trap);
                nextCost += curCost;

                if (nextCost >= dist[nextNode][nextState]) {
                    continue;
                }

                dist[nextNode][nextState] = nextCost;
                pq.add(new Edge(nextNode, nextCost, nextState));
            }
        }

        return answer;
    }

    private static int[][] fillDistance(int n, int answer) {
        int[][] dist = new int[n + 1][1 << 10];
        for (int i = 1; i <= n; i++) {
            Arrays.fill(dist[i], answer);
        }
        return dist;
    }

    private static void init(int n, int[][] roads, int[] traps, List<Edge>[] adj, Map<Integer, Integer> trap) {
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] road : roads) {
            adj[road[0]].add(new Edge(road[1], road[2], 0));
            adj[road[1]].add(new Edge(road[0], road[2], 1));
        }

        for (int i = 0; i < traps.length; i++) {
            trap.put(traps[i], i);
        }
    }

    private static int getNextState(int curState, int nextNode, Map<Integer, Integer> trap) {
        if (trap.containsKey(nextNode)) {
            curState ^= (1 << trap.get(nextNode));
        }
        return curState;
    }

    private static int isConnected(int curNode, int nextNode, int curState, Map<Integer, Integer> trap) {
        boolean currNodeTrapChk = trap.containsKey(curNode) && ((curState & (1 << trap.get(curNode))) != 0);
        boolean nextNodeTrapChk = trap.containsKey(nextNode) && ((curState & (1 << trap.get(nextNode))) != 0);

        return currNodeTrapChk ^ nextNodeTrapChk ? 1 : 0;
    }

    public static void main(String[] args) {
        System.out.println(solution(0, 0, 0, null, null));
    }
}