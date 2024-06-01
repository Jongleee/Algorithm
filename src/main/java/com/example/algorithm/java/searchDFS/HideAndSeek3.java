package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class HideAndSeek3 {
    private static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        if (n == 0) {
            dfs(1, k, 1);
        } else {
            dfs(n, k, 0);
        }
        System.out.println(min);
    }

    private static void dfs(int n, int k, int count) {
        if (n >= k) {
            min = Math.min(min, count + n - k);
            return;
        }
        min = Math.min(min, count + k - n);
        
        if (k % 2 == 0) {
            dfs(n, k / 2, count);
        } else {
            dfs(n, k + 1, count + 1);
            dfs(n, k - 1, count + 1);
        }
    }
}

/*
5 17

2
 */