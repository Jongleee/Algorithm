package com.example.algorithm.java.computationalGeometry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LineSegmentIntersection3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        double x1 = Double.parseDouble(st.nextToken());
        double y1 = Double.parseDouble(st.nextToken());
        double x2 = Double.parseDouble(st.nextToken());
        double y2 = Double.parseDouble(st.nextToken());

        st = new StringTokenizer(br.readLine(), " ");
        double x3 = Double.parseDouble(st.nextToken());
        double y3 = Double.parseDouble(st.nextToken());
        double x4 = Double.parseDouble(st.nextToken());
        double y4 = Double.parseDouble(st.nextToken());

        double den = (y2 - y1) * (x4 - x3) - (y4 - y3) * (x2 - x1);

        if (Double.compare(den, 0) == 0) {
            double check = x1 * (y2 - y1) * (x4 - x3) - x3 * (y4 - y3) * (x2 - x1) + (y3 - y1) * (x2 - x1) * (x4 - x3);
            if (Double.compare(check, 0) != 0) {
                System.out.println(0);
            } else {
                if ((x1 < x3 && x2 < x3 && x1 < x4 && x2 < x4) ||
                        (x1 > x3 && x2 > x3 && x1 > x4 && x2 > x4) ||
                        (y1 < y3 && y2 < y3 && y1 < y4 && y2 < y4) ||
                        (y1 > y3 && y2 > y3 && y1 > y4 && y2 > y4)) {
                    System.out.println(0);
                } else if ((x1 == x3 && x2 < x1 && x4 > x3) || (x1 == x3 && x2 > x1 && x4 < x3) ||
                        (y1 == y3 && y2 < y1 && y4 > y3) || (y1 == y3 && y2 > y1 && y4 < y3)) {
                    System.out.println(1);
                    System.out.println(x1 + " " + y1);
                } else if ((x1 == x4 && x2 < x1 && x3 > x4) || (x1 == x4 && x2 > x1 && x3 < x4) ||
                        (y1 == y4 && y2 < y1 && y3 > y4) || (y1 == y4 && y2 > y1 && y3 < y4)) {
                    System.out.println(1);
                    System.out.println(x1 + " " + y1);
                } else if ((x2 == x3 && x1 < x2 && x4 > x3) || (x2 == x3 && x1 > x2 && x4 < x3) ||
                        (y2 == y3 && y1 < y2 && y4 > y3) || (y2 == y3 && y1 > y2 && y4 < y3)) {
                    System.out.println(1);
                    System.out.println(x2 + " " + y2);
                } else if ((x2 == x4 && x1 < x2 && x3 > x4) || (x2 == x4 && x1 > x2 && x3 < x4) ||
                        (y2 == y4 && y1 < y2 && y3 > y4) || (y2 == y4 && y1 > y2 && y3 < y4)) {
                    System.out.println(1);
                    System.out.println(x2 + " " + y2);
                } else {
                    System.out.println(1);
                }
            }
        } else {
            boolean separated1 = ((y3 - y4) * (x1 - x3) - (x3 - x4) * (y1 - y3)) *
                    ((y3 - y4) * (x2 - x3) - (x3 - x4) * (y2 - y3)) > 0;
            boolean separated2 = ((y1 - y2) * (x3 - x1) - (x1 - x2) * (y3 - y1)) *
                    ((y1 - y2) * (x4 - x1) - (x1 - x2) * (y4 - y1)) > 0;
            if (separated1 || separated2) {
                System.out.println(0);
            } else {
                System.out.println(1);
                double x = (x1 * (y2 - y1) * (x4 - x3) - x3 * (y4 - y3) * (x2 - x1) + (y3 - y1) * (x2 - x1) * (x4 - x3))
                        / den;
                double y = (y1 * (x2 - x1) * (y4 - y3) - y3 * (x4 - x3) * (y2 - y1) + (x3 - x1) * (y2 - y1) * (y4 - y3))
                        /
                        ((x2 - x1) * (y4 - y3) - (x4 - x3) * (y2 - y1));
                System.out.println(x + " " + y);
            }
        }
    }
}

/*
2 8 9 23
1 10 9 8

1
2.7313432835820897 9.567164179104477


1 1 5 5
5 5 9 9

1
5.0 5.0
*/