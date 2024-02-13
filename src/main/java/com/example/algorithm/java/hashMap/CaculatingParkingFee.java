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
            String[] parts = r.split(" ");
            int time = getMinutes(parts[0]);
            String car = parts[1];

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
            String car = cars.get(i);
            int time = times.get(car);

            if (parking.containsKey(car)) {
                time += (lastTime - parking.get(car));
            }

            if (time > fees[0]) {
                answer[i] += fees[1] + Math.ceil((time - fees[0]) / (fees[2] * 1.0)) * fees[3];
            } else {
                answer[i] += fees[1];
            }
        }

        return answer;
    }

    public int getMinutes(String time) {
        String[] t = time.split(":");
        return Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]);
    }

    // @Test
    // void 정답() {
    //     int[] fees = { 180, 5000, 10, 600 };
    //     String[] records = { "05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT", "07:59 5961 OUT", "07:59 0148 IN",
    //             "18:59 0000 IN", "19:09 0148 OUT", "22:59 5961 IN", "23:00 5961 OUT" };
    //     int[] result = { 14600, 34400, 5000 };

    //     Assertions.assertArrayEquals(result, solution(fees, records));
    // }
}
