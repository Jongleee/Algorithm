package com.example.algorithm.java.bruteForce;

import java.util.HashSet;
import java.util.Set;

public class ConsecutivePartialSequenceSums {
    public int solution(int[] elements) {
        Set<Integer> set = new HashSet<>();

        for (int start = 0; start < elements.length; start++) {
            int sum = 0;
            for (int end = start; end < start + elements.length; end++) {
                sum += elements[end % elements.length];
                set.add(sum);
            }
        }

        return set.size();
    }

    // @Test
    // void 정답() {
    //     int[] elements = { 7,9,1,1,4 };

    //     Assertions.assertEquals(18, solution(elements));
    // }
}
