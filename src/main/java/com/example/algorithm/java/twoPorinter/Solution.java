package com.example.algorithm.java.twoPorinter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        
        int n = Integer.parseInt(br.readLine());
        int[] fluids = new int[n];
        
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < n; i++) {
            fluids[i] = Integer.parseInt(st.nextToken());
        }
        
        Arrays.sort(fluids);
        
        int start = 0;
        int end = n - 1;
        int closestSum = fluids[start] + fluids[end];
        int resultA = start;
        int resultB = end;
        
        while (start < end) {
            int sum = fluids[start] + fluids[end];
            
            if (Math.abs(sum) < Math.abs(closestSum)) {
                closestSum = sum;
                resultA = start;
                resultB = end;
            }
            
            if (sum < 0) {
                start++;
            } else if (sum > 0) {
                end--;
            } else {
                break;
            }
        }
        
        sb.append(fluids[resultA]).append(" ").append(fluids[resultB]);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
/*
4
-100 -2 -1 103

-100 103


5
-99 -2 -1 4 98

-99 98
 */