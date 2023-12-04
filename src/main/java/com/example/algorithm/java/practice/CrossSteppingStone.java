package com.example.algorithm.java.practice;

public class CrossSteppingStone {
    public int solution(int[] stones, int k) {
        int answer = 0;
        int min = 1;
        int max = 200000000;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (canCross(stones, k, mid)) {
                min = mid + 1;
                answer = Math.max(answer, mid);
            } else {
                max = mid - 1;
            }
        }

        return answer;
    }

    boolean canCross(int[] stones, int k, int friendsNum) {
        int consecutiveSkips = 0;

        for (int stone : stones) {
            consecutiveSkips = (stone - friendsNum < 0) ? consecutiveSkips + 1 : 0;
            if (consecutiveSkips == k) {
                return false;
            }
        }

        return true;
    }

    // @Test
    // void 정답() {
    //     int[] stones = { 2, 4, 5, 3, 2, 1, 4, 2, 5, 1 };
    //     int k = 3;
    //     Assertions.assertEquals(3, solution(stones, k));
    // }
}
