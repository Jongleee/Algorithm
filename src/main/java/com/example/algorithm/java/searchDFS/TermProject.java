package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class TermProject {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCases = Integer.parseInt(br.readLine());

        for (int t = 0; t < testCases; t++) {
            int n = Integer.parseInt(br.readLine());
            int[] arr = new int[n + 1];
            boolean[] visited = new boolean[n + 1];
            boolean[] finished = new boolean[n + 1];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= n; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
            }

            int cycleCount = countCycles(n, arr, visited, finished);
            System.out.println(n - cycleCount);
        }
    }

    private static int countCycles(int n, int[] arr, boolean[] visited, boolean[] finished) {
        int cycleCount = 0;

        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                cycleCount += dfs(i, arr, visited, finished);
            }
        }
        return cycleCount;
    }

    private static int dfs(int current, int[] arr, boolean[] visited, boolean[] finished) {
        int cycleCount = 0;

        visited[current] = true;
        int next = arr[current];

        if (!visited[next]) {
            cycleCount += dfs(next, arr, visited, finished);
        } else if (!finished[next]) {
            cycleCount++;
            for (int i = next; i != current; i = arr[i]) {
                cycleCount++;
            }
        }

        finished[current] = true;
        return cycleCount;
    }
}

/*
2
7
3 1 3 7 3 4 6
8
1 2 3 4 5 6 7 8

3
0
*/