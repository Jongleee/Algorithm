package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import com.example.algorithm.java.Main;

public class IndependentSetOfTree {
    private int n;
    private int[] weights;
    private Node[] graph;
    private int[][] dp;
    private boolean[] visited;
    private List<Integer> group;

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.run();
    }

    public void run() throws IOException {
        init();
        calculateDP(1);

        StringBuilder answer = new StringBuilder();
        group = new ArrayList<>();
        visited = new boolean[n + 1];

        if (dp[1][0] > dp[1][1]) {
            answer.append(dp[1][0]).append("\n");
            trace(1, false);
        } else {
            answer.append(dp[1][1]).append("\n");
            trace(1, true);
        }

        Collections.sort(group);
        for (int vertex : group) {
            answer.append(vertex).append(" ");
        }

        System.out.println(answer.toString().trim());
    }

    private void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        weights = new int[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            weights[i] = Integer.parseInt(st.nextToken());
        }

        graph = new Node[n + 1];
        dp = new int[n + 1][2];
        visited = new boolean[n + 1];

        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            insertNode(a, b);
            insertNode(b, a);
        }
    }

    private static class Node {
        int number;
        Node parent;

        Node(int number, Node parent) {
            this.number = number;
            this.parent = parent;
        }
    }

    private void insertNode(int parent, int child) {
        if (graph[parent] == null) {
            graph[parent] = new Node(child, null);
        } else {
            graph[parent] = new Node(child, graph[parent]);
        }
    }

    private void calculateDP(int current) {
        visited[current] = true;
        dp[current][1] = weights[current];

        Node neighbor = graph[current];
        while (neighbor != null) {
            if (!visited[neighbor.number]) {
                calculateDP(neighbor.number);

                dp[current][0] += Math.max(dp[neighbor.number][0], dp[neighbor.number][1]);
                dp[current][1] += dp[neighbor.number][0];
            }
            neighbor = neighbor.parent;
        }
    }

    private void trace(int current, boolean include) {
        visited[current] = true;

        if (include) {
            group.add(current);
            Node neighbor = graph[current];
            while (neighbor != null) {
                if (!visited[neighbor.number]) {
                    trace(neighbor.number, false);
                }
                neighbor = neighbor.parent;
            }
        } else {
            Node neighbor = graph[current];
            while (neighbor != null) {
                if (!visited[neighbor.number]) {
                    trace(neighbor.number, dp[neighbor.number][1] > dp[neighbor.number][0]);
                }
                neighbor = neighbor.parent;
            }
        }
    }
}

/*
7
10 30 40 10 20 20 70
1 2
2 3
4 3
4 5
6 2
6 7

140
1 3 5 7
*/