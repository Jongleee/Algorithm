package com.example.algorithm.java.kruskal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class PlanetTunnel {
    private int[] parents;

    private static class Coord implements Comparable<Coord> {
        int loc;
        int idx;

        public Coord(int loc, int idx) {
            this.loc = loc;
            this.idx = idx;
        }

        @Override
        public int compareTo(Coord other) {
            return Integer.compare(this.loc, other.loc);
        }
    }

    private static class Edge implements Comparable<Edge> {
        int cost;
        int from;
        int to;

        public Edge(int cost, int from, int to) {
            this.cost = cost;
            this.from = from;
            this.to = to;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    public static void main(String[] args) throws IOException {
        PlanetTunnel main = new PlanetTunnel();
        main.run();
    }

    private void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        parents = new int[n];
        int[][] planets = new int[n][3];

        PriorityQueue<Coord> xQueue = new PriorityQueue<>();
        PriorityQueue<Coord> yQueue = new PriorityQueue<>();
        PriorityQueue<Coord> zQueue = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());

            xQueue.add(new Coord(x, i));
            yQueue.add(new Coord(y, i));
            zQueue.add(new Coord(z, i));

            planets[i] = new int[] { x, y, z };
        }

        initializeParents(n);
        PriorityQueue<Edge> edges = new PriorityQueue<>();
        addEdges(n, xQueue, edges);
        addEdges(n, yQueue, edges);
        addEdges(n, zQueue, edges);

        long result = calculateMST(n, edges);
        System.out.println(result);
    }

    private void initializeParents(int n) {
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }
    }

    private void addEdges(int n, PriorityQueue<Coord> queue, PriorityQueue<Edge> edges) {
        Coord first = queue.poll();
        for (int i = 1; i < n; i++) {
            Coord next = queue.poll();
            edges.add(new Edge(Math.abs(first.loc - next.loc), first.idx, next.idx));
            first = next;
        }
    }

    private long calculateMST(int n, PriorityQueue<Edge> edges) {
        int count = 1;
        long totalCost = 0;
        while (count < n) {
            Edge edge = edges.poll();
            if (union(edge.from, edge.to)) {
                count++;
                totalCost += edge.cost;
            }
        }
        return totalCost;
    }

    private int find(int node) {
        if (parents[node] == node) {
            return node;
        }
        parents[node] = find(parents[node]);
        return parents[node];
    }

    private boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA == rootB) {
            return false;
        }
        parents[rootA] = rootB;
        return true;
    }
}

/*
5
11 -15 -15
14 -5 -15
-1 -1 -5
10 -4 -1
19 -4 19

4
*/