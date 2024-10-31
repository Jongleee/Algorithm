package com.example.algorithm.java.convexHull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class RobertHood {
    static class Point {
        int x;
        int y;
        Point(int x, int y) { this.x = x; this.y = y; }
    }
    
    static class PointComparator implements Comparator<Point> {
        @Override
        public int compare(Point a, Point b) {
            return a.x == b.x ? a.y - b.y : a.x - b.x;
        }
    }
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int numPoints = Integer.parseInt(br.readLine());

        Point[] points = new Point[numPoints];
        int[] prev = new int[numPoints];
        int[] next = new int[numPoints];

        for (int i = 0; i < numPoints; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            points[i] = new Point(x, y);
            prev[i] = next[i] = -1;
        }

        Arrays.sort(points, 0, numPoints, new PointComparator());
        initializeConvexHull(prev, next, points, numPoints);

        int maxDistance = findMaxDistance(points, next);
        System.out.printf("%.10f", Math.sqrt(maxDistance));
    }

    private static void initializeConvexHull(int[] prev, int[] next, Point[] points, int numPoints) {
        prev[0] = next[0] = 1;
        prev[1] = next[1] = 0;
        
        for (int i = 2; i < numPoints; i++) {
            int top = i - 1;
            int bottom = i - 1;
            while (top > 0 && ccw(points[i], points[top], points[next[top]]) < 1) {
                top = next[top];
            }
            while (bottom > 0 && ccw(points[i], points[bottom], points[prev[bottom]]) > -1) {
                bottom = prev[bottom];
            }
            prev[top] = i;
            next[i] = top;
            next[bottom] = i;
            prev[i] = bottom;
        }
    }

    private static int findMaxDistance(Point[] points, int[] next) {
        int maxDistance = 0;
        int start = 0;
        int end = next[start];
        maxDistance = distanceSquared(points[start], points[end]);

        while (end != 0) {
            while (end != start) {
                maxDistance = Math.max(maxDistance, distanceSquared(points[start], points[end]));
                if (ccw(new Point(0, 0), subtract(points[next[start]], points[start]), subtract(points[next[end]], points[end])) < 1) {
                    break;
                }
                end = next[end];
            }
            start = next[start];
            if (start == 0) break;
        }
        return maxDistance;
    }

    private static int ccw(Point p1, Point p2, Point p3) {
        int value = (p1.x - p2.x) * (p2.y - p3.y) - (p3.x - p2.x) * (p2.y - p1.y);
        return Integer.compare(value, 0);
    }

    private static int distanceSquared(Point p1, Point p2) {
        int dx = p1.x - p2.x;
        int dy = p1.y - p2.y;
        return dx * dx + dy * dy;
    }

    private static Point subtract(Point p1, Point p2) {
        return new Point(p1.x - p2.x, p1.y - p2.y);
    }
}

/*
2
2 2
-1 -2

5.0000000000


5
-4 1
-100 0
0 4
2 -3
2 300

316.8659022363
*/