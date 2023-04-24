package com.example.algorithm.java.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberBlock {
    static final int MAX = 10_000_000;

    public static int[] solution(long begin, long end) {
        int[] answer = new int[(int) (end - begin + 1)];

        for (long i = begin; i <= end; i++) {
            answer[(int) (i - begin)] = findValue(i);
        }

        return answer;
    }

    private static int findValue(long num) {
        if (num == 1) {
            return 0;
        }

        List<Integer> l = new ArrayList<>();

        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                l.add(i);

                if (num / i <= MAX) {
                    return (int) (num / i);
                }
            }
        }

        if (!l.isEmpty()) {
            return l.get(l.size() - 1);
        }
        return 1;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(100000014, 100000016)));
    }
}
