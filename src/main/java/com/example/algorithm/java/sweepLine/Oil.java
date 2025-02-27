package com.example.algorithm.java.sweepLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Oil {
    static class Point implements Comparable<Point> {
        long x, y;
        int value;

        Point(long x, long y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        @Override
        public int compareTo(Point other) {
            long ccw = (x - origin.x) * (other.y - origin.y) - (y - origin.y) * (other.x - origin.x);
            return ccw < 0 ? -1 : value(other, ccw);
        }

        private int value(Point other, long ccw) {
            return ccw > 0 ? 1 : Integer.compare(other.value, value);
        }
    }

    private static Point origin;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Point[] points = new Point[2 * n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x0 = Integer.parseInt(st.nextToken());
            int x1 = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            points[i] = new Point(Math.min(x0, x1), y, Math.abs(x0 - x1));
            points[i + n] = new Point(Math.max(x0, x1), y, -Math.abs(x0 - x1));
        }

        System.out.println(findMaxValue(n, points));
    }

    private static int findMaxValue(int n, Point[] points) {
        int maxResult = 0;

        for (int i = 0; i < n; i++) {
            origin = points[i];
            List<Point> sortedPoints = new ArrayList<>();

            for (int j = 0; j < 2 * n; j++) {
                if (origin.y < points[j].y) {
                    sortedPoints.add(points[j]);
                } else if (origin.y > points[j].y) {
                    sortedPoints.add(new Point(
                            2 * origin.x - points[j].x,
                            2 * origin.y - points[j].y,
                            -points[j].value));
                }
            }

            Collections.sort(sortedPoints);
            maxResult = Math.max(maxResult, getMaxAccumulatedValue(sortedPoints, origin.value));
        }

        return maxResult;
    }

    private static int getMaxAccumulatedValue(List<Point> sortedPoints, int startValue) {
        int maxAccumulated = startValue;
        int currentSum = startValue;

        for (Point p : sortedPoints) {
            currentSum += p.value;
            maxAccumulated = Math.max(maxAccumulated, currentSum);
        }

        return maxAccumulated;
    }
}

/*
5
100 180 20
30 60 30
70 110 40
10 40 50
0 80 70

200


3
50 60 10
-42 -42 20
25 0 10

25
*/