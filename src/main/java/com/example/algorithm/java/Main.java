package com.example.algorithm.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        Deque<Integer> stack = new ArrayDeque<>();
        int count = 0;

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            st.nextToken();
            int y = Integer.parseInt(st.nextToken());

            while (!stack.isEmpty() && stack.peek() > y) {
                stack.pop();
                count++;
            }

            if (stack.isEmpty() || stack.peek() != y) {
                stack.push(y);
            }
        }

        while (!stack.isEmpty()) {
            if (stack.pop() > 0) {
                count++;
            }
        }

        System.out.println(count);
        br.close();
    }
}
/*
10
1 1
2 2
5 1
6 3
8 1
11 0
15 2
17 3
20 2
22 1

6
 */