package com.example.algorithm.java.bruteForce;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SkiCourseDesign {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] elevations = new int[n];
        
        for (int i = 0; i < n; i++) {
            elevations[i] = Integer.parseInt(br.readLine());
        }

        int minCost = Integer.MAX_VALUE;

        for (int lowerBound = 0; lowerBound <= 100 - 17; lowerBound++) {
            int upperBound = lowerBound + 17;
            minCost = Math.min(minCost, calculateCost(elevations, lowerBound, upperBound));
        }

        System.out.println(minCost);
    }

    private static int calculateCost(int[] elevations, int minVal, int maxVal) {
        int cost = 0;

        for (int height : elevations) {
            if (height < minVal) {
                cost += (minVal - height) * (minVal - height);
            } else if (height > maxVal) {
                cost += (height - maxVal) * (height - maxVal);
            }
        }

        return cost;
    }
}

/*
5
20
4
1
24
21

18
*/