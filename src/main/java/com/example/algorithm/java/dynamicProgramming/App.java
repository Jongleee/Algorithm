package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class App {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] input = br.readLine().split(" ");
        int n = Integer.parseInt(input[0]);
        int requiredMemory = Integer.parseInt(input[1]);

        int[] memory = new int[n];
        int[] cost = new int[n];
        int[] dp = new int[10001];

        input = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            memory[i] = Integer.parseInt(input[i]);
        }

        input = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            cost[i] = Integer.parseInt(input[i]);
        }

        for (int i = 0; i <= 10000; i++) {
            dp[i] = -1;
        }

        for (int i = 0; i < n; i++) {
            int currentCost = cost[i];
            for (int j = 10000; j >= currentCost; j--) {
                if (dp[j - currentCost] != -1) {
                    dp[j] = Math.max(dp[j], dp[j - currentCost] + memory[i]);
                }
            }
            dp[currentCost] = Math.max(dp[currentCost], memory[i]);
        }

        for (int i = 0; i <= 10000; i++) {
            if (dp[i] >= requiredMemory) {
                bw.write(i + "\n");
                break;
            }
        }

        bw.flush();
        bw.close();
    }
}

/*
5 60
30 10 20 35 40
3 0 3 5 4

6
*/