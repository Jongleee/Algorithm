package com.example.algorithm.java.longestIncreasingSubsequence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class HarmoniousMatrix {
    static class Point3D implements Comparable<Point3D> {
        int a, b, c;

        public Point3D(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public int compareTo(Point3D o) {
            if (a != o.a)
                return a - o.a;
            if (b != o.b)
                return b - o.b;
            return c - o.c;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int k = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        int[][] arr = new int[4][n + 1];

        for (int i = 1; i <= 3; i++) {
            if (k < i) {
                for (int j = 1; j <= n; j++) {
                    arr[i][j] = arr[i - 1][j];
                }
            } else {
                st = new StringTokenizer(br.readLine());
                for (int j = 1; j <= n; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                }
            }
        }

        List<Point3D> tmp = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            tmp.add(new Point3D(arr[1][i], arr[2][i], arr[3][i]));
        }
        Collections.sort(tmp);

        List<long[]> pairs = new ArrayList<>();
        for (Point3D p : tmp) {
            pairs.add(new long[] { p.b, p.c });
        }

        int result = solve(n, pairs);
        bw.write(result + "\n");
        bw.flush();
    }

    private static int solve(int n, List<long[]> pairs) {
        List<TreeSet<long[]>> sets = new ArrayList<>();
        Comparator<long[]> comp = (o1, o2) -> {
            if (o1[0] != o2[0])
                return Long.compare(o1[0], o2[0]);
            return Long.compare(o1[1], o2[1]);
        };
        for (int i = 0; i <= n; i++) {
            sets.add(new TreeSet<>(comp));
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            long[] curr = pairs.get(i);
            int l = 1, r = ans + 1;
            while (l < r) {
                int m = (l + r) / 2;
                TreeSet<long[]> set = sets.get(m);
                long[] lower = set.lower(curr);
                if (lower == null || lower[1] >= curr[1]) {
                    r = m;
                } else {
                    l = m + 1;
                }
            }
            ans = Math.max(ans, l);
            TreeSet<long[]> set = sets.get(l);
            if (!set.contains(curr)) {
                set.add(curr);
                NavigableSet<long[]> tail = set.tailSet(curr, false);
                Iterator<long[]> it = tail.iterator();
                while (it.hasNext()) {
                    long[] next = it.next();
                    if (next[1] >= curr[1]) {
                        it.remove();
                    } else
                        break;
                }
            }
        }

        return ans;
    }
}

/*
2 9
10 74 41 15 89 52 16 63 75
30 53 22 33 46 45 25 47 21

5


3 9
10 74 41 15 89 52 16 63 75
30 53 22 33 46 45 25 47 21
29 49 13 26 59 17 62 34 19

4
*/