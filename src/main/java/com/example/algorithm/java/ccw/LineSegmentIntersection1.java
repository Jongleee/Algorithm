package com.example.algorithm.java.ccw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LineSegmentIntersection1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long x1 = Long.parseLong(st.nextToken());
        long y1 = Long.parseLong(st.nextToken());

        long x2 = Long.parseLong(st.nextToken());
        long y2 = Long.parseLong(st.nextToken());

        st = new StringTokenizer(br.readLine());
        long x3 = Long.parseLong(st.nextToken());
        long y3 = Long.parseLong(st.nextToken());

        long x4 = Long.parseLong(st.nextToken());
        long y4 = Long.parseLong(st.nextToken());

        System.out.println(computeIntersection(x1, y1, x2, y2, x3, y3, x4, y4));
        br.close();
    }

    private static int computeIntersection(long x1, long y1, long x2, long y2, long x3, long y3, long x4, long y4) {
        int ccw1 = computeCCW(x1, y1, x2, y2, x3, y3);
        int ccw2 = computeCCW(x1, y1, x2, y2, x4, y4);
        int ccw3 = computeCCW(x3, y3, x4, y4, x1, y1);
        int ccw4 = computeCCW(x3, y3, x4, y4, x2, y2);

        if (ccw1 * ccw2 < 0 && ccw3 * ccw4 < 0) {
            return 1;
        }
        return 0;
    }

    private static int computeCCW(long x1, long y1, long x2, long y2, long x3, long y3) {
        long crossProduct = (x2 * y3 + x3 * y1 + x1 * y2) - (y1 * x2 + y2 * x3 + y3 * x1);
        if (crossProduct > 0) {
            return 1;
        }
        return -1;
    }
}

/*
1 1 5 5
1 5 5 1

1

1 1 5 5
6 10 10 6

0
*/