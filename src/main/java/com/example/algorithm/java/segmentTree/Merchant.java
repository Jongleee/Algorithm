package com.example.algorithm.java.segmentTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Merchant {
    private static final int MAX = 500009;
    private static final int INF = 0xc0c0c0c0;
    private static int[] upTree;
    private static int[] downTree;

    private static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int u = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        List<List<Pair>> mark = new ArrayList<>();
        for (int i = 0; i <= 500001; i++) {
            mark.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int time = Integer.parseInt(st.nextToken());
            int pos = Integer.parseInt(st.nextToken());
            int val = Integer.parseInt(st.nextToken());
            mark.get(time).add(new Pair(pos, val));
        }

        upTree = new int[2 * MAX];
        downTree = new int[2 * MAX];
        Arrays.fill(upTree, INF);
        Arrays.fill(downTree, INF);

        updateSegmentTrees(s, 0, u, d);

        for (int i = 1; i <= 500001; i++) {
            processMarket(mark.get(i), u, d);
        }

        System.out.println(realQuery(s, u, d));
    }

    private static void processMarket(List<Pair> market, int u, int d) {
        if (market.isEmpty()) {
            return;
        }
        market.sort(Comparator.comparingInt(p -> p.x));
        int size = market.size();
        int[] uArray = new int[size];
        int[] dArray = new int[size];

        for (int i = 0; i < size; i++) {
            int temp = realQuery(market.get(i).x, u, d);
            uArray[i] = temp;
            dArray[i] = temp;
        }

        computeDArray(dArray, market, size, d);
        computeUArray(uArray, market, size, u);
        updateTrees(market, uArray, dArray, size, u, d);
    }

    private static void computeDArray(int[] dArray, List<Pair> market, int size, int d) {
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                int prevPos = market.get(i - 1).x;
                int currentPos = market.get(i).x;
                int candidate = dArray[i - 1] - d * (currentPos - prevPos);
                dArray[i] = Math.max(dArray[i], candidate);
            }
            dArray[i] += market.get(i).y;
        }
    }

    private static void computeUArray(int[] uArray, List<Pair> market, int size, int u) {
        for (int i = size - 1; i >= 0; i--) {
            if (i < size - 1) {
                int nextPos = market.get(i + 1).x;
                int currentPos = market.get(i).x;
                int candidate = uArray[i + 1] - u * (nextPos - currentPos);
                uArray[i] = Math.max(uArray[i], candidate);
            }
            uArray[i] += market.get(i).y;
        }
    }

    private static void updateTrees(List<Pair> market, int[] uArray, int[] dArray, int size, int u, int d) {
        for (int i = 0; i < size; i++) {
            int maxVal = Math.max(uArray[i], dArray[i]);
            updateSegmentTrees(market.get(i).x, maxVal, u, d);
        }
    }

    private static void updateSegmentTrees(int x, int v, int u, int d) {
        update(upTree, x, v - u * x);
        update(downTree, x, v + d * x);
    }

    private static void update(int[] tree, int x, int value) {
        x += MAX;
        if (tree[x] < value) {
            tree[x] = value;
            while (x > 1) {
                int sibling = x ^ 1;
                int parent = x >> 1;
                int maxVal = Math.max(tree[x], tree[sibling]);
                if (tree[parent] == maxVal) {
                    break;
                }
                tree[parent] = maxVal;
                x = parent;
            }
        }
    }

    private static int realQuery(int a, int u, int d) {
        int downQuery = query(downTree, 0, a) - d * a;
        int upQuery = query(upTree, a, MAX - 1) + u * a;
        return Math.max(downQuery, upQuery);
    }

    private static int query(int[] tree, int l, int r) {
        int res = INF;
        l += MAX;
        r += MAX;
        while (l <= r) {
            if ((l & 1) == 1) {
                res = Math.max(res, tree[l]);
                l++;
            }
            if ((r & 1) == 0) {
                res = Math.max(res, tree[r]);
                r--;
            }
            l >>= 1;
            r >>= 1;
        }
        return res;
    }
}

/*
4 5 3 100
2 80 100
20 125 130
10 75 150
5 120 110

50
*/