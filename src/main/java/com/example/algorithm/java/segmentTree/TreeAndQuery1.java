package com.example.algorithm.java.segmentTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class TreeAndQuery1 {
    private static int n, cnt, len;
    private static List<Integer>[] adj;
    private static int[] top, parent, size, index, eu, ev, ew, tree;

    private static int dfs(int node) {
        size[node] = 1;
        for (int i = 0; i < adj[node].size(); i++) {
            int next = adj[node].get(i);
            if (parent[node] != next) {
                parent[next] = node;
                size[node] += dfs(next);
                if (i == 0 || size[next] > size[adj[node].get(0)]) {
                    Collections.swap(adj[node], 0, i);
                }
            }
        }
        return size[node];
    }

    private static void hld(int node) {
        index[node] = cnt++;
        for (int next : adj[node]) {
            if (next != parent[node]) {
                top[next] = (next == adj[node].get(0)) ? top[node] : next;
                hld(next);
            }
        }
    }

    private static void init() {
        for (int i = 1; i < n; i++) {
            if (parent[ev[i]] == eu[i]) {
                int temp = ev[i];
                ev[i] = eu[i];
                eu[i] = temp;
            }
            tree[len + index[eu[i]]] = ew[i];
        }
        for (int i = len - 1; i > 0; i--) {
            tree[i] = Math.max(tree[i * 2], tree[i * 2 + 1]);
        }
    }

    private static void update(int i, int value) {
        tree[i += len] = value;
        for (i /= 2; i > 0; i /= 2) {
            tree[i] = Math.max(tree[i * 2], tree[i * 2 + 1]);
        }
    }

    private static int query(int l, int r) {
        l += len;
        r += len;
        int result = 0;
        while (l <= r) {
            if (l % 2 == 1)
                result = Math.max(result, tree[l++]);
            if (r % 2 == 0)
                result = Math.max(result, tree[r--]);
            l /= 2;
            r /= 2;
        }
        return result;
    }

    private static int solve(int u, int v) {
        int result = 0;
        while (top[u] != top[v]) {
            if (size[top[u]] < size[top[v]]) {
                int temp = u;
                u = v;
                v = temp;
            }
            result = Math.max(result, query(index[top[v]], index[v]));
            v = parent[top[v]];
        }
        if (index[u] > index[v]) {
            int temp = u;
            u = v;
            v = temp;
        }
        result = Math.max(result, query(index[u] + 1, index[v]));
        return result;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        len = (int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)));
        adj = new ArrayList[n];
        top = new int[n];
        parent = new int[n];
        size = new int[n];
        index = new int[n];
        eu = new int[n];
        ev = new int[n];
        ew = new int[n];
        tree = new int[len * 2];

        for (int i = 0; i < n; i++)
            adj[i] = new ArrayList<>();

        for (int i = 1; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            eu[i] = Integer.parseInt(st.nextToken()) - 1;
            ev[i] = Integer.parseInt(st.nextToken()) - 1;
            ew[i] = Integer.parseInt(st.nextToken());
            adj[eu[i]].add(ev[i]);
            adj[ev[i]].add(eu[i]);
        }

        dfs(0);
        hld(0);
        init();

        int q = Integer.parseInt(br.readLine());
        while (q-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            if (type == 1) {
                update(index[eu[u]], v);
            } else {
                bw.write(solve(u - 1, v - 1) + "\n");
            }
        }

        bw.flush();
        bw.close();
    }
}

/*
3
1 2 1
2 3 2
3
2 1 2
1 1 3
2 1 2

1
3
*/