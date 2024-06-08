package com.example.algorithm.java.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Tower {
    private static Point[] stack;
    private static StringBuilder sb;
    private static int top;

    static class Point {
        int value;
        int index;
    
        public Point(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }
    public static void main(String[] args) throws IOException {
        stack = new Point[500001];
        sb = new StringBuilder();
        top = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        add(br.readLine(), n);
        System.out.print(sb);
    }

    private static void add(String input, int n) {
        StringTokenizer st = new StringTokenizer(input, " ");
        for (int i = 1; i <= n; i++) {
            int value = Integer.parseInt(st.nextToken());
            add(value, i);
        }
    }

    private static void add(int value, int index) {
        while (top > 0 && stack[top - 1].value < value) {
            top--;
        }

        if (top <= 0) {
            sb.append(0).append(' ');
        } else {
            sb.append(stack[top - 1].index).append(' ');
        }

        stack[top++] = new Point(value, index);
    }
}

/*
5
6 9 5 7 4

0 0 2 2 4
 */