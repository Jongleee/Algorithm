package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class AlmostShortestPath {
    private static final long INF = Long.MAX_VALUE;

    private static class Edge implements Comparable<Edge> {
        int to;
        long weight;

        public Edge(int to, long weight) {
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Long.compare(this.weight, other.weight);
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int nodeCount = Integer.parseInt(st.nextToken());
            int edgeCount = Integer.parseInt(st.nextToken());

            if (nodeCount == 0 && edgeCount == 0)
                break;

            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int destination = Integer.parseInt(st.nextToken());

            List<Edge>[] graph = new ArrayList[nodeCount];
            List<Integer>[] prevNodes = new ArrayList[nodeCount];

            for (int i = 0; i < nodeCount; i++) {
                graph[i] = new ArrayList<>();
                prevNodes[i] = new ArrayList<>();
            }

            for (int i = 0; i < edgeCount; i++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                long weight = Long.parseLong(st.nextToken());
                graph[from].add(new Edge(to, weight));
            }

            boolean[][] isRemovedEdge = new boolean[nodeCount][nodeCount];

            long[] dist = findShortestPath(start, nodeCount, graph, isRemovedEdge, prevNodes);
            if (dist[destination] == INF) {
                sb.append("-1\n");
                continue;
            }

            removeShortestPathEdges(destination, start, isRemovedEdge, prevNodes);

            List<Integer>[] newPrevNodes = new ArrayList[nodeCount];
            for (int i = 0; i < nodeCount; i++) {
                newPrevNodes[i] = new ArrayList<>();
            }

            dist = findShortestPath(start, nodeCount, graph, isRemovedEdge, newPrevNodes);

            sb.append(dist[destination] == INF ? "-1\n" : dist[destination] + "\n");
        }

        System.out.print(sb);
    }

    private static long[] findShortestPath(int start, int nodeCount, List<Edge>[] graph, boolean[][] isRemovedEdge,
            List<Integer>[] prevNodes) {
        long[] dist = new long[nodeCount];
        Arrays.fill(dist, INF);
        dist[start] = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(start, 0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            int curNode = current.to;

            if (current.weight > dist[curNode])
                continue;

            for (Edge next : graph[curNode]) {
                if (isRemovedEdge[curNode][next.to])
                    continue;

                long newDist = dist[curNode] + next.weight;
                if (newDist < dist[next.to]) {
                    dist[next.to] = newDist;
                    prevNodes[next.to].clear();
                    prevNodes[next.to].add(curNode);
                    pq.add(new Edge(next.to, newDist));
                } else if (newDist == dist[next.to]) {
                    prevNodes[next.to].add(curNode);
                }
            }
        }
        return dist;
    }

    private static void removeShortestPathEdges(int end, int start, boolean[][] isRemovedEdge,
            List<Integer>[] prevNodes) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[prevNodes.length];
        queue.add(end);
        visited[end] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == start)
                continue;

            for (int prevNode : prevNodes[current]) {
                if (!visited[prevNode]) {
                    queue.add(prevNode);
                    visited[prevNode] = true;
                }
                isRemovedEdge[prevNode][current] = true;
            }
        }
    }
}

/*
7 9
0 6
0 1 1
0 2 1
0 3 2
0 4 3
1 5 2
2 6 4
3 6 2
4 6 4
5 6 1
4 6
0 2
0 1 1
1 2 1
1 3 1
3 2 1
2 0 3
3 0 2
6 8
0 1
0 1 1
0 2 2
0 3 3
2 5 3
3 4 2
4 1 1
5 1 1
3 0 1

0 0
5
-1
6
*/