package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CutVertex {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int vertices = Integer.parseInt(st.nextToken());
        int edges = Integer.parseInt(st.nextToken());

        List<Integer>[] adjacencyList = new ArrayList[vertices + 1];
        boolean[] isCutVertex = new boolean[vertices + 1];
        int[] discovered = new int[vertices + 1];
        int counter = 0;

        for (int i = 1; i <= vertices; i++) {
            adjacencyList[i] = new ArrayList<>();
            discovered[i] = -1;
        }

        for (int i = 0; i < edges; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adjacencyList[a].add(b);
            adjacencyList[b].add(a);
        }

        for (int i = 1; i <= vertices; i++) {
            if (discovered[i] == -1) {
                counter = findCutVertex(i, true, adjacencyList, isCutVertex, discovered, counter);
            }
        }

        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 1; i <= vertices; i++) {
            if (isCutVertex[i]) {
                count++;
                sb.append(i).append(" ");
            }
        }
        System.out.println(count);
        if (count > 0) {
            System.out.println(sb.toString());
        }
    }

    private static int findCutVertex(int current, boolean isRoot, List<Integer>[] adjacencyList, boolean[] isCutVertex,
            int[] discovered, int counter) {
        discovered[current] = counter++;
        int ret = discovered[current];
        int childCount = 0;

        for (int next : adjacencyList[current]) {
            if (discovered[next] == -1) {
                childCount++;
                int subTree = findCutVertex(next, false, adjacencyList, isCutVertex, discovered, counter);
                if (!isRoot && subTree >= discovered[current]) {
                    isCutVertex[current] = true;
                }
                ret = Math.min(ret, subTree);
            } else {
                ret = Math.min(ret, discovered[next]);
            }
        }
        if (isRoot) {
            isCutVertex[current] = childCount >= 2;
        }
        return ret;
    }
}

/*
7 7
1 4
4 5
5 1
1 6
6 7
2 7
7 3

3
1 6 7
*/