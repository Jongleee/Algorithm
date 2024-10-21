package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class StronglyConnectedComponent {
    private int nodeCount;
    private List<List<Integer>> graph;
    private boolean solved;
    private int sccCount;
    private int id;
    private boolean[] onStack;
    private int[] ids;
    private int[] low;
    private Deque<Integer> stack;
    private static final int UNVISITED = -1;

    public StronglyConnectedComponent(List<List<Integer>> graph) {
        this.nodeCount = graph.size();
        this.graph = graph;
    }

    public int sccCount() {
        if (!solved) solve();
        return sccCount;
    }

    public int[] getSccs() {
        if (!solved) solve();
        return low;
    }

    private void solve() {
        ids = new int[nodeCount];
        low = new int[nodeCount];
        onStack = new boolean[nodeCount];
        stack = new ArrayDeque<>();
        Arrays.fill(ids, UNVISITED);

        for (int i = 1; i < nodeCount; i++) {
            if (ids[i] == UNVISITED) {
                dfs(i);
            }
        }
        solved = true;
    }

    private void dfs(int currentNode) {
        stack.push(currentNode);
        onStack[currentNode] = true;
        ids[currentNode] = low[currentNode] = id++;

        for (int neighbor : graph.get(currentNode)) {
            if (ids[neighbor] == UNVISITED) {
                dfs(neighbor);
            }
            if (onStack[neighbor]) {
                low[currentNode] = Math.min(low[currentNode], low[neighbor]);
            }
        }

        if (ids[currentNode] == low[currentNode]) {
            while (true) {
                int node = stack.pop();
                onStack[node] = false;
                low[node] = ids[currentNode];
                if (node == currentNode) break;
            }
            sccCount++;
        }
    }

    public static List<List<Integer>> createGraph(int size) {
        List<List<Integer>> graph = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            graph.add(new ArrayList<>());
        }
        return graph;
    }

    public static void addEdge(List<List<Integer>> graph, int from, int to) {
        graph.get(from).add(to);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int nodeCount = Integer.parseInt(st.nextToken()) + 1;
        int edgeCount = Integer.parseInt(st.nextToken());

        List<List<Integer>> graph = createGraph(nodeCount);
        for (int i = 0; i < edgeCount; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            addEdge(graph, from, to);
        }

        StronglyConnectedComponent solver = new StronglyConnectedComponent(graph);
        int[] sccs = solver.getSccs();

        Map<Integer, List<Integer>> sccGroups = new LinkedHashMap<>();
        for (int i = 1; i < nodeCount; i++) {
            sccGroups.computeIfAbsent(sccs[i], k -> new ArrayList<>()).add(i);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(solver.sccCount()).append("\n");

        for (List<Integer> scc : sccGroups.values()) {
            for (int node : scc) {
                sb.append(node).append(" ");
            }
            sb.append("-1\n");
        }
        System.out.print(sb);
    }
}

/*
7 9
1 4
4 5
5 1
1 6
6 7
2 7
7 3
3 7
7 2

3
1 4 5 -1
2 3 7 -1
6 -1
*/