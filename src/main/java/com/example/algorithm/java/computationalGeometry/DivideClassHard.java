package com.example.algorithm.java.computationalGeometry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DivideClassHard {
    static class Point {
        double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        double distanceTo(Point p) {
            return Math.hypot(this.x - p.x, this.y - p.y);
        }
    }

    static class Pair {
        int index;
        double ratio;

        Pair(int index, double ratio) {
            this.index = index;
            this.ratio = ratio;
        }
    }

    static int n;
    static double allArea, allDist;
    static List<Point> hull = new ArrayList<>();
    static double[] areaPrefix, distPrefix;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            hull.add(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        computePrefixSums();
        System.out.println("YES");

        List<Pair> samePoints = getSamePoints();
        List<Double> areaDiffs = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Pair p = samePoints.get(i);
            Point internal = interpolate(p.index, p.ratio);
            int j = p.index % n;
            double area = getArea(i % n, j) + triangleArea(hull.get(i), hull.get(j), internal) - allArea / 2;
            areaDiffs.add(area);
        }

        if (areaDiffs.contains(0.0)) {
            int i = areaDiffs.indexOf(0.0);
            Pair p = samePoints.get(i);
            System.out.println((i + 1) + " 0");
            System.out.println(((p.index % n) + 1) + " " + p.ratio);
        } else {
            binarySearchResult(areaDiffs.get(0), samePoints.get(0));
        }
    }

    static void computePrefixSums() {
        areaPrefix = new double[n];
        distPrefix = new double[n];
        for (int i = 1; i < n; i++) {
            areaPrefix[i] = areaPrefix[i - 1] + cross(hull.get(i - 1), hull.get(i));
            distPrefix[i] = distPrefix[i - 1] + hull.get(i - 1).distanceTo(hull.get(i));
        }
        allArea = Math.abs(areaPrefix[n - 1] + cross(hull.get(n - 1), hull.get(0)));
        allDist = distPrefix[n - 1] + hull.get(n - 1).distanceTo(hull.get(0));
    }

    static List<Pair> getSamePoints() {
        List<Pair> points = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < n; i++) {
            while (j + 1 < n && distPrefix[(j + 1) % n] - distPrefix[i] + allDist * ((j + 1) / n) <= allDist / 2) {
                j++;
            }
            double dx = allDist / 2 - (distPrefix[j % n] - distPrefix[i] + allDist * ((j / n)));
            double r = dx / length(j % n);
            points.add(new Pair(j, r));
        }
        return points;
    }

    static void binarySearchResult(double baseAreaDiff, Pair initial) {
        Pair lo = new Pair(0, 0);
        Pair hi = initial;
        while (interpolate(lo.index, lo.ratio).distanceTo(interpolate(hi.index, hi.ratio)) > 1e-7) {
            Pair mid = getMid(lo, hi);
            Pair c = findCounterpart(mid);
            Point p1 = interpolate(mid.index, mid.ratio);
            Point p2 = interpolate(c.index, c.ratio);
            double ax = getArea((mid.index + 1) % n, c.index % n)
                    + rectangleArea(p1, hull.get((mid.index + 1) % n), hull.get(c.index % n), p2);
            if ((ax >= allArea / 2) ^ (baseAreaDiff >= 0)) {
                hi = mid;
            } else {
                lo = mid;
            }
        }
        Pair c = findCounterpart(hi);
        System.out.println((hi.index + 1) + " " + hi.ratio);
        System.out.println((c.index + 1) + " " + c.ratio);
    }

    static Pair getMid(Pair lo, Pair hi) {
        if (hi.ratio == 0 && lo.index + 1 == hi.index) {
            return new Pair(lo.index, (lo.ratio + 1) / 2);
        } else if (lo.index != hi.index) {
            return new Pair((lo.index + hi.index + 1) / 2, 0);
        } else {
            return new Pair(lo.index, (lo.ratio + hi.ratio) / 2);
        }
    }

    static Pair findCounterpart(Pair start) {
        Pair lo = new Pair(start.index, start.ratio);
        Pair hi = new Pair(start.index + n - 1, 1);
        double dx = length(start.index) * start.ratio;

        while (lo.index != hi.index
                || interpolate(lo.index, lo.ratio).distanceTo(interpolate(hi.index, hi.ratio)) > 1e-7) {
            Pair mid = getMid(lo, hi);
            double dt = distPrefix[mid.index % n] - distPrefix[start.index] - dx
                    + length(mid.index % n) * mid.ratio
                    + (mid.index >= n ? allDist : 0);
            if (dt >= allDist / 2) {
                hi = mid;
            } else {
                lo = mid;
            }
        }
        return hi;
    }

    static double getArea(int i, int j) {
        if (i == j)
            return 0;
        if (i < j) {
            return Math.abs(cross(hull.get(j), hull.get(i)) + areaPrefix[j] - areaPrefix[i]);
        } else {
            return allArea - Math.abs(cross(hull.get(j), hull.get(i)) + areaPrefix[j] - areaPrefix[i]);
        }
    }

    static double triangleArea(Point a, Point b, Point c) {
        return Math.abs(a.x * b.y + b.x * c.y + c.x * a.y - a.y * b.x - b.y * c.x - c.y * a.x);
    }

    static double rectangleArea(Point a, Point b, Point c, Point d) {
        return Math.abs(a.x * b.y + b.x * c.y + c.x * d.y + d.x * a.y
                - a.y * b.x - b.y * c.x - c.y * d.x - d.y * a.x);
    }

    static double length(int i) {
        i %= n;
        return hull.get(i).distanceTo(hull.get((i + 1) % n));
    }

    static Point interpolate(int i, double r) {
        i %= n;
        Point a = hull.get(i);
        Point b = hull.get((i + 1) % n);
        return new Point(a.x * (1 - r) + b.x * r, a.y * (1 - r) + b.y * r);
    }

    static double cross(Point a, Point b) {
        return a.x * b.y - a.y * b.x;
    }
}

/*
3
0 0
10 0
5 10

YES
1 0.5000000074505806
3 7.450580596923828E-9


4
4 0
10 10
0 7
0 6

YES
1 0.9189700856804848
4 0.3843222111463547
*/