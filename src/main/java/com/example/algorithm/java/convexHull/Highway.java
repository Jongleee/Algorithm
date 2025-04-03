package com.example.algorithm.java.convexHull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Highway {
    private static class Point implements Comparable<Point> {
        long x, y;

        Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point o) {
            long val = ccw(base, this, o);
            return val == 0 ? Long.compare(dist(base, this), dist(base, o)) : Long.compare(val, 0);
        }
    }

    private static final long INF = Long.MAX_VALUE;
    private static Point base;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(br.readLine());
            List<Point> points = new ArrayList<>();
            base = new Point(INF, INF);

            for (int j = 0; j < n; j++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                long x = Long.parseLong(st.nextToken());
                long y = Long.parseLong(st.nextToken());
                Point point = new Point(x, y);
                points.add(point);
                if (y < base.y || (y == base.y && x < base.x)) {
                    base = point;
                }
            }

            List<Point> hull = convexHull(points);
            Point[] result = rotatingCalipers(hull);

            sb.append(result[0].x).append(' ').append(result[0].y).append(' ')
                    .append(result[1].x).append(' ').append(result[1].y).append('\n');
        }
        System.out.print(sb);
    }

    private static Point[] rotatingCalipers(List<Point> hull) {
        int j = 1;
        long maxDist = 0;
        Point[] result = new Point[2];

        for (int i = 0; i < hull.size(); i++) {
            int ni = (i + 1) % hull.size();
            while (true) {
                int nj = (j + 1) % hull.size();
                if (ccw(hull.get(i), hull.get(ni), hull.get(j), hull.get(nj)) < 0) {
                    j = nj;
                } else {
                    break;
                }
            }
            long distance = dist(hull.get(i), hull.get(j));
            if (distance > maxDist) {
                maxDist = distance;
                result[0] = hull.get(i);
                result[1] = hull.get(j);
            }
        }
        return result;
    }

    private static long ccw(Point p1, Point p2, Point p3) {
        return (p2.x - p1.x) * (p3.y - p2.y) - (p3.x - p2.x) * (p2.y - p1.y);
    }

    private static long ccw(Point p1, Point p2, Point p3, Point p4) {
        return (p2.x - p1.x) * (p4.y - p3.y) - (p4.x - p3.x) * (p2.y - p1.y);
    }

    private static long dist(Point p1, Point p2) {
        return (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y);
    }

    private static List<Point> convexHull(List<Point> points) {
        Collections.sort(points);
        Stack<Point> stack = new Stack<>();
        stack.add(base);

        for (Point point : points) {
            while (stack.size() > 1 && ccw(stack.get(stack.size() - 2), stack.peek(), point) >= 0) {
                stack.pop();
            }
            stack.add(point);
        }
        return new ArrayList<>(stack);
    }
}

/*
2
4
-100 -50
20 -50
-20 50
100 50
9
-1 -1
3 -3
6 -6
-3 -6
12 0
3 4
-6 3
0 9
6 9

-100 -50 100 50
-6 3 12 0
*/