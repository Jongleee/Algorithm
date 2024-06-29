package com.example.algorithm.java.bruteForce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class HighRiseBuilding {
    static int n;
    static int[] buildings;
    static int[] visibleCount;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        buildings = new int[n];
        visibleCount = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < n; i++) {
            buildings[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < n - 1; i++) {
            visibleCount[i]++;
            visibleCount[i + 1]++;
            double maxSlope = (double) buildings[i + 1] - buildings[i];

            for (int j = i + 2; j < n; j++) {
                double currentSlope = calculateSlope(i, j);
                if (currentSlope > maxSlope) {
                    maxSlope = currentSlope;
                    visibleCount[i]++;
                    visibleCount[j]++;
                }
            }
        }

        int maxVisible = 0;
        for (int count : visibleCount) {
            if (count > maxVisible) {
                maxVisible = count;
            }
        }

        System.out.println(maxVisible);
    }

    static double calculateSlope(int i, int j) {
        return (double) (buildings[j] - buildings[i]) / (j - i);
    }
}

/*
15
1 5 3 2 6 3 2 6 4 2 5 7 3 1 5

7


1
10

0


4
5 5 5 5

2


5
1 2 7 3 2

4


10
1000000000 999999999 999999998 999999997 999999996 1 2 3 4 5

6
 */