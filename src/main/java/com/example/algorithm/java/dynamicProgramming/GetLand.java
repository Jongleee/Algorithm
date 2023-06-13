package com.example.algorithm.java.dynamicProgramming;

public class GetLand {
    int solution(int[][] land) {
        int answer = 0;

        for (int i = 1; i < land.length; i++) {
            land[i][0] += maxScore(land[i - 1][1], land[i - 1][2], land[i - 1][3]);
            land[i][1] += maxScore(land[i - 1][0], land[i - 1][2], land[i - 1][3]);
            land[i][2] += maxScore(land[i - 1][0], land[i - 1][1], land[i - 1][3]);
            land[i][3] += maxScore(land[i - 1][0], land[i - 1][1], land[i - 1][2]);
        }

        for (int score : land[land.length - 1]) {
            answer = Math.max(answer, score);
        }

        return answer;
    }

    public int maxScore(int... scores) {
        int max = scores[0];
        for (int i = 1; i < scores.length; i++) {
            max = Math.max(max, scores[i]);
        }
        return max;
    }
}
