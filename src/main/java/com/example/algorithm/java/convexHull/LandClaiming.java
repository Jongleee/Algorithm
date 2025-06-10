package com.example.algorithm.java.convexHull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class LandClaiming {
    private static class Land implements Comparable<Land> {
        int w, h;

        Land(int w, int h) {
            this.w = w;
            this.h = h;
        }

        @Override
        public int compareTo(Land o) {
            if (this.w != o.w)
                return this.w - o.w;
            return o.h - this.h;
        }
    }

    private static class Line {
        long slope, intercept;
        double xLeft;

        Line(long slope, long intercept, double xLeft) {
            this.slope = slope;
            this.intercept = intercept;
            this.xLeft = xLeft;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        List<Land> lands = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int w = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            lands.add(new Land(w, h));
        }

        Collections.sort(lands);
        for (int i = n - 2; i >= 0; i--) {
            if (lands.get(i).w == lands.get(i + 1).w) {
                lands.remove(i + 1);
            } else if (lands.get(i).h <= lands.get(i + 1).h) {
                lands.remove(i);
            }
        }

        List<Line> hull = new ArrayList<>();
        hull.add(new Line(lands.get(0).h, 0, 0));
        int s = 0, e = 0;

        for (int i = 0; i < lands.size(); i++) {
            while (s < e && lands.get(i).w >= hull.get(s + 1).xLeft) {
                s++;
            }
            long dp = hull.get(s).slope * lands.get(i).w + hull.get(s).intercept;
            if (i == lands.size() - 1) {
                System.out.println(dp);
                return;
            }
            Line nextLine = new Line(lands.get(i + 1).h, dp, 0);
            while (s <= e && !isValid(hull.get(e), nextLine)) {
                hull.remove(e);
                e--;
            }
            hull.add(nextLine);
            e++;
        }
    }

    private static boolean isValid(Line prev, Line curr) {
        double x = (double) (curr.intercept - prev.intercept) / (prev.slope - curr.slope);
        if (prev.xLeft < x) {
            curr.xLeft = x;
            return true;
        }
        return false;
    }
}

/*
4
100 1
15 15
20 5
1 100

500
*/