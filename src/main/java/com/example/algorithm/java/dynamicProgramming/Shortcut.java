package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Shortcut {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int len = Integer.parseInt(st.nextToken());

        int[] memo = new int[len + 1];
        for (int i = 0; i <= len; i++) {
            memo[i] = i;
        }

        int[][] road = new int[n][3];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            road[i][0] = Integer.parseInt(st.nextToken());
            road[i][1] = Integer.parseInt(st.nextToken());
            road[i][2] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i <= len; i++) {
            if (i > 0) {
                memo[i] = Math.min(memo[i], memo[i - 1] + 1);
            }
            for (int j = 0; j < n; j++) {
                if (road[j][1] == i) {
                    if (road[j][2] < road[j][1] - road[j][0]) {
                        memo[i] = Math.min(memo[road[j][0]] + road[j][2], memo[i]);
                    }
                }
            }
        }

        System.out.println(memo[len]);
    }
}


/*
5 150
0 50 10
0 50 20
50 100 10
100 151 10
110 140 90

70


2 100
10 60 40
50 90 20

80


8 900
0 10 9
20 60 45
80 190 100
50 70 15
160 180 14
140 160 14
420 901 5
450 900 0

432
 */