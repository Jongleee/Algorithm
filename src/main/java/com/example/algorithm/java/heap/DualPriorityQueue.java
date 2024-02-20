package com.example.algorithm.java.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

public class DualPriorityQueue {
    public int[] solution(String[] operations) {
        int[] answer = { 0, 0 };
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (String op : operations) {
            String[] splitOp = op.split(" ");
            String command = splitOp[0];
            int value = Integer.parseInt(splitOp[1]);

            if (command.equals("I")) {
                maxHeap.add(value);
                minHeap.add(value);
            } else if (command.equals("D") && !maxHeap.isEmpty()) {
                if (value == 1) {
                    int max = maxHeap.poll();
                    minHeap.remove(max);
                } else {
                    int min = minHeap.poll();
                    maxHeap.remove(min);
                }
            }
        }

        if (!maxHeap.isEmpty()) {
            answer[0] = maxHeap.peek();
            answer[1] = minHeap.peek();
        }

        return answer;
    }

    // @Test
    // void 정답() {
    //     String[] operations1 = { "I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1" };
    //     String[] operations2 = { "I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333" };

    //     int[] result1 = { 0, 0 };
    //     int[] result2 = { 333, -45 };

    //     Assertions.assertArrayEquals(result1, solution(operations1));
    //     Assertions.assertArrayEquals(result2, solution(operations2));
    // }
}
