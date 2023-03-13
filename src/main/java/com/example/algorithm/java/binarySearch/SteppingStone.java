package com.example.algorithm.java.binarySearch;

import java.util.Arrays;

public class SteppingStone {
    public static int solution(int distance, int[] rocks, int n) {
        Arrays.sort(rocks);
        return binarySearch(distance, rocks, n);
    }

    private static int binarySearch(int distance, int[] rocks, int n) {
        int left = 1;
        int right = distance;
        int answer = 0;

        while (left <= right) {
            int mid = (left + right) / 2;
            int removedRockCnt = countRemovedRocks(rocks, mid, distance);

            if (removedRockCnt <= n) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return answer;
    }

    private static int countRemovedRocks(int[] rocks, int mid, int distance) {
        int before = 0;
        int end = distance;
        int removeCnt = 0;

        for (int rock : rocks) {
            if (rock - before < mid) {
                removeCnt++;
            } else {
                before = rock;
            }
        }

        if (end - before < mid) {
            removeCnt++;
        }

        return removeCnt;
    }

    public static void main(String[] args) {
        int d1 = 25;
        int[] r1 = { 2, 14, 11, 21, 17 };
        int n1 = 2;
        System.out.println(solution(d1, r1, n1));// 4
    }
}
