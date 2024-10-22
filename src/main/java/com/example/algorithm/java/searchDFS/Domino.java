package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Domino {
    static int testcase;
    static int n;
    static int m;
    static boolean[] visited;
    static ArrayList<Integer>[] graph;
    static Deque<Integer> stack;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        testcase = Integer.parseInt(br.readLine());

        for (int t = 1; t <= testcase; t++) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());

            graph = new ArrayList[n + 1];
            for (int i = 1; i <= n; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int j = 0; j < m; j++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                graph[u].add(v);
            }

            visited = new boolean[n + 1];
            stack = new ArrayDeque<>();

            for (int i = 1; i <= n; i++) {
                if (!visited[i]) {
                    dfs(i);
                }
            }

            Arrays.fill(visited, false);

            int sccCount = 0;

            while (!stack.isEmpty()) {
                int current = stack.pop();
                if (!visited[current]) {
                    reverseDfs(current);
                    sccCount++;
                }
            }
            sb.append(sccCount).append('\n');
        }
        System.out.print(sb.toString());
    }

    private static void dfs(int node) {
        visited[node] = true;
        for (int next : graph[node]) {
            if (!visited[next]) {
                dfs(next);
            }
        }
        stack.push(node);
    }

    private static void reverseDfs(int node) {
        visited[node] = true;
        for (int next : graph[node]) {
            if (!visited[next]) {
                reverseDfs(next);
            }
        }
    }
}

/*
1
3 2
1 2
2 3

1
*/