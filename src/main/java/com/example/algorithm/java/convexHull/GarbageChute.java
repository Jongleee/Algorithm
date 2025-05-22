package com.example.algorithm.java.convexHull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class GarbageChute {
    private static final double INF = Double.POSITIVE_INFINITY;

    private static class Point {
        long x, y;

        Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int testCase = 1;

        while (true) {
            int n = Integer.parseInt(br.readLine());
            if (n == 0)
                break;

            List<Point> points = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                long x = Long.parseLong(st.nextToken());
                long y = Long.parseLong(st.nextToken());
                points.add(new Point(x, y));
            }

            Point base = findBasePoint(points);
            List<Point> sorted = new ArrayList<>(points);
            sorted.remove(base);
            sortByPolarAngle(sorted, base);
            sorted.add(0, base);
            List<Point> hull = buildConvexHull(sorted, base);
            double result = computeMinWidth(hull);
            sb.append("Case ").append(testCase++).append(": ")
                    .append(String.format("%.2f", Math.ceil(result * 100) / 100))
                    .append('\n');
        }

        System.out.print(sb);
    }

    private static long ccw(Point p1, Point p2, Point p3) {
        return (p2.x - p1.x) * (p3.y - p2.y) - (p3.x - p2.x) * (p2.y - p1.y);
    }

    private static long distance(Point p1, Point p2) {
        long dx = p2.x - p1.x;
        long dy = p2.y - p1.y;
        return dx * dx + dy * dy;
    }

    private static Point findBasePoint(List<Point> points) {
        Point base = new Point(Long.MAX_VALUE, Long.MAX_VALUE);
        for (Point p : points) {
            if (p.y < base.y || (p.y == base.y && p.x < base.x)) {
                base = p;
            }
        }
        return base;
    }

    private static List<Point> sortByPolarAngle(List<Point> points, Point base) {
        points.sort((a, b) -> {
            long val = ccw(base, a, b);
            if (val == 0) {
                return Long.compare(distance(base, a), distance(base, b));
            }
            return val > 0 ? -1 : 1;
        });
        return points;
    }

    private static List<Point> buildConvexHull(List<Point> points, Point base) {
        Stack<Point> stack = new Stack<>();
        stack.add(base);
        for (Point p : points) {
            while (stack.size() > 1 &&
                    ccw(stack.get(stack.size() - 2), stack.get(stack.size() - 1), p) <= 0) {
                stack.pop();
            }
            stack.push(p);
        }
        return new ArrayList<>(stack);
    }

    private static double computeMinWidth(List<Point> hull) {
        int size = hull.size();
        double minWidth = INF;

        for (int i = 0; i < size; i++) {
            Point p1 = hull.get(i);
            Point p2 = hull.get((i + 1) % size);
            double a = p2.y - p1.y;
            double b = p1.x - p2.x;
            double c = p2.x * p1.y - p1.x * p2.y;

            double maxDist = 0;
            for (int j = (i + 2) % size; j != i; j = (j + 1) % size) {
                Point p3 = hull.get(j);
                double dist = Math.abs(a * p3.x + b * p3.y + c) / Math.sqrt(a * a + b * b);
                maxDist = Math.max(maxDist, dist);
            }

            minWidth = Math.min(minWidth, maxDist);
        }

        return minWidth;
    }
}

/*
3
0 0
3 0
0 4
4
0 10
10 0
20 10
10 20
0

Case 1: 2.40
Case 2: 14.15
*/