package com.example.algorithm.java.lowestCommonAncestor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TreeExcenter {
    private static final int MAX_HEIGHT = 18;
    private static List<Integer>[] graph;
    private static int[][] parent;
    private static int[] level;
    private static int n;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            graph[x].add(y);
            graph[y].add(x);
        }

        parent = new int[MAX_HEIGHT][n + 1];
        level = new int[n + 1];

        dfs(1, 0, 0);
        precomputeParents();

        int q = Integer.parseInt(br.readLine());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        while (q-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            bw.write(query(a, b, c) + "\n");
        }
        bw.flush();
    }

    private static void dfs(int node, int parentNode, int depth) {
        parent[0][node] = parentNode;
        level[node] = depth;
        for (int next : graph[node]) {
            if (next != parentNode) {
                dfs(next, node, depth + 1);
            }
        }
    }

    private static void precomputeParents() {
        for (int i = 1; i < MAX_HEIGHT; i++) {
            for (int j = 1; j <= n; j++) {
                parent[i][j] = parent[i - 1][parent[i - 1][j]];
            }
        }
    }

    private static int levelUp(int node, int diff) {
        for (int i = MAX_HEIGHT - 1; i >= 0; i--) {
            if ((diff & (1 << i)) != 0) {
                node = parent[i][node];
            }
        }
        return node;
    }

    private static int findLCA(int x, int y) {
        x = levelUp(x, Math.max(0, level[x] - level[y]));
        y = levelUp(y, Math.max(0, level[y] - level[x]));
        if (x == y)
            return x;
        for (int i = MAX_HEIGHT - 1; i >= 0; i--) {
            if (parent[i][x] != parent[i][y]) {
                x = parent[i][x];
                y = parent[i][y];
            }
        }
        return parent[0][x];
    }

    private static int treeDistance(int a, int b) {
        int lca = findLCA(a, b);
        return level[a] + level[b] - 2 * level[lca];
    }

    private static int findMidpoint(int a, int b) {
        int dist = treeDistance(a, b);
        if (dist % 2 != 0)
            return -1;
        return level[a] > level[b] ? levelUp(a, dist / 2) : levelUp(b, dist / 2);
    }

    private static int query(int a, int b, int c) {
        int[] midpoints = { findMidpoint(a, b), findMidpoint(a, c), findMidpoint(b, c) };
        for (int m : midpoints) {
            if (m != -1 && treeDistance(a, m) == treeDistance(b, m) && treeDistance(b, m) == treeDistance(c, m)) {
                return m;
            }
        }
        return -1;
    }
}

/*
4
1 2
1 3
1 4
2
2 3 4
1 2 3

1
-1


6
1 2
2 3
2 4
3 5
5 6
1
1 4 6

3
*/