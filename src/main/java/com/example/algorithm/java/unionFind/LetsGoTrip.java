package com.example.algorithm.java.unionFind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LetsGoTrip {
    private static final char CONNECTED = '1';
    private static final String YES = "YES";
    private static final String NO = "NO";

    private static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());
        parent = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            parent[i] = -1;
        }

        for (int i = 1; i <= n; i++) {
            String str = br.readLine();
            for (int j = 1; j <= n; j++) {
                if (str.charAt((j - 1) * 2) == CONNECTED) {
                    union(i, j);
                }
            }
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        int startCityRoot = find(Integer.parseInt(st.nextToken()));

        for (int i = 1; i < m; i++) {
            int city = Integer.parseInt(st.nextToken());
            if (startCityRoot != find(city)) {
                System.out.print(NO);
                return;
            }
        }

        System.out.print(YES);
    }

    private static int find(int v) {
        if (parent[v] < 0) {
            return v;
        }
        parent[v] = find(parent[v]);
        return parent[v];
    }

    private static void union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);

        if (rootU != rootV) {
            if (parent[rootU] < parent[rootV]) {
                parent[rootV] = rootU;
            } else {
                if (parent[rootU] == parent[rootV]) {
                    parent[rootV]--;
                }
                parent[rootU] = rootV;
            }
        }
    }
}

/*
3
3
0 1 0
1 0 1
0 1 0
1 2 3

YES
 */