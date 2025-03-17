package com.example.algorithm.java.math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class NumberOfPaths {
    private static final int MOD = 1000003;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        long t = Long.parseLong(st.nextToken());

        int size = 5 * n + 1;
        long[][] matrix = new long[size][size];
        long[][] result = new long[size][size];

        for (int i = 1; i < size; i++) {
            result[i][i] = 1;
        }

        initializeMatrix(br, matrix, n);
        result = matrixExponentiation(matrix, result, t, size);

        System.out.println(result[5 * s][5 * e]);
    }

    private static void initializeMatrix(BufferedReader br, long[][] matrix, int n) throws IOException {
        for (int i = 1; i <= n; i++) {
            String line = br.readLine();
            for (int j = 0; j < line.length(); j++) {
                int weight = line.charAt(j) - '0';
                if (weight >= 1) {
                    matrix[i * 5][(j + 1) * 5 - (weight - 1)] = 1;
                }
            }
            for (int j = 1; j <= 4; j++) {
                matrix[(i - 1) * 5 + j][(i - 1) * 5 + j + 1] = 1;
            }
        }
    }

    private static long[][] matrixExponentiation(long[][] base, long[][] result, long exp, int size) {
        while (exp > 0) {
            if (exp % 2 == 1) {
                result = multiplyMatrix(result, base, size);
            }
            base = multiplyMatrix(base, base, size);
            exp /= 2;
        }
        return result;
    }

    private static long[][] multiplyMatrix(long[][] a, long[][] b, int size) {
        long[][] result = new long[size][size];
        for (int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                for (int k = 1; k < size; k++) {
                    result[i][j] = (result[i][j] + a[i][k] * b[k][j]) % MOD;
                }
            }
        }
        return result;
    }
}

/*
3 1 3 5
012
201
120

8
*/