package com.example.algorithm.java.tarjanSCC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class AerialCity {
    static class Reader {
        BufferedReader br;
        StringTokenizer st;

        public Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static ArrayList<Integer>[] graph, condensedGraph;
    static int[] discoveryTime, componentId;
    static ArrayDeque<Integer> stack;
    static ArrayList<Integer> leaves;
    static int time, componentCount, n;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();

        n = in.nextInt();
        int m = in.nextInt();
        int[][] edges = new int[m][2];
        init();

        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            edges[i][0] = u;
            edges[i][1] = v;
            graph[u].add(v);
            graph[v].add(u);
        }

        for (int i = 1; i <= n; i++) {
            if (discoveryTime[i] == -1) {
                dfsTarjan(i, -1);
            }
        }

        condensedGraph = new ArrayList[componentCount];
        for (int i = 0; i < componentCount; i++) {
            condensedGraph[i] = new ArrayList<>(2);
        }

        int[] componentToVertex = new int[componentCount];
        for (int i = 1; i <= n; i++) {
            componentToVertex[componentId[i]] = i;
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            if (componentId[u] != componentId[v]) {
                condensedGraph[componentId[u]].add(componentId[v]);
                condensedGraph[componentId[v]].add(componentId[u]);
            }
        }

        int leafCount = 0;
        for (int i = 0; i < componentCount; i++) {
            if (condensedGraph[i].size() == 1) {
                leafCount++;
            }
        }

        writeln((leafCount + 1) / 2);

        leaves = new ArrayList<>();
        collectLeaves(0, -1);

        for (int i = 0; i < leafCount / 2; i++) {
            write(componentToVertex[leaves.get(i)] + " ");
            writeln(componentToVertex[leaves.get(i + leafCount / 2)]);
        }

        if (leafCount % 2 == 1) {
            write(componentToVertex[leaves.get(0)] + " ");
            writeln(componentToVertex[leaves.get(leafCount - 1)]);
        }

        bw.flush();
        bw.close();
    }

    static void collectLeaves(int node, int parent) {
        if (condensedGraph[node].size() == 1) {
            leaves.add(node);
        }
        for (int neighbor : condensedGraph[node]) {
            if (neighbor != parent) {
                collectLeaves(neighbor, node);
            }
        }
    }

    @SuppressWarnings("unchecked")
    static void init() {
        graph = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>(3);
        }
        discoveryTime = new int[n + 1];
        componentId = new int[n + 1];
        Arrays.fill(discoveryTime, -1);
        Arrays.fill(componentId, -1);
        time = componentCount = 0;
        stack = new ArrayDeque<>();
    }

    static int dfsTarjan(int node, int parent) {
        int low = discoveryTime[node] = time++;
        stack.push(node);
        boolean hasParent = false;

        for (int neighbor : graph[node]) {
            if (neighbor == parent && !hasParent) {
                hasParent = true;
                continue;
            }
            if (discoveryTime[neighbor] == -1) {
                low = Math.min(low, dfsTarjan(neighbor, node));
            } else if (componentId[neighbor] == -1) {
                low = Math.min(low, discoveryTime[neighbor]);
            }
        }

        if (low == discoveryTime[node]) {
            while (true) {
                int top = stack.pop();
                componentId[top] = componentCount;
                if (top == node)
                    break;
            }
            componentCount++;
        }

        return low;
    }

    static void write(String s) throws IOException {
        bw.write(s);
    }

    static void writeln(long val) throws IOException {
        bw.write(Long.toString(val));
        bw.newLine();
    }
}

/*
9 10
1 2
2 3
2 3
3 4
4 5
4 6
3 6
6 7
6 8
8 9

2
5 7
1 9


6 7
1 2
1 3
2 4
2 4
4 5
3 5
5 6

1
6 5
*/