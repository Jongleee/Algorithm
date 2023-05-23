package com.example.algorithm.java.recursive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenewalMenu {
    private List<String> answerList = new ArrayList<>();
    private Map<String, Integer> hashMap = new HashMap<>();

    public String[] solution(String[] orders, int[] course) {
        sortOrders(orders);
        generateCombinations(orders, course);

        Collections.sort(answerList);
        return answerList.toArray(new String[0]);
    }

    private void sortOrders(String[] orders) {
        for (int i = 0; i < orders.length; i++) {
            char[] arr = orders[i].toCharArray();
            Arrays.sort(arr);
            orders[i] = String.valueOf(arr);
        }
    }

    private void generateCombinations(String[] orders, int[] course) {
        for (int courseLength : course) {
            for (String order : orders) {
                combination("", order, courseLength);
            }
            checkCondition();
            hashMap.clear();
        }
    }

    private void combination(String order, String others, int count) {
        if (count == 0) {
            hashMap.put(order, hashMap.getOrDefault(order, 0) + 1);
            return;
        }

        for (int i = 0; i < others.length(); i++) {
            combination(order + others.charAt(i), others.substring(i + 1), count - 1);
        }
    }

    private void checkCondition() {
        if (!hashMap.isEmpty()) {
            int max = Collections.max(hashMap.values());

            if (max > 1) {
                for (String key : hashMap.keySet()) {
                    if (hashMap.get(key) == max)
                        answerList.add(key);
                }
            }
        }
    }

}
