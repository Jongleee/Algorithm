package com.example.algorithm.java.practice.dynamicProgramming;

public class CodingTestStudy {
    public int solution(int alp, int cop, int[][] problems) {
        int goalAlp = getMaxValue(problems, 0);
        int goalCop = getMaxValue(problems, 1);

        if (goalAlp <= alp && goalCop <= cop) {
            return 0;
        }

        alp = Math.min(alp, goalAlp);
        cop = Math.min(cop, goalCop);

        int[][] dp = initializeDP(alp, cop, goalAlp, goalCop);

        for (int i = alp; i <= goalAlp; i++) {
            for (int j = cop; j <= goalCop; j++) {
                dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + 1);
                dp[i][j + 1] = Math.min(dp[i][j + 1], dp[i][j] + 1);
                updateDP(dp, problems, i, j, goalAlp, goalCop);
            }
        }

        return dp[goalAlp][goalCop];
    }

    private int getMaxValue(int[][] problems, int index) {
        int maxValue = 0;
        for (int[] problem : problems) {
            maxValue = Math.max(maxValue, problem[index]);
        }
        return maxValue;
    }

    private int[][] initializeDP(int alp, int cop, int goalAlp, int goalCop) {
        int[][] dp = new int[goalAlp + 2][goalCop + 2];
        for (int i = alp; i <= goalAlp; i++) {
            for (int j = cop; j <= goalCop; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        dp[alp][cop] = 0;
        return dp;
    }

    private void updateDP(int[][] dp, int[][] problems, int i, int j, int goalAlp, int goalCop) {
        for (int[] problem : problems) {
            int p0 = problem[0];
            int p1 = problem[1];
            int p2 = problem[2];
            int p3 = problem[3];
            int p4 = problem[4];

            if (i >= p0 && j >= p1) {
                if (i + p2 > goalAlp && j + p3 > goalCop) {
                    dp[goalAlp][goalCop] = Math.min(dp[goalAlp][goalCop], dp[i][j] + p4);
                } else if (i + p2 > goalAlp) {
                    dp[goalAlp][j + p3] = Math.min(dp[goalAlp][j + p3], dp[i][j] + p4);
                } else if (j + p3 > goalCop) {
                    dp[i + p2][goalCop] = Math.min(dp[i + p2][goalCop], dp[i][j] + p4);
                } else if (i + p2 <= goalAlp && j + p3 <= goalCop) {
                    dp[i + p2][j + p3] = Math.min(dp[i + p2][j + p3], dp[i][j] + p4);
                }
            }
        }
    }

    // @Test
    // void 정답() {
    //     int[][] p1 = { { 10, 15, 2, 1, 2 }, { 20, 20, 3, 3, 4 } };
    //     int[][] p2 = { { 0, 0, 2, 1, 2 }, { 4, 5, 3, 1, 2 }, { 4, 11, 4, 0, 2 }, { 10, 4, 0, 4, 2 } };

    //     Assertions.assertEquals(15, solution(10, 10, p1));
    //     Assertions.assertEquals(13, solution(0, 0, p2));
    // }
}
