package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class LongestIncreasingSubsequence4 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] num = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }

        int[] lisIndices = new int[n];
        int[] previousIndex = new int[n];
        int length = 0;

        for (int i = 0; i < n; i++) {
            int low = -1;
            int hi = length;

            while (low + 1 < hi) {
                int mid = (low + hi) / 2;
                if (num[lisIndices[mid]] < num[i]) {
                    low = mid;
                } else {
                    hi = mid;
                }
            }

            lisIndices[hi] = i;
            previousIndex[i] = (hi > 0) ? lisIndices[hi - 1] : -1;

            if (hi == length) {
                length++;
            }
        }

        ArrayList<Integer> result = new ArrayList<>();
        for (int i = lisIndices[length - 1]; i >= 0; i = previousIndex[i]) {
            result.add(num[i]);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(length).append("\n");
        for (int i = result.size() - 1; i >= 0; i--) {
            sb.append(result.get(i)).append(" ");
        }
        System.out.println(sb);
    }
}

/*
6
10 20 10 30 20 50

4
10 20 30 50
*/