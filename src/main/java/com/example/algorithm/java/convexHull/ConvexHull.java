package com.example.algorithm.java.convexHull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ConvexHull {
    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof Point))
                return false;
            Point other = (Point) obj;
            return x == other.x && y == other.y;
        }

        @Override
        public int hashCode() {
            return Long.hashCode(x) * 31 + Long.hashCode(y);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        Point[] points = new Point[n];
        Point lowestPoint = new Point(40001, 40001);
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            points[i] = new Point(x, y);
            if (y < lowestPoint.y || (y == lowestPoint.y && x < lowestPoint.x)) {
                lowestPoint = points[i];
            }
        }

        List<Point> convexHull = findConvexHull(points, lowestPoint, n);
        bw.write(convexHull.size() + "\n");
        bw.flush();
    }

    private static List<Point> findConvexHull(Point[] points, Point startPoint, int n) {
        List<Point> hull = new ArrayList<>();
        Point p1 = startPoint;

        do {
            hull.add(p1);
            Point p2 = points[0];
            for (int i = 1; i < n; i++) {
                Point p3 = points[i];
                int orientation = ccw(p1, p2, p3);
                if (orientation > 0 || (orientation == 0 && distance(p1, p3) > distance(p1, p2))) {
                    p2 = p3;
                }
            }
            p1 = p2;
        } while (!p1.equals(startPoint));

        return hull;
    }

    private static int ccw(Point p1, Point p2, Point p3) {
        long value = (long) (p2.x - p1.x) * (p3.y - p1.y) - (long) (p2.y - p1.y) * (p3.x - p1.x);
        return Long.compare(value, 0);
    }

    private static long distance(Point p1, Point p2) {
        return (long) (p1.x - p2.x) * (p1.x - p2.x) + (long) (p1.y - p2.y) * (p1.y - p2.y);
    }
}

/*
8
1 1
1 2
1 3
2 1
2 2
2 3
3 1
3 2

5
*/