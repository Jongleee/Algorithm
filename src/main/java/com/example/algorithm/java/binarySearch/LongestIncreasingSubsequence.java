package com.example.algorithm.java.binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LongestIncreasingSubsequence {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int length = Integer.parseInt(br.readLine());
        int[] sequence = readSequence(br, length);
        int lisLength = calculateLISLength(sequence, length);
        System.out.println(lisLength);
    }

    private static int[] readSequence(BufferedReader br, int length) throws IOException {
        int[] sequence = new int[length];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < length; i++) {
            sequence[i] = Integer.parseInt(st.nextToken());
        }
        return sequence;
    }

    private static int calculateLISLength(int[] sequence, int length) {
        int[] lis = new int[length + 1];
        int end = 0;

        for (int i = 0; i < length; i++) {
            int value = sequence[i];
            if (value > lis[end]) {
                lis[++end] = value;
            } else {
                int index = lowerBound(lis, 0, end, value);
                lis[index] = value;
            }
        }

        return end;
    }

    private static int lowerBound(int[] arr, int start, int end, int key) {
        while (start < end) {
            int mid = (start + end) / 2;
            if (key <= arr[mid]) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return end;
    }
}

/*
6
10 20 10 30 20 50

4
*/