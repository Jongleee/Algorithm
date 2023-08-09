package com.example.algorithm.java.implement;

public class Overpaint {
    public int solution(int n, int m, int[] section) {
        int roller = section[0];
        int cnt = 1;
        for (int s : section) {
            if (s > roller + m - 1) {
                cnt++;
                roller = s;
            }
        }
        return cnt;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(2, solution(8, 4, new int[] { 2, 3, 6 }));
    //     Assertions.assertEquals(1, solution(5, 4, new int[] { 1, 3 }));
    //     Assertions.assertEquals(4, solution(4, 1, new int[] { 1, 2, 3, 4 }));
    // }
}
