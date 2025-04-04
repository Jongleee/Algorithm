package com.example.algorithm.java.divideAndConquer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FibonacciNumber6 {
    private static final long MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine());

        long[][] matrix = { { 1, 1 }, { 1, 0 } };
        System.out.println(matrixPower(matrix, n - 1)[0][0]);
    }

    private static long[][] matrixPower(long[][] matrix, long exponent) {
        if (exponent == 0 || exponent == 1) {
            return matrix;
        }

        long[][] half = matrixPower(matrix, exponent / 2);
        long[][] result = multiply(half, half);

        if (exponent % 2 == 1) {
            long[][] origin = { { 1, 1 }, { 1, 0 } };
            result = multiply(result, origin);
        }

        return result;
    }

    private static long[][] multiply(long[][] a, long[][] b) {
        long[][] result = new long[2][2];
        result[0][0] = (a[0][0] * b[0][0] + a[0][1] * b[1][0]) % MOD;
        result[0][1] = (a[0][0] * b[0][1] + a[0][1] * b[1][1]) % MOD;
        result[1][0] = (a[1][0] * b[0][0] + a[1][1] * b[1][0]) % MOD;
        result[1][1] = (a[1][0] * b[0][1] + a[1][1] * b[1][1]) % MOD;
        return result;
    }
}

/*
1000

517691607
*/