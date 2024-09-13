package com.example.algorithm.java.binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SharpEye {
    private static long[][] ranges;
    private static long result = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        ranges = new long[n][3];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long start = Long.parseLong(st.nextToken());
            long end = Long.parseLong(st.nextToken());
            long step = Long.parseLong(st.nextToken());

            ranges[i][0] = start;
            ranges[i][1] = end;
            ranges[i][2] = step;
        }

        long left = 1;
        long right = 2147483647;

        while (left <= right) {
            long mid = (left + right) / 2;

            if (countNumbersUpTo(mid) % 2 == 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
                result = mid;
            }
        }

        if (result != -1) {
            System.out.println(result + " " + (countNumbersUpTo(result) - countNumbersUpTo(result - 1)));
        } else {
            System.out.println("NOTHING");
        }
    }

    private static int countNumbersUpTo(long num) {
        int total = 0;

        for (long[] range : ranges) {
            long start = range[0];
            long end = range[1];
            long step = range[2];

            if (start > num) continue;

            if (end <= num) {
                total += (end - start) / step + 1;
            } else {
                total += (num - start) / step + 1;
            }
        }
        return total;
    }
}

/*
4
1 10 1
4 4 1
1 5 1
6 10 1

4 3
*/