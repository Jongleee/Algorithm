package com.example.algorithm.java.hashMap;

import java.util.HashMap;

public class Clothes {
    public int solution(String[][] clothes) {
        HashMap<String, Integer> clothesMap = new HashMap<>();

        for (String[] clothe : clothes) {
            String type = clothe[1];
            clothesMap.put(type, clothesMap.getOrDefault(type, 0) + 1);
        }

        int totalCombinations = 1;
        for (int count : clothesMap.values()) {
            totalCombinations *= (count + 1);
        }

        return totalCombinations - 1;
    }

    // @Test
    // void 정답() {
    //     String[][] clothes1 = { { "yellow_hat", "headgear" }, { "blue_sunglasses", "eyewear" },
    //             { "green_turban", "headgear" } };
    //     String[][] clothes2 = { { "crow_mask", "face" }, { "blue_sunglasses", "face" }, { "smoky_makeup", "face" } };

    //     Assertions.assertEquals(5, solution(clothes1));
    //     Assertions.assertEquals(3, solution(clothes2));
    // }
}
