package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class RGBStreet2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int[][] dp = new int[n][3];
        int[][] house = new int[n][3];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < 3; j++) {
                house[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = 1000002;

        for (int firstColor = 0; firstColor < 3; firstColor++) {
            for (int color = 0; color < 3; color++) {
                dp[n - 1][color] = (color == firstColor) ? 1000001 : house[n - 1][color];
            }

            for (int i = n - 2; i > 0; i--) {
                dp[i][0] = Math.min(dp[i + 1][1], dp[i + 1][2]) + house[i][0];
                dp[i][1] = Math.min(dp[i + 1][0], dp[i + 1][2]) + house[i][1];
                dp[i][2] = Math.min(dp[i + 1][0], dp[i + 1][1]) + house[i][2];
            }

            for (int color = 0; color < 3; color++) {
                if (firstColor != color) {
                    answer = Math.min(answer, house[0][firstColor] + dp[1][color]);
                }
            }
        }

        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
        br.close();
    }
}

/*
8
71 39 44
32 83 55
51 37 63
89 29 100
83 58 11
65 13 15
47 25 29
60 66 19

253


6
30 19 5
64 77 64
15 19 97
4 71 57
90 86 84
93 32 91

208
*/