package com.example.algorithm.java.ccw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LineSegmentIntersection2 {
    static long[] a = new long[2];
    static long[] b = new long[2];
    static long[] c = new long[2];
    static long[] d = new long[2];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        a[0] = Integer.parseInt(st.nextToken());
        a[1] = Integer.parseInt(st.nextToken());
        b[0] = Integer.parseInt(st.nextToken());
        b[1] = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        c[0] = Integer.parseInt(st.nextToken());
        c[1] = Integer.parseInt(st.nextToken());
        d[0] = Integer.parseInt(st.nextToken());
        d[1] = Integer.parseInt(st.nextToken());

        int n1 = computeCCW(a, b, c) * computeCCW(a, b, d);
        int n2 = computeCCW(c, d, a) * computeCCW(c, d, b);

        if (n1 <= 0 && n2 <= 0) {
            if (n1 == 0 && n2 == 0) {
                boolean condition1 = Math.max(a[0], b[0]) >= Math.min(c[0], d[0]);
                boolean condition2 = Math.min(a[0], b[0]) <= Math.max(c[0], d[0]);
                boolean condition3 = Math.max(a[1], b[1]) >= Math.min(c[1], d[1]);
                boolean condition4 = Math.min(a[1], b[1]) <= Math.max(c[1], d[1]);

                if (condition1 && condition2 && condition3 && condition4) {
                    System.out.println(1);
                } else {
                    System.out.println(0);
                }
            } else {
                System.out.println(1);
            }
        } else {
            System.out.println(0);
        }
    }

    static int computeCCW(long[] a, long[] b, long[] c) {
        long temp = a[0] * b[1] + b[0] * c[1] + c[0] * a[1] - (a[1] * b[0] + b[1] * c[0] + c[1] * a[0]);

        if (temp > 0)
            return 1;
        else if (temp == 0)
            return 0;

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


1 1 5 5
5 5 1 1

1


1 1 5 5
3 3 5 5

1
*/