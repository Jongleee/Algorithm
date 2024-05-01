package com.example.algorithm.java.queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Card2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        int n = Integer.parseInt(reader.readLine());
        
        int[] queue = new int[2 * n];
        for (int i = 1; i <= n; i++) {
            queue[i] = i;
        }
        int prevIndex = 1;
        int lastIndex = n;
        
        while (n-- > 1) {
            prevIndex++;
            queue[lastIndex + 1] = queue[prevIndex];
            lastIndex++;
            prevIndex++;
        }

        System.out.println(queue[prevIndex]);
    }
}

/*
6

4
 */