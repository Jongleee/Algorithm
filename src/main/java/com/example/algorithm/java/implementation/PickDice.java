package com.example.algorithm.java.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class PickDice {
    private ArrayList<Combination> combinations;
    private int n;

    private class Combination {
        Integer[] selected;
        HashMap<Integer, Integer> sums;

        Combination(Integer[] selected) {
            this.selected = selected;
            sums = new HashMap<>();
        }

        Combination() {
            selected = new Integer[0];
            sums = new HashMap<>();
        }

        void setSum(int[][] dice) {
            ArrayList<Integer> sumsList = new ArrayList<>();
            for (int i : dice[selected[0] - 1]) {
                sumsList.add(i);
            }
            for (int i = 1; i < selected.length; i++) {
                ArrayList<Integer> temp = new ArrayList<>();
                for (Integer p : sumsList) {
                    for (int n : dice[selected[i] - 1]) {
                        temp.add(p + n);
                    }
                    sumsList = temp;
                }
            }
            for (Integer num : sumsList) {
                sums.put(num, sums.getOrDefault(num, 0) + 1);
            }
        }

        boolean isOpposite(Combination other) {
            Integer[] o = other.selected;
            for (int i = 0; i < o.length; i++) {
                for (int j = 0; j < o.length; j++) {
                    if (selected[i] == o[j]) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private void findCombinations(Integer[] combination) {
        int prev = 0;
        int idx = 0;
        for (Integer i : combination) {
            if (i == null) {
                break;
            }
            prev = i;
            idx++;
        }
        if (idx == combination.length) {
            combinations.add(new Combination(combination));
            return;
        }
        for (int i = prev + 1; i <= n; i++) {
            Integer[] temp = combination.clone();
            temp[idx] = i;
            findCombinations(temp);
        }
    }

    private int calculateScore(int idx) {
        int score = 0;
        Combination a = combinations.get(idx);
        Combination b = new Combination();
        for (Combination combination : combinations) {
            if (a.isOpposite(combination)) {
                b = combination;
                break;
            }
        }
        for (Entry<Integer, Integer> aEntry : a.sums.entrySet()) {
            int aNum = aEntry.getKey();
            int aCount = aEntry.getValue();
            for (Entry<Integer, Integer> bEntry : b.sums.entrySet()) {
                int bNum = bEntry.getKey();
                int bCount = bEntry.getValue();
                if (aNum > bNum) {
                    score += aCount * bCount;
                }
            }
        }
        return score;
    }

    public int[] solution(int[][] dice) {
        combinations = new ArrayList<>();
        n = dice.length;
        int[] result;
        findCombinations(new Integer[n / 2]);
        for (Combination c : combinations) {
            c.setSum(dice);
        }
        result = new int[combinations.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = calculateScore(i);
        }
        int idx = 0;
        int max = 0;
        for (int i = 0; i < result.length; i++) {
            if (result[i] > max) {
                idx = i;
                max = result[i];
            }
        }
        int[] answer = new int[n / 2];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = combinations.get(idx).selected[i];
        }
        return answer;
    }

    // @Test
    // void 정답() {
    //     int[][][] edges = { { { 1, 2, 3, 4, 5, 6 }, { 3, 3, 3, 3, 4, 4 }, { 1, 3, 3, 4, 4, 4 }, { 1, 1, 4, 4, 5, 5 } },
    //             { { 1, 2, 3, 4, 5, 6 }, { 2, 2, 4, 4, 6, 6 } },
    //             { { 40, 41, 42, 43, 44, 45 }, { 43, 43, 42, 42, 41, 41 }, { 1, 1, 80, 80, 80, 80 },
    //                     { 70, 70, 1, 1, 70, 70 } }
    //     };
    //     int[][] result = { { 1, 4 }, { 2 }, { 1, 3 } };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertArrayEquals(result[i], solution(edges[i]));
    //     }
    // }
}
