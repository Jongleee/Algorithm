package com.example.algorithm.java.math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FibonacciNumber3 {
    private static final long DIV = 1000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine());
        System.out.println(findFib(n));
    }

    private static long findFib(long n) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        long[][] mat = power(new long[][] { { 1, 1 }, { 1, 0 } }, n - 1);
        return mat[0][0];
    }

    private static long[][] power(long[][] mat, long p) {
        long[][] result = { { 1, 0 }, { 0, 1 } };
        while (p > 0) {
            if (p % 2 == 1)
                result = multiply(result, mat);
            mat = multiply(mat, mat);
            p /= 2;
        }
        return result;
    }

    private static long[][] multiply(long[][] lhs, long[][] rhs) {
        return new long[][] {
                { (lhs[0][0] * rhs[0][0] + lhs[0][1] * rhs[1][0]) % DIV,
                        (lhs[0][0] * rhs[0][1] + lhs[0][1] * rhs[1][1]) % DIV },
                { (lhs[1][0] * rhs[0][0] + lhs[1][1] * rhs[1][0]) % DIV,
                        (lhs[1][0] * rhs[0][1] + lhs[1][1] * rhs[1][1]) % DIV }
        };
    }
}

/*
1000

228875
*/