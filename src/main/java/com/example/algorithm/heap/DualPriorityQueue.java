package com.example.algorithm.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

public class DualPriorityQueue {
    public int[] solution(String[] operations) {
        //                new String[]{"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"}
        int[] answer = {0,0};
        PriorityQueue<Integer> priorityQueueWithMax = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> priorityQueueWithMin = new PriorityQueue<>();

        for (String operation : operations) {
            String[] splitOperation = operation.split(" ");

            if (splitOperation[0].equals("I")) {
                priorityQueueWithMax.add(Integer.parseInt(splitOperation[1]));
                priorityQueueWithMin.add(Integer.parseInt(splitOperation[1]));
            }

            if (splitOperation[0].equals("D")) {
                if (!priorityQueueWithMax.isEmpty()) {
                    if (splitOperation[1].equals("1")) {
                        int max = priorityQueueWithMax.poll();
                        priorityQueueWithMin.remove(max);

                    } else {
                        int min = priorityQueueWithMin.poll();
                        priorityQueueWithMax.remove(min);
                    }
                }
            }

        }
        if (!priorityQueueWithMax.isEmpty()) {
            answer[0] = priorityQueueWithMax.peek();
            answer[1] = priorityQueueWithMin.peek();
        }
        return answer;
    }
}