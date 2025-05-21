package com.example.algorithm.java.kruskal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class CityDivisionPlan {
    static class Edge implements Comparable<Edge> {
        int u, v, w;

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Edge other) {
            return this.w - other.w;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int nodeCount = Integer.parseInt(st.nextToken());
        int edgeCount = Integer.parseInt(st.nextToken());

        int[] parents = new int[nodeCount + 1];
        for (int i = 1; i <= nodeCount; i++) {
            parents[i] = i;
        }

        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>();
        for (int i = 0; i < edgeCount; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            edgeQueue.offer(new Edge(u, v, w));
        }
        br.close();

        int result = 0;
        int selectedEdgeCount = 0;

        while (selectedEdgeCount < nodeCount - 2) {
            Edge edge = edgeQueue.poll();
            if (union(edge.u, edge.v, parents)) {
                result += edge.w;
                selectedEdgeCount++;
            }
        }

        bw.write(result + "\n");
        bw.flush();
        bw.close();
    }

    private static int find(int x, int[] parents) {
        if (parents[x] == x) {
            return x;
        }
        return parents[x] = find(parents[x], parents);
    }

    private static boolean union(int a, int b, int[] parents) {
        int rootA = find(a, parents);
        int rootB = find(b, parents);
        if (rootA != rootB) {
            parents[rootB] = rootA;
            return true;
        }
        return false;
    }
}

/*
7 12
1 2 3
1 3 2
3 2 1
2 5 2
3 4 4
7 3 6
5 1 5
1 6 2
6 4 1
6 5 3
4 5 3
6 7 4

8
*/