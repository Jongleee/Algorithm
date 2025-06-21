package com.example.algorithm.java.fenwickTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Meteor {
    static final String NIE = "NIE\n";
    static final char NEW_LINE = '\n';

    static int m, q, count;
    static FenwickTree fenwick;
    static BinarySearchNode[] pendingQueries;

    static class FenwickTree {
        int size;
        long[] tree, initial;

        FenwickTree(int size) {
            this.size = size;
            tree = new long[size + 2];
            initial = new long[size + 2];
        }

        void reset() {
            System.arraycopy(initial, 1, tree, 1, size);
        }

        void updateRange(int l, int r, long delta) {
            add(l, delta);
            if (r < size)
                add(r + 1, -delta);
        }

        private void add(int index, long delta) {
            while (index <= size) {
                tree[index] += delta;
                index += index & -index;
            }
        }

        long query(int index) {
            long result = 0;
            while (index > 0) {
                result += tree[index];
                index -= index & -index;
            }
            return result;
        }
    }

    static class Area {
        int index;
        Area next;

        Area(int index, Area next) {
            this.index = index;
            this.next = next;
        }
    }

    static class Query {
        int left, right;
        long value;

        Query(int left, int right, long value) {
            this.left = left;
            this.right = right;
            this.value = value;
        }

        void apply() {
            if (left <= right) {
                fenwick.updateRange(left, right, value);
            } else {
                fenwick.updateRange(left, m, value);
                fenwick.updateRange(1, right, value);
            }
        }
    }

    static class BinarySearchNode {
        int l = 1;
        int r = q + 1;
        int result = q + 1;
        long target;
        Area areaHead;
        BinarySearchNode next;

        BinarySearchNode(long target, BinarySearchNode next) {
            this.target = target;
            this.next = next;
        }

        void evaluate(int mid) {
            long sum = 0;
            for (Area area = areaHead; area != null; area = area.next) {
                sum += fenwick.query(area.index);
                if (sum >= target)
                    break;
            }

            pendingQueries[mid] = next;
            if (sum < target) {
                l = mid + 1;
            } else {
                r = mid;
            }

            if (l >= r) {
                result = r;
                count++;
            } else {
                int newMid = (l + r) >>> 1;
                next = pendingQueries[newMid];
                pendingQueries[newMid] = this;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        StringTokenizer areaTokens = new StringTokenizer(br.readLine());
        StringTokenizer goalTokens = new StringTokenizer(br.readLine());
        q = Integer.parseInt(br.readLine());

        Query[] queries = new Query[q + 1];
        for (int i = 1; i <= q; i++) {
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            long a = Integer.parseInt(st.nextToken());
            queries[i] = new Query(l, r, a);
        }

        pendingQueries = new BinarySearchNode[q + 2];
        BinarySearchNode[] answers = new BinarySearchNode[n + 1];

        int mid = (q + 2) >>> 1;
        for (int i = 1; i <= n; i++) {
            long target = Long.parseLong(goalTokens.nextToken());
            pendingQueries[mid] = new BinarySearchNode(target, pendingQueries[mid]);
            answers[i] = pendingQueries[mid];
        }

        for (int i = 1; i <= m; i++) {
            int owner = Integer.parseInt(areaTokens.nextToken());
            answers[owner].areaHead = new Area(i, answers[owner].areaHead);
        }

        fenwick = new FenwickTree(m);

        while (count < n) {
            fenwick.reset();
            for (int i = 1; i <= q; i++) {
                queries[i].apply();
                while (pendingQueries[i] != null) {
                    pendingQueries[i].evaluate(i);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (answers[i].result > q) {
                sb.append(NIE);
            } else {
                sb.append(answers[i].result).append(NEW_LINE);
            }
        }

        System.out.print(sb);
    }
}

/*
3 5
1 3 2 1 3
10 5 7
3
4 2 4
1 3 1
3 5 2

3
NIE
1
*/