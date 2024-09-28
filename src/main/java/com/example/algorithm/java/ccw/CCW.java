package com.example.algorithm.java.ccw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CCW {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int x1 = Integer.parseInt(st.nextToken());
        int y1 = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int x2 = Integer.parseInt(st.nextToken());
        int y2 = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int x3 = Integer.parseInt(st.nextToken());
        int y3 = Integer.parseInt(st.nextToken());

        System.out.println(computeCCW(x1, y1, x2, y2, x3, y3));
        }

    private static int computeCCW(int x1, int y1, int x2, int y2, int x3, int y3) {
        int temp = x1 * y2 + x2 * y3 + x3 * y1 - (y1 * x2 + y2 * x3 + y3 * x1);

        if (temp > 0)
            return 1;
        if (temp == 0)
            return 0;

        return -1;
    }
}

/*
1 1
5 5
7 3

-1


1 1
3 3
5 5

0


1 1
7 3
5 5

1
*/