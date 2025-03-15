package com.example.algorithm.java.twoPorinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ThreeLiquids {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);
        int[] result = findClosestThreeSum(arr, n);

        System.out.println(result[0] + " " + result[1] + " " + result[2]);
    }

    private static int[] findClosestThreeSum(int[] arr, int n) {
        long minDiff = Long.MAX_VALUE;
        int[] bestTriplet = new int[3];

        for (int i = 0; i < n - 2; i++) {
            int left = i + 1, right = n - 1;

            while (left < right) {
                long sum = (long) arr[i] + arr[left] + arr[right];

                if (Math.abs(sum) < minDiff) {
                    minDiff = Math.abs(sum);
                    bestTriplet[0] = arr[i];
                    bestTriplet[1] = arr[left];
                    bestTriplet[2] = arr[right];
                }

                if (sum == 0) {
                    return bestTriplet;
                } else if (sum > 0) {
                    right--;
                } else {
                    left++;
                }
            }
        }
        return bestTriplet;
    }
}

/*
5
-2 6 -97 -6 98

-97 -2 98


7
-2 -3 -24 -6 98 100 61

-6 -3 -2
*/