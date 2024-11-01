package com.example.algorithm.java.convexHull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class ToxicBarrier {
    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static int n;
    static int l;
    static Point[] arr;
    static Point[] newPoints;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer token = new StringTokenizer(br.readLine());
        n = Integer.parseInt(token.nextToken());
        l = Integer.parseInt(token.nextToken());

        arr = new Point[n + 1];

        for (int i = 0; i < n; i++) {
            token = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(token.nextToken());
            int y = Integer.parseInt(token.nextToken());

            arr[i] = new Point(x, y);
        }

        int minIndex = 0;

        for (int i = 1; i < n; i++) {
            if (arr[i].y < arr[minIndex].y) {
                minIndex = i;
            } else if (arr[i].y == arr[minIndex].y) {
                if (arr[i].x < arr[minIndex].x) {
                    minIndex = i;
                }
            }
        }

        Point temp = arr[0];
        arr[0] = arr[minIndex];
        arr[minIndex] = temp;

        Arrays.sort(arr, 1, n, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                int c = ccw(arr[0], p1, p2);
                if (c == 0) {
                    return dist(arr[0], p2) >= dist(arr[0], p1) ? -1 : 1;
                }
                return c;
            }
        });

        newPoints = new Point[n + 1];
        newPoints[0] = arr[n - 1];

        for (int i = 1; i <= n; i++) {
            newPoints[i] = arr[i - 1];
        }

        int m = 1;

        for (int i = 2; i <= n; i++) {
            while (ccw(newPoints[m - 1], newPoints[m], newPoints[i]) >= 0) {
                if (m > 1) {
                    m -= 1;
                } else if (i == n)
                    break;
                else
                    i += 1;
            }

            m += 1;

            Point tmp = newPoints[m];
            newPoints[m] = newPoints[i];
            newPoints[i] = tmp;
        }

        double ans = 0;

        for (int i = 0; i < m; i++) {
            ans += getDistance(newPoints[i], newPoints[i + 1]);
        }
        ans += (2 * l * Math.PI);

        System.out.println(Math.round(ans));
    }

    static long dist(Point p1, Point p2) {
        return ((long) p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }

    static double getDistance(Point p1, Point p2) {
        return Math.sqrt(dist(p1, p2));
    }

    static int ccw(Point p1, Point p2, Point p3) {
        long temp = ((long) p1.x * p2.y) + (p2.x * p3.y) + (p3.x * p1.y);
        temp -= (p1.y * p2.x) + (p2.y * p3.x) + (p3.y * p1.x);

        if (temp > 0) {
            return -1;
        } else if (temp < 0) {
            return 1;
        } else {
            return 0;
        }
    }
}

/*
9 100
200 400
300 400
300 300
400 300
400 400
500 400
500 200
350 200
200 200

1628
*/