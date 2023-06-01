package com.example.algorithm.java.bruteForce;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CuttingRollCake {
    public int solution(int[] topping) {
        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> compare = new HashSet<>();
        int answer = 0;

        for (int i = 0; i < topping.length; i++) {
            if (map.containsKey(topping[i])) {
                map.put(topping[i], map.get(topping[i]) + 1);
            } else {
                map.put(topping[i], 1);
            }
        }

        for (int i = 0; i < topping.length; i++) {
            map.put(topping[i], map.get(topping[i]) - 1);
            compare.add(topping[i]);

            if (map.get(topping[i]) == 0) {
                map.remove(topping[i]);
            }

            if (compare.size() == map.size()) {
                answer++;
            }
        }

        return answer;
    }
}
