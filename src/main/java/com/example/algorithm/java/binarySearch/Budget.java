package com.example.algorithm.java.binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Budget {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int left = 0;
        int right = -1;
        int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            if (right < arr[i])
                right = arr[i];
        }

        int m = Integer.parseInt(br.readLine());
        while (left <= right) {
            int mid = (left + right) / 2;
            long budget = 0;
            for (int i = 0; i < n; i++) {
                budget += (arr[i] > mid) ? mid : arr[i];
            }
            if (budget <= m) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        System.out.println(right);
    }
}

/*
4
120 110 140 150
485

127

5
70 80 30 40 100
450

100
 */