package com.example.algorithm.java.knuth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class FileMerge2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            int[] values = new int[n + 1];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= n; i++) {
                values[i] = Integer.parseInt(st.nextToken());
            }
            bw.write(computeDP(n, values) + "\n");
        }
        br.close();
        bw.flush();
        bw.close();
    }

    private static int computeDP(int n, int[] values) {
        int[][] dp = new int[n + 1][n + 1];
        int[][] knuth = new int[n + 1][n + 1];
        int[] prefixSum = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            prefixSum[i] = prefixSum[i - 1] + values[i];
            dp[i - 1][i] = 0;
            knuth[i - 1][i] = i;
        }

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {
                int j = i + len;
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = knuth[i][j - 1]; k <= knuth[i + 1][j]; k++) {
                    int cost = dp[i][k] + dp[k][j] + prefixSum[j] - prefixSum[i];
                    if (dp[i][j] > cost) {
                        dp[i][j] = cost;
                        knuth[i][j] = k;
                    }
                }
            }
        }
        return dp[0][n];
    }
}

/*
2
4
40 30 30 50
15
1 21 3 4 5 35 5 4 3 5 98 21 14 17 32

300
864
*/