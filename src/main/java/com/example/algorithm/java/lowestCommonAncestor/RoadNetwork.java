package com.example.algorithm.java.lowestCommonAncestor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class RoadNetwork {
    private static final int INF = 987654321;
    private static int logN, n;
    private static List<Edge>[] adjList;
    private static int[] depth;
    private static int[][] parentST, minWeightST, maxWeightST;
    
    private static class Edge {
        int to, weight;
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
    
    private static class Result {
        int minWeight, maxWeight;
        Result(int minWeight, int maxWeight) {
            this.minWeight = minWeight;
            this.maxWeight = maxWeight;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        n = Integer.parseInt(br.readLine());
        logN = (int) Math.ceil(Math.log(n) / Math.log(2));
        initialize();
        
        for (int i = 1; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            adjList[a].add(new Edge(b, w));
            adjList[b].add(new Edge(a, w));
        }
        
        dfs(1, 1);
        buildSparseTable();
        
        int q = Integer.parseInt(br.readLine());
        while (q-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            Result res = findMinMaxWeights(c, d);
            sb.append(res.minWeight).append(" ").append(res.maxWeight).append("\n");
        }
        
        System.out.print(sb);
    }

    @SuppressWarnings("unchecked")
    private static void initialize() {
        adjList = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) adjList[i] = new ArrayList<>();
        
        depth = new int[n + 1];
        parentST = new int[logN + 1][n + 1];
        minWeightST = new int[logN + 1][n + 1];
        maxWeightST = new int[logN + 1][n + 1];
        for (int[] row : minWeightST) Arrays.fill(row, INF);
    }

    private static void dfs(int node, int d) {
        depth[node] = d;
        for (Edge e : adjList[node]) {
            if (depth[e.to] == 0) {
                parentST[0][e.to] = node;
                minWeightST[0][e.to] = maxWeightST[0][e.to] = e.weight;
                dfs(e.to, d + 1);
            }
        }
    }
    
    private static void buildSparseTable() {
        for (int k = 1; k <= logN; k++) {
            for (int i = 1; i <= n; i++) {
                int ancestor = parentST[k - 1][i];
                parentST[k][i] = parentST[k - 1][ancestor];
                minWeightST[k][i] = Math.min(minWeightST[k - 1][i], minWeightST[k - 1][ancestor]);
                maxWeightST[k][i] = Math.max(maxWeightST[k - 1][i], maxWeightST[k - 1][ancestor]);
            }
        }
    }
    
    private static Result findMinMaxWeights(int node1, int node2) {
        if (depth[node1] > depth[node2]) return findMinMaxWeights(node2, node1);
        
        int minW = INF, maxW = 0;
        int diff = depth[node2] - depth[node1];
        
        for (int k = logN; k >= 0; k--) {
            if ((diff & (1 << k)) != 0) {
                minW = Math.min(minW, minWeightST[k][node2]);
                maxW = Math.max(maxW, maxWeightST[k][node2]);
                node2 = parentST[k][node2];
            }
        }
        
        if (node1 == node2) return new Result(minW, maxW);
        
        for (int k = logN; k >= 0; k--) {
            if (parentST[k][node1] != parentST[k][node2]) {
                minW = Math.min(minW, Math.min(minWeightST[k][node1], minWeightST[k][node2]));
                maxW = Math.max(maxW, Math.max(maxWeightST[k][node1], maxWeightST[k][node2]));
                node1 = parentST[k][node1];
                node2 = parentST[k][node2];
            }
        }
        
        minW = Math.min(minW, Math.min(minWeightST[0][node1], minWeightST[0][node2]));
        maxW = Math.max(maxW, Math.max(maxWeightST[0][node1], maxWeightST[0][node2]));
        
        return new Result(minW, maxW);
    }
}

/*
9
1 2 2
2 3 1
3 4 5
2 7 4
1 5 3
5 6 1
5 9 2
1 8 3
5
6 9
7 8
9 4
1 2
7 3

1 2
2 4
1 5
2 2
1 4
*/