package com.example.algorithm.java.ccw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class PolygonArea {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int numberOfPoints = Integer.parseInt(br.readLine());
        long areaSum = 0;
        long[][] points = new long[numberOfPoints][2];

        for (int i = 0; i < numberOfPoints; i++) {
            st = new StringTokenizer(br.readLine());
            long x = Long.parseLong(st.nextToken());
            long y = Long.parseLong(st.nextToken());
            points[i][0] = x;
            points[i][1] = y;
        }

        long x1 = points[0][0];
        long y1 = points[0][1];
        for (int i = 1; i < numberOfPoints - 1; i++) {
            long x2 = points[i][0];
            long y2 = points[i][1];
            long x3 = points[i + 1][0];
            long y3 = points[i + 1][1];
            long tmp = x1 * y2 + x2 * y3 + x3 * y1 - x2 * y1 - x3 * y2 - x1 * y3;
            areaSum += tmp;
        }

        long absoluteArea = Math.abs(areaSum);
        if (absoluteArea % 2 == 0) {
            bw.write((absoluteArea / 2) + ".0\n");
        } else {
            bw.write((absoluteArea / 2) + ".5\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }
}

/*
4
0 0
0 10
10 10
10 0

100.0
*/