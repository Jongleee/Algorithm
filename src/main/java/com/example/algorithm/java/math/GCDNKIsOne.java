package com.example.algorithm.java.math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GCDNKIsOne {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine());
        System.out.println(computeTotient(n));
    }

    private static long computeTotient(long n) {
        long result = n;
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                result = result / i * (i - 1);
            }
            while (n % i == 0) {
                n /= i;
            }
        }
        if (n > 1) {
            result = result / n * (n - 1);
        }
        return result;
    }
}

/*
99

60


45

24
*/