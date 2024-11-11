package com.example.algorithm.java.prim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class WateringTheFields {
    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static int n;
    private static int threshold;
    private static Point[] points;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        threshold = Integer.parseInt(st.nextToken());
        points = new Point[n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            points[i] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        System.out.println(findMST());
    }

    private static long findMST() {
        int[] minDistance = new int[n];
        boolean[] visited = new boolean[n];
        long totalCost = 0;

        for (int i = 0; i < n; i++) {
            minDistance[i] = Integer.MAX_VALUE;
        }

        int current = 0;
        for (int i = 0; i < n - 1; i++) {
            visited[current] = true;
            int nearest = -1;
            int minCost = Integer.MAX_VALUE;

            for (int j = 0; j < n; j++) {
                if (visited[j])
                    continue;

                int cost = calculateCost(points[current], points[j]);
                minDistance[j] = Math.min(minDistance[j], cost);

                if (minDistance[j] < minCost) {
                    minCost = minDistance[j];
                    nearest = j;
                }
            }

            if (nearest == -1) {
                return -1;
            }

            totalCost += minCost;
            current = nearest;
        }

        return totalCost;
    }

    private static int calculateCost(Point p1, Point p2) {
        int distSquared = (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
        return distSquared < threshold ? Integer.MAX_VALUE : distSquared;
    }
}

/*
3 11
0 2
5 0
4 3

46
*/