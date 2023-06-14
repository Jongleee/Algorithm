package com.example.algorithm.java.hashMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaculatingParkingFee {
    public int[] solution(int[] fees, String[] records) {
        int lastTime = getMinutes("23:59");
        Map<String, Integer> parking = new HashMap<>();
        Map<String, Integer> times = new HashMap<>();
        List<String> cars = new ArrayList<>();

        for (String r : records) {
            String[] rc = r.split(" ");
            int time = getMinutes(rc[0]);
            String car = rc[1];

            if (!cars.contains(car)) {
                cars.add(car);
                times.put(car, 0);
            }

            if (parking.containsKey(car)) {
                times.put(car, times.get(car) + (time - parking.get(car)));
                parking.remove(car);
            } else {
                parking.put(car, time);
            }
        }

        int[] answer = new int[cars.size()];
        Collections.sort(cars);

        for (int i = 0; i < cars.size(); i++) {
            answer[i] = fees[1];
            String car = cars.get(i);
            int time = times.get(car) - fees[0];

            if (parking.containsKey(car)) {
                time += (lastTime - parking.get(car));
            }

            if (time > 0) {
                answer[i] += Math.ceil(time / (fees[2] * 1.0)) * fees[3];
            }
        }

        return answer;
    }

    public int getMinutes(String time) {
        String[] t = time.split(":");
        return Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]);
    }
}
