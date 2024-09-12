package com.example.algorithm.java.unionFind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SetRepresentation {
    private static int n;
    private static int[] parents;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        parents = new int[n + 1];
        initializeParents();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            if (command == 0) {
                union(x, y);
            } else {
                sb.append(isSameParent(x, y) ? "YES\n" : "NO\n");
            }
        }

        System.out.print(sb);
    }

    private static void initializeParents() {
        for (int i = 1; i <= n; i++) {
            parents[i] = i;
        }
    }

    private static int find(int x) {
        if (x == parents[x]) {
            return x;
        }
        parents[x] = find(parents[x]);
        return parents[x];
    }

    private static void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rootX > rootY) {
                parents[rootY] = rootX;
            } else {
                parents[rootX] = rootY;
            }
        }
    }

    private static boolean isSameParent(int x, int y) {
        return find(x) == find(y);
    }
}

/*
7 8
0 1 3
1 1 7
0 7 6
1 7 1
0 3 7
0 4 2
0 1 1
1 1 1

NO
NO
YES
*/