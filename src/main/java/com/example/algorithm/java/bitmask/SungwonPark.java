package com.example.algorithm.java.bitmask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SungwonPark {
    private static int n;
    private static int k;
    private static int[] tenValue;
    private static int[] remain;
    private static int[] len;
    private static String[] input;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        input = new String[n];
        len = new int[n];

        for (int i = 0; i < n; i++) {
            input[i] = br.readLine();
            len[i] = input[i].length();
        }

        k = Integer.parseInt(br.readLine());

        init();
        long able = simulateDP();

        long fact = factorial(n);
        long gcd = getGCD(able, fact);

        System.out.println((able / gcd) + "/" + (fact / gcd));
    }

    private static void init() {
        tenValue = new int[51];
        remain = new int[n];
        tenValue[0] = k == 1 ? 0 : 1;

        for (int i = 1; i <= 50; i++) {
            tenValue[i] = (tenValue[i - 1] * 10) % k;
        }

        for (int i = 0; i < n; i++) {
            remain[i] = calculateMod(input[i]);
        }
    }

    private static int calculateMod(String number) {
        int mod = 0;
        for (char digit : number.toCharArray()) {
            mod = (mod * 10 + (digit - '0')) % k;
        }
        return mod;
    }

    private static long simulateDP() {
        long[][] dp;
        dp = new long[1 << n][k];
        dp[0][0] = 1;

        for (int status = 0; status < (1 << n); status++) {
            for (int i = 0; i < n; i++) {
                if ((status & (1 << i)) != 0)
                    continue;

                int nextStatus = status | (1 << i);
                for (int j = 0; j < k; j++) {
                    int nextRemain = (j * tenValue[len[i]] + remain[i]) % k;
                    dp[nextStatus][nextRemain] += dp[status][j];
                }
            }
        }

        return dp[(1 << n) - 1][0];
    }

    private static long factorial(int num) {
        long result = 1;
        for (int i = 2; i <= num; i++) {
            result *= i;
        }
        return result;
    }

    private static long getGCD(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}

/*
9
13
10129414190271203
102
102666818896
1216
1217
1218
101278001
1000021412678412681
21

5183/36288


5
11
101
1001
10001
100001
10

0/1
*/