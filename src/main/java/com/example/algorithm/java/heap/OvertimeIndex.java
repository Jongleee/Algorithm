package com.example.algorithm.java.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

public class OvertimeIndex {
    public static long solution(int n, int[] works) {
        long answer = 0;
        PriorityQueue<Integer> worksQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int work : works) {
            worksQueue.add(work);
        }
        while (n > 0) {
            int max = worksQueue.poll();
            worksQueue.add(max - 1);
            n--;

            if (worksQueue.peek() == 0)
                break;
        }
        if (n > 0)
            return 0;
        for (int i = 0; i < works.length; i++) {
            int work = worksQueue.poll();
            answer += (long) work * work;
        }
        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution(4, new int[] { 4, 3, 3 }));
    }
}
