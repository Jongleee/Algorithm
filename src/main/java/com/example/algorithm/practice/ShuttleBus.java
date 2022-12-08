package com.example.algorithm.practice;

import java.util.PriorityQueue;

public class ShuttleBus {
    public static void main(String[] args) {
        System.out.println(solution(
                10,
                60,
                45,
                new String[]{"23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59"}));
    }

    public static String solution(int n, int t, int m, String[] timetable) {
        int busCount = 0;
        int busTime = timeToInteger("09:00");
        PriorityQueue<Integer> crew = new PriorityQueue<Integer>();
        for (String time : timetable) {
            crew.add(timeToInteger(time));
        }
        int time = 0;
        while (busCount <= n - 1) {
            if (busCount != 0) busTime += t;
            int cnt = 0;
            while (crew.size() != 0 && cnt <= m - 1) {
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

    static public int timeToInteger(String time) {
        String[] times = time.split(":");
        return Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
    }

}

