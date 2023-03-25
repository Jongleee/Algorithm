package com.example.algorithm.java.bruteForce;

import java.util.ArrayList;
import java.util.List;

public class TrainerRian {
    static int max = 1320 + 1;
    static int[] numberOfPeople;
    static int start;
    static int end;

    public static int solution(int n, int m, int[][] timetable) {
        int answer = 0;
        if (m <= 1)
            return 0;
        int maxCrowded = findMaxCrowd(timetable);
        for (int i = 2 * n - 2; i > 0; i--) {
            for (int y = 0; y < n; y++) {
                for (int x = 0; x < n; x++) {
                    List<int[]> people = new ArrayList<>();
                    people.add(new int[] { y, x });

                    for (int y2 = y; y2 < n; y2++) {
                        for (int x2 = 0; x2 < n; x2++) {
                            if (y2 == y && x2 <= x) {
                                continue;
                            }

                            if (canPlaceFurther(new int[] { y2, x2 }, i, people)) {
                                people.add(new int[] { y2, x2 });
                            }

                            if (people.size() == maxCrowded) {
                                return i;
                            }
                        }
                    }
                }
            }
        }
        return answer;
    }

    private static int findMaxCrowd(int[][] timetable) {
        numberOfPeople = new int[max];
        start = Integer.MAX_VALUE;
        end = 0;
        for (int[] t : timetable) {
            for (int i = t[0]; i <= t[1]; i++) {
                numberOfPeople[i]++;
                start = Math.min(start, i);
                end = Math.max(end, i);
            }
        }

        int maxCrowded = 0;

        for (int i = start; i <= end; i++) {
            maxCrowded = Math.max(maxCrowded, numberOfPeople[i]);
        }
        if (maxCrowded <= 1) {
            return 0;
        }
        return maxCrowded;
    }

    static boolean canPlaceFurther(int[] coord, int maxDistance, List<int[]> people) {
        for (int[] p : people) {
            if (getDistance(p, coord) < maxDistance) {
                return false;
            }
        }
        return true;
    }

    static int getDistance(int[] a, int[] b) {
        return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
    }

    public static void main(String[] args) {
        int n1 = 3;
        int m1 = 2;
        int[][] t1 = { { 1170, 1210 }, { 1200, 1260 } };
        int n2 = 2;
        int m2 = 1;
        int[][] t2 = { { 840, 900 } };
        int n3 = 2;
        int m3 = 2;
        int[][] t3 = { { 600, 630 }, { 630, 700 } };
        int n4 = 4;
        int m4 = 5;
        int[][] t4 = { { 1140, 1200 }, { 1150, 1200 }, { 1100, 1200 }, { 1210, 1300 }, { 1220, 1280 } };
        System.out.println(solution(n1, m1, t1));// 4
        System.out.println(solution(n2, m2, t2));// 0
        System.out.println(solution(n3, m3, t3));// 2
        System.out.println(solution(n4, m4, t4));// 4
    }
}
