package com.example.algorithm.java.unionFind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class RedBlueSpanningTree {
    private static class Line {
        int from, to;

        public Line(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            if (n == 0 && m == 0 && k == 0)
                break;

            ArrayList<Line> redEdges = new ArrayList<>();
            ArrayList<Line> blueEdges = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                char color = st.nextToken().charAt(0);
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                if (color == 'R') {
                    redEdges.add(new Line(from, to));
                } else {
                    blueEdges.add(new Line(from, to));
                }
            }

            if (blueEdges.size() < k) {
                sb.append("0\n");
                continue;
            }

            if (!isFeasible(n, k, redEdges, blueEdges)) {
                sb.append("0\n");
                continue;
            }

            if (!isOverused(n, k, redEdges, blueEdges)) {
                sb.append("1\n");
            } else {
                sb.append("0\n");
            }
        }

        System.out.print(sb);
    }

    private static boolean isFeasible(int n, int k, ArrayList<Line> red, ArrayList<Line> blue) {
        int[] parent = initParent(n);
        int redCount = 0;
        int blueCount = 0;

        for (Line r : red) {
            if (union(r.from, r.to, parent) && ++redCount == n - 1)
                break;
        }
        for (Line b : blue) {
            if (union(b.from, b.to, parent) && ++blueCount + redCount == n - 1)
                break;
        }

        return k >= blueCount;
    }

    private static boolean isOverused(int n, int k, ArrayList<Line> red, ArrayList<Line> blue) {
        int[] parent = initParent(n);
        int redCount = 0;
        int blueCount = 0;

        for (Line b : blue) {
            if (union(b.from, b.to, parent) && ++blueCount == n - 1)
                break;
        }
        for (Line r : red) {
            if (union(r.from, r.to, parent) && ++redCount + blueCount == n - 1)
                break;
        }

        return blueCount < k;
    }

    private static int[] initParent(int n) {
        int[] parent = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }
        return parent;
    }

    private static boolean union(int a, int b, int[] parent) {
        int rootA = find(a, parent);
        int rootB = find(b, parent);
        if (rootA == rootB)
            return false;
        if (rootA < rootB) {
            parent[rootB] = rootA;
        } else {
            parent[rootA] = rootB;
        }
        return true;
    }

    private static int find(int a, int[] parent) {
        if (a == parent[a])
            return a;
        return parent[a] = find(parent[a], parent);
    }
}

/*
3 3 2
B 1 2
B 2 3
R 3 1
2 1 1
R 1 2
0 0 0

1
0
*/