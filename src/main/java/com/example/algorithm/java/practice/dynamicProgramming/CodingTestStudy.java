package com.example.algorithm.java.practice.dynamicProgramming;

public class CodingTestStudy {
    int goalAlp = 0;
    int goalCop = 0;

    public int solution(int alp, int cop, int[][] problems) {

        getGoal(problems);

        if (goalAlp <= alp && goalCop <= cop) {
            return 0;
        }

        if (alp >= goalAlp) {
            alp = goalAlp;
        }
        if (cop >= goalCop) {
            cop = goalCop;
        }

        int[][] dp = new int[goalAlp + 2][goalCop + 2];

        dpSetting(alp, cop, dp);

        dp[alp][cop] = 0;

        for (int i = alp; i <= goalAlp; i++) {
            for (int j = cop; j <= goalCop; j++) {

                dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + 1);

                dp[i][j + 1] = Math.min(dp[i][j + 1], dp[i][j] + 1);

                findAnswer(problems, dp, i, j);
            }
        }

        return dp[goalAlp][goalCop];
    }

    private void getGoal(int[][] problems) {
        for (int i = 0; i < problems.length; i++) {
            if (problems[i][0] > goalAlp)
                goalAlp = problems[i][0];

            if (problems[i][1] > goalCop)
                goalCop = problems[i][1];
        }
    }

    private void dpSetting(int alp, int cop, int[][] dp) {
        for (int i = alp; i <= goalAlp; i++) {
            for (int j = cop; j <= goalCop; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    private void findAnswer(int[][] problems, int[][] dp, int i, int j) {
        for (int[] p : problems) {

            if (i >= p[0] && j >= p[1]) {
                if (i + p[2] > goalAlp && j + p[3] > goalCop) {
                    dp[goalAlp][goalCop] = Math.min(dp[goalAlp][goalCop], dp[i][j] + p[4]);
                } else if (i + p[2] > goalAlp) {
                    dp[goalAlp][j + p[3]] = Math.min(dp[goalAlp][j + p[3]], dp[i][j] + p[4]);
                } else if (j + p[3] > goalCop) {
                    dp[i + p[2]][goalCop] = Math.min(dp[i + p[2]][goalCop], dp[i][j] + p[4]);
                } else if (i + p[2] <= goalAlp && j + p[3] <= goalCop) {
                    dp[i + p[2]][j + p[3]] = Math.min(dp[i + p[2]][j + p[3]], dp[i][j] + p[4]);
                }
            }

        }
    }
}
