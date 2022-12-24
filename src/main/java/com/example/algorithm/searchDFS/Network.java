package com.example.algorithm.searchDFS;

public class Network {
    public static int solution(int n, int[][] computers) {
        int answer = 0;
        boolean[] check = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!check[i]) {
                dfs(computers, check, i);
                answer++;
            }
        }
        return answer;
    }

    static void dfs(int[][] computers, boolean[] check, int num) {
        check[num] = true;
        for (int i = 0; i < computers.length; i++) {
            if (computers[num][i] == 1 && !check[i]) {
                dfs(computers, check, i);
            }
        }
    }
    public static void main(String[] args) {
        System.out.println(solution(3, new int[][]{{1, 1, 0}, {1, 1, 1}, {0, 1, 1}}));
    }
}
