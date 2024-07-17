package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class HikingEnthusiast {
    static int n;
    static long result = 0;
    static int[] dp;
    static List<Integer>[] adjList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());

        initialize();

        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adjList[a].add(b);
            adjList[b].add(a);
        }

        calculateDP(1);
        System.out.println(result);
    }

    private static int calculateDP(int currentNode) {
        dp[currentNode] = 1;
        for (int neighbor : adjList[currentNode]) {
            if (dp[neighbor] == 0) {
                dp[currentNode] += calculateDP(neighbor);
            }
        }
        if (currentNode != 1) {
            result += combination(n) - combination(n - dp[currentNode]);
        }
        return dp[currentNode];
    }

    private static long combination(int n) {
        return (long) n * (long) (n - 1) / 2;
    }

    @SuppressWarnings("unchecked")
    private static void initialize() {
        adjList = new ArrayList[n + 1];
        dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            adjList[i] = new ArrayList<>();
        }
    }
}

/*
3
1 2
2 3

5


8
6 2
7 5
3 4
5 6
1 5
4 1
8 6

84
 */