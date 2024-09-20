package com.example.algorithm.java.prim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CommunionWithCosmicGods {
    static class Point {
        long x;
        long y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Node {
        int vertex;
        Node link;

        public Node(int vertex, Node link) {
            this.vertex = vertex;
            this.link = link;
        }
    }

    static double[] minDist;
    static boolean[] visited;
    static double result;
    static Point[] gods;
    static int n;
    static int m;
    static int remaining;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        gods = new Point[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            gods[i] = new Point(Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken()));
        }

        Node[] adjList = new Node[n];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int from = Integer.parseInt(st.nextToken()) - 1;
            int to = Integer.parseInt(st.nextToken()) - 1;

            adjList[from] = new Node(to, adjList[from]);
            adjList[to] = new Node(from, adjList[to]);
        }

        minDist = new double[n];
        visited = new boolean[n];
        Arrays.fill(minDist, Double.MAX_VALUE);
        minDist[0] = 0;
        result = 0;
        remaining = n;

        while (remaining > 0) {
            double min = Double.MAX_VALUE;
            int current = -1;

            for (int i = 0; i < n; i++) {
                if (!visited[i] && minDist[i] < min) {
                    min = minDist[i];
                    current = i;
                }
            }

            result += min;
            dfs(current, adjList);
        }

        System.out.printf("%.2f", Math.round(result * 100) / 100.0);
    }

    private static void dfs(int current, Node[] adjList) {
        remaining--;
        visited[current] = true;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                double dist = getDist(gods[current], gods[i]);
                if (minDist[i] > dist) {
                    minDist[i] = dist;
                }
            }
        }

        for (Node node = adjList[current]; node != null; node = node.link) {
            if (!visited[node.vertex]) {
                dfs(node.vertex, adjList);
            }
        }
    }

    private static double getDist(Point p1, Point p2) {
        return Math.sqrt(Math.pow((double) p1.x - p2.x, 2) + Math.pow((double) p1.y - p2.y, 2));
    }
}

/*
4 1
1 1
3 1
2 3
4 3
1 4

4.00
*/