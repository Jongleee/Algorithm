package com.example.algorithm.java.practice.dynamicProgramming;

public class CodingTestStudy {
    public class Solution {
        public int solution(int alp, int cop, int[][] problems) {
            int goalAlp = getMaxValue(problems, 0);
            int goalCop = getMaxValue(problems, 1);

            if (goalAlp <= alp && goalCop <= cop) {
                return 0;
            }

            alp = Math.min(alp, goalAlp);
            cop = Math.min(cop, goalCop);

            int[][] dp = new int[goalAlp + 2][goalCop + 2];
            initializeDP(dp, alp, cop, goalAlp, goalCop);

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

        private void initializeDP(int[][] dp, int alp, int cop, int goalAlp, int goalCop) {
            for (int i = alp; i <= goalAlp; i++) {
                for (int j = cop; j <= goalCop; j++) {
                    dp[i][j] = Integer.MAX_VALUE;
                }
            }
            dp[alp][cop] = 0;
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
    }
}
