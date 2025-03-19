package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class CutEdge {
    static class Node implements Comparable<Node> {
        int from, to;

        Node(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public int compareTo(Node o) {
            return this.from != o.from ? this.from - o.from : this.to - o.to;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        List<List<Integer>> graph = createGraph(v, e, br);
        List<Node> bridges = findBridges(v, graph);

        StringBuilder sb = new StringBuilder();
        sb.append(bridges.size()).append('\n');
        for (Node bridge : bridges) {
            sb.append(bridge.from).append(' ').append(bridge.to).append('\n');
        }
        System.out.print(sb);
    }

    private static List<List<Integer>> createGraph(int v, int e, BufferedReader br) throws IOException {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= v; i++) {
            graph.add(new ArrayList<>());
        }

        while (e-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph.get(a).add(b);
            graph.get(b).add(a);
        }
        return graph;
    }

    private static List<Node> findBridges(int v, List<List<Integer>> graph) {
        int[] visit = new int[v + 1];
        int[] order = { 1 };
        List<Node> bridges = new ArrayList<>();

        for (int i = 1; i <= v; i++) {
            if (visit[i] == 0) {
                dfs(i, 0, graph, visit, order, bridges);
            }
        }

        bridges.sort(Comparator.naturalOrder());
        return bridges;
    }

    private static int dfs(int cur, int parent, List<List<Integer>> graph, int[] visit, int[] order,
            List<Node> bridges) {
        visit[cur] = order[0]++;
        int result = visit[cur];

        for (int next : graph.get(cur)) {
            if (next == parent)
                continue;

            if (visit[next] > 0) {
                result = Math.min(result, visit[next]);
                continue;
            }

            int prev = dfs(next, cur, graph, visit, order, bridges);
            if (prev > visit[cur]) {
                bridges.add(new Node(Math.min(cur, next), Math.max(cur, next)));
            }
            result = Math.min(result, prev);
        }
        return result;
    }
}

/*
7 8
1 4
4 5
5 1
1 6
6 7
2 7
7 3
2 3

2
1 6
6 7
*/