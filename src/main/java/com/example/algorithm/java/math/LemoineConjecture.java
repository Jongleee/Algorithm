package com.example.algorithm.java.math;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class LemoineConjecture {
    private static final int MOD = 998244353;
    private static final int BASE = 3;
    private static final int SIZE = 4194304;
    private static final int END = 1048576;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        boolean[] isPrime = sievePrimes(END);
        long[] convolutionResult = computeConvolution(isPrime);

        processQueries(br, sb, convolutionResult);

        bw.write(sb.toString());
        bw.close();
    }

    private static boolean[] sievePrimes(int end) {
        boolean[] isPrime = new boolean[end];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;

        for (int i = 2; i * i < end; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < end; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        return isPrime;
    }

    private static long[] computeConvolution(boolean[] isPrime) {
        long[] primes = new long[SIZE];
        long[] twicePrimes = new long[SIZE];
        populatePrimesArrays(isPrime, primes, twicePrimes);

        long[] fftPrimes = fft(primes, false);
        long[] fftTwicePrimes = fft(twicePrimes, false);

        multiplyFFTResults(fftPrimes, fftTwicePrimes);

        return fft(fftPrimes, true);
    }

    private static void populatePrimesArrays(boolean[] isPrime, long[] primes, long[] twicePrimes) {
        for (int i = 2; i < END; i++) {
            if (isPrime[i]) {
                primes[i] = 1;
                twicePrimes[i * 2] = 1;
            }
        }
    }

    private static void multiplyFFTResults(long[] a, long[] b) {
        for (int i = 0; i < a.length; i++) {
            a[i] = a[i] * b[i] % MOD;
        }
    }

    private static void processQueries(BufferedReader br, StringBuilder sb, long[] result) throws IOException {
        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int num = Integer.parseInt(br.readLine());
            sb.append(result[num]).append("\n");
        }
    }

    private static long[] fft(long[] input, boolean inverse) {
        long[] arr = Arrays.copyOf(input, input.length);
        performBitReversal(arr);
        transform(arr, inverse);
        if (inverse) {
            applyInverse(arr);
        }
        return arr;
    }

    private static void performBitReversal(long[] arr) {
        int n = arr.length;
        int j = 0;
        for (int i = 1; i < n; i++) {
            int bit = n >> 1;
            while (j >= bit) {
                j -= bit;
                bit >>= 1;
            }
            j += bit;
            if (i < j) {
                swap(arr, i, j);
            }
        }
    }

    private static void swap(long[] arr, int i, int j) {
        long temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void transform(long[] arr, boolean inverse) {
        int n = arr.length;
        for (int len = 2; len <= n; len <<= 1) {
            int halfLen = len >> 1;
            long unit = calculateUnit(len, inverse);
            processButterflies(arr, len, halfLen, unit);
        }
    }

    private static long calculateUnit(int len, boolean inverse) {
        long unit = pow(BASE, (MOD - 1) / len);
        return inverse ? pow(unit, MOD - 2) : unit;
    }

    private static void processButterflies(long[] arr, int len, int halfLen, long unit) {
        int n = arr.length;
        for (int p = 0; p < n; p += len) {
            long w = 1;
            for (int k = 0; k < halfLen; k++) {
                int evenIndex = p + k;
                int oddIndex = evenIndex + halfLen;
                long even = arr[evenIndex];
                long odd = arr[oddIndex] * w % MOD;

                long sum = (even + odd) % MOD;
                long diff = (even - odd) % MOD;
                arr[evenIndex] = (sum + MOD) % MOD;
                arr[oddIndex] = (diff + MOD) % MOD;

                w = w * unit % MOD;
            }
        }
    }

    private static void applyInverse(long[] arr) {
        long invLen = pow(arr.length, MOD - 2);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i] * invLen % MOD;
        }
    }

    private static long pow(long base, int exp) {
        long result = 1;
        base = base % MOD;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = result * base % MOD;
            }
            base = base * base % MOD;
            exp >>= 1;
        }
        return result;
    }
}

/*
6
9
11
17
19
1929
1999

2
2
4
2
65
30
*/