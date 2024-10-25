package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class TwoSAT3 {
    static int id;
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        ArrayList<Integer>[] graph = new ArrayList[2 * n];
        int[] scc = new int[2 * n];
        boolean[] finished = new boolean[2 * n];
        Stack<Integer> stack = new Stack<>();
        id = 1;

        for (int i = 0; i < 2 * n; i++)
            graph[i] = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            u = u > 0 ? u - 1 : n - u - 1;
            v = v > 0 ? v - 1 : n - v - 1;
            graph[u < n ? u + n : u - n].add(v);
            graph[v < n ? v + n : v - n].add(u);
        }

        for (int i = 0; i < 2 * n; i++) {
            if (scc[i] != 0)
                continue;
            findSCC(i, graph, scc, finished, stack);
        }

        for (int i = 0; i < n; i++) {
            if (scc[i] == scc[i + n]) {
                System.out.println(0);
                return;
            }
        }
        System.out.println(1);
    }

    private static int findSCC(int idx, ArrayList<Integer>[] graph, int[] scc, boolean[] finished,
            Stack<Integer> stack) {
        stack.push(idx);
        scc[idx] = id++;
        int parent = scc[idx];

        for (int next : graph[idx]) {
            if (scc[next] == 0) {
                parent = Math.min(parent, findSCC(next, graph, scc, finished, stack));
            }
            if (!finished[next]) {
                parent = Math.min(parent, scc[next]);
            }
        }

        if (parent == scc[idx]) {
            while (!stack.isEmpty()) {
                int top = stack.pop();
                scc[top] = parent;
                finished[top] = true;
                if (top == idx)
                    break;
            }
        }
        return parent;
    }
}

/*
3 4
-1 2
-2 3
1 3
3 2

1


1 2
1 1
-1 -1

0
*/