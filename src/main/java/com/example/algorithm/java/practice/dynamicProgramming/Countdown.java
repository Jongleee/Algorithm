package com.example.algorithm.java.practice.dynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;

public class Countdown {

    private static ArrayList<Integer> score = new ArrayList<>();
    private static ArrayList<Integer> multiScore = new ArrayList<>();
    private static int max = 32421;
    private static int[][] dp;

    public static void init(int t) {
        for (int i = 1; i <= t; i++)
            for (int j = 0; j < 1; j++)
                dp[i][j] = max;

        score.add(50);
        for (int i = 1; i < 21; i++)
            score.add(i);
        for (int i = 1; i < 21; i++) {
            for (int j = 2; j < 4; j++) {
                if (i * j <= 20)
                    continue;
                multiScore.add(i * j);
            }
        }
    }

    public static void setMin(int[] res, int[] comp) {
        if (res[0] > comp[0]) {
            res[0] = comp[0];
            res[1] = comp[1];
        } else if (res[0] == comp[0] && res[1] < comp[1])
            res[1] = comp[1];
    }

    public static int[] solve(int remain) {
        if (remain == 0)
            return new int[] { 0, 0 };
        if (remain < 0)
            return new int[] { max, max };
        if (dp[remain][0] != max)
            return dp[remain];

        int[] result = new int[] { max, max };

        // Single, Ball 숫자 순회 tmpRes[0] + 1, tmpRes[0] + 1
        for (int i = 0; i < score.size(); i++) {
            int[] tmpRes = solve(remain - score.get(i));
            setMin(result, new int[] { tmpRes[0] + 1, tmpRes[1] + 1 });
        }
        // Double, Tripple 숫자 순회
        for (int i = 0; i < multiScore.size(); i++) {
            int[] tmpRes = solve(remain - multiScore.get(i));
            setMin(result, new int[] { tmpRes[0] + 1, tmpRes[1] });
        }

        dp[remain][0] = result[0];
        dp[remain][1] = result[1];
        return dp[remain];
    }

    public static int[] solution(int target) {
        dp = new int[target + 1][2];
        init(target);
        return solve(target);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(58)));
        // 2,2
    }
}
