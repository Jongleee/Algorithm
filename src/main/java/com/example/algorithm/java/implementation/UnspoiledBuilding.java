package com.example.algorithm.java.implementation;

public class UnspoiledBuilding {
    public int solution(int[][] board, int[][] skill) {
        int n = board.length;
        int m = board[0].length;

        int[][] sum = calculateSkillSum(n, m, skill);

        return updateBoardAndGetCount(board, sum, n, m);
    }

    private int[][] calculateSkillSum(int n, int m, int[][] skill) {
        int[][] sum = new int[n + 1][m + 1];

        for (int[] s : skill) {
            int y1 = s[1];
            int x1 = s[2];
            int y2 = s[3];
            int x2 = s[4];
            int durability = (s[0] == 1 ? -s[5] : s[5]);

            sum[y1][x1] += durability;
            sum[y1][x2 + 1] += (-durability);
            sum[y2 + 1][x1] += (-durability);
            sum[y2 + 1][x2 + 1] += durability;
        }

        return sum;
    }

    private int updateBoardAndGetCount(int[][] board, int[][] sum, int n, int m) {
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
                if (board[i][j] > 0) {
                    answer++;
                }
            }
        }

        return answer;
    }

    // @Test
    // void 정답() {
    //     int[][] b1 = { { 5, 5, 5, 5, 5 }, { 5, 5, 5, 5, 5 }, { 5, 5, 5, 5, 5 }, { 5, 5, 5, 5, 5 } };
    //     int[][] s1 = { { 1, 0, 0, 3, 4, 4 }, { 1, 2, 0, 2, 3, 2 }, { 2, 1, 0, 3, 1, 2 },
    //             { 1, 0, 1, 3, 3, 1 } };
    //     Assertions.assertEquals(10, solution(b1, s1));
    //     int[][] b2 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
    //     int[][] s2 = { { 1, 1, 1, 2, 2, 4 }, { 1, 0, 0, 1, 1, 2 }, { 2, 2, 0, 2, 0, 100 } };
    //     Assertions.assertEquals(6, solution(b2, s2));
    // }
}
