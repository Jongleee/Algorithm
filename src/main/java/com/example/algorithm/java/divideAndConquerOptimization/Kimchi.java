package com.example.algorithm.java.divideAndConquerOptimization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Kimchi {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        long[] t = new long[n + 1];
        long[] v = new long[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++)
            t[i] = Long.parseLong(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++)
            v[i] = Long.parseLong(st.nextToken());

        long[] result = new long[1];
        divideAndConquer(1, n, 1, n, d, t, v, result);
        System.out.println(result[0]);
    }

    private static void divideAndConquer(int start, int end, int left, int right, int d, long[] t, long[] v,
            long[] result) {
        if (start > end)
            return;
        int mid = (start + end) >> 1;

        int opt = Math.max(left, mid - d);
        int upper = Math.min(mid, right);
        for (int i = opt + 1; i <= upper; i++) {
            if (computeCost(i, mid, t, v) > computeCost(opt, mid, t, v)) {
                opt = i;
            }
        }

        result[0] = Math.max(result[0], computeCost(opt, mid, t, v));

        divideAndConquer(start, mid - 1, left, opt, d, t, v, result);
        divideAndConquer(mid + 1, end, opt, right, d, t, v, result);
    }

    private static long computeCost(int i, int j, long[] t, long[] v) {
        return (j - i) * t[j] + v[i];
    }
}

/*
4 4
23 22 21 20
20 40 30 50

80


4 1
23 22 21 20
20 40 30 50

61
*/