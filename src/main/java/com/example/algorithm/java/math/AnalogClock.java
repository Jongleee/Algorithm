package com.example.algorithm.java.math;

public class AnalogClock {
    public int solution(int h1, int m1, int s1, int h2, int m2, int s2) {
        int startTime = hmsToSec(h1, m1, s1);
        int endTime = hmsToSec(h2, m2, s2);

        return countAlarms(endTime) - countAlarms(startTime) + (isAlarmAtStartTime(startTime) ? 1 : 0);
    }

    private int hmsToSec(int h, int m, int s) {
        return h * 3600 + m * 60 + s;
    }

    private int countAlarms(int time) {
        int hourSecondAlarm = time * 719 / 43200;
        int minuteSecondAlarm = time * 59 / 3600;
        int overlap = 43200 <= time ? 2 : 1;

        return hourSecondAlarm + minuteSecondAlarm - overlap;
    }

    private boolean isAlarmAtStartTime(int time) {
        return time * 719 % 43200 == 0 || time * 59 % 3600 == 0;
    }

    // @Test
    // void 정답() {
    //     int[] h1 = { 0, 12, 0, 11, 11, 1, 0 };
    //     int[] m1 = { 5, 0, 6, 59, 58, 5, 0 };
    //     int[] s1 = { 30, 0, 1, 30, 59, 5, 0 };
    //     int[] h2 = { 0, 12, 0, 12, 11, 1, 23 };
    //     int[] m2 = { 7, 0, 6, 0, 59, 5, 59 };
    //     int[] s2 = { 0, 30, 6, 0, 0, 6, 59 };

    //     int[] result = { 2, 1, 0, 1, 1, 2, 2852 };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertEquals(result[i], solution(h1[i], m1[i], s1[i], h2[i], m2[i], s2[i]));
    //     }
    // }
}
