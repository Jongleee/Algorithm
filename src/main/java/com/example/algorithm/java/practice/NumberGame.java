package com.example.algorithm.java.practice;

import java.util.Arrays;
import java.util.PriorityQueue;

public class NumberGame {
    public static int solution1(int[] a, int[] b) {
        int answer = 0;
        Arrays.sort(a);
        Arrays.sort(b);
        int indexB = a.length-1;
        for (int i = a.length-1; i >=0; i--) {
            if (a[i] < b[indexB]) {
                answer++;
                indexB--;
            }
        }
        return answer;
    }
    public int solution2(int[] a, int[] b) {
        PriorityQueue<Integer> queueA = new PriorityQueue<>();
        PriorityQueue<Integer> queueB = new PriorityQueue<>();
        int answer = 0;
        for (int i = 0; i < a.length ; i++) {
            queueA.add(a[i]);
            queueB.add(b[i]);
        }
        while (!queueB.isEmpty()&&!queueA.isEmpty()) {
            if (queueA.peek() < queueB.peek()) {
                answer++;
                queueA.poll();
            }
            queueB.poll();
        }
        return answer;
    }
    public static void main(String[] args) {
        System.out.println(solution1(new int[]{5, 1, 3, 7}, new int[]{2, 2, 6, 8}));
    }
}
