package com.example.algorithm.java.bitmask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CountingOnes {
    static long a;
    static long b;
    static long[] dp = new long[57];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        a = Long.parseLong(st.nextToken());
        b = Long.parseLong(st.nextToken());

        dp[0] = 1;
        for (int i = 1; i < 56; i++) {
            dp[i] = 2 * dp[i - 1] + (1L << i);
        }

        System.out.println(countOnes(b) - countOnes(a - 1));
    }

    static long countOnes(long n) {
        if (n < 0) return 0;

        long count = n & 1;
        int bits = (int) (Math.log(n) / Math.log(2));

        for (int i = bits; i > 0; i--) {
            if ((n & (1L << i)) == 0) continue;

            count += dp[i - 1] + (n - (1L << i) + 1);
            n -= (1L << i);
        }

        return count;
    }
}


/*
2 12

21
 */