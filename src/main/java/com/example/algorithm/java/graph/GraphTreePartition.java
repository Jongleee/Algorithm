package com.example.algorithm.java.graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphTreePartition {
    static int v;
    static int e;
    static int[] vertexes;
    static List<List<Edge>> adj;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] input = br.readLine().split(" ");
        v = Integer.parseInt(input[0]);
        if (v < 3) {
            System.out.println(-1);
            return;
        }
        e = Integer.parseInt(input[1]);

        adj = new ArrayList<>(v + 1);
        adj.add(null);
        for (int i = 0; i <= v; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 1; i <= e; i++) {
            input = br.readLine().split(" ");
            int l = Integer.parseInt(input[0]);
            int r = Integer.parseInt(input[1]);
            adj.get(l).add(new Edge(l, r, i));
            adj.get(r).add(new Edge(r, l, i));
        }

        vertexes = new int[v + 1];
        int splitCount = 0;
        for (int i = 1; i <= v; i++) {
            if (vertexes[i] == 0) {
                dfs(++splitCount, i);
            }
        }

        if (splitCount > 2) {
            System.out.println(-1);
            return;
        }
        if (splitCount == 1) {
            boolean[] visit = new boolean[v + 1];
            int leaf = findOne(visit, 1);
            vertexes[leaf] = 2;
        }

        int firstSize = 0;
        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();
        int secondStart = 0;
        for (int i = 1; i <= v; i++) {
            if (vertexes[i] == 1) {
                firstSize++;
                first.append(i).append(' ');
            } else {
                second.append(i).append(' ');
                secondStart = i;
            }
        }

        if (firstSize == v - firstSize) {
            System.out.println(-1);
            return;
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(firstSize + " " + (v - firstSize));
        bw.newLine();
        bw.write(first.toString());
        bw.newLine();

        boolean[] temp = new boolean[v + 1];
        Set<Integer> firstEdgeSet = new HashSet<>();
        selectEdges(firstEdgeSet, temp, 1);

        for (Integer i : firstEdgeSet) {
            bw.write(i.toString());
            bw.write(' ');
        }

        bw.newLine();
        bw.write(second.toString());
        bw.newLine();

        Set<Integer> secondEdgeSet = new HashSet<>();
        selectEdges(secondEdgeSet, temp, secondStart);
        for (Integer i : secondEdgeSet) {
            bw.write(i.toString());
            bw.write(' ');
        }

        bw.flush();
        bw.close();
    }

    private static void dfs(int start, int i) {
        vertexes[i] = start;
        for (Edge edge : adj.get(i)) {
            if (vertexes[edge.end] == 0) {
                dfs(start, edge.end);
            }
        }
    }

    private static int findOne(boolean[] visit, int i) {
        visit[i] = true;
        for (Edge edge : adj.get(i)) {
            if (!visit[edge.end]) {
                return findOne(visit, edge.end);
            }
        }
        return i;
    }

    private static void selectEdges(Set<Integer> selected, boolean[] visit, int start) {
        visit[start] = true;
        for (Edge edge : adj.get(start)) {
            if (!visit[edge.end] && vertexes[edge.end] == vertexes[start]) {
                selected.add(edge.index);
                selectEdges(selected, visit, edge.end);
            }
        }
    }

    static class Edge {
        public final int start;
        public final int end;
        public final int index;
    
        public Edge(int s, int e, int i) {
            this.start = s;
            this.end = e;
            this.index = i;
        }
    
        @Override
        public String toString() {
            return String.format("Edge(start=%d, end=%d, index=%d)", start, end, index);
        }
    }
}


/*
5 5
1 2
1 3
2 3
3 4
4 5

4 1
1 2 3 4
1 3 4
5
 */