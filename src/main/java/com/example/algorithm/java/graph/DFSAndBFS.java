package com.example.algorithm.java.graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class DFSAndBFS {
    static class Node {
        int idx;
        Node next;

        public Node(int idx) {
            this.idx = idx;
        }

        public void insert(Node node) {
            if (idx > node.idx) {
                int tmp = idx;
                idx = node.idx;
                node.idx = tmp;
                node.next = this.next;
                next = node;
            } else if (next == null) {
                next = node;
            } else {
                next.insert(node);
            }
        }
    }

    static int n;
    static Node[] graph;
    static boolean[] visited;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        graph = new Node[n + 1];
        int m = Integer.parseInt(st.nextToken());
        int v = Integer.parseInt(st.nextToken());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            if (graph[from] == null) {
                graph[from] = new Node(to);
            } else {
                graph[from].insert(new Node(to));
            }
            if (graph[to] == null) {
                graph[to] = new Node(from);
            } else {
                graph[to].insert(new Node(from));
            }
        }

        visited = new boolean[n + 1];
        dfs(v);

        sb.append('\n');
        visited = new boolean[n + 1];
        bfs(v);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static void bfs(int v) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(v);
        visited[v] = true;
        sb.append(v).append(' ');

        while (!queue.isEmpty()) {
            int cur = queue.poll();

            for (Node tmp = graph[cur]; tmp != null; tmp = tmp.next) {
                if (visited[tmp.idx]) continue;
                sb.append(tmp.idx).append(' ');
                visited[tmp.idx] = true;
                queue.add(tmp.idx);
            }
        }
    }

    private static void dfs(int cur) {
        visited[cur] = true;
        sb.append(cur).append(' ');
        for (Node tmp = graph[cur]; tmp != null; tmp = tmp.next) {
            if (visited[tmp.idx]) continue;
            dfs(tmp.idx);
        }
    }
}

/*
4 5 1
1 2
1 3
1 4
2 4
3 4

1 2 4 3
1 2 3 4


5 5 3
5 4
5 2
1 2
3 4
3 1

3 1 2 5 4
3 1 4 2 5


1000 1 1000
999 1000

1000 999
1000 999
 */