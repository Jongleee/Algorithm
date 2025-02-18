package com.example.algorithm.java.unionFind;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Mountaineers {
    private static class Edge implements Comparable<Edge> {
        int start, end, weight;

        public Edge(int start, int end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            if (weight != o.weight)
                return Integer.compare(weight, o.weight);
            if (start != o.start)
                return Integer.compare(start, o.start);
            return Integer.compare(end, o.end);
        }
    }

    private static class DSU {
        int[] parent;

        public DSU(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int fx = find(x), fy = find(y);
            if (fx != fy)
                parent[fx] = fy;
        }
    }

    private static int getId(int i, int j) {
        return (i - 1) * 500 + (j - 1);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] input = br.readLine().split(" ");
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);
        int q = Integer.parseInt(input[2]);

        int[][] grid = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            input = br.readLine().split(" ");
            for (int j = 1; j <= m; j++) {
                grid[i][j] = Integer.parseInt(input[j - 1]);
            }
        }

        List<Edge> edges = getEdges(n, m, grid);
        Collections.sort(edges);

        int[][] queries = new int[q][2];
        for (int i = 0; i < q; i++) {
            input = br.readLine().split(" ");
            queries[i][0] = getId(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
            queries[i][1] = getId(Integer.parseInt(input[2]), Integer.parseInt(input[3]));
        }

        processQueries(bw, q, grid, edges, queries);
        bw.flush();
    }

    private static List<Edge> getEdges(int n, int m, int[][] grid) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (i < n)
                    edges.add(new Edge(getId(i, j), getId(i + 1, j), Math.max(grid[i][j], grid[i + 1][j])));
                if (j < m)
                    edges.add(new Edge(getId(i, j), getId(i, j + 1), Math.max(grid[i][j], grid[i][j + 1])));
            }
        }
        return edges;
    }

    private static void processQueries(BufferedWriter bw, int q, int[][] grid, List<Edge> edges, int[][] queries)
            throws IOException {
        int[] left = new int[q], right = new int[q];
        Arrays.fill(left, 0);
        Arrays.fill(right, edges.size() - 1);

        while (true) {
            boolean updated = false;
            List<List<Integer>> batches = createBatches(edges.size());

            for (int i = 0; i < q; i++) {
                if (left[i] < right[i]) {
                    int mid = (left[i] + right[i]) / 2;
                    batches.get(mid).add(i);
                    updated = true;
                }
            }
            if (!updated)
                break;

            updateDsu(edges, queries, left, right, batches);
        }

        for (int i = 0; i < q; i++) {
            int u = queries[i][0], v = queries[i][1];
            if (u == v) {
                bw.write(grid[u / 500 + 1][u % 500 + 1] + "\n");
            } else {
                bw.write(edges.get(right[i]).weight + "\n");
            }
        }
    }

    private static List<List<Integer>> createBatches(int size) {
        List<List<Integer>> batches = new ArrayList<>();
        for (int i = 0; i < size; i++)
            batches.add(new ArrayList<>());
        return batches;
    }

    private static void updateDsu(List<Edge> edges, int[][] queries, int[] left, int[] right,
            List<List<Integer>> batches) {
        DSU dsu = new DSU(250000);
        for (int i = 0; i < edges.size(); i++) {
            dsu.union(edges.get(i).start, edges.get(i).end);
            for (int j : batches.get(i)) {
                if (dsu.find(queries[j][0]) == dsu.find(queries[j][1])) {
                    right[j] = i;
                } else {
                    left[j] = i + 1;
                }
            }
        }
    }
}

/*
3 5 3
1 3 2 1 3
2 4 5 4 4
2 1 3 2 2
1 1 3 2
2 4 2 2
1 4 3 4

2
4
3
*/