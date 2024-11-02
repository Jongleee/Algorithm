package com.example.algorithm.java.bipartiteMatching;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class StoneRemoval {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        ArrayList<Integer>[] adjList = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adjList[a].add(b);
        }

        int[] matched = new int[n + 1];
        Arrays.fill(matched, -1);

        int matchCount = 0;
        for (int i = 1; i <= n; i++) {
            boolean[] visited = new boolean[n + 1];
            if (findMatch(i, adjList, matched, visited)) {
                matchCount++;
            }
        }

        System.out.println(matchCount);
    }

    private static boolean findMatch(int node, ArrayList<Integer>[] adjList, int[] matched, boolean[] visited) {
        for (int neighbor : adjList[node]) {
            if (visited[neighbor]) {
                continue;
            }
            visited[neighbor] = true;
            if (matched[neighbor] == -1 || findMatch(matched[neighbor], adjList, matched, visited)) {
                matched[neighbor] = node;
                return true;
            }
        }
        return false;
    }
}

/*
3 4
1 1
1 3
2 2
3 2

2
*/