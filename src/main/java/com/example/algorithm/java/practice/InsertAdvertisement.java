package com.example.algorithm.java.practice;

public class InsertAdvertisement {
    public String solution(String playTime, String advTime, String[] logs) {
        int playTimeSeconds = timeToInt(playTime);
        int advTimeSeconds = timeToInt(advTime);

        int[] accumulatedTime = new int[playTimeSeconds + 1];

        for (String log : logs) {
            String[] l = log.split("-");
            int start = timeToInt(l[0]);
            int end = timeToInt(l[1]);
            for (int i = start; i < end; i++) {
                accumulatedTime[i]++;
            }
        }

        int optimalStartTime = findOptimalStartTime(accumulatedTime, advTimeSeconds);

        return timeToString(optimalStartTime);
    }

    private int timeToInt(String time) {
        String[] times = time.split(":");
        int toSec = 3600;
        int totalTime = 0;

        for (String t : times) {
            int num = Integer.parseInt(t);
            totalTime += num * toSec;
            toSec /= 60;
        }

        return totalTime;
    }

    private String timeToString(int time) {
        int hour = time / 3600;
        int minute = (time % 3600) / 60;
        int second = time % 60;

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    private int findOptimalStartTime(int[] accumulatedTime, int advTimeSeconds) {
        long maxSum = 0;
        long sum = 0;
        int optimalStartTime = 0;

        for (int i = 0; i < advTimeSeconds; i++) {
            sum += accumulatedTime[i];
        }
        maxSum = sum;

        for (int i = advTimeSeconds; i < accumulatedTime.length; i++) {
            sum += accumulatedTime[i] - accumulatedTime[i - advTimeSeconds];

            if (sum > maxSum) {
                maxSum = sum;
                optimalStartTime = i - advTimeSeconds + 1;
            }
        }

        return optimalStartTime;
    }

    // @Test
    // void 정답() {
    //     String[] l1 = { "01:20:15-01:45:14", "00:40:31-01:00:00", "00:25:50-00:48:29", "01:30:59-01:53:29",
    //             "01:37:44-02:02:30" };
    //     String[] l2 = { "69:59:59-89:59:59", "01:00:00-21:00:00", "79:59:59-99:59:59", "11:00:00-31:00:00" };
    //     String[] l3 = { "15:36:51-38:21:49", "10:14:18-15:36:51", "38:21:49-42:51:45" };
    //     Assertions.assertEquals("01:30:59", solution("02:03:55", "00:14:15", l1));
    //     Assertions.assertEquals("01:00:00", solution("99:59:59", "25:00:00", l2));
    //     Assertions.assertEquals("00:00:00", solution("50:00:00", "50:00:00", l3));
    // }
}
