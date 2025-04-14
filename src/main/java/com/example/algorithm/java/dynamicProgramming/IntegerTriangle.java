package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class IntegerTriangle {
    public static void main(String[] args) throws Exception {
        StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        st.nextToken();
        int n = (int) st.nval;

        int[] prev = new int[3];
        st.nextToken();
        prev[1] = (int) st.nval;

        for (int i = 1; i < n; i++) {
            int[] curr = new int[i + 3];
            for (int j = 1; j <= i + 1; j++) {
                st.nextToken();
                int value = (int) st.nval;
                curr[j] = value + Math.max(prev[j - 1], prev[j]);
            }
            prev = curr;
        }

        int max = 0;
        for (int i = 1; i <= n; i++) {
            max = Math.max(max, prev[i]);
        }

        System.out.println(max);
    }
}

/*
5  
7  
3 8
8 1 0
2 7 4 4
4 5 2 6 5

30
*/