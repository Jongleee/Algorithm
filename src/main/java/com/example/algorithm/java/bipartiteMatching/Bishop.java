package com.example.algorithm.java.bipartiteMatching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Bishop {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] board = new int[n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] diagonalGroup1 = new int[n][n];
        int[][] diagonalGroup2 = new int[n][n];
        int groupCount = createDiagonalGroups(n, diagonalGroup1, diagonalGroup2);

        List<Integer>[] graph = buildBipartiteGraph(n, board, diagonalGroup1, diagonalGroup2, groupCount);

        int[] match = new int[groupCount];
        boolean[] visited = new boolean[groupCount];
        Arrays.fill(match, -1);

        int bishopCount = 0;
        for (int u = 1; u < groupCount; u++) {
            Arrays.fill(visited, false);
            if (findMatching(u, graph, match, visited)) {
                bishopCount++;
            }
        }

        System.out.println(bishopCount);
    }

    private static int createDiagonalGroups(int n, int[][] group1, int[][] group2) {
        int maxGroup = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                group1[i][j] = i + j + 1;
                maxGroup = Math.max(maxGroup, group1[i][j]);
            }
        }

        int label = 1;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                group2[i][j] = label + j;
            }
            label++;
        }

        return maxGroup + 1;
    }

    @SuppressWarnings("unchecked")
    private static List<Integer>[] buildBipartiteGraph(int n, int[][] board, int[][] group1, int[][] group2, int size) {
        List<Integer>[] graph = new ArrayList[size];
        for (int i = 0; i < size; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 1) {
                    graph[group1[i][j]].add(group2[i][j]);
                }
            }
        }

        return graph;
    }

    private static boolean findMatching(int current, List<Integer>[] graph, int[] match, boolean[] visited) {
        for (int next : graph[current]) {
            if (visited[next])
                continue;
            visited[next] = true;

            if (match[next] == -1 || findMatching(match[next], graph, match, visited)) {
                match[next] = current;
                return true;
            }
        }
        return false;
    }
}

/*
5
1 1 0 1 1
0 1 0 0 0
1 0 1 0 1
1 0 0 0 0
1 0 1 1 1

7
*/