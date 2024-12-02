package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class RamenPurchaseSmall {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] array = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(st.nextToken());
        }

        int totalSum = 0;

        for (int i = 0; i < n - 2; i++) {
            int minAdjacent = Math.min(array[i], array[i + 1]);

            if (array[i + 1] > array[i + 2]) {
                if (array[i] > array[i + 1] - array[i + 2]) {
                    int extraPairs = Math.min(array[i] - array[i + 1] + array[i + 2], array[i + 2]);

                    totalSum += extraPairs * 2;
                    array[i + 2] -= extraPairs;
                }
            } else {
                totalSum += minAdjacent * 2;
                array[i + 2] -= minAdjacent;
            }

            totalSum += array[i] * 3 + minAdjacent * 2;
            array[i + 1] -= minAdjacent;
        }

        totalSum += Math.min(array[n - 2], array[n - 1]) * 5;
        totalSum += Math.abs(array[n - 2] - array[n - 1]) * 3;

        System.out.println(totalSum);
    }
}

/*
3
1 0 1

6


5
1 1 1 0 2

13
*/