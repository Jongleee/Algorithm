package com.example.algorithm.java.binarySearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class WriteIf {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static String[] jobs;
    static int[] powers;

    public static void main(String[] args) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int size = Integer.parseInt(st.nextToken());
        int human = Integer.parseInt(st.nextToken());

        jobs = new String[size];
        powers = new int[size];

        for (int i = 0; i < size; i++) {
            st = new StringTokenizer(br.readLine());
            jobs[i] = st.nextToken();
            powers[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < human; i++) {
            int power = Integer.parseInt(br.readLine());
            int resultIndex = findClosestJobIndex(power);
            sb.append(jobs[resultIndex]).append("\n");
        }

        System.out.print(sb);
    }

    static int findClosestJobIndex(int target) {
        int left = 0;
        int right = powers.length - 1;

        while (left < right) {
            int mid = (left + right) / 2;
            if (powers[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}

/*
3 8
WEAK 10000
NORMAL 100000
STRONG 1000000
0
9999
10000
10001
50000
100000
500000
1000000

WEAK
WEAK
WEAK
NORMAL
NORMAL
NORMAL
STRONG
STRONG


3 5
B 100
A 100
C 1000
99
100
101
500
1000

B
B
C
C
C
 */