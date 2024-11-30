package com.example.algorithm.java.recursive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class HideAndSeek {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(tokenizer.nextToken());
        int k = Integer.parseInt(tokenizer.nextToken());
        System.out.println(dfs(n, k));
    }

    private static int dfs(int n, int k) {
        if (n >= k)
            return n - k;
        if (k == 1)
            return 1;
        if (k % 2 == 0)
            return Math.min(k - n, dfs(n, k / 2) + 1);

        return Math.min(dfs(n, k + 1), dfs(n, k - 1)) + 1;
    }
}

/*
5 17

4
*/