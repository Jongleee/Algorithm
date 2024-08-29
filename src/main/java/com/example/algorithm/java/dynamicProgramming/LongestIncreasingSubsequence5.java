package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LongestIncreasingSubsequence5 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] sequence = new int[n];
        int[] lisIndices = new int[n];
        int[] lisArray = new int[n];
        int lisLength = 0;

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        sequence[0] = Integer.parseInt(st.nextToken());
        lisArray[lisLength++] = sequence[0];

        for (int i = 1; i < n; i++) {
            sequence[i] = Integer.parseInt(st.nextToken());

            if (sequence[i] > lisArray[lisLength - 1]) {
                lisArray[lisLength] = sequence[i];
                lisIndices[i] = lisLength;
                lisLength++;
            } else {
                int pos = lowerBound(lisArray, sequence[i], lisLength);
                lisArray[pos] = sequence[i];
                lisIndices[i] = pos;
            }
        }

        StringBuilder result = new StringBuilder();
        result.append(lisLength).append('\n');

        int[] lisResult = new int[lisLength];
        int idx = lisLength - 1;
        for (int i = n - 1; i >= 0; i--) {
            if (lisIndices[i] == idx) {
                lisResult[idx] = sequence[i];
                idx--;
            }
        }

        for (int i = 0; i < lisLength; i++) {
            result.append(lisResult[i]).append(' ');
        }

        System.out.println(result.toString().trim());
    }

    private static int lowerBound(int[] arr, int key, int length) {
        int low = 0;
        int high = length;
        while (low < high) {
            int mid = (low + high) / 2;
            if (arr[mid] >= key) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
}

/*
6
10 20 10 30 20 50

4
10 20 30 50
*/