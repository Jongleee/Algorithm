package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Party {
    static final int INF = 35000;
    static int n;

    static List<Node>[] adj;
    static List<Node>[] reverseAdj;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());

        adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
        reverseAdj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) reverseAdj[i] = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            adj[a].add(new Node(b, t));
            reverseAdj[b].add(new Node(a, t));
        }

        int[] distToX = dijkstra(x, adj);
        int[] distFromX = dijkstra(x, reverseAdj);

        int max = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            max = Math.max(max, distToX[i] + distFromX[i]);
        }

        System.out.println(max);
    }

    private static int[] dijkstra(int start, List<Node>[] graph) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));

        boolean[] visited = new boolean[n + 1];
        int[] dist = new int[n + 1];
        Arrays.fill(dist, INF);
        dist[start] = 0;

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (visited[current.v]) continue;
            visited[current.v] = true;

            for (Node neighbor : graph[current.v]) {
                if (!visited[neighbor.v] && dist[neighbor.v] > dist[current.v] + neighbor.w) {
                    dist[neighbor.v] = dist[current.v] + neighbor.w;
                    pq.offer(new Node(neighbor.v, dist[neighbor.v]));
                }
            }
        }

        return dist;
    }
    
    static class Node implements Comparable<Node> {
        int v;
        int w;

        public Node(int v, int w) {
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.w, o.w);
        }
    }
}


/*
4 8 2
1 2 4
1 3 2
1 4 7
2 1 1
2 3 5
3 1 2
3 4 4
4 2 3

10
 */