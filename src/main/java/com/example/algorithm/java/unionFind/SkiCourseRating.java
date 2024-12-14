package com.example.algorithm.java.unionFind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class SkiCourseRating {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int rows = Integer.parseInt(st.nextToken());
        int cols = Integer.parseInt(st.nextToken());
        int threshold = Integer.parseInt(st.nextToken());

        int[][] grid = readGrid(br, rows, cols);
        boolean[][] startPoints = readStartPoints(br, rows, cols);
        int totalNodes = rows * cols;

        long result = calculateDifficulty(grid, startPoints, rows, cols, totalNodes, threshold);
        System.out.println(result);
    }

    private static int[][] readGrid(BufferedReader br, int rows, int cols) throws IOException {
        int[][] grid = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < cols; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        return grid;
    }

    private static boolean[][] readStartPoints(BufferedReader br, int rows, int cols) throws IOException {
        boolean[][] startPoints = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < cols; j++) {
                startPoints[i][j] = Integer.parseInt(st.nextToken()) == 1;
            }
        }
        return startPoints;
    }

    private static long calculateDifficulty(int[][] grid, boolean[][] startPoints, int rows, int cols, int totalNodes,
            int threshold) {
        PriorityQueue<Edge> edges = buildEdges(grid, rows, cols);
        UnionFind uf = new UnionFind(totalNodes);

        initializeStartPoints(startPoints, rows, cols, uf);

        long totalDifficulty = 0;

        while (!edges.isEmpty()) {
            Edge edge = edges.poll();
            int root1 = uf.find(edge.start);
            int root2 = uf.find(edge.end);

            if (root1 == root2)
                continue;

            uf.union(root1, root2);
            int newRoot = uf.find(edge.start);

            if (uf.getSize(newRoot) >= threshold && uf.getStartCount(newRoot) > 0) {
                totalDifficulty += (long) edge.weight * uf.getStartCount(newRoot);
                uf.resetStartCount(newRoot);
            }
        }

        return totalDifficulty;
    }

    private static PriorityQueue<Edge> buildEdges(int[][] grid, int rows, int cols) {
        PriorityQueue<Edge> edges = new PriorityQueue<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int startIdx = i * cols + j + 1;
                if (i < rows - 1) {
                    int endIdx = (i + 1) * cols + j + 1;
                    edges.add(new Edge(startIdx, endIdx, Math.abs(grid[i][j] - grid[i + 1][j])));
                }
                if (j < cols - 1) {
                    int endIdx = i * cols + (j + 1) + 1;
                    edges.add(new Edge(startIdx, endIdx, Math.abs(grid[i][j] - grid[i][j + 1])));
                }
            }
        }
        return edges;
    }

    private static void initializeStartPoints(boolean[][] startPoints, int rows, int cols, UnionFind uf) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (startPoints[i][j]) {
                    int idx = i * cols + j + 1;
                    uf.incrementStartCount(idx);
                }
            }
        }
    }

    private static class Edge implements Comparable<Edge> {
        int start, end, weight;

        Edge(int start, int end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    private static class UnionFind {
        private final int[] parent;
        private final int[] size;
        private final int[] startCount;

        public UnionFind(int totalNodes) {
            parent = new int[totalNodes + 1];
            size = new int[totalNodes + 1];
            startCount = new int[totalNodes + 1];

            for (int i = 1; i <= totalNodes; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int node) {
            if (parent[node] != node) {
                parent[node] = find(parent[node]);
            }
            return parent[node];
        }

        public void union(int node1, int node2) {
            int root1 = find(node1);
            int root2 = find(node2);

            if (root1 == root2)
                return;

            if (size[root1] < size[root2]) {
                parent[root1] = root2;
                size[root2] += size[root1];
                startCount[root2] += startCount[root1];
            } else {
                parent[root2] = root1;
                size[root1] += size[root2];
                startCount[root1] += startCount[root2];
            }
        }

        public int getSize(int node) {
            return size[node];
        }

        public int getStartCount(int node) {
            return startCount[node];
        }

        public void incrementStartCount(int node) {
            startCount[node]++;
        }

        public void resetStartCount(int node) {
            startCount[node] = 0;
        }
    }
}

/*
3 5 10
20 21 18 99 5
19 22 20 16 17
18 17 40 60 80
1 0 0 0 0
0 0 0 0 0
0 0 0 0 1

24
*/