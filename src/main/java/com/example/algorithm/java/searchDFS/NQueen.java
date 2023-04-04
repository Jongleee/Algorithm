package com.example.algorithm.java.searchDFS;

public class NQueen {
    private static final int MAX = 12;
    private static int[] location = new int[MAX];
    private static int cnt = 0;

    public static int solution(int n) {
        dfs(0, n);
        return cnt;
    }
    private static boolean isPossible(int row) {
        for (int i = 0; i < row; i++) {
            if (location[row] == location[i]
                    || Math.abs(row - i) == Math.abs(location[row] - location[i])) {
                return false;
            }
        }
        return true;
    }

    private static void dfs(int row, int n) {
        if (row == n) {
            cnt++;
            return;
        }

        for (int col = 0; col < n; col++) {
            location[row] = col;
            if (isPossible(row)) {
                dfs(row + 1,n);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(solution(4));
    }
}
