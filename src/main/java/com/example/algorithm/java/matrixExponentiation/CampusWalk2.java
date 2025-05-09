package com.example.algorithm.java.matrixExponentiation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CampusWalk2 {
    private static final long MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine());

        long[][] baseMatrix = new long[8][8];
        initializeBaseMatrix(baseMatrix);

        long[][] result = powerMatrix(baseMatrix, n);
        System.out.println(result[0][0]);
    }

    private static void initializeBaseMatrix(long[][] matrix) {
        matrix[0][1] = matrix[0][7] = 1;
        matrix[1][0] = matrix[1][2] = matrix[1][7] = 1;
        matrix[2][1] = matrix[2][3] = matrix[2][6] = matrix[2][7] = 1;
        matrix[3][2] = matrix[3][4] = matrix[3][6] = 1;
        matrix[4][3] = matrix[4][5] = 1;
        matrix[5][4] = matrix[5][6] = 1;
        matrix[6][2] = matrix[6][3] = matrix[6][5] = matrix[6][7] = 1;
        matrix[7][0] = matrix[7][1] = matrix[7][2] = matrix[7][6] = 1;
    }

    private static long[][] powerMatrix(long[][] matrix, long exponent) {
        if (exponent == 1)
            return matrix;
        if (exponent % 2 == 0) {
            long[][] half = powerMatrix(matrix, exponent / 2);
            return multiplyMatrices(half, half);
        } else {
            return multiplyMatrices(powerMatrix(matrix, exponent - 1), matrix);
        }
    }

    private static long[][] multiplyMatrices(long[][] a, long[][] b) {
        long[][] result = new long[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    result[i][j] = (result[i][j] + a[i][k] * b[k][j] % MOD) % MOD;
                }
            }
        }
        return result;
    }
}

/*
100000000

261245548
*/