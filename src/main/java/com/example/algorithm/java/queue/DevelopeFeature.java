package com.example.algorithm.java.queue;

import java.util.*;

public class DevelopeFeature {
    public int[] solution(int[] progresses, int[] speeds) {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < speeds.length; i++) {
            int remainingDays = (100 - progresses[i]) / speeds[i];
            if ((100 - progresses[i]) % speeds[i] != 0) {
                remainingDays++;
            }
            queue.offer(remainingDays);
        }

        int[] result = new int[queue.size()];
        int idx = 0;
        int prev = 0;
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            if (prev >= curr) {
                result[idx - 1]++;
            } else {
                result[idx] = 1;
                prev = curr;
                idx++;
            }
        }

        return Arrays.copyOf(result, idx);
    }
}
