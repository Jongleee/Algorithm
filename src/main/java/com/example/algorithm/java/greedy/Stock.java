package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Stock {
    static int day;
    static long profit;
    static int[] stocks;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        StringTokenizer st;

        for (int t = 1; t <= testCase; t++) {
            day = Integer.parseInt(br.readLine());
            stocks = new int[day];
            profit = 0;

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < day; i++) {
                stocks[i] = Integer.parseInt(st.nextToken());
            }

            int max = stocks[day - 1];
            calculateProfit(max);

            System.out.println(profit);
        }
    }

    static void calculateProfit(int max) {
        for (int i = day - 2; i >= 0; i--) {
            if (stocks[i] < max) {
                profit += max - stocks[i];
            } else {
                max = stocks[i];
            }
        }
    }
}

/*
3
3
10 7 6
3
3 5 9
5
1 1 3 1 2

0
10
5
 */