package com.example.algorithm.java.tree.centroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class ParallelUniverse {
    private static class Edge {
        int to;
        Edge next;

        Edge(int to, Edge next) {
            this.to = to;
            this.next = next;
        }
    }

    private static class TreeHash implements Comparable<TreeHash> {
        int size;
        long hash;

        TreeHash(long hash, int size) {
            this.hash = hash;
            this.size = size;
        }

        @Override
        public int compareTo(TreeHash o) {
            if (this.size == o.size) {
                return Long.compare(this.hash, o.hash);
            }
            return this.size- o.size;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCount = Integer.parseInt(br.readLine());
        Set<Long> uniqueTreeHashes = new HashSet<>();

        while (testCount-- > 0) {
            int nodeCount = Integer.parseInt(br.readLine());
            Edge[] adj = new Edge[nodeCount];
            for (int i = 1; i < nodeCount; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                adj[u] = new Edge(v, adj[u]);
                adj[v] = new Edge(u, adj[v]);
            }

            int[] subSize = new int[nodeCount];
            int threshold = computeSubtreeSize(0, -1, adj, subSize) >> 1;

            Set<Integer> centroids = new HashSet<>();
            int centroid = findCentroid(0, -1, threshold, adj, subSize);
            centroids.add(centroid);
            addSecondaryCentroids(centroid, -1, threshold, adj, subSize, centroids, nodeCount);

            TreeHash bestTree = new TreeHash(-1L, -1);
            for (int cent : centroids) {
                bestTree = getMaxTree(bestTree, computeHash(cent, -1, adj));
            }

            uniqueTreeHashes.add(bestTree.hash);
        }

        System.out.println(uniqueTreeHashes.size());
    }

    private static int computeSubtreeSize(int current, int parent, Edge[] adj, int[] subSize) {
        subSize[current] = 1;
        for (Edge edge = adj[current]; edge != null; edge = edge.next) {
            if (edge.to == parent)
                continue;
            subSize[current] += computeSubtreeSize(edge.to, current, adj, subSize);
        }
        return subSize[current];
    }

    private static int findCentroid(int current, int parent, int threshold, Edge[] adj, int[] subSize) {
        for (Edge edge = adj[current]; edge != null; edge = edge.next) {
            if (edge.to == parent)
                continue;
            if (subSize[edge.to] > threshold) {
                return findCentroid(edge.to, current, threshold, adj, subSize);
            }
        }
        return current;
    }

    private static void addSecondaryCentroids(int current, int parent, int threshold,
            Edge[] adj, int[] subSize, Set<Integer> centroids, int totalSize) {
        if ((totalSize & 1) == 1)
            return;

        for (Edge edge = adj[current]; edge != null; edge = edge.next) {
            if (edge.to == parent)
                continue;

            int partialSize = 1;
            boolean isValid = true;

            if (subSize[edge.to] - 1 > threshold)
                continue;

            for (Edge subEdge = adj[edge.to]; subEdge != null; subEdge = subEdge.next) {
                if (subEdge.to == current)
                    continue;
                partialSize += subSize[subEdge.to];
                if (subSize[subEdge.to] > threshold)
                    isValid = false;
            }

            int remaining = totalSize - partialSize;
            if (isValid && remaining <= threshold) {
                centroids.add(edge.to);
            }
        }
    }

    private static TreeHash computeHash(int current, int parent, Edge[] adj) {
        List<TreeHash> children = new ArrayList<>();

        for (Edge edge = adj[current]; edge != null; edge = edge.next) {
            if (edge.to == parent)
                continue;
            children.add(computeHash(edge.to, current, adj));
        }

        Collections.sort(children);
        TreeHash result = new TreeHash(1L, 1);

        for (TreeHash child : children) {
            result.hash <<= child.size;
            result.hash |= child.hash;
            result.size += child.size;
        }

        result.hash <<= 1;
        result.size++;

        return result;
    }

    private static TreeHash getMaxTree(TreeHash a, TreeHash b) {
        return (a.compareTo(b) > 0) ? a : b;
    }
}

/*
3
4
0 1
1 2
2 3
4
0 2
2 3
3 1
4
0 1
1 2
1 3

2
*/