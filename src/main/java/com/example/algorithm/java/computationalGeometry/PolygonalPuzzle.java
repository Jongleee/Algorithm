package com.example.algorithm.java.computationalGeometry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class PolygonalPuzzle {
    private static final double EPS = 1e-7;
    private static final double PI = Math.PI;
    private static int prevI, prevJ;

    static class Point {
        double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        Point add(Point p) {
            return new Point(x + p.x, y + p.y);
        }

        Point subtract(Point p) {
            return new Point(x - p.x, y - p.y);
        }

        Point multiply(double scalar) {
            return new Point(x * scalar, y * scalar);
        }

        double cross(Point p) {
            return x * p.y - y * p.x;
        }

        double dist(Point p) {
            return Math.hypot(x - p.x, y - p.y);
        }

        double dot(Point p) {
            return x * p.x + y * p.y;
        }

        Point rotCCW(double angle) {
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            return new Point(x * cos - y * sin, x * sin + y * cos);
        }

        Point rotCCW90() {
            return new Point(-y, x);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Point point = (Point) obj;
            return equal(x, point.x) && equal(y, point.y);
        }
    }

    static class Segment {
        Point p, q;

        Segment(Point p, Point q) {
            this.p = p;
            this.q = q;
        }

        double length() {
            return p.dist(q);
        }

        boolean isHorizontal() {
            return equal(p.y, q.y);
        }

        boolean faceRight() {
            return !isHorizontal() && p.y < q.y;
        }

        boolean faceLeft() {
            return !isHorizontal() && p.y > q.y;
        }

        boolean containsExceptEndpoints(Point r) {
            if (!zero(q.subtract(p).cross(r.subtract(p))))
                return false;
            if (q.subtract(p).dot(r.subtract(p)) < EPS)
                return false;
            if (p.subtract(q).dot(r.subtract(q)) < EPS)
                return false;
            return true;
        }

        boolean contains(Point r) {
            if (p.equals(r) || q.equals(r))
                return true;
            return containsExceptEndpoints(r);
        }

        boolean intersect(Segment s) {
            int o1 = orientation(p, q, s.p);
            int o2 = orientation(p, q, s.q);
            int o3 = orientation(s.p, s.q, p);
            int o4 = orientation(s.p, s.q, q);
            return o1 * o2 < 0 && o3 * o4 < 0;
        }

        double commonLength(Segment s) {
            if (contains(s.p) && contains(s.q))
                return s.length();
            if (s.contains(p) && s.contains(q))
                return length();
            return commonLengthAux(s);
        }

        private double commonLengthAux(Segment s) {
            if (contains(s.p) && s.contains(p))
                return p.dist(s.p);
            if (contains(s.p) && s.contains(q))
                return q.dist(s.p);
            if (contains(s.q) && s.contains(p))
                return p.dist(s.q);
            if (contains(s.q) && s.contains(q))
                return q.dist(s.q);
            return 0;
        }
    }

    private static boolean zero(double x) {
        return Math.abs(x) < EPS;
    }

    private static boolean equal(double a, double b) {
        return zero(a - b);
    }

    private static int orientation(Point o, Point a, Point b) {
        double cross = a.subtract(o).cross(b.subtract(o));
        if (zero(cross))
            return 0;
        return cross > 0 ? 1 : -1;
    }

    private static double angle(Point a, Point b) {
        double ang = Math.atan2(a.cross(b), a.dot(b));
        return ang < 0 ? ang + 2 * PI : ang;
    }

    private static Point vertexAt(List<Point> polygon, int index) {
        int n = polygon.size();
        index = (index % n + n) % n;
        return polygon.get(index);
    }

    private static boolean intersectionAux(List<Point> p1, List<Point> p2, int i, int j) {
        Point a0 = vertexAt(p1, i - 1);
        Point a1 = vertexAt(p1, i);
        Point a2 = vertexAt(p1, i + 1);
        Point b0 = vertexAt(p2, j - 1);
        Point b1 = vertexAt(p2, j);
        Point b2 = vertexAt(p2, j + 1);

        Segment segA = new Segment(a1, a2);
        Segment segB = new Segment(b1, b2);

        if (segA.intersect(segB)) {
            prevI = i;
            prevJ = j;
            return true;
        }

        if (segB.containsExceptEndpoints(a1)) {
            if (orientation(b1, b2, a2) == 1 || orientation(b1, b2, a0) == 1) {
                prevI = i;
                prevJ = j;
                return true;
            }
        }
        if (segA.containsExceptEndpoints(b1)) {
            if (orientation(a1, a2, b2) == 1 || orientation(a1, a2, b0) == 1) {
                prevI = i;
                prevJ = j;
                return true;
            }
        }

        if (a1.equals(b1)) {
            Point base = b2.subtract(b1);
            double totalAngle = angle(base, b0.subtract(b1));
            double angle1 = angle(base, a0.subtract(b1));
            double angle2 = angle(base, a2.subtract(b1));
            if ((angle1 > EPS && angle1 < totalAngle - EPS) ||
                    (angle2 > EPS && angle2 < totalAngle - EPS)) {
                prevI = i;
                prevJ = j;
                return true;
            }
        }

        return false;
    }

    private static boolean polygonsIntersect(List<Point> p1, List<Point> p2) {
        int itersI = p1.size() / 2 + 1;
        int itersJ = p2.size() / 2 + 1;

        for (int i = 0; i < itersI; i++) {
            for (int j = 0; j < itersJ; j++) {
                if (intersectionAux(p1, p2, prevI + i, prevJ + j))
                    return true;
                if (j > 0 && intersectionAux(p1, p2, prevI + i, prevJ - j))
                    return true;
            }
            for (int j = 0; i != 0 && j < itersJ; j++) {
                if (intersectionAux(p1, p2, prevI - i, prevJ + j))
                    return true;
                if (j > 0 && intersectionAux(p1, p2, prevI - i, prevJ - j))
                    return true;
            }
        }
        return false;
    }

    private static List<List<Point>> createRotations(List<Point> polygon, boolean invert) {
        List<List<Point>> rotations = new ArrayList<>();
        for (int i = 0; i < polygon.size(); i++) {
            List<Point> rotated = new ArrayList<>();
            Point base = polygon.get(i);
            for (Point p : polygon) {
                rotated.add(p.subtract(base));
            }

            Point next = vertexAt(rotated, i + 1);
            double ang = Math.atan2(next.y, -next.x);
            for (int j = 0; j < rotated.size(); j++) {
                rotated.set(j, rotated.get(j).rotCCW(ang));
            }

            Point newNext = vertexAt(rotated, i + 1);
            for (int j = 0; j < rotated.size(); j++) {
                rotated.set(j, rotated.get(j).subtract(newNext));
            }

            if (invert) {
                for (int j = 0; j < rotated.size(); j++) {
                    rotated.set(j, rotated.get(j).rotCCW90().rotCCW90());
                }
            }
            rotations.add(rotated);
        }
        return rotations;
    }

    private static double commonBoundaryLength(List<Point> p1, List<Point> p2) {
        double total = 0;
        for (int i = 0; i < p1.size(); i++) {
            Segment edge1 = new Segment(p1.get(i), vertexAt(p1, i + 1));
            for (int j = 0; j < p2.size(); j++) {
                Segment edge2 = new Segment(p2.get(j), vertexAt(p2, j + 1));
                total += edge1.commonLength(edge2);
            }
        }
        return total;
    }

    private static boolean rangeContains(double a, double b, double x) {
        if (a > b) {
            double temp = a;
            a = b;
            b = temp;
        }
        return a <= x + EPS && x <= b + EPS;
    }

    private static void collectShifts(List<Point> edges, List<Point> vertices, boolean isRight, double maxShift,
            List<Double> shifts) {
        for (int i = 0; i < edges.size(); i++) {
            Point p = edges.get(i);
            Point q = vertexAt(edges, i + 1);
            Segment wall = new Segment(p, q);

            if (wall.isHorizontal())
                continue;
            double slope = (q.y - p.y) / (q.x - p.x);

            for (int j = 0; j < vertices.size(); j++) {
                Point v = vertices.get(j);
                if (!rangeContains(p.y, q.y, v.y))
                    continue;

                Point v0 = vertexAt(vertices, j - 1);
                Point v1 = vertices.get(j);
                Point v2 = vertexAt(vertices, j + 1);
                if (orientation(v0, v1, v2) == -1)
                    continue;

                Segment seg1 = new Segment(v0, v2);
                Segment seg2 = new Segment(v1, v2);
                boolean pointLeft = seg1.faceLeft() || seg2.faceLeft();
                boolean pointRight = seg2.faceRight();

                if (wall.faceRight() && pointRight)
                    continue;
                if (wall.faceLeft() && pointLeft)
                    continue;

                double x;
                if (equal(p.x, q.x)) {
                    x = v.x - p.x;
                } else {
                    double intercept = p.y - p.x * slope;
                    x = v.x - (v.y - intercept) / slope;
                }
                x = isRight ? x : -x;
                if (x > -EPS && x < maxShift + EPS) {
                    shifts.add(x);
                }
            }
        }
    }

    private static double findOptimalShift(List<Point> poly1, List<Point> poly2, double maxShift) {
        List<Double> shifts = new ArrayList<>();
        collectShifts(poly1, poly2, true, maxShift, shifts);
        collectShifts(poly2, poly1, false, maxShift, shifts);

        Collections.sort(shifts);
        double prevShift = 0;
        double best = 0;

        for (double shift : shifts) {
            if (shift - prevShift < 0.3)
                continue;

            List<Point> shifted = new ArrayList<>();
            double delta = shift - prevShift;
            for (Point p : poly1) {
                shifted.add(new Point(p.x + delta, p.y));
            }

            if (!polygonsIntersect(poly2, shifted)) {
                double common = commonBoundaryLength(poly2, shifted);
                best = Math.max(best, common);
            }
            prevShift = shift;
            poly1 = shifted;
        }
        return best;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DecimalFormat df = new DecimalFormat("0.000000000000");

        String line;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty())
                continue;
            int n1 = Integer.parseInt(line.trim());
            List<Point> poly1 = new ArrayList<>();
            for (int i = 0; i < n1; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                double x = Double.parseDouble(st.nextToken());
                double y = Double.parseDouble(st.nextToken());
                poly1.add(new Point(x, y));
            }

            int n2 = Integer.parseInt(br.readLine().trim());
            List<Point> poly2 = new ArrayList<>();
            for (int i = 0; i < n2; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                double x = Double.parseDouble(st.nextToken());
                double y = Double.parseDouble(st.nextToken());
                poly2.add(new Point(x, y));
            }

            Collections.reverse(poly1);
            Collections.reverse(poly2);

            prevI = 0;
            prevJ = 0;

            List<List<Point>> rotations1 = createRotations(poly1, true);
            List<List<Point>> rotations2 = createRotations(poly2, false);

            double ans = 0;
            double[] base2 = new double[rotations2.size()];
            for (int i = 0; i < rotations2.size(); i++) {
                List<Point> poly = rotations2.get(i);
                base2[i] = poly.get(i).dist(vertexAt(poly, i + 1));
            }

            for (int i = 0; i < rotations1.size(); i++) {
                List<Point> poly = rotations1.get(i);
                double base1 = poly.get(i).dist(vertexAt(poly, i - 1));

                for (int j = 0; j < rotations2.size(); j++) {
                    double shift = findOptimalShift(
                            rotations1.get(i),
                            rotations2.get(j),
                            base1 + base2[j]);
                    ans = Math.max(ans, shift);
                }
            }

            System.out.println(df.format(ans));
        }
    }
}

/*
3
1 0
0 30
40 0
3
1 0
0 30
40 0

50.000000000000


8
0 0
0 10
10 10
15 15
24 6
24 10
30 10
30 0
7
-5 0
-5 10
10 10
15 5
20 10
35 10
35 0

30.142135623731
*/