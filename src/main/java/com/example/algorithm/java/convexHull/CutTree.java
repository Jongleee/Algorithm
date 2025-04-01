package com.example.algorithm.java.convexHull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CutTree {
    static class Line {
        long a, b;
        double s;

        Line(long a, long b) {
            this.a = a;
            this.b = b;
            this.s = 0;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] a = new int[n];
        int[] b = new int[n];
        long[] dp = new long[n];
        List<Line> s = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            b[i] = Integer.parseInt(st.nextToken());
        }

        computeDP(n, a, b, dp, s);
        System.out.println(dp[n - 1]);
    }

    private static void computeDP(int n, int[] a, int[] b, long[] dp, List<Line> s) {
        for (int i = 1; i < n; ++i) {
            Line g = new Line(b[i - 1], dp[i - 1]);
            adjustConvexHull(s, g);
            dp[i] = findOptimalValue(s, a[i]);
        }
    }

    private static void adjustConvexHull(List<Line> s, Line g) {
        while (!s.isEmpty()) {
            g.s = cross(g, s.get(s.size() - 1));
            if (g.s < s.get(s.size() - 1).s) {
                s.remove(s.size() - 1);
            } else {
                break;
            }
        }
        s.add(g);
    }

    private static long findOptimalValue(List<Line> s, long x) {
        int left = 0, right = s.size() - 1, fpos = 0;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (s.get(mid).s < x) {
                fpos = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return s.get(fpos).a * x + s.get(fpos).b;
    }

    private static double cross(Line l1, Line l2) {
        return (double) (l2.b - l1.b) / (l1.a - l2.a);
    }
}

/*
6
1 2 3 10 20 30
6 5 4 3 2 0   

138


5
1 2 3 4 5
5 4 3 2 0

25
*/