package com.example.algorithm.java.slidingWindow;

import java.util.HashMap;
import java.util.Map;

public class DiscountEvent {
    public int solution(String[] want, int[] number, String[] discount) {
        int answer = 0;
        Map<String, Integer> matchMap = createMatchMap(want);

        int total = calculateTotal(number);
        int ptr1 = 0;
        int ptr2 = total - 1;

        int[] saleNum = calculateSaleNum(discount, matchMap, total, number);
        while (true) {
            if (checkMatch(number, saleNum))
                answer++;

            if (matchMap.containsKey(discount[ptr1])) {
                saleNum[matchMap.get(discount[ptr1])]--;
            }

            ptr1++;
            ptr2++;

            if (ptr2 == discount.length)
                break;

            if (matchMap.containsKey(discount[ptr2])) {
                saleNum[matchMap.get(discount[ptr2])]++;
            }
        }
        return answer;
    }

    private Map<String, Integer> createMatchMap(String[] want) {
        Map<String, Integer> matchMap = new HashMap<>();
        for (int i = 0; i < want.length; i++) {
            matchMap.put(want[i], i);
        }
        return matchMap;
    }

    private int calculateTotal(int[] number) {
        int total = 0;
        for (int i = 0; i < number.length; i++) {
            total += number[i];
        }
        return total;
    }

    private int[] calculateSaleNum(String[] discount, Map<String, Integer> matchMap, int total, int[] number) {
        int[] saleNum = new int[number.length];
        for (int i = 0; i < total; i++) {
            if (matchMap.containsKey(discount[i])) {
                saleNum[matchMap.get(discount[i])]++;
            }
        }
        return saleNum;
    }

    private boolean checkMatch(int[] number, int[] saleNum) {
        for (int i = 0; i < number.length; i++) {
            if (number[i] > saleNum[i])
                return false;
        }
        return true;
    }

    // @Test
    // void 정답() {
    //     String[] w1 = { "banana", "apple", "rice", "pork", "pot" };
    //     int[] n1 = { 3, 2, 2, 2, 1 };
    //     String[] d1 = { "chicken", "apple", "apple", "banana", "rice",
    //             "apple", "pork", "banana", "pork", "rice", "pot",
    //             "banana", "apple", "banana" };

    //     String[] w2 = { "apple" };
    //     int[] n2 = { 10 };
    //     String[] d2 = { "banana", "banana", "banana", "banana", "banana",
    //             "banana", "banana", "banana", "banana", "banana" };

    //     Assertions.assertEquals(3, solution(w1, n1, d1));
    //     Assertions.assertEquals(0, solution(w2, n2, d2));
    // }
}
