package com.example.algorithm.java.implement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Frame {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[][] frames = new int[n][2];

        for (int i = 0; i < n; i++) {
            String[] s = br.readLine().split(" ");
            frames[i][0] = Integer.parseInt(s[0]);
            frames[i][1] = Integer.parseInt(s[1]);
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            int cnt = 1;
            for (int j = 0; j < n; j++) {
                if (i != j && frames[i][0] < frames[j][0] && frames[i][1] < frames[j][1]) {
                    cnt++;
                }
            }
            sb.append(cnt).append(" ");
        }

        System.out.println(sb);
    }
}
/*
55 185
58 183
88 186
60 175
46 155

2 2 1 2 5
 */