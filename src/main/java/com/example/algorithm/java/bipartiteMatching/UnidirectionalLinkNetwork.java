package com.example.algorithm.java.bipartiteMatching;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class UnidirectionalLinkNetwork {
    private static class Matcher {
        private final int n;
        private final List<List<Integer>> graph;
        private final int[] matched;
        private boolean[] visited;

        Matcher(int n) {
            this.n = n;
            this.graph = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                graph.add(new ArrayList<>());
            }
            this.matched = new int[n];
            Arrays.fill(matched, -1);
        }

        void addEdge(int from, int to) {
            graph.get(from).add(to);
        }

        int maxMatching() {
            int matches = 0;
            for (int i = 0; i < n; i++) {
                visited = new boolean[n];
                if (dfs(i)) {
                    matches++;
                }
            }
            return matches;
        }

        private boolean dfs(int node) {
            for (int neighbor : graph.get(node)) {
                if (visited[neighbor]) {
                    continue;
                }
                visited[neighbor] = true;
                if (matched[neighbor] == -1 || dfs(matched[neighbor])) {
                    matched[neighbor] = node;
                    return true;
                }
            }
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        int testCases = Integer.parseInt(br.readLine());

        for (int t = 0; t < testCases; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            Matcher matcher = new Matcher(n);

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                matcher.addEdge(u, v);
            }

            sb.append(matcher.maxMatching()).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}

/*
3
4 3
3 2
1 0
2 3
6 6
0 1
1 2
2 3
3 1
3 4
4 5
14 19
0 1
1 2
2 3
3 4
4 5
5 0
5 4
2 1
2 6
6 7
7 8
8 9
9 1
8 7
7 10
10 11
11 12
12 13
13 8

3
5
13
*/