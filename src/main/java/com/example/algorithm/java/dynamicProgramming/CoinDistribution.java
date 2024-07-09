package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CoinDistribution {
    static int n;
    static int[][] coins;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        for (int tc = 1; tc <= 3; tc++) {
            n = Integer.parseInt(br.readLine());
            int sum = 0;
            coins = new int[n][2];

            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int coin = Integer.parseInt(st.nextToken());
                int cnt = Integer.parseInt(st.nextToken());
                coins[i][0] = coin;
                coins[i][1] = cnt;
                sum += coin * cnt;
            }

            if (sum % 2 != 0) {
                sb.append(0);
            } else {
                sb.append(solve(sum / 2));
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
    }

    static int solve(int target) {
        int[] dp = new int[target + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 0; i < n; i++) {
            int coin = coins[i][0];
            int cnt = coins[i][1];

            for (int j = 0; j <= target; j++) {
                if (dp[j] == Integer.MAX_VALUE) {
                    continue;
                }
                if (j + coin <= target && dp[j] < cnt) {
                    dp[j + coin] = Math.min(dp[j + coin], dp[j] + 1);
                }
                dp[j] = 0;
            }
        }

        return dp[target] == Integer.MAX_VALUE ? 0 : 1;
    }
}

/*
2
500 1
50 1
3
100 2
50 1
10 5
3
1 1
2 1
3 1

0
1
1
 */