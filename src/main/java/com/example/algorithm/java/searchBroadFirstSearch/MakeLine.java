package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class MakeLine {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int numOfVertices = Integer.parseInt(st.nextToken());
        int numOfEdges = Integer.parseInt(st.nextToken());

        List<Integer>[] adjacencyList = new ArrayList[numOfVertices + 1];
        int[] indegrees = new int[numOfVertices + 1];
        StringBuilder result = new StringBuilder();

        for (int i = 1; i <= numOfVertices; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < numOfEdges; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            adjacencyList[from].add(to);
            indegrees[to]++;
        }

        performTopologicalSort(adjacencyList, indegrees, result);
        System.out.println(result);
    }

    static void performTopologicalSort(List<Integer>[] adjacencyList, int[] indegrees, StringBuilder result) {
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 1; i < adjacencyList.length; i++) {
            if (indegrees[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            result.append(currentVertex).append(" ");

            for (int adjacentVertex : adjacencyList[currentVertex]) {
                indegrees[adjacentVertex]--;
                if (indegrees[adjacentVertex] == 0) {
                    queue.add(adjacentVertex);
                }
            }
        }
    }
}
/*
4 2
4 2
3 1

1 2 3

4 2
4 2
3 1

3 4 1 2
 */