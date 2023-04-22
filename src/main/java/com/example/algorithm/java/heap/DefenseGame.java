package com.example.algorithm.java.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

public class DefenseGame {
    public int solution(int n, int k, int[] enemy) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.reverseOrder());
        int answer = 0;

        for (Integer e : enemy) {
            queue.add(e);
            n -= e;
            if (n < 0) {
                if (k > 0) {
                    n += queue.poll();
                    k--;
                } else {
                    return answer;
                }
            }
            answer++;
        }
        return enemy.length;
    }

}
