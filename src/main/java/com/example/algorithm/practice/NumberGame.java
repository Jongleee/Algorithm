package com.example.algorithm.practice;

import java.util.Arrays;
import java.util.PriorityQueue;

public class NumberGame {
    // new int[]{5, 1, 3, 7}, new int[]{2, 2, 6, 8}
    public int solution1(int[] A, int[] B) {
        int answer = 0;
        Arrays.sort(A);
        Arrays.sort(B);
        int indexB = A.length-1;
        for (int i = A.length-1; i >=0; i--) {
            if (A[i] < B[indexB]) {
                answer++;
                indexB--;
            }
        }
        return answer;
    }
    public int solution2(int[] A, int[] B) {
        PriorityQueue<Integer> queueA = new PriorityQueue<>();
        PriorityQueue<Integer> queueB = new PriorityQueue<>();
        int answer = 0;
        for (int i = 0; i < A.length ; i++) {
            queueA.add(A[i]);
            queueB.add(B[i]);
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
}
