package com.example.algorithm.java.dynamicProgramming;

public class WordPuzzle {

    public static int solution(String[] strs, String t) {
        int[] dp = new int[t.length()];

        for (int i = 0; i < t.length(); i++) {
            updateDp(strs, t, dp, i);
        }
        return dp[t.length() - 1] == 0 ? -1 : dp[t.length() - 1];

    }

    private static void updateDp(String[] strs, String t, int[] dp, int i) {
        for (String word : strs) {
            int tempIdx = i - word.length() + 1;
            if (tempIdx >= 0 && word.equals(t.substring(tempIdx, i + 1))) {
                if (tempIdx == 0) {
                    dp[i] = 1;
                    continue;
                }
                if (dp[tempIdx - 1] > 0) {
                    dp[i] = dp[i] == 0 ? dp[tempIdx - 1] + 1 : Math.min(dp[i], dp[tempIdx - 1] + 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        String[] str1 = { "ba", "na", "n", "a" };
        String t1 = "banana";
        String[] str2 = { "app", "ap", "p", "l", "e", "ple", "pp" };
        String t2 = "apple";
        String[] str3 = { "ba", "an", "nan", "ban", "n" };
        String t3 = "banana";
        System.out.println(solution(str1, t1));// 3
        System.out.println(solution(str2, t2));// 2
        System.out.println(solution(str3, t3));// -1
    }
}
