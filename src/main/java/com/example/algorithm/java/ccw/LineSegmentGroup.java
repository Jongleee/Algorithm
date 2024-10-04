package com.example.algorithm.java.ccw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LineSegmentGroup {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[][] segments = new int[n + 1][4];
        int[] parent = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }

        for (int i = 1; i <= n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            segments[i][0] = Integer.parseInt(st.nextToken());
            segments[i][1] = Integer.parseInt(st.nextToken());
            segments[i][2] = Integer.parseInt(st.nextToken());
            segments[i][3] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                if (doIntersect(segments[i], segments[j])) {
                    union(i, j, parent);
                }
            }
        }

        int[] count = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            count[find(i, parent)]++;
        }

        int groups = 0;
        int maxGroupSize = 0;
        for (int i = 1; i <= n; i++) {
            if (count[i] > 0) {
                groups++;
                if (count[i] > maxGroupSize) {
                    maxGroupSize = count[i];
                }
            }
        }

        System.out.println(groups);
        System.out.println(maxGroupSize);
    }

    private static int ccw(int ax, int ay, int bx, int by, int cx, int cy) {
        long value = (long) (ax) * by + (long) (bx) * cy + (long) (cx) * ay
                - (long) (ay) * bx - (long) (by) * cx - (long) (cy) * ax;
        if (value > 0)
            return 1;
        if (value < 0)
            return -1;
        return 0;
    }

    private static boolean doIntersect(int[] seg1, int[] seg2) {
        int ax = seg1[0];
        int ay = seg1[1];
        int bx = seg1[2];
        int by = seg1[3];
        int cx = seg2[0];
        int cy = seg2[1];
        int dx = seg2[2];
        int dy = seg2[3];

        int ccw1 = ccw(ax, ay, bx, by, cx, cy);
        int ccw2 = ccw(ax, ay, bx, by, dx, dy);
        int ccw3 = ccw(cx, cy, dx, dy, ax, ay);
        int ccw4 = ccw(cx, cy, dx, dy, bx, by);

        if (ccw1 * ccw2 <= 0 && ccw3 * ccw4 <= 0) {
            if (ccw1 == 0 && ccw2 == 0) {
                return isOverlapping(seg1, seg2);
            }
            return true;
        }
        return false;
    }

    private static boolean isOverlapping(int[] seg1, int[] seg2) {
        boolean overlapX = Math.max(seg1[0], seg1[2]) >= Math.min(seg2[0], seg2[2])
                && Math.max(seg2[0], seg2[2]) >= Math.min(seg1[0], seg1[2]);
        boolean overlapY = Math.max(seg1[1], seg1[3]) >= Math.min(seg2[1], seg2[3])
                && Math.max(seg2[1], seg2[3]) >= Math.min(seg1[1], seg1[3]);
        return overlapX && overlapY;
    }

    private static int find(int a, int[] parent) {
        if (a == parent[a]) {
            return a;
        }
        parent[a] = find(parent[a], parent);
        return parent[a];
    }

    private static void union(int a, int b, int[] parent) {
        int rootA = find(a, parent);
        int rootB = find(b, parent);
        if (rootA != rootB) {
            parent[rootA] = rootB;
        }
    }
}

/*
3
1 1 2 3
2 1 0 0
1 0 1 1

1
3


3
-1 -1 1 1
-2 -2 2 2
0 1 -1 0

2
2
*/