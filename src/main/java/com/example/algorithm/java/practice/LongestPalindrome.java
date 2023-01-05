package com.example.algorithm.java.practice;

public class LongestPalindrome {
    public static void main(String[] args) {
        System.out.println(solution1("abcdcba"));
    }
        //반복문
        public static int solution1(String s)
        {
            char[] chr = s.toCharArray();

            for (int leng = s.length(); leng > 1; leng--) {

                for (int start = 0; start + leng <= s.length(); start++) {
                    boolean check = true;
                    for (int i = 0; i < leng/2; i++) {
                        if (chr[start + i] != chr[start + leng  - i - 1]) {
                            check = false;
                            break;
                        }
                    }

                    if (check) return leng;
                }
            }

            return 1;
        }

        //DP
    public static int solution2(String s) {
        int answer = 1;
        int len = s.length();
        char[] a = s.toCharArray();

        int[][] dp = new int[len][len];

        // 1. 1자리
        for (int i = 0; i < len; i++)
            dp[i][i] = 1;

        // 2. 2자리
        for (int i = 0; i < len-1; i++) {
            if( a[i] == a[i+1] ) {
                dp[i][i + 1] = 1;
                answer = 2;
            }
        }
        // 3. 3자리 이상
        for (int k = 3; k <= len; k++) {
            for (int i = 0; i <= len-k ; i++) {
                int j = i+k-1;
                if( a[i] == a[j] && dp[i+1][j-1] == 1 ) {
                    dp[i][j] = 1;
                    answer = Math.max(answer,k);
                }
            }
        }

        return answer;
    }
}
