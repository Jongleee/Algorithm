package com.example.algorithm.java.topologicalSort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class ProblemSet {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int nodeCount = Integer.parseInt(st.nextToken());
        int edgeCount = Integer.parseInt(st.nextToken());

        int[] indegree = new int[nodeCount + 1];
        List<List<Integer>> graph = new ArrayList<>();
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        for (int i = 0; i <= nodeCount; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edgeCount; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            indegree[to]++;
            graph.get(from).add(to);
        }

        for (int i = 1; i <= nodeCount; i++) {
            if (indegree[i] == 0) {
                priorityQueue.add(i);
            }
        }

        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();
            sb.append(current).append(" ");

            for (int nextNode : graph.get(current)) {
                indegree[nextNode]--;
                if (indegree[nextNode] == 0) {
                    priorityQueue.add(nextNode);
                }
            }
        }

        System.out.println(sb);
    }
}

/*
4 2
4 2
3 1

3 1 4 2 
*/