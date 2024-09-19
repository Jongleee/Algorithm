package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Hacking {
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder result = new StringBuilder();
        
        int testCases = Integer.parseInt(br.readLine());
        
        while (testCases-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int startNode = Integer.parseInt(st.nextToken());
            
            @SuppressWarnings("unchecked")
            List<Node>[] graph = new ArrayList[n + 1];
            for (int i = 1; i <= n; i++) {
                graph[i] = new ArrayList<>();
            }
            
            for (int i = 0; i < d; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                graph[v].add(new Node(u, w));
            }

            int[] minTime = dijkstra(graph, n, startNode);
            int count = 0;
            int maxTime = 0;

            for (int time : minTime) {
                if (time != INF) {
                    count++;
                    maxTime = Math.max(maxTime, time);
                }
            }

            result.append(count).append(" ").append(maxTime).append("\n");
        }
        
        System.out.print(result);
    }

    private static int[] dijkstra(List<Node>[] graph, int n, int start) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[n + 1];
        int[] minTime = new int[n + 1];
        
        Arrays.fill(minTime, INF);
        minTime[start] = 0;
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (visited[current.num]) {
                continue;
            }
            visited[current.num] = true;

            for (Node neighbor : graph[current.num]) {
                if (!visited[neighbor.num] && minTime[neighbor.num] > minTime[current.num] + neighbor.weight) {
                    minTime[neighbor.num] = minTime[current.num] + neighbor.weight;
                    pq.offer(new Node(neighbor.num, minTime[neighbor.num]));
                }
            }
        }

        return minTime;
    }

    private static class Node implements Comparable<Node> {
        int num;
        int weight;

        Node(int node, int weight) {
            this.num = node;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }
    }
}

/*
2
3 2 2
2 1 5
3 2 5
3 3 1
2 1 2
3 1 8
3 2 4

2 5
3 6
*/