package com.example.algorithm.java.hashMap;

import java.util.HashMap;
import java.util.Map;

public class SeesawPartner {
    public boolean isPartner(int f1, int f2) {
        int[] factors = { 2, 3, 4 };
        for (int factor1 : factors) {
            for (int factor2 : factors) {
                if (f1 * factor1 == f2 * factor2) {
                    return true;
                }
            }
        }
        return false;
    }

    public long solution(int[] weights) {
        long answer = 0;
        Map<Integer, Integer> weightMap = new HashMap<>();
        for (int weight : weights) {
            weightMap.put(weight, weightMap.getOrDefault(weight, 0) + 1);
        }
        Integer[] uniqueWeights = weightMap.keySet().toArray(new Integer[0]);
        Integer[] weightCounts = weightMap.values().toArray(new Integer[0]);
        for (int i = 0; i < uniqueWeights.length - 1; i++) {
            int count = weightCounts[i];
            if (count > 1) {
                answer += (long) count * (count - 1) / 2;
            }
            for (int j = i + 1; j < uniqueWeights.length; j++) {
                if (isPartner(uniqueWeights[i], uniqueWeights[j])) {
                    answer += (long) count * weightCounts[j];
                }
            }
        }
        int count = weightCounts[uniqueWeights.length - 1];
        if (count > 1) {
            answer += (long) count * (count - 1) / 2;
        }
        return answer;
    }
}
