package com.example.algorithm.java.prefixSum;

import java.util.Arrays;

public class RentHotel {
    public int solution(String[][] bookTimeSlots) {
        int[] roomUsage = new int[1449]; 
        for (String[] timeSlot : bookTimeSlots) {
            int startTime = timeToMinutes(timeSlot[0], false);
            int endTime = timeToMinutes(timeSlot[1], true);
            for (int i = startTime; i <= endTime; i++) {
                roomUsage[i]++;
            }
        }

        return Arrays.stream(roomUsage).max().getAsInt();
    }

    public int timeToMinutes(String time, boolean isEnd) {
        int hours = Integer.parseInt(time.substring(0, 2));
        int minutes = Integer.parseInt(time.substring(3, 5));
        int totalMinutes = hours * 60 + minutes;
        if (isEnd) {
            totalMinutes += 9;
        }
        return totalMinutes;
    }
}
