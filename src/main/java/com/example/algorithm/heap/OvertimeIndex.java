package com.example.algorithm.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

public class OvertimeIndex {
    public long solution(int n, int[] works) {
        //4, new int[] {4, 3, 3}
        //1, new int[] {2,1,2}
        //3, new int[] {1,1}
        long answer = 0;
        PriorityQueue<Integer> worksQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int work : works) {
            worksQueue.add(work);
        }
        while (n > 0) {
            int max = worksQueue.poll();
            worksQueue.add(max - 1);
            n--;

            if (worksQueue.peek() == 0) break;
        }
        if (n > 0) return 0;
        for (int i = 0; i < works.length; i++) {
            int work = worksQueue.poll();
            answer += (long) work * work;
        }
        return answer;
    }
}
