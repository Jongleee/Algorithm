package com.example.algorithm.java.fft;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class GoldbachPartition2 {
    private static final int MAX_PRIME_INDEX = 499999;
    private static final long MOD = 2_281_701_377L;
    private static final long ROOT = 3;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int testCaseCount = Integer.parseInt(br.readLine());

        long[] primes = preparePrimeArray();
        long[] partitionCounts = computePartitionCounts(primes);

        while (testCaseCount-- > 0) {
            int input = Integer.parseInt(br.readLine());
            int index = input / 2 - 3;
            if (index == -1) {
                sb.append("1\n");
            } else {
                sb.append((partitionCounts[index] + 1) / 2).append("\n");
            }
        }

        System.out.print(sb);
    }

    private static long[] preparePrimeArray() {
        long[] primes = new long[MAX_PRIME_INDEX];
        Arrays.fill(primes, 1);
        for (int i = 0; i < 499; i++) {
            if (primes[i] == 0)
                continue;
            int p = 2 * i + 3;
            for (int j = (p * p - 3) / 2; j < MAX_PRIME_INDEX; j += p) {
                primes[j] = 0;
            }
        }
        return primes;
    }

    private static long[] computePartitionCounts(long[] primes) {
        return nttMultiply(primes, primes);
    }

    private static long power(long base, long exp, long mod) {
        long result = 1;
        long factor = base;
        while (exp > 0) {
            if ((exp & 1) == 1)
                result = (result * factor) % mod;
            factor = (factor * factor) % mod;
            exp >>= 1;
        }
        return result;
    }

    private static void ntt(long[] poly, long root, long mod) {
        int n = poly.length;
        if (n == 1)
            return;

        long[] even = new long[n / 2];
        long[] odd = new long[n / 2];
        for (int i = 0; i < n / 2; i++) {
            even[i] = poly[2 * i];
            odd[i] = poly[2 * i + 1];
        }

        long nextRoot = (root * root) % mod;
        ntt(even, nextRoot, mod);
        ntt(odd, nextRoot, mod);

        long w = 1;
        for (int i = 0; i < n / 2; i++) {
            long val = (w * odd[i]) % mod;
            poly[i] = (even[i] + val) % mod;
            poly[i + n / 2] = (even[i] - val + mod) % mod;
            w = (w * root) % mod;
        }
    }

    private static long[] nttMultiply(long[] a, long[] b) {
        int n = 1;
        while (n <= a.length || n <= b.length)
            n *= 2;
        n *= 2;

        long[] aCopy = Arrays.copyOf(a, n);
        long[] bCopy = Arrays.copyOf(b, n);
        long[] result = new long[n];

        long w = power(ROOT, (MOD - 1) / n, MOD);
        ntt(aCopy, w, MOD);
        ntt(bCopy, w, MOD);

        for (int i = 0; i < n; i++) {
            result[i] = (aCopy[i] * bCopy[i]) % MOD;
        }

        long invW = power(w, MOD - 2, MOD);
        long invN = power(n, MOD - 2, MOD);
        ntt(result, invW, MOD);

        for (int i = 0; i < n; i++) {
            result[i] = (result[i] * invN) % MOD;
        }

        return Arrays.copyOf(result, a.length + b.length - 1);
    }
}

/*
5
6
8
10
12
100

1
1
2
1
6
*/