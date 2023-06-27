package com.example.algorithm.java.implement;

import java.util.HashSet;
import java.util.Set;

public class ConsecutivePartialSequenceSums {
    public int solution(int[] elements) {
        Set<Integer> set = new HashSet<>();

        for (int start = 1; start <= elements.length; start++) {
            for (int i = 0; i < elements.length; i++) {
                int value = 0;
                for (int j = i; j < i + start; j++) {
                    value += elements[j % elements.length];
                }
                set.add(value);
            }
        }

        return set.size();
    }
}
