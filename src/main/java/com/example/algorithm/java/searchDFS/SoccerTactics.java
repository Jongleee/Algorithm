package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class SoccerTactics {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int testCases = Integer.parseInt(br.readLine());

        for (int testCase = 0; testCase < testCases; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            boolean[] visited = new boolean[n];
            List<List<Integer>> graph = new ArrayList<>();
            List<List<Integer>> transposition = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                graph.add(new ArrayList<>());
                transposition.add(new ArrayList<>());
            }

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                graph.get(from).add(to);
                transposition.get(to).add(from);
            }

            if (testCase < testCases - 1) {
                br.readLine();
            }

            Stack<Integer> stack = new Stack<>();

            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    postOrder(i, graph, visited, stack);
                }
            }

            int root = stack.pop();
            visited = new boolean[n];
            postOrder(root, graph, visited, new Stack<>());

            boolean allVisited = true;
            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    sb.append("Confused\n\n");
                    allVisited = false;
                    break;
                }
            }

            if (allVisited) {
                List<Integer> list = new ArrayList<>();
                dfs(root, transposition, visited, list);
                list.sort(null);

                for (int value : list) {
                    sb.append(value).append("\n");
                }
                sb.append("\n");
            }
        }

        System.out.println(sb);
    }

    static void postOrder(int v, List<List<Integer>> graph, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;
        for (int next : graph.get(v)) {
            if (!visited[next]) {
                postOrder(next, graph, visited, stack);
            }
        }
        stack.push(v);
    }

    static void dfs(int v, List<List<Integer>> transposition, boolean[] visited, List<Integer> list) {
        Stack<Integer> s = new Stack<>();
        s.push(v);
        visited[v] = false;

        list.add(v);

        while (!s.isEmpty()) {
            int cur = s.pop();
            for (int next : transposition.get(cur)) {
                if (visited[next]) {
                    visited[next] = false;
                    s.push(next);
                    list.add(next);
                }
            }
        }
    }
}

/*
2
4 4
0 1
1 2
2 0
2 3

4 4
0 3
1 0
2 0
2 3

0
1
2

Confused
*/