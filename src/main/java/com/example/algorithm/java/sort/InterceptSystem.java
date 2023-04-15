package com.example.algorithm.java.sort;

import java.util.Arrays;

public class InterceptSystem {
    public int solution(int[][] targets) {
        Arrays.sort(targets, (o1, o2) -> {
            return o1[1] - o2[1];
        });
        int lastLocation = -1;
        int missileCnt = 0;
        for (int[] t : targets) {
            if (t[0] >= lastLocation) {
                missileCnt++;
                lastLocation = t[1];
            }
        }
        return missileCnt;
    }
}
