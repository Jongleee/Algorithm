package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Boomerangs {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int[] nm = parseInput(br);
        int nodes = nm[0], edges = nm[1];

        List<Integer>[] graph = new ArrayList[nodes + 1];
        List<Integer>[] children = new ArrayList[nodes + 1];
        for (int i = 0; i <= nodes; i++) {
            graph[i] = new ArrayList<>();
            children[i] = new ArrayList<>();
        }

        for (int i = 0; i < edges; i++) {
            int[] edge = parseInput(br);
            int node1 = edge[0], node2 = edge[1];
            graph[node1].add(node2);
            graph[node2].add(node1);
        }

        boolean[] visited = new boolean[nodes + 1];
        int[] parent = new int[nodes + 1];
        int[] childCount = new int[nodes + 1];
        List<int[]> triplets = new ArrayList<>();
        int[] components = new int[1];

        for (int i = 1; i <= nodes; i++) {
            if (!visited[i]) {
                dfs(i, graph, children, visited, parent, childCount, triplets, components);
            }
        }

        bw.write(Integer.toString(components[0]));
        bw.newLine();
        for (int[] triplet : triplets) {
            bw.write(triplet[0] + " " + triplet[1] + " " + triplet[2]);
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    private static void dfs(int node, List<Integer>[] graph, List<Integer>[] children, boolean[] visited,
            int[] parent, int[] childCount, List<int[]> triplets, int[] components) {
        visited[node] = true;
        childCount[node] = graph[node].size();

        for (int neighbor : graph[node]) {
            if (visited[neighbor]) {
                if (parent[node] != neighbor) {
                    children[neighbor].add(node);
                }
                childCount[neighbor]--;
            } else {
                parent[neighbor] = node;
                dfs(neighbor, graph, children, visited, parent, childCount, triplets, components);
            }
        }

        components[0] += childCount[node] / 2;

        if (childCount[node] % 2 == 1 && parent[node] != 0) {
            childCount[parent[node]]++;
        }

        List<Integer> childList = children[node];
        int size = childList.size();
        for (int i = 1; i < size; i += 2) {
            triplets.add(new int[] { childList.get(i - 1), node, childList.get(i) });
        }

        if (size % 2 == 1 && parent[node] != 0) {
            triplets.add(new int[] { childList.get(size - 1), node, parent[node] });
        } else if (size % 2 == 0 && parent[node] != 0) {
            children[parent[node]].add(node);
        }
    }

    private static int[] parseInput(BufferedReader br) throws IOException {
        String[] parts = br.readLine().split(" ");
        int[] result = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            result[i] = Integer.parseInt(parts[i]);
        }
        return result;
    }
}

/*
4 6
1 2
1 3
1 4
2 3
2 4
3 4

3
4 3 2
4 2 1
3 1 4


5 7
1 2
1 4
2 3
2 4
2 5
3 4
3 5

3
4 3 5
4 2 5
3 2 1
*/