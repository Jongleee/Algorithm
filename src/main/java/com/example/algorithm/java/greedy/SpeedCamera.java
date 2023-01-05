package com.example.algorithm.java.greedy;

import java.util.Arrays;
import java.util.Comparator;

public class SpeedCamera {
    public static int solution(int[][] routes) {
        Arrays.sort(routes, Comparator.comparingInt(o -> o[1]));
        int location = routes[0][1];
        int answer = 1;

        for (int[] route : routes) {
            if (route[0] > location) {
                answer++;
                location = route[1];
            }
        }
        return answer;
    }
    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{-20,-15}, {-14,-5}, {-18,-13}, {-5,-3}}));
    }
}

