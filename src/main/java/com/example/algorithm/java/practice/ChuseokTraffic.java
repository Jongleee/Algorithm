package com.example.algorithm.java.practice;

public class ChuseokTraffic {
    public static int solution(String[] lines) {
        String[] time = new String[lines.length];
        convertTime(lines, time);
        int answer = 0;
        for (int i = 0; i < time.length; i++) {
            String[] split = time[i].split(" ");
            Double area = Double.valueOf(split[0]) + 1;
            int cnt = 1;
            for (int j = i + 1; j < time.length; j++) {
                String[] temp = time[j].split(" ");
                Double start = Double.valueOf(temp[0]) - Double.valueOf(temp[1]) + 0.001;
                if (start < area)
                    cnt++;
                else
                    continue;
            }
            answer = Math.max(answer, cnt);
        }
        return answer;
    }

    private static void convertTime(String[] lines, String[] time) {
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].substring(11).replace(":", "").replace("s", "");
            int t = Integer.valueOf(lines[i].substring(0, 2)) * 3600
                    + Integer.valueOf(lines[i].substring(2, 4)) * 60
                    + Integer.valueOf(lines[i].substring(4, 6));
            time[i] = "" + t + lines[i].substring(6);
        }
    }

    public static void main(String[] args) {
        System.out.println(solution(new String[] {
                "2016-09-15 20:59:57.421 0.351s",
                "2016-09-15 20:59:58.233 1.181s",
                "2016-09-15 20:59:58.299 0.8s",
                "2016-09-15 20:59:58.688 1.041s",
                "2016-09-15 20:59:59.591 1.412s",
                "2016-09-15 21:00:00.464 1.466s",
                "2016-09-15 21:00:00.741 1.581s",
                "2016-09-15 21:00:00.748 2.31s",
                "2016-09-15 21:00:00.966 0.381s",
                "2016-09-15 21:00:02.066 2.62s" }));
    }
}
