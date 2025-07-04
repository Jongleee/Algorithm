package com.example.algorithm.java.divideAndConquerOptimization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class MoneyForNothing {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int pdCount = Integer.parseInt(st.nextToken());
        int qeCount = Integer.parseInt(st.nextToken());

        List<int[]> pd = new ArrayList<>();
        List<int[]> qe = new ArrayList<>();

        for (int i = 0; i < pdCount; i++) {
            pd.add(readIntArray(br));
        }

        for (int i = 0; i < qeCount; i++) {
            qe.add(readIntArray(br));
        }

        Comparator<int[]> comparator = (a, b) -> {
            if (a[0] != b[0])
                return a[0] - b[0];
            return a[1] - b[1];
        };

        Collections.sort(pd, comparator);
        Collections.sort(qe, comparator);

        List<int[]> filteredPd = new ArrayList<>();
        List<int[]> filteredQe = new ArrayList<>();

        int[] lastPd = pd.get(0);
        filteredPd.add(lastPd);

        for (int i = 1; i < pdCount; i++) {
            int[] current = pd.get(i);
            if (lastPd[1] <= current[1])
                continue;
            filteredPd.add(current);
            lastPd = current;
        }

        int[] lastQe = qe.get(qeCount - 1);
        filteredQe.add(lastQe);

        for (int i = qeCount - 2; i >= 0; i--) {
            int[] current = qe.get(i);
            if (lastQe[1] >= current[1])
                continue;
            filteredQe.add(current);
            lastQe = current;
        }

        Collections.reverse(filteredQe);

        int n = filteredQe.size();
        int k = 1;

        long[][] dpTable = new long[n + 1][k + 1];
        long result = computeDp(dpTable, filteredPd, filteredQe, n);

        bw.write(Long.toString(result));
        bw.flush();
        bw.close();
    }

    private static int[] readIntArray(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        return new int[] { Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()) };
    }

    private static long computeDp(long[][] dp, List<int[]> pd, List<int[]> qe, int n) {
        long maxResult = 0;
        long negInf = (long) 1e18 * 5 * -1;

        divideConquerDp(dp, pd, qe, 1, 0, n - 1, 0, pd.size() - 1, negInf);

        for (int i = 0; i < n; i++) {
            maxResult = Math.max(maxResult, dp[i][1]);
        }

        return maxResult;
    }

    private static void divideConquerDp(long[][] dp, List<int[]> pd, List<int[]> qe,
            int ck, int i, int j, int a, int b, long negInf) {
        if (i > j)
            return;

        int mid = (i + j) / 2;
        int bestIdx = -1;
        long maxVal = negInf;

        for (int p = a; p <= b; p++) {
            long cost = computeCost(pd.get(p), qe.get(mid));
            if (cost > maxVal) {
                maxVal = cost;
                bestIdx = p;
            }
        }

        dp[mid][ck] = maxVal;

        divideConquerDp(dp, pd, qe, ck, i, mid - 1, a, bestIdx, negInf);
        divideConquerDp(dp, pd, qe, ck, mid + 1, j, bestIdx, b, negInf);
    }

    private static long computeCost(int[] pd, int[] qe) {
        if (pd[0] >= qe[0] && pd[1] >= qe[1])
            return 0L;
        return (long) (qe[0] - pd[0]) * (qe[1] - pd[1]);
    }
}

/*
2 2
1 3
2 1
3 5
7 2

5


1 2
10 10
9 11
11 9

0
*/