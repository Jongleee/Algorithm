package com.example.algorithm.java.dynamicProgramming;

public class CountEvenRows {
    public static final int MOD = 10000019;

    public static int solution(int[][] a) {
        int row = a.length;
        int col = a[0].length;

        int[][] combinations = makeCombinations(row);

        int[] numOfOne = countNumOfOne(a, row, col);

        int[][] dp = new int[col + 1][row + 1];
        dp[1][row - numOfOne[0]] = combinations[row][row - numOfOne[0]];

        for (int curCol = 0; curCol < col; curCol++) {
            for (int curRow = 0; curRow < row; curRow++) {
                if (dp[curCol][curRow] == 0)
                    continue;

                for (int one = 0; one <= numOfOne[curCol]; one++) {
                    int next = (curRow - one) + (numOfOne[curCol] - one);
                    if (next > row || curRow < one)
                        continue;
                    int cases = (int) (((long) combinations[curRow][one]
                            * combinations[row - curRow][numOfOne[curCol] - one])
                            % MOD);

                    dp[curCol + 1][next] = (dp[curCol + 1][next] + (int) (((long) dp[curCol][curRow] * cases) % MOD))
                            % MOD;
                }
            }
        }

        return dp[col][row];
    }

    private static int[][] makeCombinations(int row) {
        int[][] combinations = new int[row + 1][row + 1];
        combinations[0][0] = 1;

        for (int i = 1; i <= row; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0)
                    combinations[i][j] = 1;
                else if (i == j)
                    combinations[i][j] = 1;
                else
                    combinations[i][j] = (combinations[i - 1][j - 1] + combinations[i - 1][j]) % MOD;
            }
        }
        return combinations;
    }

    private static int[] countNumOfOne(int[][] a, int row, int col) {
        int[] numOfOne = new int[col + 1];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (a[i][j] == 1)
                    numOfOne[j]++;
            }
        }
        return numOfOne;
    }

    public static void main(String[] args) {
        int[][] a1 = { { 0, 1, 0 }, { 1, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 } };
        int[][] a2 = { { 1, 0, 0 }, { 1, 0, 0 } };
        int[][] a3 = { { 1, 0, 0, 1, 1 }, { 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 0 }, { 0, 0, 0, 0, 1 } };
        System.out.println(solution(a1));// 6
        System.out.println(solution(a2));// 0
        System.out.println(solution(a3));// 72
    }
}
