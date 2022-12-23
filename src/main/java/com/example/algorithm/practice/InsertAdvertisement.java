package com.example.algorithm.practice;

public class InsertAdvertisement {
    public static String solution(String playTime, String advTime, String[] logs) {
        int playTime1 = timeToSecond(playTime);
        int advTime1 = timeToSecond(advTime);
        int[] accumulatedTime = new int[playTime1 + 1];

        for (String log : logs) {
            String[] arr = log.split("-");

            int start = timeToSecond(arr[0]);
            int end = timeToSecond(arr[1]);

            for (int j = start; j < end; j++) {
                accumulatedTime[j]++;
            }
        }

        long sum = 0;
        for (int i = 0; i < advTime1; i++) {
            sum += accumulatedTime[i];
        }

        long max = sum;
        int start = 0;
        for (int i = 1, j = advTime1; j <= playTime1-advTime1; i++, j++) {
            sum += accumulatedTime[j] - accumulatedTime[i - 1];

            if (max < sum) {
                max = sum;
                start = i;
            }
        }

        return secondToTime(start);
    }

    static int timeToSecond(String time) {
        int second = 0;
        String[] arr = time.split(":");

        second += Integer.parseInt(arr[0]) * 3600;
        second += Integer.parseInt(arr[1]) * 60;
        second += Integer.parseInt(arr[2]);

        return second;
    }

    static String secondToTime(int second) {
        String hour;
        String minute;
        String second1;
        hour = second / 3600 > 9 ? second / 3600 + "" : "0" + second / 3600;
        second %= 3600;
        minute = second / 60 > 9 ? second / 60 + "" : "0" + second / 60;
        second%=60;
        second1 = second>9? second+"" : "0"+second;
        return hour+":"+minute+":"+second1;
    }
    public static void main(String[] args) {
        System.out.println(solution("99:59:59","25:00:00",new String[]{"69:59:59-89:59:59", "01:00:00-21:00:00", "79:59:59-99:59:59", "11:00:00-31:00:00"}));
    }
}
