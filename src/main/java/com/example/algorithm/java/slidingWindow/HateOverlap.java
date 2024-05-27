package com.example.algorithm.java.slidingWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class HateOverlap {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] sequence = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            sequence[i] = Integer.parseInt(st.nextToken());
        }

        int[] cnt = new int[100001];
        int left = 0;
        int right = 0;
        int max = 0;
        while (right < n) {
            if (cnt[sequence[right]] < k) {
                cnt[sequence[right]]++;
                right++;
                max = Math.max(max, right - left);
            } else {
                cnt[sequence[left]]--;
                left++;
            }
        }
        System.out.println(max);
    }
}


/*
9 2
3 2 5 5 6 4 4 5 7

7


10 1
1 2 3 4 5 6 6 7 8 9

6
 */