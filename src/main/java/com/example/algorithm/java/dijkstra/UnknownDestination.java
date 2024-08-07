package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class UnknownDestination {
    static BufferedReader br;
    static StringTokenizer st;
    static int n;
    static int m;
    static int t;
    static int s;
    static int g;
    static int h;
    static List<List<int[]>> graph;
    static int[] targets;
    static int[][] dist;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseCount = Integer.parseInt(br.readLine());
        while (testCaseCount-- > 0) {
            readInput();
            solution();
        }
        System.out.println(sb.toString());
    }

    static void readInput() throws IOException {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        s = Integer.parseInt(st.nextToken());
        g = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());

        graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            graph.get(a).add(new int[] { b, d });
            graph.get(b).add(new int[] { a, d });
        }

        targets = new int[t];
        for (int i = 0; i < t; i++) {
            targets[i] = Integer.parseInt(br.readLine());
        }
    }

    static void solution() {
        dist = new int[2][n + 1];
        Arrays.fill(dist[0], Integer.MAX_VALUE);
        dist[0][s] = 0;

        PriorityQueue<Road> pq = new PriorityQueue<>();
        pq.offer(new Road(s, 0, false));

        while (!pq.isEmpty()) {
            Road current = pq.poll();
            if (dist[0][current.node] < current.dist)
                continue;

            for (int[] next : graph.get(current.node)) {
                boolean visited = current.visited;

                if ((current.node == g && next[0] == h) || (next[0] == g && current.node == h)) {
                    visited = true;
                }

                if (dist[0][next[0]] > current.dist + next[1]) {
                    dist[0][next[0]] = current.dist + next[1];
                    dist[1][next[0]] = visited ? 1 : 0;
                    pq.offer(new Road(next[0], dist[0][next[0]], visited));
                } else if (dist[1][next[0]] == 0 && visited && dist[0][next[0]] == current.dist + next[1]) {
                    dist[1][next[0]] = 1;
                    pq.offer(new Road(next[0], dist[0][next[0]], visited));
                }
            }
        }

        Arrays.sort(targets);
        for (int target : targets) {
            if (dist[1][target] == 1) {
                sb.append(target).append(" ");
            }
        }
        sb.append("\n");
    }

    static class Road implements Comparable<Road> {
        int node;
        int dist;
        boolean visited;

        Road(int node, int dist, boolean visited) {
            this.node = node;
            this.dist = dist;
            this.visited = visited;
        }

        @Override
        public int compareTo(Road other) {
            return this.dist - other.dist;
        }
    }
}

/*
2
5 4 2
1 2 3
1 2 6
2 3 2
3 4 4
3 5 3
5
4
6 9 2
2 3 1
1 2 1
1 3 3
2 4 4
2 5 5
3 4 3
3 6 2
4 5 4
4 6 3
5 6 7
5
6

4 5 
6
*/