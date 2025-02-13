package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class InternationalMessiOrganization {
    private static int n, s, idx;
    private static int[] size, depth, head, parent, start, end;
    private static int[] segmentTree, lazyMul, lazyAdd;
    private static List<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        size = new int[n];
        depth = new int[n];
        start = new int[n];
        end = new int[n];
        head = new int[n];
        parent = new int[n];
        adj = new ArrayList[n];

        for (int i = 0; i < n; i++)
            adj[i] = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            adj[u].add(v);
            adj[v].add(u);
        }
        dfs(0);
        hld(0);
        initSegmentTree();
        int x, y, v;
        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            switch (Integer.parseInt(st.nextToken())) {
                case 1:
                    x = Integer.parseInt(st.nextToken()) - 1;
                    v = Integer.parseInt(st.nextToken());
                    update(start[x], end[x], 1, v, 1, 0, s - 1);
                    break;
                case 2:
                    x = Integer.parseInt(st.nextToken()) - 1;
                    y = Integer.parseInt(st.nextToken()) - 1;
                    v = Integer.parseInt(st.nextToken());
                    hldUpdate(x, y, 1, v);
                    break;
                case 3:
                    x = Integer.parseInt(st.nextToken()) - 1;
                    v = Integer.parseInt(st.nextToken());
                    update(start[x], end[x], v, 0, 1, 0, s - 1);
                    break;
                case 4:
                    x = Integer.parseInt(st.nextToken()) - 1;
                    y = Integer.parseInt(st.nextToken()) - 1;
                    v = Integer.parseInt(st.nextToken());
                    hldUpdate(x, y, v, 0);
                    break;
                case 5:
                    x = Integer.parseInt(st.nextToken()) - 1;
                    bw.write(unsignInt(query(start[x], end[x], 1, 0, s - 1)) + "\n");
                    break;
                case 6:
                    x = Integer.parseInt(st.nextToken()) - 1;
                    y = Integer.parseInt(st.nextToken()) - 1;
                    bw.write(unsignInt(hldQuery(x, y)) + "\n");
                    break;
                default:
                    break;
            }
        }
        bw.close();
    }

    private static void dfs(int cur) {
        size[cur] = 1;
        for (int i = 0; i < adj[cur].size(); i++) {
            int next = adj[cur].get(i);
            if (parent[cur] == next)
                continue;
            parent[next] = cur;
            depth[next] = depth[cur] + 1;
            dfs(next);
            size[cur] += size[next];
            if (size[next] > size[adj[cur].get(0)] || parent[cur] == adj[cur].get(0)) {
                Collections.swap(adj[cur], 0, i);
            }
        }
    }

    private static void hld(int cur) {
        start[cur] = idx++;
        for (int next : adj[cur]) {
            if (parent[cur] == next)
                continue;
            head[next] = (next == adj[cur].get(0)) ? head[cur] : next;
            hld(next);
        }
        end[cur] = idx - 1;
    }

    private static void initSegmentTree() {
        for (s = 1; s < n; s *= 2)
            ;
        segmentTree = new int[s * 2];
        lazyMul = new int[s * 2];
        lazyAdd = new int[s * 2];
        Arrays.fill(lazyMul, 1);
    }

    private static void propagate(int node, int nl, int nr) {
        if (node < s) {
            int left = node * 2, right = node * 2 + 1;
            applyLazy(left, lazyMul[node], lazyAdd[node]);
            applyLazy(right, lazyMul[node], lazyAdd[node]);
        }
        segmentTree[node] = lazyMul[node] * segmentTree[node] + lazyAdd[node] * (nr - nl + 1);
        lazyMul[node] = 1;
        lazyAdd[node] = 0;
    }

    private static void applyLazy(int node, int mul, int add) {
        lazyMul[node] *= mul;
        lazyAdd[node] = lazyAdd[node] * mul + add;
    }

    private static void update(int l, int r, int mul, int add, int node, int nl, int nr) {
        propagate(node, nl, nr);
        if (nr < l || nl > r)
            return;
        if (l <= nl && nr <= r) {
            applyLazy(node, mul, add);
            propagate(node, nl, nr);
            return;
        }
        int mid = (nl + nr) / 2;
        update(l, r, mul, add, node * 2, nl, mid);
        update(l, r, mul, add, node * 2 + 1, mid + 1, nr);
        segmentTree[node] = segmentTree[node * 2] + segmentTree[node * 2 + 1];
    }

    private static int query(int l, int r, int node, int nl, int nr) {
        propagate(node, nl, nr);
        if (nr < l || nl > r)
            return 0;
        if (l <= nl && nr <= r)
            return segmentTree[node];
        int mid = (nl + nr) / 2;
        return query(l, r, node * 2, nl, mid) + query(l, r, node * 2 + 1, mid + 1, nr);
    }

    private static void hldUpdate(int u, int v, int mul, int add) {
        while (head[u] != head[v]) {
            if (depth[head[u]] > depth[head[v]]) {
                int temp = u;
                u = v;
                v = temp;
            }
            update(start[head[v]], start[v], mul, add, 1, 0, s - 1);
            v = parent[head[v]];
        }
        if (depth[u] > depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }
        update(start[u], start[v], mul, add, 1, 0, s - 1);
    }

    private static int hldQuery(int u, int v) {
        int result = 0;
        while (head[u] != head[v]) {
            if (depth[head[u]] > depth[head[v]]) {
                int temp = u;
                u = v;
                v = temp;
            }
            result += query(start[head[v]], start[v], 1, 0, s - 1);
            v = parent[head[v]];
        }
        if (depth[u] > depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }
        return result + query(start[u], start[v], 1, 0, s - 1);
    }

    static long unsignInt(int a) {
        return a < 0 ? (1L << 32) + a : a;
    }
}

/*
5 10
2 4
4 3
5 4
2 1
3 1 82
6 3 5
2 2 5 45
2 3 2 70
6 3 5
5 3
4 2 1 47
1 1 95
6 3 2
4 5 1 38

0
230
70
5875


10 20
3 7
5 6
10 9
6 8
10 2
6 3
1 3
6 4
10 4
1 10 97
1 10 50
3 9 9
5 5
1 8 27
5 10
2 8 7 20
2 4 4 41
2 2 5 92
3 4 96
3 5 12
1 7 32
2 7 3 75
4 5 6 60
6 8 7
6 1 2
3 9 0
1 3 20
6 1 1
1 6 82

0
1617
6989
65471
0
*/