package com.example.algorithm.practice;

import java.util.PriorityQueue;

public class ShuttleBus {
    public static void main(String[] args) {
        String time = "23:59";
        System.out.println(solution(
                10,
                60,
                45,
                new String[]{time, time, time, time, time, time, time, time, time, time, time, time, time, time, time, time}));
    }

    public static String solution(int n, int t, int m, String[] timetable) {
        int busCount = 0;
        int busTime = timeToInteger("09:00");
        PriorityQueue<Integer> crew = new PriorityQueue<>();
        for (String time : timetable) {
            crew.add(timeToInteger(time));
        }
        int time = 0;
        while (busCount <= n - 1) {
            if (busCount != 0) busTime += t;
            int cnt = 0;
            while (!crew.isEmpty() && cnt <= m - 1) {
                if (crew.peek() > busTime) break;
                if (cnt == m - 1 && busCount == n - 1) {
                    time = (crew.poll() - 1);
                    return String.format("%02d", time / 60) + ":" + String.format("%02d", time % 60);
                }
                crew.poll();
                cnt++;
            }
            busCount++;
        }
        time = busTime;

        return String.format("%02d", time / 60) + ":" + String.format("%02d", time % 60);
    }

    public static int timeToInteger(String time) {
        String[] times = time.split(":");
        return Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
    }

}

