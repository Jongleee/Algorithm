package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class FindMinimumCost2 {
    private static class Node implements Comparable<Node> {
        int num;
        int cost;
        Node next;

        Node(int num, int cost) {
            this.num = num;
            this.cost = cost;
        }

        Node(int num, int cost, Node next) {
            this.num = num;
            this.cost = cost;
            this.next = next;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    private static class Path {
        int cost;
        int citySize;
        String string;

        Path(int cityNum) {
            this.cost = Integer.MAX_VALUE;
            this.citySize = 1;
            this.string = cityNum + " ";
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int citySize = Integer.parseInt(br.readLine().trim());
        int busSize = Integer.parseInt(br.readLine().trim());

        Node[] graph = new Node[citySize + 1];
        for (int i = 0; i < busSize; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph[from] = new Node(to, cost, graph[from]);
        }

        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        int src = Integer.parseInt(st.nextToken());
        int dest = Integer.parseInt(st.nextToken());

        Path[] costs = new Path[citySize + 1];
        for (int i = 0; i <= citySize; i++) {
            costs[i] = new Path(i);
        }
        costs[src].cost = 0;

        findMinDistance(graph, costs, src, citySize);

        StringBuilder output = new StringBuilder();
        output.append(costs[dest].cost).append("\n");
        output.append(costs[dest].citySize).append("\n");
        output.append(costs[dest].string);
        System.out.println(output);
    }

    private static void findMinDistance(Node[] graph, Path[] costs, int src, int citySize) {
        boolean[] visited = new boolean[citySize + 1];
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(src, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (visited[current.num])
                continue;
            visited[current.num] = true;

            Node next = graph[current.num];
            while (next != null) {
                int newCost = costs[current.num].cost + next.cost;
                if (newCost < costs[next.num].cost) {
                    costs[next.num].cost = newCost;
                    costs[next.num].citySize = costs[current.num].citySize + 1;
                    costs[next.num].string = costs[current.num].string + next.num + " ";
                    pq.add(new Node(next.num, newCost));
                }
                next = next.next;
            }
        }
    }
}

/*
5
8
1 2 2
1 3 3
1 4 1
1 5 10
2 4 2
3 4 1
3 5 1
4 5 3
1 5

4
3
1 4 5
*/