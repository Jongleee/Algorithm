package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SocialNetworkService {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(br.readLine());
        Node[] graph = new Node[n + 1];
        int[][] dp = new int[2][n + 1];
        boolean[] visited = new boolean[n + 1];

        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            graph[u] = new Node(v, graph[u]);
            graph[v] = new Node(u, graph[v]);
        }

        dfs(1, dp, visited, graph);
        System.out.print(Math.min(dp[0][1], dp[1][1]));
    }

    private static void dfs(int num, int[][] dp, boolean[] visited, Node[] graph) {
        visited[num] = true;
        dp[1][num] = 1;

        for (Node neighbor = graph[num]; neighbor != null; neighbor = neighbor.next) {
            if (visited[neighbor.num]) {
                continue;
            }
            dfs(neighbor.num, dp, visited, graph);

            dp[0][num] += dp[1][neighbor.num];
            dp[1][num] += Math.min(dp[0][neighbor.num], dp[1][neighbor.num]);
        }
    }

    private static class Node {
        int num;
        Node next;

        public Node(int num, Node next) {
            this.num = num;
            this.next = next;
        }
    }
}

/*
8
1 2
1 3
1 4
2 5
2 6
4 7
4 8

3


9
1 2
1 3
2 4
3 5
3 6
4 7
4 8
4 9

3
*/