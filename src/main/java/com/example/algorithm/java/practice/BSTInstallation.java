package com.example.algorithm.java.practice;

public class BSTInstallation {
    public int solution(int n, int[] stations, int w) {
        int answer = 0;
        int start = 1;

        for (int station : stations) {
            if (start < station - w) {
                answer = calculateStations(start, station - w, w, answer);
            }
            start = station + w + 1;
        }

        if (start <= n) {
            return calculateStations(start, n + 1, w, answer);
        }

        return answer;
    }

    private int calculateStations(int start, int end, int w, int answer) {
        int length = end - start;
        int coverage = w * 2 + 1;
        int count = length / coverage;

        if (length % coverage != 0) {
            count++;
        }

        answer += count;
        return answer;
    }

    // @Test
    // void 정답() {
    //     int[] s1 = { 4, 11 };
    //     int[] s2 = { 9 };
    //     Assertions.assertEquals(3, solution(11, s1, 1));
    //     Assertions.assertEquals(3, solution(16, s2, 2));
    // }
}
