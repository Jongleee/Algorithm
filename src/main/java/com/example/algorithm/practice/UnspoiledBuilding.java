package com.example.algorithm.practice;

public class UnspoiledBuilding {
    static int[][] sum;
    static int n;
    static int m;

    public static int solution(int[][] board, int[][] skill) {
        n = board.length;
        m = board[0].length;

        sum = new int[n + 1][m + 1];
        for (int[] s : skill) {
            int y1 = s[1];
            int x1 = s[2];
            int y2 = s[3];
            int x2 = s[4];
            int durability = (s[0] == 1 ? -s[5] : s[5]);

            sum[y1][x1] += durability;
            sum[y1][x2 + 1] += (durability * -1);
            sum[y2 + 1][x1] += (durability * -1);
            sum[y2 + 1][x2 + 1] += durability;
        }
        for (int y = 1; y < n; y++) {
            for (int x = 0; x < m; x++) {
                sum[y][x] += sum[y - 1][x];
            }
        }
        for (int x = 1; x < m; x++) {
            for (int y = 0; y < n; y++) {
                sum[y][x] += sum[y][x - 1];
            }
        }
        int answer = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                board[i][j] += sum[i][j];
                if (board[i][j] > 0)
                    answer++;
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        System.out.println(
                solution(new int[][] { { 5, 5, 5, 5, 5 }, { 5, 5, 5, 5, 5 }, { 5, 5, 5, 5, 5 }, { 5, 5, 5, 5, 5 } },
                        new int[][] { { 1, 0, 0, 3, 4, 4 }, { 1, 2, 0, 2, 3, 2 }, { 2, 1, 0, 3, 1, 2 },
                                { 1, 0, 1, 3, 3, 1 } }));
    }
}
