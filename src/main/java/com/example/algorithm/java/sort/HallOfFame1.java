package com.example.algorithm.java.sort;

import java.util.ArrayList;
import java.util.Collections;

public class HallOfFame1 {
    public int[] solution(int k, int[] score) {
        int[] answer = new int[score.length];
        ArrayList<Integer> temp = new ArrayList<>();

        for (int i = 0; i < score.length; i++) {
            if (temp.size() < k) {
                insertAndSort(temp, score[i]);
            } else if (temp.get(0) < score[i]) {
                temp.remove(0);
                insertAndSort(temp, score[i]);
            }
            answer[i] = temp.get(0);
        }
        return answer;
    }

    private void insertAndSort(ArrayList<Integer> list, int value) {
        int index = Collections.binarySearch(list, value);
        if (index < 0) {
            index = -index - 1;
        }
        list.add(index, value);
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertArrayEquals(new int[] { 10, 10, 10, 20, 20, 100, 100 },
    //             solution(3, new int[] { 10, 100, 20, 150, 1, 100, 200 }));
    //     Assertions.assertArrayEquals(new int[] { 0, 0, 0, 0, 20, 40, 70, 70, 150, 300 },
    //             solution(4, new int[] { 0, 300, 40, 300, 20, 70, 150, 50, 500, 1000 }));
    // }
}
