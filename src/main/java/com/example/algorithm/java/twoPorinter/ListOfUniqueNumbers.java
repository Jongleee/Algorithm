package com.example.algorithm.java.twoPorinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ListOfUniqueNumbers {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine().trim());

        int[] lastPosition = new int[100001];
        long sum = 0;
        int start = 0;

        for (int i = 1; i <= n; i++) {
            int num = Integer.parseInt(st.nextToken());

            if (start < lastPosition[num]) {
                start = lastPosition[num];
            }

            lastPosition[num] = i;
            sum += (i - start);
        }
        System.out.println(sum);
    }
}

/*
5
1 2 3 4 5

15


5
1 2 3 1 2

12


5
1 1 1 1 1

5
 */