package com.example.algorithm.java.math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BinomialCoefficient3 {
    private static final long MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        long n = Long.parseLong(st.nextToken());
        long k = Long.parseLong(st.nextToken());

        long numerator = factorial(n);
        long denominator = factorial(k) * factorial(n - k) % MOD;

        long result = numerator * modInverse(denominator, MOD - 2) % MOD;
        System.out.println(result);
    }

    private static long factorial(long num) {
        long result = 1;
        for (long i = 2; i <= num; i++) {
            result = (result * i) % MOD;
        }
        return result;
    }

    private static long modInverse(long base, long exp) {
        long result = 1;
        while (exp > 0) {
            if (exp % 2 == 1) {
                result = (result * base) % MOD;
            }
            base = (base * base) % MOD;
            exp /= 2;
        }
        return result;
    }
}


/*
5 2

10
*/