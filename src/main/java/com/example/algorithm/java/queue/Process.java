package com.example.algorithm.java.queue;

import java.util.LinkedList;
import java.util.Queue;

public class Process {
    public int solution(int[] priorities, int location) {
        Queue<Integer> queue = new LinkedList<>();
        int[] counts = new int[10];
        int answer = 0;

        for (int i = 0; i < priorities.length; i++) {
            queue.offer(i);
            counts[priorities[i]]++;
        }

        int priority = 9;

        while (!queue.isEmpty()) {
            int currentDoc = queue.poll();

            while (counts[priority] == 0) {
                priority--;
            }

            if (currentDoc == location && priority == priorities[currentDoc]) {
                answer++;
                break;
            }

            if (priorities[currentDoc] < priority) {
                queue.offer(currentDoc);
            } else {
                answer++;
                counts[priorities[currentDoc]]--;
            }
        }

        return answer;
    }

    // @Test
    // void 정답() {
    //     int[] p1 = { 2, 1, 3, 2 };
    //     int l1 = 2;
    //     int[] p2 = { 1, 1, 9, 1, 1, 1 };
    //     int l2 = 0;
    //     Assertions.assertEquals(1, solution(p1, l1));
    //     Assertions.assertEquals(5, solution(p2, l2));
    // }
}
