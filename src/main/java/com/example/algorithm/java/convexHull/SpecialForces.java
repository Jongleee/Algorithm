package com.example.algorithm.java.convexHull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SpecialForces {
    private static double a, b;

    private static class Curve {
        double ability, dp, crossPoint;

        Curve(double ability, double dp, double crossPoint) {
            this.ability = ability;
            this.dp = dp;
            this.crossPoint = crossPoint;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
        double c = Integer.parseInt(st.nextToken());

        long[] prefixSum = new long[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            prefixSum[i] = Integer.parseInt(st.nextToken()) + prefixSum[i - 1];
        }

        List<Curve> curves = new ArrayList<>();
        curves.add(new Curve(0, 0, 0));

        int start = 0, end = 0;
        for (int i = 1; i <= n; i++) {
            while (start < end && prefixSum[i] >= curves.get(start + 1).crossPoint) {
                start++;
            }

            double currentDp = a * Math.pow(prefixSum[i] - curves.get(start).ability, 2)
                    + b * (prefixSum[i] - curves.get(start).ability) + c + curves.get(start).dp;

            if (i == n) {
                System.out.print((long) currentDp);
                return;
            }

            Curve currentCurve = new Curve(prefixSum[i], currentDp, 0);
            while (start <= end && !updateCrossPoint(curves.get(end), currentCurve)) {
                curves.remove(end--);
            }
            curves.add(currentCurve);
            end++;
        }
    }

    private static boolean updateCrossPoint(Curve x, Curve y) {
        double cross = (x.ability + y.ability) / 2 - b / (2 * a) + (x.dp - y.dp) / ((x.ability - y.ability) * (2 * a));
        if (x.crossPoint <= cross) {
            y.crossPoint = cross;
            return true;
        }
        return false;
    }
}

/*
4
-1 10 -20
2 2 3 4

9
*/