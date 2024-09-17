package com.example.algorithm.java.prim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class MinimumSpanningTree {
    private static class Node implements Comparable<Node> {
        int no;
        int weight;
        Node next;

        public Node(int no, int weight, Node next) {
            this.no = no;
            this.weight = weight;
            this.next = next;
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int vertices = Integer.parseInt(st.nextToken());
        int edges = Integer.parseInt(st.nextToken());

        Node[] adjList = new Node[vertices + 1];

        for (int i = 0; i < edges; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            adjList[from] = new Node(to, weight, adjList[from]);
            adjList[to] = new Node(from, weight, adjList[to]);
        }

        boolean[] visited = new boolean[vertices + 1];
        int[] distance = new int[vertices + 1];
        Arrays.fill(distance, Integer.MAX_VALUE);

        int start = 1;
        distance[start] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0, null));

        int totalWeight = 0;
        int connectedNodes = 0;

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (visited[current.no])
                continue;

            visited[current.no] = true;
            totalWeight += current.weight;

            if (++connectedNodes == vertices)
                break;

            for (Node neighbor = adjList[current.no]; neighbor != null; neighbor = neighbor.next) {
                if (!visited[neighbor.no] && distance[neighbor.no] > neighbor.weight) {
                    distance[neighbor.no] = neighbor.weight;
                    pq.offer(new Node(neighbor.no, distance[neighbor.no], null));
                }
            }
        }

        System.out.println(totalWeight);
    }
}

/*
3 3
1 2 1
2 3 2
1 3 3

3
*/