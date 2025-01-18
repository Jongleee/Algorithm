package com.example.algorithm.java.bruteForce;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Gerrymandering2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[][] array = new int[n + 1][n + 1];
        int[][] prefixSum = new int[n + 1][n + 1];
        int totalSum = 0;

        for (int i = 1; i <= n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            for (int j = 1; j <= n; j++) {
                totalSum += array[i][j] = Integer.parseInt(st.nextToken());
                prefixSum[i][j] = prefixSum[i - 1][j] + prefixSum[i][j - 1] - prefixSum[i - 1][j - 1] + array[i][j];
            }
        }

        int currentSum, maxSum, minSum, sectionSum, result = Integer.MAX_VALUE;

        for (int i = 1; i <= n - 2; i++) {
            for (int j = 2; j <= n - 1; j++) {
                for (int k = 1; j - k > 0; k++) {
                    for (int l = 1; j + l <= n && i + k + l <= n; l++) {
                        currentSum = 0;
                        sectionSum = 0;

                        if (i > 1) {
                            currentSum += prefixSum[i - 1][j];
                        }
                        for (int m = i, p = j - 1; m < i + k; m++, p--) {
                            currentSum += prefixSum[m][p] - prefixSum[m - 1][p] - prefixSum[m][0] + prefixSum[m - 1][0];
                        }
                        maxSum = currentSum;
                        minSum = currentSum;
                        sectionSum += currentSum;

                        currentSum = 0;
                        if (j + l < n) {
                            currentSum += prefixSum[i + l][n] - prefixSum[0][n] - prefixSum[i + l][j + l] + prefixSum[0][j + l];
                        }
                        for (int m = j + l, p = i + l - 1; m > j; m--, p--) {
                            currentSum += prefixSum[p][m] - prefixSum[0][m] - prefixSum[p][m - 1] + prefixSum[0][m - 1];
                        }
                        maxSum = Math.max(maxSum, currentSum);
                        minSum = Math.min(minSum, currentSum);
                        sectionSum += currentSum;

                        currentSum = 0;
                        if (i + k + l < n) {
                            currentSum += prefixSum[n][n] - prefixSum[i + k + l][n] - prefixSum[n][j + l - k - 1] + prefixSum[i + k + l][j + l - k - 1];
                        }
                        for (int m = i + k + l, p = j + l - k + 1; m > i + l; m--, p++) {
                            currentSum += prefixSum[m][n] - prefixSum[m - 1][n] - prefixSum[m][p - 1] + prefixSum[m - 1][p - 1];
                        }
                        maxSum = Math.max(maxSum, currentSum);
                        minSum = Math.min(minSum, currentSum);
                        sectionSum += currentSum;

                        currentSum = 0;
                        if (j - k > 0) {
                            currentSum += prefixSum[n][j - k - 1] - prefixSum[i + k - 1][j - k - 1] - prefixSum[n][0] + prefixSum[i + k - 1][0];
                        }
                        for (int m = j - k, p = i + k + 1; m < j + l - k; m++, p++) {
                            currentSum += prefixSum[n][m] - prefixSum[p - 1][m] - prefixSum[n][m - 1] + prefixSum[p - 1][m - 1];
                        }
                        maxSum = Math.max(maxSum, currentSum);
                        minSum = Math.min(minSum, currentSum);
                        sectionSum += currentSum;

                        currentSum = totalSum - sectionSum;
                        maxSum = Math.max(maxSum, currentSum);
                        minSum = Math.min(minSum, currentSum);
                        result = Math.min(result, maxSum - minSum);
                    }
                }
            }
        }

        System.out.println(result);
    }
}

/*
8
1 2 3 4 5 6 7 8
2 3 4 5 6 7 8 9
3 4 5 6 7 8 9 1
4 5 6 7 8 9 1 2
5 6 7 8 9 1 2 3
6 7 8 9 1 2 3 4
7 8 9 1 2 3 4 5
8 9 1 2 3 4 5 6

23


6
5 5 5 5 5 5
5 5 5 5 5 5
5 5 5 5 5 5
5 5 5 5 5 5
5 5 5 5 5 5
5 5 5 5 5 5

20
*/