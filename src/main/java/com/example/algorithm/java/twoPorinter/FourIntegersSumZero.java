package com.example.algorithm.java.twoPorinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class FourIntegersSumZero {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int num = Integer.parseInt(br.readLine());
        int[][] map = readInput(br, num);

        sortColumns(map);
        int[] sum1 = computeSumArray(map, num, 0, 1);
        int[] sum2 = computeSumArray(map, num, 2, 3);

        Arrays.sort(sum1);
        Arrays.sort(sum2);

        long result = countZeroSums(sum1, sum2);
        System.out.println(result);
    }

    private static int[][] readInput(BufferedReader br, int num) throws IOException {
        int[][] map = new int[4][num];
        for (int i = 0; i < num; i++) {
            String[] values = br.readLine().split(" ");
            for (int j = 0; j < 4; j++) {
                map[j][i] = Integer.parseInt(values[j]);
            }
        }
        return map;
    }

    private static void sortColumns(int[][] map) {
        for (int j = 0; j < 4; j++) {
            Arrays.sort(map[j]);
        }
    }

    private static int[] computeSumArray(int[][] map, int num, int idx1, int idx2) {
        int[] sumArray = new int[num * num];
        int index = 0;
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                sumArray[index++] = map[idx1][i] + map[idx2][j];
            }
        }
        return sumArray;
    }

    private static long countZeroSums(int[] sum1, int[] sum2) {
        int right = sum2.length - 1;
        long totalCount = 0;
        long sameValueCount = 0;

        for (int left = 0; left < sum1.length; left++) {
            if (left > 0 && sum1[left] == sum1[left - 1]) {
                totalCount += sameValueCount;
                continue;
            }
            sameValueCount = 0;

            while (right >= 0 && sum1[left] + sum2[right] > 0) {
                right--;
            }
            while (right >= 0 && sum1[left] + sum2[right] == 0) {
                sameValueCount++;
                right--;
            }
            totalCount += sameValueCount;
        }
        return totalCount;
    }
}

/*
6
-45 22 42 -16
-41 -27 56 30
-36 53 -37 77
-36 30 -75 -46
26 -38 -10 62
-32 -54 -6 45

5
*/