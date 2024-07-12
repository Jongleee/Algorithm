package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class ControllingRobot {
    static final int INF = 5000;
    static final int MAX = 1000;

    static int n;
    static int m;
    static int[][] arr = new int[MAX][MAX];
    static int[][] dp1 = new int[MAX][MAX];
    static int[][] dp2 = new int[MAX][MAX];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp();

        bw.write(dp1[n - 1][m - 1] + "\n");
        bw.flush();

        br.close();
        bw.close();
    }

    static void dp() {
        dp1[0][0] = dp2[0][0] = arr[0][0];
        for (int i = 1; i < m; i++) {
            dp1[0][i] = dp2[0][i] = arr[0][i] + dp1[0][i - 1];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dp1[i][j] = dp2[i][j] = dp1[i - 1][j] + arr[i][j];
            }
            for (int j = 0; j < m - 1; j++) {
                dp1[i][j + 1] = Math.max(dp1[i][j + 1], dp1[i][j] + arr[i][j + 1]);
            }
            for (int j = m - 1; j > 0; j--) {
                dp2[i][j - 1] = Math.max(dp2[i][j - 1], dp2[i][j] + arr[i][j - 1]);
            }
            for (int j = 0; j < m; j++) {
                dp1[i][j] = Math.max(dp1[i][j], dp2[i][j]);
            }
        }
    }
}

/*
5 5
10 25 7 8 13
68 24 -78 63 32
12 -69 100 -29 -25
-16 -22 -57 -33 99
7 -76 -11 77 15

319
 */