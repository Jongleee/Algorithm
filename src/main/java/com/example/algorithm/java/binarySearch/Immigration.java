package com.example.algorithm.java.binarySearch;

import java.util.Arrays;

public class Immigration {
    public long solution(int n, int[] times) {
        Arrays.sort(times);

        long start = 1;
        long end = (long) times[times.length - 1] * n;
        long answer = 0;

        while (start <= end) {
            long mid = (start + end) / 2;
            long sum = 0;

            for (int time : times) {
                sum += mid / time;
            }

            if (sum >= n) {
                end = mid - 1;
                answer = mid;
            } else {
                start = mid + 1;
            }
        }

        return answer;
    }

    // @Test
    // void 정답() {
    //     int n = 6;
    //     int[] times = { 7, 10 };

    //     Assertions.assertEquals(28, solution(n, times));
    // }
}
