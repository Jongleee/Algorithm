package com.example.algorithm.java.ccw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PizzaAlvoloc {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int x1 = Integer.parseInt(st.nextToken());
        int y1 = Integer.parseInt(st.nextToken());

        int x2 = Integer.parseInt(st.nextToken());
        int y2 = Integer.parseInt(st.nextToken());

        int x3 = Integer.parseInt(st.nextToken());
        int y3 = Integer.parseInt(st.nextToken());

        int x4 = Integer.parseInt(st.nextToken());
        int y4 = Integer.parseInt(st.nextToken());

        int ccw1 = computeCCW(x1, y1, x2, y2, x3, y3);
        int ccw2 = computeCCW(x1, y1, x2, y2, x4, y4);
        int ccw3 = computeCCW(x3, y3, x4, y4, x1, y1);
        int ccw4 = computeCCW(x3, y3, x4, y4, x2, y2);

        int product1 = ccw1 * ccw2;
        int product2 = ccw3 * ccw4;

        if (product1 < 0 && product2 < 0) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
        br.close();
    }

    private static int computeCCW(int x1, int y1, int x2, int y2, int x3, int y3) {
        int crossProduct = (x1 * y2) + (x2 * y3) + (x3 * y1)
                - (y1 * x2) - (y2 * x3) - (y3 * x1);

        if (crossProduct > 0)
            return 1;
        if (crossProduct == 0)
            return 0;
        return -1;
    }
}

/*
0 0 6 2 5 -4 2 2

1


-1 -5 6 3 1 10 -4 -1

0
*/