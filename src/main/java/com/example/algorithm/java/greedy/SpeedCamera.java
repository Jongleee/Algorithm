package com.example.algorithm.java.greedy;

import java.util.Arrays;
import java.util.Comparator;

public class SpeedCamera {
    public int solution(int[][] routes) {
        Arrays.sort(routes, Comparator.comparingInt(o -> o[1]));

        int cameraLocation = routes[0][1];
        int cameraCount = 1;

        for (int[] route : routes) {
            if (route[0] > cameraLocation) {
                cameraCount++;
                cameraLocation = route[1];
            }
        }
        return cameraCount;
    }

    // @Test
    // void 정답() {
    //     int[][] routes = { { -20, -15 }, { -14, -5 }, { -18, -13 }, { -5, -3 } };
    //     Assertions.assertEquals(2, solution(routes));
    // }
}

