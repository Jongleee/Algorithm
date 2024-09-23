package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TreeAndQuery {
    static int[] size;
    static List<Integer>[] adjList;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        int nodeCount = Integer.parseInt(st.nextToken());
        int root = Integer.parseInt(st.nextToken());
        int queryCount = Integer.parseInt(st.nextToken());

        adjList = new ArrayList[nodeCount + 1];
        for (int i = 1; i <= nodeCount; i++) {
            adjList[i] = new ArrayList<>();
        }

        size = new int[nodeCount + 1];

        for (int i = 1; i < nodeCount; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            adjList[start].add(end);
            adjList[end].add(start);
        }

        calculateSubtreeSize(root, 0);

        for (int i = 0; i < queryCount; i++) {
            int queryNode = Integer.parseInt(br.readLine());
            sb.append(size[queryNode]).append('\n');
        }
        System.out.print(sb);
    }

    static void calculateSubtreeSize(int currentNode, int parentNode) {
        size[currentNode] = 1;
        for (Integer childNode : adjList[currentNode]) {
            if (childNode != parentNode) {
                calculateSubtreeSize(childNode, currentNode);
                size[currentNode] += size[childNode];
            }
        }
    }
}

/*
9 5 3
1 3
4 3
5 4
5 6
6 7
2 3
9 6
6 8
5
4
8

9
4
1
*/