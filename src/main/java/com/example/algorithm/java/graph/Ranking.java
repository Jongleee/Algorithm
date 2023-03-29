package com.example.algorithm.java.graph;

public class Ranking {
    public static int solution(int n, int[][] results) {
        int answer = 0;
        int[][] graph = new int[n + 1][n + 1];

        makeGraph(n, results, graph);

        for (int i = 1; i <= n; i++) {
            int cnt = 0;
            for (int j = 1; j <= n; j++) {
                if (graph[i][j] != 0)
                    cnt++;
            }
            if (cnt == n - 1)
                answer++;
        }

        return answer;
    }

    private static void makeGraph(int n, int[][] results, int[][] graph) {
        for (int i = 0; i < results.length; i++) {
            graph[results[i][0]][results[i][1]] = 1;
            graph[results[i][1]][results[i][0]] = -1;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                findDirection(n, graph, i, j);
            }
        }
    }

    private static void findDirection(int n, int[][] graph, int i, int j) {
        for (int k = 1; k <= n; k++) {
            if (graph[i][k] == 1 && graph[k][j] == 1) {
                graph[i][j] = 1;
                graph[j][i] = -1;
            }
            if (graph[i][k] == -1 && graph[k][j] == -1) {
                graph[i][j] = -1;
                graph[j][i] = 1;
            }
        }
    }


    public static void main(String[] args) {
        System.out.println(solution(5, new int[][] { { 4, 3 }, { 4, 2 }, { 3, 2 }, { 1, 2 }, { 2, 5 } }));
    }
}
