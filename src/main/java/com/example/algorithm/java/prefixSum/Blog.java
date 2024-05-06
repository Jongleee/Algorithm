package com.example.algorithm.java.prefixSum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Blog {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int day = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int[] prefixSum = new int[day];

        st = new StringTokenizer(br.readLine());
        prefixSum[0] = Integer.parseInt(st.nextToken());
        for (int i = 1; i < day; i++) {
            prefixSum[i] = Integer.parseInt(st.nextToken()) + prefixSum[i - 1];
        }
        
        int maxSum = prefixSum[x - 1];
        int count = 1;
        for (int i = x; i < day; i++) {
            int currentSum = prefixSum[i] - prefixSum[i - x];
            if (currentSum > maxSum) {
                maxSum = currentSum;
                count = 1;
            } else if (currentSum == maxSum) {
                count++;
            }
        }

        if (maxSum == 0) {
            System.out.println("SAD");
        } else {
            System.out.println(maxSum);
            System.out.println(count);
        }
    }
}

/*
5 2
1 4 2 5 1

7
1


5 3
0 0 0 0 0

SAD


7 5
1 1 1 1 1 5 1

9
2
 */