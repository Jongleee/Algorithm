package com.example.algorithm.java.divideAndConquer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Multiplication {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long base = Long.parseLong(st.nextToken());
        long exponent = Long.parseLong(st.nextToken());
        long mod = Long.parseLong(st.nextToken());

        System.out.println(modularExponentiation(base, exponent, mod));
    }

    private static long modularExponentiation(long base, long exponent, long mod) {
        if (exponent == 1) {
            return base % mod;
        }

        long temp = modularExponentiation(base, exponent / 2, mod);
        long result = (temp * temp) % mod;

        if (exponent % 2 == 1) {
            result = (result * base) % mod;
        }

        return result;
    }
}

/*
10 11 12

4
*/