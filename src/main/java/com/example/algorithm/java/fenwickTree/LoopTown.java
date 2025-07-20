package com.example.algorithm.java.fenwickTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class LoopTown {
    private static class Pair implements Comparable<Pair> {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int compareTo(Pair o) {
            return this.x != o.x ? this.x - o.x : this.y - o.y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        st.nextToken();

        Pair[] a = new Pair[n + 1];
        int[] yCoords = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            a[i] = new Pair(x, y);
            yCoords[i] = y;
        }

        Arrays.sort(a, 1, n + 1);

        int[] compressedY = Arrays.copyOfRange(yCoords, 1, n + 1);
        Arrays.sort(compressedY);
        Map<Integer, Integer> coordMap = new HashMap<>();
        int id = 1;
        for (int val : compressedY) {
            if (!coordMap.containsKey(val)) {
                coordMap.put(val, id++);
            }
        }

        int[] p = new int[n + 2];
        int[] q = new int[n + 2];
        int[] c = new int[2 * n + 5];
        int[] d = new int[n + 5];

        for (int i = 1; i <= n; i++) {
            p[coordMap.get(a[i].y)] = i;
        }

        long r = 0, s = 0, ans = Long.MAX_VALUE;
        int k = 0;

        for (int i = n; i >= 1; i--) {
            int pi = p[i];
            r += sum(pi, d);
            add(pi, 1, d);
            q[pi] = i;

            if (pi > i) {
                k++;
                s += 2L * (pi - i);
                c[pi - i]++;
            }
        }

        for (int i = 1; i <= n; i++) {
            int j = q[i];
            ans = Math.min(ans, r - s + 1L * k * (n - k));
            s -= 2L * k;
            k -= c[i];
            r += n - 2L * j + 1;

            if (j < n) {
                k++;
                s += 2L * (n - j);
                c[n - j + i]++;
            }
        }

        System.out.println(ans);
    }

    private static int sum(int x, int[] d) {
        int res = 0;
        while (x > 0) {
            res += d[x];
            x -= x & -x;
        }
        return res;
    }

    private static void add(int x, int y, int[] d) {
        while (x < d.length) {
            d[x] += y;
            x += x & -x;
        }
    }
}

/*
3 100
10 50
30 20
60 40

0


4 100
30 70
10 12
60 75
90 50

1
*/