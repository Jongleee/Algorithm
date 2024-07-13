package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class JewelThief {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer token = new StringTokenizer(br.readLine(), " ");
        int n = Integer.parseInt(token.nextToken());
        int k = Integer.parseInt(token.nextToken());

        PriorityQueue<Jewel> jewelQueue = new PriorityQueue<>(new Comparator<Jewel>() {
            @Override
            public int compare(Jewel o1, Jewel o2) {
                return o1.mass - o2.mass;
            }
        });

        PriorityQueue<Integer> valueQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        for (int i = 0; i < n; i++) {
            token = new StringTokenizer(br.readLine(), " ");
            int mass = Integer.parseInt(token.nextToken());
            int value = Integer.parseInt(token.nextToken());
            jewelQueue.offer(new Jewel(mass, value));
        }

        int[] bags = new int[k];
        for (int i = 0; i < k; i++) {
            bags[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(bags);

        long totalValue = 0;
        for (int i = 0; i < k; i++) {
            int currentBag = bags[i];
            while (!jewelQueue.isEmpty() && jewelQueue.peek().mass <= currentBag) {
                valueQueue.offer(jewelQueue.poll().value);
            }
            if (!valueQueue.isEmpty()) {
                totalValue += valueQueue.poll();
            }
        }
        System.out.println(totalValue);
    }

    static class Jewel {
        int mass;
        int value;

        public Jewel(int mass, int value) {
            this.mass = mass;
            this.value = value;
        }
    }
}

/*
2 1
5 10
100 100
11

10


3 2
1 65
5 23
2 99
10
2

164
 */