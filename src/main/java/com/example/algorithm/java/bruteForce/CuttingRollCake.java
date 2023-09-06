package com.example.algorithm.java.bruteForce;

import java.util.HashSet;

public class CuttingRollCake {
    public int solution(int[] topping) {
        int answer = 0;
        HashSet<Integer> set = new HashSet<>();
        int[] toppingType1 = new int[topping.length];
        int[] toppingType2 = new int[topping.length];

        calculateDistinctToppings(topping, set, toppingType1);
        set.clear();
        calculateDistinctToppings(reverseArray(topping), set, toppingType2);

        for (int i = 0; i < topping.length - 1; i++) {
            if (toppingType1[i] == toppingType2[topping.length - i - 2]) {
                answer++;
            }
        }

        return answer;
    }

    public void calculateDistinctToppings(int[] topping, HashSet<Integer> set, int[] dp) {
        for (int i = 0; i < topping.length; i++) {
            set.add(topping[i]);
            dp[i] = set.size();
        }
    }

    public int[] reverseArray(int[] arr) {
        int[] reversed = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            reversed[i] = arr[arr.length - i - 1];
        }
        return reversed;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(2, solution(new int[] { 1, 2, 1, 3, 1, 4, 1, 2 }));
    //     Assertions.assertEquals(0, solution(new int[] { 1, 2, 3, 1, 4}));
    // }
}
