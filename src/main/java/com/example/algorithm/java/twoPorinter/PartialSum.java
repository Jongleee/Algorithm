package com.example.algorithm.java.twoPorinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PartialSum {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int[] arr = new int[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int count = 1;
        int sum = 0;
        int rightIdx = 0;
        int minLength = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            count--;
            while (sum < s && rightIdx < n) {
                sum += arr[rightIdx++];
                count++;
            }
            if (sum >= s) {
                minLength = Math.min(minLength, count);
                sum -= arr[i];
            }
        }

        if (minLength == Integer.MAX_VALUE) {
            System.out.println("0");
        } else {
            System.out.println(minLength);
        }
    }
}

/*
10 15
5 1 3 5 10 7 4 9 2 8

2
 */