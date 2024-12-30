package com.example.algorithm.java.bruteForce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Resignation {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());

        int[] period = new int[n];
        int[] money = new int[n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            period[i] = Integer.parseInt(st.nextToken());
            money[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(dfs(0, 0, n, period, money));
    }

    private static int dfs(int index, int sum, int n, int[] period, int[] money) {
        int max = sum;

        for (int i = index; i < n; i++) {
            if (i + period[i] <= n) {
                max = Math.max(max, dfs(i + period[i], sum + money[i], n, period, money));
            }
        }
        return max;
    }
}

/*
7
3 10
5 20
1 10
1 20
2 15
4 40
2 200

45


10
5 50
4 40
3 30
2 20
1 10
1 10
2 20
3 30
4 40
5 50

90
*/