package com.example.algorithm.java.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class GreenClothedZelda {
    static int n;
    static int[][] map;
    static final int[] dr = {-1, 0, 1, 0};
    static final int[] dc = {0, 1, 0, -1};

    static class Node implements Comparable<Node> {
        int r;
        int c;
        int cost;
        
        Node(int r, int c, int cost) {
            this.r = r;
            this.c = c;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int tc = 0;
        
        while (true) {
            n = Integer.parseInt(br.readLine());
            if (n == 0) break;
            
            map = new int[n][n];
            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < n; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            sb.append("Problem ").append(++tc).append(": ").append(dijkstra()).append("\n");
        }
        System.out.print(sb.toString());
    }

    static int dijkstra() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[][] minCost = new int[n][n];
        
        for (int[] row : minCost) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        
        pq.add(new Node(0, 0, map[0][0]));
        minCost[0][0] = map[0][0];
        
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int r = current.r;
            int c = current.c;
            int cost = current.cost;
            
            if (r == n - 1 && c == n - 1) return cost;
            
            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];
                
                if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                    int newCost = cost + map[nr][nc];
                    if (newCost < minCost[nr][nc]) {
                        minCost[nr][nc] = newCost;
                        pq.add(new Node(nr, nc, newCost));
                    }
                }
            }
        }
        return -1;
    }
}


/*
3
5 5 4
3 9 1
3 2 7
5
3 7 2 0 1
2 8 0 9 1
1 2 1 8 1
9 8 9 2 0
3 6 5 1 5
7
9 0 5 1 1 5 3
4 1 2 1 6 5 3
0 7 6 1 6 8 5
1 1 7 8 3 2 3
9 4 0 7 6 4 1
5 8 3 2 4 8 3
7 4 8 4 8 3 4
0

Problem 1: 20
Problem 2: 19
Problem 3: 36
 */