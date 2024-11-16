package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class GetRank {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int tScore = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());

        int[] score = new int[p];
        if (n > 0) {
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) score[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(score);

        if (p == n && score[0] >= tScore) {
            System.out.println(-1);
            return;
        }

        int cnt = 1;
        int limit = Math.max(0, p - n - 1);
        for (int i = p - 1; i >= limit; i--) {
            if (score[i] > tScore) cnt++;
            else break;
        }
        System.out.println(cnt);
    }
}
/*
3 90 10
100 90 80

2

10 1 10
10 9 8 7 6 5 4 3 2 1

-1
 */