package com.example.algorithm.java.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

public class DiskController {
    public int solution(int[][] jobs) {
        Arrays.sort(jobs, (o1, o2) -> o1[0] - o2[0]);

        PriorityQueue<int[]> minHeap = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);

        int time = 0;
        int totalResponseTime = 0;
        int jobsIndex = 0;
        int count = 0;

        while (count < jobs.length) {
            while (jobsIndex < jobs.length && jobs[jobsIndex][0] <= time) {
                minHeap.add(jobs[jobsIndex++]);
            }

            if (minHeap.isEmpty()) {
                time = jobs[jobsIndex][0];
            } else {
                int[] currentJob = minHeap.poll();
                totalResponseTime += (time + currentJob[1] - currentJob[0]);
                time += currentJob[1];
                count++;
            }
        }

        return totalResponseTime / jobs.length;
    }

    // @Test
    // void 정답() {
    //     int[][] jobs = { { 0, 3 }, { 1, 9 }, { 2, 6 } };

    //     Assertions.assertEquals(9, solution(jobs));
    // }
}
