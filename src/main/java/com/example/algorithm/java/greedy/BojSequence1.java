package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BojSequence1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        long result = 0L;

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int value = Integer.parseInt(st.nextToken()) - i;
            pq.offer(value);
            if (!pq.isEmpty() && pq.peek() > value) {
                result += pq.peek() - value;
                pq.poll();
                pq.offer(value);
            }
        }

        System.out.println(result);
    }
}

/*
7
9 4 8 20 14 15 18

13
*/