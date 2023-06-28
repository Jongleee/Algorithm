package com.example.algorithm.java.hashMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PickTangerine {
    public int solution(int k, int[] tangerine) {
        Map<Integer, Integer> sizeMap = new HashMap<>();

        for (int size : tangerine) {
            sizeMap.put(size, sizeMap.getOrDefault(size, 0) + 1);
        }

        List<Integer> keyList = new ArrayList<>(sizeMap.keySet());
        keyList.sort(Comparator.comparingInt(sizeMap::get).reversed());

        int answer = 0;
        for (int i = 0; i < keyList.size() && k > 0; i++) {
            int size = keyList.get(i);
            int count = sizeMap.get(size);
            answer++;
            k -= count;
        }

        return answer;
    }
}
