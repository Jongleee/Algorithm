package com.example.algorithm.java.dijkstra;

public class Delivery {
    static final int MAX = 500001;

    public int solution(int n, int[][] road, int k) {
        int answer = 0;

        int[][] map = new int[n + 1][n + 1];
        int[] dist = new int[n + 1];
        boolean[] visited = new boolean[n + 1];

        init(n, road, map, dist);

        visited[1] = true;

        for (int i = 1; i <= n - 1; i++) { 

            int minIdx = 1;
            int minValue = MAX;
            for (int j = 2; j <= n; j++) {
                if (!visited[j] && dist[j] < minValue) {
                    minValue = dist[j];
                    minIdx = j;
                }
            }

            visited[minIdx] = true;

            for (int j = 2; j <= n; j++) {
                if (dist[j] > dist[minIdx] + map[minIdx][j]) {
                    dist[j] = dist[minIdx] + map[minIdx][j];
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            if (dist[i] <= k) {
                answer++;
            }
        }

        return answer;
    }

    private void init(int n, int[][] road, int[][] map, int[] dist) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) {
                    continue;
                }
                map[i][j] = MAX;
            }
        }

        for (int i = 0; i < road.length; i++) {
            int a = road[i][0];
            int b = road[i][1];
            int w = road[i][2];

            if (map[a][b] > w) {
                map[a][b] = w;
                map[b][a] = w;
            }
        }

        for (int i = 2; i <= n; i++) {
            dist[i] = (dist[i] == 0) ? MAX : map[1][i];
        }
    }
}
