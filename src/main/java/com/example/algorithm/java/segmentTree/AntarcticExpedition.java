package com.example.algorithm.java.segmentTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AntarcticExpedition {
    static int n, queryCount, index, segmentSize;
    static int[] penguinCount, p, subtreeSize, parent, dfsIndex, depth, segmentTree, topChain;
    static Query[] q;
    static List<Integer>[] adj;

    static class Query {
        String cmd;
        int a, b;

        Query(String cmd, int a, int b) {
            this.cmd = cmd;
            this.a = a;
            this.b = b;
        }
    }

    static int find(int n) {
        return n == p[n] ? n : (p[n] = find(p[n]));
    }

    static boolean merge(int u, int v) {
        u = find(u);
        v = find(v);
        if (u == v)
            return false;
        p[v] = u;
        return true;
    }

    static int dfs(int cur) {
        subtreeSize[cur] = 1;
        for (int i = 0; i < adj[cur].size(); i++) {
            int next = adj[cur].get(i);
            if (next == parent[cur])
                continue;
            parent[next] = cur;
            depth[next] = depth[cur] + 1;
            subtreeSize[cur] += dfs(next);
            int t = adj[cur].get(0);
            if (t == parent[cur] || subtreeSize[next] > subtreeSize[t]) {
                adj[cur].set(0, next);
                adj[cur].set(i, t);
            }
        }
        return subtreeSize[cur];
    }

    static void hld(int cur) {
        dfsIndex[cur] = index++;
        for (int next : adj[cur]) {
            if (next == parent[cur])
                continue;
            topChain[next] = next == adj[cur].get(0) ? topChain[cur] : next;
            hld(next);
        }
    }

    static void init() {
        for (segmentSize = 1; segmentSize < index; segmentSize *= 2)
            ;
        segmentTree = new int[2 * segmentSize];

        for (int i = 1; i <= n; i++) {
            segmentTree[segmentSize + dfsIndex[i]] = penguinCount[i];
            p[i] = i;
        }

        for (int i = segmentSize - 1; i > 0; i--)
            segmentTree[i] = segmentTree[i * 2] + segmentTree[i * 2 + 1];
    }

    static void update(int i, int w) {
        segmentTree[i += segmentSize] = w;
        while ((i /= 2) > 0)
            segmentTree[i] = segmentTree[i * 2] + segmentTree[i * 2 + 1];
    }

    static int sum(int l, int r) {
        int ret = 0;
        for (l += segmentSize, r += segmentSize; l <= r; l /= 2, r /= 2) {
            if (l % 2 == 1)
                ret += segmentTree[l++];
            if (r % 2 == 0)
                ret += segmentTree[r--];
        }
        return ret;
    }

    static String query(int u, int v) {
        if (find(u) != find(v))
            return "impossible";
        int ret = 0;
        while (topChain[u] != topChain[v]) {
            if (depth[topChain[u]] > depth[topChain[v]]) {
                int temp = u;
                u = v;
                v = temp;
            }
            ret += sum(dfsIndex[topChain[v]], dfsIndex[v]);
            v = parent[topChain[v]];
        }
        if (depth[u] > depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }
        ret += sum(dfsIndex[u], dfsIndex[v]);
        return "" + ret;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        penguinCount = new int[n + 1];
        p = new int[n + 1];
        subtreeSize = new int[n + 1];
        parent = new int[n + 1];
        depth = new int[n + 1];
        topChain = new int[n + 1];
        dfsIndex = new int[n + 1];
        adj = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
            p[i] = i;
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++)
            penguinCount[i] = Integer.parseInt(st.nextToken());

        queryCount = Integer.parseInt(br.readLine());
        q = new Query[queryCount];
        for (int i = 0; i < queryCount; i++) {
            st = new StringTokenizer(br.readLine());
            String cmd = st.nextToken();
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            q[i] = new Query(cmd, a, b);
            if (cmd.contentEquals("bridge") && merge(a, b)) {
                adj[a].add(b);
                adj[b].add(a);
            }
        }

        for (int i = 1; i <= n; i++)
            if (subtreeSize[i] == 0)
                dfs(i);

        for (int i = 1; i <= n; i++)
            if (dfsIndex[i] == 0)
                hld(i);
        init();
        executeQuery();
    }

    private static void executeQuery() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (Query query : q) {
            if (query.cmd.equals("bridge"))
                bw.write(merge(query.a, query.b) ? "yes\n" : "no\n");
            else if (query.cmd.equals("penguins"))
                update(dfsIndex[query.a], query.b);
            else
                bw.write(query(query.a, query.b) + "\n");
        }
        bw.close();
    }
}

/*
6
1 2 3 4 5 6
10
bridge 1 2
bridge 2 3
bridge 4 5
excursion 1 3
excursion 1 5
bridge 3 4
excursion 1 5
penguins 3 10
excursion 1 3
bridge 1 5

yes
yes
yes
6
impossible
yes
15
13
no


5
4 2 4 5 6
10
excursion 1 1
excursion 1 2
bridge 1 2
excursion 1 2
bridge 3 4
bridge 3 5
excursion 4 5
bridge 1 3
excursion 2 4
excursion 2 5

4
impossible
yes
6
yes
yes
15
yes
15
16
*/