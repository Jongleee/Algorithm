package com.example.algorithm.java.topologicalSort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class MusicProgram {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<Integer>[] graph = new ArrayList[n + 1];
        int[] inDegree = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int count = Integer.parseInt(st.nextToken());
            int prev = Integer.parseInt(st.nextToken());

            for (int j = 1; j < count; j++) {
                int curr = Integer.parseInt(st.nextToken());
                graph[prev].add(curr);
                inDegree[curr]++;
                prev = curr;
            }
        }

        List<Integer> order = topologicalSort(n, graph, inDegree);

        if (order.size() != n) {
            System.out.println(0);
        } else {
            for (int singer : order) {
                sb.append(singer).append("\n");
            }
            System.out.print(sb);
        }
    }

    private static List<Integer> topologicalSort(int n, List<Integer>[] graph, int[] inDegree) {
        Queue<Integer> queue = new ArrayDeque<>();
        List<Integer> result = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();
            result.add(node);

            for (int next : graph[node]) {
                if (--inDegree[next] == 0) {
                    queue.add(next);
                }
            }
        }

        return result;
    }
}

/*
6 3
3 1 4 3  
4 6 2 5 4
2 2 3

6
2
1
5
4
3
*/