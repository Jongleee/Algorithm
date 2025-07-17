package com.example.algorithm.java.floydWarshall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BusinessExpansion {
    private static class Node {
        int x, y;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][n];
        int[][] dist = new int[n][n];
        boolean[][] visited = new boolean[n][n];

        init(n, map, dist);

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            map[u][v] = 1;
        }

        floydWarshall(n, map);
        dist[1][1] = 1;

        while (true) {
            Node current = findNext(n, dist, visited);
            if (current.x == 0 && current.y == 0)
                break;

            visited[current.x][current.y] = true;
            updateDistance(n, current, map, dist);
        }

        System.out.println(dist[0][0]);
    }

    private static void init(int n, int[][] map, int[][] dist) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                map[i][j] = (i == j) ? 0 : 987654321;
                dist[i][j] = 987654321;
            }
        }
    }

    private static void floydWarshall(int n, int[][] map) {
        for (int k = 0; k < n; ++k)
            for (int i = 0; i < n; ++i)
                for (int j = 0; j < n; ++j)
                    map[i][j] = Math.min(map[i][j], map[i][k] + map[k][j]);
    }

    private static Node findNext(int n, int[][] dist, boolean[][] visited) {
        int minX = -1, minY = -1;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (visited[i][j])
                    continue;
                if (minX == -1 || dist[i][j] < dist[minX][minY]) {
                    minX = i;
                    minY = j;
                }
            }
        }
        if (minX == -1)
            return new Node(0, 0);
        return new Node(minX, minY);
    }

    private static void updateDistance(int n, Node node, int[][] map, int[][] dist) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i != node.x && i != node.y && j != node.x && j != node.y) {
                    int cost = dist[node.x][node.y] + map[node.y][i] + map[i][j] + map[j][node.x] - 1;
                    if (cost < dist[i][j]) {
                        dist[i][j] = cost;
                    }
                }
            }
        }
    }
}

/*
6 7
1 3
3 4
4 5
5 1
4 2
2 6
6 3

6


9 11
1 3
3 4
4 2
2 5
5 3
3 6
6 1
2 7
7 8
8 9
9 1

6
*/