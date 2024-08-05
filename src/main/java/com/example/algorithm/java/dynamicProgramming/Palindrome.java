package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Palindrome {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n + 1];
        boolean[][] dp = new boolean[n + 1][n + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= n; i++) {
            dp[i][i] = true;
        }

        for (int i = 1; i < n; i++) {
            if (arr[i] == arr[i + 1]) {
                dp[i][i + 1] = true;
            }
        }

        for (int len = 2; len < n; len++) {
            for (int start = 1; start <= n - len; start++) {
                int end = start + len;
                if (arr[start] == arr[end] && dp[start + 1][end - 1]) {
                    dp[start][end] = true;
                }
            }
        }

        int m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            sb.append(dp[start][end] ? 1 : 0).append("\n");
        }

        System.out.print(sb);
    }
}

/*
7
1 2 1 3 1 2 1
4
1 3
2 5
3 3
5 7

1
0
1
1
*/