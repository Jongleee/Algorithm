package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Sasuatang {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] arr = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }
        br.close();

        Arrays.sort(arr);
        int ans = 0;
        int[][] dp = new int[(n + 1)][(n + 1)];

        for (int i = 1, s = Arrays.binarySearch(arr, 0); i <= n; i++) {
            for (int[] row : dp) {
                Arrays.fill(row, -1);
            }
            ans = Math.max(ans, i * m - recursive(s, s, i, arr, dp, n));
        }

        bw.write(Integer.toString(ans));
        bw.flush();
        bw.close();
    }

    private static int recursive(int s, int e, int left, int[] arr, int[][] dp, int n) {
        if (left < 1)
            return 0;
        if (dp[s][e] != -1)
            return dp[s][e];

        int l = Math.min(s, e);
        int r = Math.max(s, e);
        dp[s][e] = Integer.MAX_VALUE;

        if (r < n) {
            dp[s][e] = Math.min(dp[s][e], recursive(l, r + 1, left - 1, arr, dp, n) + left * (arr[r + 1] - arr[e]));
        }
        if (l > 0) {
            dp[s][e] = Math.min(dp[s][e], recursive(r, l - 1, left - 1, arr, dp, n) + left * (arr[e] - arr[l - 1]));
        }
        return dp[s][e];
    }
}

/*
3 15
6
-3
1

25
*/