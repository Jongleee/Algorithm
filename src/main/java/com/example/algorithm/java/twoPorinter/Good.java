package com.example.algorithm.java.twoPorinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Good {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (isGoodNumber(arr, i)) {
                count++;
            }
        }

        System.out.println(count);
    }

    static boolean isGoodNumber(int[] arr, int index) {
        int target = arr[index];
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            if (left == index) {
                left++;
            } else if (right == index) {
                right--;
            } else {
                int sum = arr[left] + arr[right];
                if (sum == target) {
                    return true;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return false;
    }
}

/*
10
1 2 3 4 5 6 7 8 9 10

8
 */