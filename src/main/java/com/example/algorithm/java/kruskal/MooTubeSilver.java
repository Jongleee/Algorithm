package com.example.algorithm.java.kruskal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MooTubeSilver {
    private int[] parent;
    private int[] rank;

    private static class Edge implements Comparable<Edge> {
        int p;
        int q;
        int weight;

        public Edge(int p, int q, int weight) {
            this.p = p;
            this.q = q;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return other.weight - this.weight;
        }
    }

    private static class Query implements Comparable<Query> {
        int index;
        int minWeight;
        int vertex;

        public Query(int index, int minWeight, int vertex) {
            this.index = index;
            this.minWeight = minWeight;
            this.vertex = vertex;
        }

        @Override
        public int compareTo(Query other) {
            return other.minWeight - this.minWeight;
        }
    }

    public MooTubeSilver(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        MooTubeSilver uf = new MooTubeSilver(n);
        Edge[] edges = new Edge[n - 1];
        Query[] queries = new Query[q];

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken()) - 1;
            int qNode = Integer.parseInt(st.nextToken()) - 1;
            int weight = Integer.parseInt(st.nextToken());
            edges[i] = new Edge(p, qNode, weight);
        }

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int minWeight = Integer.parseInt(st.nextToken());
            int vertex = Integer.parseInt(st.nextToken()) - 1;
            queries[i] = new Query(i, minWeight, vertex);
        }

        Arrays.sort(edges);
        Arrays.sort(queries);

        int[] results = new int[q];
        int edgeIndex = 0;

        for (Query query : queries) {
            while (edgeIndex < n - 1 && edges[edgeIndex].weight >= query.minWeight) {
                uf.union(edges[edgeIndex].p, edges[edgeIndex].q);
                edgeIndex++;
            }
            results[query.index] = uf.getSize(query.vertex) - 1;
        }

        StringBuilder sb = new StringBuilder();
        for (int result : results) {
            sb.append(result).append("\n");
        }
        System.out.print(sb);
    }

    private void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP != rootQ) {
            if (rank[rootP] >= rank[rootQ]) {
                parent[rootQ] = rootP;
                rank[rootP] += rank[rootQ];
            } else {
                parent[rootP] = rootQ;
                rank[rootQ] += rank[rootP];
            }
        }
    }

    private int find(int p) {
        if (parent[p] != p) {
            parent[p] = find(parent[p]);
        }
        return parent[p];
    }

    private int getSize(int p) {
        return rank[find(p)];
    }
}

/*
4 3
1 2 3
2 3 2
2 4 4
1 2
4 1
3 1

3
0
2
*/