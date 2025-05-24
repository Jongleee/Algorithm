package com.example.algorithm.java.tarjanSCC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Idol {
    static class SCCSolver {
        int n, varCount, index, sccCount;
        List<Integer>[] graph;
        int[] ids, sccIds;
        boolean[] finished;
        Deque<Integer> stack;

        @SuppressWarnings("unchecked")
        SCCSolver(int n) {
            this.n = n;
            this.varCount = 2 * n;
            this.index = 0;
            this.sccCount = 0;
            this.graph = new List[varCount + 1];
            this.ids = new int[varCount + 1];
            this.sccIds = new int[varCount + 1];
            this.finished = new boolean[varCount + 1];
            this.stack = new ArrayDeque<>();
            for (int i = 0; i <= varCount; i++)
                graph[i] = new ArrayList<>();
        }

        int toIndex(int x) {
            return (x > 0) ? x : -x + n;
        }

        void addImplication(int u, int v) {
            graph[toIndex(-u)].add(toIndex(v));
            graph[toIndex(-v)].add(toIndex(u));
        }

        void addConstraintTrue() {
            graph[toIndex(-1)].add(toIndex(1));
        }

        boolean solve() {
            for (int i = 1; i <= varCount; i++) {
                if (ids[i] == 0)
                    dfs(i);
            }
            for (int i = 1; i <= n; i++) {
                if (sccIds[i] == sccIds[i + n])
                    return false;
            }
            return true;
        }

        int dfs(int at) {
            ids[at] = ++index;
            int lowLink = ids[at];
            stack.push(at);

            for (int to : graph[at]) {
                if (ids[to] == 0) {
                    lowLink = Math.min(lowLink, dfs(to));
                } else if (!finished[to]) {
                    lowLink = Math.min(lowLink, ids[to]);
                }
            }

            if (lowLink == ids[at]) {
                while (true) {
                    int node = stack.pop();
                    finished[node] = true;
                    sccIds[node] = sccCount;
                    if (node == at)
                        break;
                }
                sccCount++;
            }

            return lowLink;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        String line;
        while ((line = br.readLine()) != null) {
            st = new StringTokenizer(line);
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            SCCSolver solver = new SCCSolver(n);
            solver.addConstraintTrue();

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                solver.addImplication(u, v);
            }

            bw.write(solver.solve() ? "yes\n" : "no\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }
}

/*
4 3
1 2
-2 -3
2 4
2 4
1 2
1 -2
-1 2
-1 -2
^Z

yes
no
*/