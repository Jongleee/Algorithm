package com.example.algorithm.java.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LargestRectangleInHistogram {
    private static int[] histogram;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        while (true) {
            st = new StringTokenizer(br.readLine(), " ");
            int n = Integer.parseInt(st.nextToken());

            if (n == 0) {
                break;
            }

            histogram = new int[n];
            for (int i = 0; i < n; i++) {
                histogram[i] = Integer.parseInt(st.nextToken());
            }

            sb.append(getMaxArea(n)).append('\n');
        }
        System.out.print(sb.toString());
        br.close();
    }

    private static long getMaxArea(int len) {
        int[] stack = new int[len];
        int size = 0;
        int top = -1;
        long maxArea = 0;

        for (int i = 0; i < len; i++) {
            while (size > 0 && histogram[stack[top]] >= histogram[i]) {
                int height = histogram[stack[top--]];
                size--;
                long width = size == 0 ? i : i - 1 - stack[top];
                maxArea = Math.max(maxArea, height * width);
            }
            stack[++top] = i;
            size++;
        }

        while (size > 0) {
            int height = histogram[stack[top--]];
            size--;
            long width = size == 0 ? len : len - 1 - stack[top];
            maxArea = Math.max(maxArea, width * height);
        }

        return maxArea;
    }
}

/*
7 2 1 4 5 1 3 3
4 1000 1000 1000 1000
0

8
4000
*/