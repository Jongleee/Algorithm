package com.example.algorithm.java.convexHull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class HalfPlaneLandClaiming2 {
    static class Line {
        long a, b;

        Line(long a, long b) {
            this.a = a;
            this.b = b;
        }

        long evaluate(long x) {
            return a * x + b;
        }
    }

    static class Operation {
        int start, end;
        Line line;

        Operation(int start, int end, Line line) {
            this.start = start;
            this.end = end;
            this.line = line;
        }
    }

    static class SegmentTree {
        private final List<Line>[] tree;
        private final int size;

        @SuppressWarnings("unchecked")
        SegmentTree(int n) {
            int s = 1;
            while (s < n)
                s <<= 1;
            size = s;
            tree = new ArrayList[size << 1];
            for (int i = 0; i < tree.length; i++)
                tree[i] = new ArrayList<>();
        }

        void update(int node, int left, int right, int ql, int qr, Line line) {
            if (qr < left || right < ql)
                return;
            if (ql <= left && right <= qr) {
                tree[node].add(line);
                return;
            }
            int mid = (left + right) >> 1;
            update(node << 1, left, mid, ql, qr, line);
            update(node << 1 | 1, mid + 1, right, ql, qr, line);
        }

        void build(int node, int left, int right) {
            tree[node] = buildConvexHull(tree[node]);
            if (left == right)
                return;
            int mid = (left + right) >> 1;
            build(node << 1, left, mid);
            build(node << 1 | 1, mid + 1, right);
        }

        List<Line> buildConvexHull(List<Line> lines) {
            lines.sort(Comparator.comparingLong(l -> l.a));
            List<Line> hull = new ArrayList<>();
            for (Line line : lines) {
                while (hull.size() >= 2 && isRedundant(hull.get(hull.size() - 2), hull.get(hull.size() - 1), line)) {
                    hull.remove(hull.size() - 1);
                }
                hull.add(line);
            }
            return hull;
        }

        boolean isRedundant(Line a, Line b, Line c) {
            return (b.a - a.a) * (c.b - a.b) >= (c.a - a.a) * (b.b - a.b);
        }

        long query(int node, int left, int right, int idx, long x) {
            long val = binarySearchMax(tree[node], x);
            if (left == right)
                return val;
            int mid = (left + right) >> 1;
            if (idx <= mid)
                return Math.max(val, query(node << 1, left, mid, idx, x));
            else
                return Math.max(val, query(node << 1 | 1, mid + 1, right, idx, x));
        }

        long binarySearchMax(List<Line> lines, long x) {
            if (lines.isEmpty())
                return (long) -5e18;
            int l = 0, r = lines.size() - 1;
            while (l < r) {
                int m = (l + r) >> 1;
                if (lines.get(m).evaluate(x) < lines.get(m + 1).evaluate(x)) {
                    l = m + 1;
                } else {
                    r = m;
                }
            }
            return lines.get(l).evaluate(x);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int queryCount = Integer.parseInt(br.readLine());

        int[] cumulativeCount = new int[queryCount + 2];
        int[] isActive = new int[queryCount + 2];
        long[] queryX = new long[queryCount + 2];
        Line[] lineMap = new Line[queryCount + 2];
        List<Operation> operations = new ArrayList<>();

        for (int i = 1; i <= queryCount; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());

            if (type == 1) {
                long a = Long.parseLong(st.nextToken());
                long b = Long.parseLong(st.nextToken());
                lineMap[i] = new Line(a, b);
                isActive[i] = 1;
            } else if (type == 2) {
                int targetIndex = Integer.parseInt(st.nextToken());
                operations.add(
                        new Operation(cumulativeCount[targetIndex] + 1, cumulativeCount[i - 1], lineMap[targetIndex]));
                isActive[targetIndex] = 0;
            } else {
                long x = Long.parseLong(st.nextToken());
                queryX[i] = x;
                cumulativeCount[i]++;
            }
            cumulativeCount[i] += cumulativeCount[i - 1];
        }

        if (cumulativeCount[queryCount] == 0)
            return;

        for (int i = 1; i <= queryCount; i++) {
            if (isActive[i] == 1) {
                operations.add(new Operation(cumulativeCount[i] + 1, cumulativeCount[queryCount], lineMap[i]));
            }
        }

        int totalQueries = cumulativeCount[queryCount];
        SegmentTree segmentTree = new SegmentTree(totalQueries);

        for (Operation op : operations) {
            segmentTree.update(1, 1, totalQueries, op.start, op.end, op.line);
        }

        segmentTree.build(1, 1, totalQueries);

        for (int i = 1; i <= queryCount; i++) {
            if (cumulativeCount[i] != cumulativeCount[i - 1]) {
                long result = segmentTree.query(1, 1, totalQueries, cumulativeCount[i], queryX[i]);
                System.out.println(result <= -5e18 ? "EMPTY" : result);
            }
        }
    }
}

/*
7
3 1
1 2 3
3 1
1 -1 100
3 1
2 4
3 1

EMPTY
5
99
5
*/