package com.example.algorithm.java.greedy;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ConsultantNumber {
    public int solution(int k, int n, int[][] reqs) {
        int answer = 0;

        List<List<Time>> timeForEachType = new ArrayList<>();
        for (int i = 0; i < k + 1; i++) {
            timeForEachType.add(new ArrayList<>());
        }

        for (int[] req : reqs) {
            int startTime = req[0];
            int duration = req[1];
            int type = req[2];
            timeForEachType.get(type).add(new Time(startTime, startTime + duration));
        }

        int[][] waitTimeForEachType = calculateWaitTimeForEachType(k, n, timeForEachType);

        int[] currentCounselors = new int[k + 1];
        for (int type = 1; type < k + 1; type++) {
            currentCounselors[type] = 1;
        }

        int remainCounselorNumber = n - k;
        while (remainCounselorNumber-- > 0) {
            int maxReduceTime = 0;
            int correspondingType = 0;

            for (int type = 1; type < k + 1; type++) {
                int currentCounselorsByType = currentCounselors[type];
                int reduceWaitingTime = waitTimeForEachType[type][currentCounselorsByType] - waitTimeForEachType[type][currentCounselorsByType + 1];

                if (reduceWaitingTime >= maxReduceTime) {
                    maxReduceTime = reduceWaitingTime;
                    correspondingType = type;
                }
            }

            if (maxReduceTime == 0) break;

            currentCounselors[correspondingType]++;
        }

        for (int type = 1; type < k + 1; type++) {
            if (timeForEachType.get(type).isEmpty()) continue;
            int counselors = currentCounselors[type];
            answer += waitTimeForEachType[type][counselors];
        }

        return answer;
    }

    private int[][] calculateWaitTimeForEachType(int k, int n, List<List<Time>> timeForEachType) {
        int[][] waitTimeForEachTime = new int[k + 1][n + 1];
        for (int type = 1; type < k + 1; type++) {
            if (timeForEachType.get(type).isEmpty()) continue;
            for (int counselors = 1; counselors <= (n - k) + 1; counselors++) {
                waitTimeForEachTime[type][counselors] = calculateWaitTime(timeForEachType.get(type), counselors);
            }
        }
        return waitTimeForEachTime;
    }

    private int calculateWaitTime(List<Time> typeTime, int counselorNumber) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int waitTime = 0;

        for (Time t : typeTime) {
            if (pq.isEmpty() || pq.size() < counselorNumber) {
                pq.add(t.end);
            } else {
                int earlyConsultEndTime = pq.poll();
                if (t.start < earlyConsultEndTime) {
                    waitTime += (earlyConsultEndTime - t.start);
                    pq.add(earlyConsultEndTime + (t.end - t.start));
                } else {
                    pq.add(t.end);
                }
            }
        }
        return waitTime;
    }

    private static class Time {
        int start;
        int end;

        public Time(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
    
    // @Test
    // void 정답() {
    //     int[] k = { 3, 2 };
    //     int[] n = { 5, 3 };
    //     int[][][] reqs = {
    //             { { 10, 60, 1 }, { 15, 100, 3 }, { 20, 30, 1 }, { 30, 50, 3 }, { 50, 40, 1 }, { 60, 30, 2 },
    //                     { 65, 30, 1 }, { 70, 100, 2 } },
    //             { { 5, 55, 2 }, { 10, 90, 2 }, { 20, 40, 2 }, { 50, 45, 2 }, { 100, 50, 2 } } };
    //     int[] result = { 25, 90 };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertEquals(result[i], solution(k[i], n[i], reqs[i]));
    //     }
    // }
}
