package com.example.algorithm.java.searchDFS;

import java.util.Arrays;

public class ArcheryCompetition {
    static int[] res = { -1 };
    static int[] lion;
    static int diff = 0;

    public static int[] solution(int n, int[] info) {
        lion = new int[11];
        dfs(info, 1, n);
        return res;
    }

    public static void dfs(int[] info, int cnt, int n) {
        if (cnt == n + 1) {
            int apeachPoint = 0;
            int lionPoint = 0;
            for (int i = 0; i <= 10; i++) {
                int score = 10 - i;
                if (info[i] + lion[i] != 0) {
                    if (info[i] < lion[i]) {
                        lionPoint += score;
                    } else {
                        apeachPoint += score;
                    }
                }
            }
            if (updateCondition(apeachPoint, lionPoint)) {
                res = lion.clone();
                diff = lionPoint - apeachPoint;
            }
            return;
        }
        for (int j = 0; j <= 10 && lion[j] <= info[j]; j++) {
            lion[j]++;
            dfs(info, cnt + 1, n);
            lion[j]--;
        }
    }

    private static boolean updateCondition(int apeachPoint, int lionPoint) {
        return lionPoint > apeachPoint && lionPoint - apeachPoint >= diff;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(10, new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 3, 4, 3 })));// [1,1,1,1,1,1,1,1,0,0,2]
    }
}
