package com.example.algorithm.java.practice;

import java.util.PriorityQueue;

public class ShuttleBus {
    public String solution(int n, int t, int m, String[] timetable) {
        int busTime = 9 * 60;
        int busCount = 0;
        int lastCrewTime = 0;

        PriorityQueue<Integer> crew = new PriorityQueue<>();
        for (String time : timetable) {
            crew.add(timeToInteger(time));
        }

        while (busCount < n) {
            if (busCount != 0)
                busTime += t;
            int cnt = 0;

            while (!crew.isEmpty() && cnt < m) {
                if (crew.peek() > busTime)
                    break;
                if (busCount == n - 1 && cnt == m - 1) {
                    lastCrewTime = (crew.poll() - 1);
                    return String.format("%02d:%02d", lastCrewTime / 60, lastCrewTime % 60);
                }
                crew.poll();
                cnt++;
            }
            busCount++;
        }
        return String.format("%02d:%02d", busTime / 60, busTime % 60);
    }

    public int timeToInteger(String time) {
        String[] times = time.split(":");
        return Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
    }

    // @Test
    // void 정답() {
    //     String[] t1 = { "08:00", "08:01", "08:02", "08:03" };
    //     String[] t2 = { "09:10", "09:09", "08:00" };
    //     String[] t3 = { "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59",
    //             "23:59", "23:59", "23:59", "23:59", "23:59", "23:59" };
    //     Assertions.assertEquals("09:00", solution(1, 1, 5, t1));
    //     Assertions.assertEquals("09:09", solution(2, 10, 2, t2));
    //     Assertions.assertEquals("18:00", solution(10, 60, 45, t3));
    // }
}

