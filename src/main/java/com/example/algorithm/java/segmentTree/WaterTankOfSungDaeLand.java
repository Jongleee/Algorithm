package com.example.algorithm.java.segmentTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class WaterTankOfSungDaeLand {
    private static int n, root, rangeCounter = 0;
    private static int[] newIndex, rangeEnd, depth, tree;
    private static List<List<Integer>> adjacency = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        root = Integer.parseInt(st.nextToken());

        initializeAdjacencyList();
        readEdges(br);
        prepareTree();
        processQueries(br, sb);

        bw.write(sb.toString());
        bw.flush();
    }

    private static void initializeAdjacencyList() {
        for (int i = 0; i <= n; i++) {
            adjacency.add(new ArrayList<>());
        }
    }

    private static void readEdges(BufferedReader br) throws IOException {
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int city1 = Integer.parseInt(st.nextToken());
            int city2 = Integer.parseInt(st.nextToken());
            adjacency.get(city1).add(city2);
            adjacency.get(city2).add(city1);
        }
    }

    private static void prepareTree() {
        newIndex = new int[n + 1];
        rangeEnd = new int[n + 1];
        depth = new int[n + 1];
        tree = new int[4 * n];
        assignNewIndex(root, 0);
    }

    private static void assignNewIndex(int node, int parent) {
        newIndex[node] = ++rangeCounter;
        rangeEnd[node] = rangeCounter;
        depth[node] = depth[parent] + 1;

        for (int next : adjacency.get(node)) {
            if (next != parent) {
                assignNewIndex(next, node);
                rangeEnd[node] = Math.max(rangeEnd[node], rangeEnd[next]);
            }
        }
    }

    private static void processQueries(BufferedReader br, StringBuilder sb) throws IOException {
        int q = Integer.parseInt(br.readLine());
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int queryType = Integer.parseInt(st.nextToken());
            int city = Integer.parseInt(st.nextToken());

            if (queryType == 1) {
                update(1, 1, n, newIndex[city]);
            } else {
                long result = depth[city] * query(1, 1, n, newIndex[city], rangeEnd[city]);
                sb.append(result).append("\n");
            }
        }
    }

    private static void update(int node, int start, int end, int idx) {
        tree[node]++;
        if (start < end) {
            int mid = (start + end) / 2;
            if (idx <= mid) {
                update(node * 2, start, mid, idx);
            } else {
                update(node * 2 + 1, mid + 1, end, idx);
            }
        }
    }

    private static long query(int node, int start, int end, int left, int right) {
        if (left <= start && end <= right) {
            return tree[node];
        }
        if (end < left || right < start) {
            return 0;
        }
        int mid = (start + end) / 2;
        return query(node * 2, start, mid, left, right) + query(node * 2 + 1, mid + 1, end, left, right);
    }
}

/*
7 1
1 2
1 3
1 4
2 5
4 6
4 7
8
1 6
2 1
2 4
2 6
1 1
2 1
2 4
2 6

1
2
3
2
2
3
*/