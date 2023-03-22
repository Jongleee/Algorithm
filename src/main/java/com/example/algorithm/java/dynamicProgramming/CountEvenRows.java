package com.example.algorithm.java.dynamicProgramming;

public class CountEvenRows {
    public static final int MOD = 10000019;

    public static int solution(int[][] a) {
        int row = a.length;
        int col = a[0].length;

        int[][] combis = new int[row + 2][row + 2];
        combis[0][0] = 1;

        for (int i = 1; i <= row; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0)
                    combis[i][j] = 1;
                else if (i == j)
                    combis[i][j] = 1;
                else
                    combis[i][j] = (combis[i - 1][j - 1] + combis[i - 1][j]) % MOD;
            }
        }

        int[] numOfOne = new int[col+2];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (a[i][j] == 1)
                    numOfOne[j]++;
            }
        }

        int[][] DP = new int[col + 2][row + 2];
        DP[1][row - numOfOne[0]] = combis[row][row - numOfOne[0]];

        for (int curCol = 0; curCol <= col; curCol++) {
            for (int curRow = 0; curRow <= row; curRow++) {
                if (DP[curCol][curRow] == 0)
                    continue;

                for (int one = 0; one <= numOfOne[curCol]; one++) {
                    int next = (curRow - one) + (numOfOne[curCol] - one);
                    if (next > row || curRow < one)
                        continue;
                    int cases = (int) (((long) combis[curRow][one] * combis[row - curRow][numOfOne[curCol] - one])
                            % MOD);

                    DP[curCol + 1][next] = (DP[curCol + 1][next] + (int) (((long) DP[curCol][curRow] * cases) % MOD))
                            % MOD;
                }
            }
        }

        return DP[col][row];
    }
    public static void main(String[] args) {
        int [][] a1 = {{0,1,0},{1,1,1},{1,1,0},{0,1,1}};
        int[][] a2={{1,0,0},{1,0,0}};	
        int[][] a3 = {{1,0,0,1,1},{0,0,0,0,0},{1,1,0,0,0},{0,0,0,0,1}};
        System.out.println(solution(a1));//6
        System.out.println(solution(a2));//0
        System.out.println(solution(a3));//72
    }
}
