package com.example.algorithm.java.fenwickTree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class RangeSumQuery3 {
    static int n;
    static int[][] arr;
    static int[][] tree;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        arr = new int[n + 1][n + 1];
        tree = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                int value = Integer.parseInt(st.nextToken());
                arr[i][j] = value;
                update(i, j, value);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int op = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x1 = Integer.parseInt(st.nextToken());

            if (op == 0) {
                int newValue = Integer.parseInt(st.nextToken());
                update(y1, x1, newValue - arr[y1][x1]);
                arr[y1][x1] = newValue;
            } else {
                int y2 = Integer.parseInt(st.nextToken());
                int x2 = Integer.parseInt(st.nextToken());
                sb.append(sum(y2, x2) - sum(y2, x1 - 1) - sum(y1 - 1, x2) + sum(y1 - 1, x1 - 1)).append('\n');
            }
        }
        br.close();
        System.out.print(sb);
    }

    static void update(int y, int x, int val) {
        while (y <= n) {
            for (int i = x; i <= n; i += i & -i) {
                tree[y][i] += val;
            }
            y += y & -y;
        }
    }

    static int sum(int y, int x) {
        int total = 0;
        while (y > 0) {
            for (int i = x; i > 0; i -= i & -i) {
                total += tree[y][i];
            }
            y -= y & -y;
        }
        return total;
    }
}

/*
4 5
1 2 3 4
2 3 4 5
3 4 5 6
4 5 6 7
1 2 2 3 4
0 2 3 7
1 2 2 3 4
0 3 4 5
1 3 4 3 4

27
30
5
*/