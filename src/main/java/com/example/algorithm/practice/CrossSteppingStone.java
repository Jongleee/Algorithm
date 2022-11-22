package com.example.algorithm.practice;

public class CrossSteppingStone {
    public static void main(String[] args) {
        System.out.println(solution(
                new int[]{2, 4, 5, 3, 2, 1, 4, 2, 5, 1}, 3
        ));

    }

    public static int solution(int[] stones, int k) {
        int answer = 0;
        int min = 1;
        int max = 200000000;
        while (min <= max) {
            int mid = (min + max) / 2;

            if (isPossible(stones, k, mid)) {
                min = mid + 1;
                answer = Math.max(answer, mid);
            } else {
                max = mid - 1;
            }
        }

        return answer;
    }

    static boolean isPossible(int[] stones, int k, int friendsNum) {
        int skip = 0;

        for (int stone : stones) {
            skip = stone - friendsNum < 0 ? skip + 1 : 0;
            if (skip == k)
                return false;
        }

        return true;
    }
}
