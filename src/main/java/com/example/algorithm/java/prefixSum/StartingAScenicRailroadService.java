package com.example.algorithm.java.prefixSum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class StartingAScenicRailroadService {
    public static void main(String[] args) throws IOException {
        final int MAX = 100001;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] imos = new int[MAX];
        int[] prefixSum = new int[MAX];
        int[] suffixSum = new int[MAX];
        int[][] segments;

        int n = Integer.parseInt(br.readLine());
        segments = new int[n][2];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            imos[x]++;
            imos[y]--;
            prefixSum[y]++;
            suffixSum[x]++;
            segments[i][0] = x;
            segments[i][1] = y;
        }

        int maxOverlap = getMaxOverlap(imos);
        computePrefixSum(prefixSum);
        computeSuffixSum(suffixSum);

        int minRemove = getMinRemove(prefixSum, suffixSum, segments);
        System.out.println((n - minRemove) + " " + maxOverlap);
    }

    private static int getMaxOverlap(int[] imos) {
        int max = 0;
        for (int i = 1; i < imos.length; i++) {
            imos[i] += imos[i - 1];
            if (imos[i] > max)
                max = imos[i];
        }
        return max;
    }

    private static void computePrefixSum(int[] prefixSum) {
        for (int i = 1; i < prefixSum.length; i++) {
            prefixSum[i] += prefixSum[i - 1];
        }
    }

    private static void computeSuffixSum(int[] suffixSum) {
        for (int i = suffixSum.length - 2; i >= 0; i--) {
            suffixSum[i] += suffixSum[i + 1];
        }
    }

    private static int getMinRemove(int[] prefixSum, int[] suffixSum, int[][] segments) {
        int min = Integer.MAX_VALUE;
        for (int[] seg : segments) {
            int x = seg[0];
            int y = seg[1];
            int temp = prefixSum[x] + suffixSum[y];
            if (temp < min)
                min = temp;
        }
        return min;
    }
}

/*
10
84 302
275 327
364 538
26 364
29 386
545 955
715 965
404 415
903 942
150 402

6 5

4
1 3
1 3
3 6
3 6

2 2
*/