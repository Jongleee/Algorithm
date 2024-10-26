package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class TwoSAT4 {
    static int n;
    static int v;
    static int num;
    static ArrayList<ArrayList<Integer>> graph;
    static ArrayList<ArrayList<Integer>> scc;
    static int[] parent;
    static int[] compare;
    static int[] cnf;
    static boolean[] visited;
    static Stack<Integer> stack;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        parent = new int[2 * n + 1];
        compare = new int[2 * n + 1];
        visited = new boolean[2 * n + 1];
        stack = new Stack<>();
        num = 0;
        v = 0;
        cnf = new int[2 * n + 1];
        Arrays.fill(cnf, -1);

        graph = new ArrayList<>();
        scc = new ArrayList<>();
        for (int i = 0; i < 2 * n + 1; i++) {
            graph.add(new ArrayList<>());
        }

        while (m-- > 0) {
            st = new StringTokenizer(br.readLine());

            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            graph.get(validate(-u)).add(validate(v));
            graph.get(validate(-v)).add(validate(u));
        }

        for (int i = 1; i < 2 * n + 1; i++) {
            if (!visited[i]) {
                findSCC(i);
            }
        }
        int sts = 1;
        for (int i = 1; i < n + 1; i++) {
            if (compare[i] == compare[i + n])
                sts = 0;
        }

        bw.write(sts + "\n");
        if (sts == 1)
            bw.write(setCNF() + "\n");

        bw.flush();
        bw.close();
        br.close();
    }

    private static int validate(int i) {
        return (0 < i && i < n + 1) ? i : -i + n;
    }

    private static String setCNF() {
        for (int i = v - 1; i > -1; i--) {
            for (int j : scc.get(i)) {
                int now = Math.abs(validate(j));
                if (cnf[now] == -1) {
                    if (j > n) {
                        cnf[now] = 1;
                    } else {
                        cnf[now] = 0;
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < n + 1; i++) {
            sb.append(cnf[i]).append(' ');
        }
        return sb.toString();
    }

    private static int findSCC(int idx) {
        parent[idx] = ++num;
        stack.push(idx);

        int root = parent[idx];
        for (int next : graph.get(idx)) {
            if (parent[next] == 0)
                root = Math.min(root, findSCC(next));
            else if (!visited[next])
                root = Math.min(root, parent[next]);
        }

        if (root == parent[idx]) {
            ArrayList<Integer> tmp = new ArrayList<>();
            while (!stack.isEmpty()) {
                int top = stack.pop();
                tmp.add(top);
                visited[top] = true;
                compare[top] = v;

                if (top == idx)
                    break;
            }
            v++;
            scc.add(tmp);
        }
        return root;
    }
}

/*
3 4
-1 2
-2 3
1 3
3 2

1
1 1 1


1 2
1 1
-1 -1

0
*/