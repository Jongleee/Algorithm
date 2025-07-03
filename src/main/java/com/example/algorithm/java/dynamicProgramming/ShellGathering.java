package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class ShellGathering {
    static class BIT {
        long[] tree;
        int n;

        public BIT(int size) {
            n = size;
            tree = new long[n + 2];
        }

        void update(int l, int r, int v) {
            for (int i = l; i <= n; i += i & -i)
                tree[i] += v;
            for (int i = r + 1; i <= n; i += i & -i)
                tree[i] -= v;
        }

        long query(int x) {
            long sum = 0;
            for (; x > 0; x ^= x & -x)
                sum += tree[x];
            return sum;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());

        int[][] arr = new int[n + 1][n + 1];
        int[][] dp = new int[n + 1][n + 1];
        long ans = 0;

        for (int i = 1; i <= n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        BIT[] bits = new BIT[n + 1];
        for (int i = 1; i <= n; i++)
            bits[i] = new BIT(n);

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]) + arr[i][j];
                ans += dp[i][j];
            }
        }

        bw.write(ans + "\n");
        bw.flush();

        int[] s = new int[n + 1];
        int[] e = new int[n + 1];

        for (int q = 1; q <= n; q++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            char op = st.nextToken().charAt(0);
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = (op == 'U') ? 1 : -1;

            s[a] = e[a] = b;
            for (int i = a + 1; i <= n; i++) {
                s[i] = n + 1;
                e[i] = 0;
            }

            int i = a, j = b;
            while (true) {
                if (j < n && Math.max(get(dp, bits, i - 1, j + 1), get(dp, bits, i, j)) + c == Math
                        .max(get(dp, bits, i - 1, j + 1), get(dp, bits, i, j) + c)) {
                    j++;
                } else {
                    i++;
                }
                if (i > n)
                    break;
                e[i] = j;
            }

            i = a;
            j = b;
            while (true) {
                if (i < n && Math.max(get(dp, bits, i + 1, j - 1), get(dp, bits, i, j)) + c == Math
                        .max(get(dp, bits, i + 1, j - 1), get(dp, bits, i, j) + c)) {
                    i++;
                } else {
                    j++;
                }
                if (j > n || e[i] < j)
                    break;
                s[i] = Math.min(s[i], j);
            }

            for (i = a; i <= n; i++) {
                if (s[i] > e[i])
                    continue;
                bits[i].update(s[i], e[i], c);
                ans += (long) c * (e[i] - s[i] + 1);
            }
            bw.write(ans + "\n");
        }

        bw.flush();
    }

    private static long get(int[][] dp, BIT[] bits, int i, int j) {
        if (i <= 0 || j <= 0)
            return 0;
        return dp[i][j] + bits[i].query(j);
    }
}

/*
3
3 2 7
4 2 6
5 3 8
U 1 2
D 3 2
U 1 2

107
111
110
114
*/