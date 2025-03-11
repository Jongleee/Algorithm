package com.example.algorithm.java.binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LongestIncreasingSubsequence2 {
    public static void main(String[] args) throws IOException {
        int[] sequence = parseInput();
        int lengthOfLIS = findLISLength(sequence);

        System.out.println(lengthOfLIS);
    }

    private static int[] parseInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] sequence = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < n; i++) {
            sequence[i] = Integer.parseInt(st.nextToken());
        }
        br.close();
        return sequence;
    }

    private static int findLISLength(int[] sequence) {
        int n = sequence.length;
        int[] lis = new int[n];
        lis[0] = sequence[0];
        int lengthOfLIS = 1;

        for (int i = 1; i < n; i++) {
            int key = sequence[i];

            if (lis[lengthOfLIS - 1] < key) {
                lis[lengthOfLIS++] = key;
            } else {
                int pos = lowerBound(lis, lengthOfLIS, key);
                lis[pos] = key;
            }
        }
        return lengthOfLIS;
    }

    private static int lowerBound(int[] lis, int length, int key) {
        int lo = 0, hi = length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (lis[mid] < key) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }
}

/*
6
10 20 10 30 20 50

4
*/