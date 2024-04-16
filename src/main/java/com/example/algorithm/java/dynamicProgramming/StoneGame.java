package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class StoneGame {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int rockCnt = Integer.parseInt(br.readLine());

        boolean[] dp = new boolean[rockCnt + 1];

        if (rockCnt > 1)
            dp[2] = true;

        for (int i = 4; i <= rockCnt; i++) {
            dp[i] = !dp[i - 1];
        }

        if (dp[rockCnt])
            bw.write("CY\n");
        else
            bw.write("SK\n");
        
        br.close();
        bw.flush();
        bw.close();
    }
}
/*
5

SK
 */