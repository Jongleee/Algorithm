package com.example.algorithm.java.math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MatrixExponentiation {
    static int n;
    static long b;
    static int[][] matrix;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        b = Long.parseLong(st.nextToken());
        matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(st.nextToken()) % 1000;
            }
        }

        int[][] result = matrixPower(matrix, b);

        StringBuilder sb = new StringBuilder();
        for (int[] row : result) {
            for (int val : row) {
                sb.append(val).append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }

    private static int[][] matrixPower(int[][] base, long exp) {
        if (exp == 1) {
            return base;
        }
        int[][] result = matrixPower(base, exp / 2);
        result = multiplyMatrices(result, result);

        if (exp % 2 == 1) {
            result = multiplyMatrices(result, base);
        }

        return result;
    }

    private static int[][] multiplyMatrices(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                int temp = 0;
                for (int i = 0; i < n; i++) {
                    temp += a[r][i] * b[i][c];
                }
                result[r][c] = temp % 1000;
            }
        }

        return result;
    }
}

/*
2 5
1 2
3 4

69 558 
337 406


3 3
1 2 3
4 5 6
7 8 9

468 576 684 
62 305 548
656 34 412


5 10
1 0 0 0 1
1 0 0 0 1
1 0 0 0 1
1 0 0 0 1
1 0 0 0 1

512 0 0 0 512 
512 0 0 0 512
512 0 0 0 512
512 0 0 0 512
512 0 0 0 512
*/