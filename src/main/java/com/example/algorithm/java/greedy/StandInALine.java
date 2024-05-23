package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StandInALine {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] heights = new int[n + 1];
        List<Integer> result = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            heights[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = n; i >= 1; i--) {
            result.add(heights[i], i);
        }

        StringBuilder sb = new StringBuilder();
        for (int k : result) {
            sb.append(k).append(" ");
        }

        System.out.println(sb.toString().trim());
    }
}

/*
4
2 1 1 0

4 2 1 3


5
0 0 0 0 0

1 2 3 4 5


6
5 4 3 2 1 0

6 5 4 3 2 1


7
6 1 1 1 2 0 0

6 2 3 4 7 5 1
 */