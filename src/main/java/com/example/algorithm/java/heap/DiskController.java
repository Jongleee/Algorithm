package com.example.algorithm.java.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

public class DiskController {
    public static int solution(int[][] jobs) {

        int answer = 0;
        int time = 0;
        int jobsIndex = 0;
        int count = 0;
        Arrays.sort(jobs, (o1, o2) -> o1[0] - o2[0]);
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);

        while (count < jobs.length) {
            while (jobsIndex < jobs.length && jobs[jobsIndex][0] <= time) {
                priorityQueue.add(jobs[jobsIndex++]);
            }
            if (priorityQueue.isEmpty()) {
                time = jobs[jobsIndex][0];
            } else {
                int[] currentJob = priorityQueue.poll();
                answer += currentJob[1] + time - currentJob[0];
                time += currentJob[1];
                count++;
            }
        }

        return (answer / jobs.length);
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[][] { { 0, 3 }, { 1, 9 }, { 2, 6 } }));
    }
}
