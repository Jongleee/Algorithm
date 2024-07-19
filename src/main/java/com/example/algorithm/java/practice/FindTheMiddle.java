package com.example.algorithm.java.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FindTheMiddle {
    private static final int DIFF = 10000;
    private static final int RANGE = 20000;
    private static final char LINE_BREAK = '\n';
    
    private static int root;
    private static int[] fenwickTree;
    
    private static void insert(int num) {
        for (int i = num; i <= root; i += i & -i) {
            fenwickTree[i]++;
        }
    }
    
    private static int query(int mid) {
        int idx = 0;
        int prefix = 0;
        for (int i = root; i > 0; i >>= 1) {
            if (prefix + fenwickTree[idx + i] < mid) {
                idx += i;
                prefix += fenwickTree[idx];
            }
        }
        return idx + 1;
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        for (root = 1; root < RANGE; root <<= 1);
        fenwickTree = new int[root + 1];
        
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            int num = Integer.parseInt(br.readLine()) + DIFF;
            insert(num);
            sb.append(query((i + 1) >> 1) - DIFF).append(LINE_BREAK);
        }
        
        System.out.print(sb);
    }
}


/*
7
1
5
2
10
-99
7
5

1
1
2
2
2
2
5
 */