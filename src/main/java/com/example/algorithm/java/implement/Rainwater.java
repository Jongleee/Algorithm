package com.example.algorithm.java.implement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Rainwater {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        st.nextToken();
        int w = Integer.parseInt(st.nextToken());

        int[] heights = new int[w];
        int[] leftMax = new int[w];
        int[] rightMax = new int[w];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < w; i++) {
            heights[i] = Integer.parseInt(st.nextToken());
        }

        int currentMax = 0;
        for (int i = 0; i < w; i++) {
            currentMax = Math.max(currentMax, heights[i]);
            leftMax[i] = currentMax;
        }

        currentMax = 0;
        for (int i = w - 1; i >= 0; i--) {
            currentMax = Math.max(currentMax, heights[i]);
            rightMax[i] = currentMax;
        }

        int answer = 0;
        for (int i = 0; i < w; i++) {
            answer += Math.min(leftMax[i], rightMax[i]) - heights[i];
        }

        System.out.println(answer);
    }
}
/*
4 4
3 0 1 4

5


4 8
3 1 2 3 4 1 1 2

5


3 5
0 0 0 2 0

0
 */