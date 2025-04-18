package com.example.algorithm.java.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class FindParentInTree {
    private static class Node {
        int num;
        Node next;

        Node(int num, Node next) {
            this.num = num;
            this.next = next;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int nodeCount = Integer.parseInt(br.readLine());
        Node[] nodes = buildGraph(br, nodeCount);

        int[] parent = findParents(nodes, nodeCount);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < nodeCount; i++) {
            sb.append(parent[i]).append('\n');
        }

        System.out.print(sb);
    }

    private static Node[] buildGraph(BufferedReader br, int nodeCount) throws IOException {
        Node[] nodes = new Node[nodeCount];
        for (int i = 1; i < nodeCount; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()) - 1;
            int to = Integer.parseInt(st.nextToken()) - 1;

            nodes[from] = new Node(to, nodes[from]);
            nodes[to] = new Node(from, nodes[to]);
        }
        return nodes;
    }

    private static int[] findParents(Node[] nodes, int nodeCount) {
        int[] parent = new int[nodeCount];
        boolean[] visited = new boolean[nodeCount];
        Queue<Integer> queue = new ArrayDeque<>();

        Node current = nodes[0];
        visited[0] = true;

        while (current != null) {
            int neighbor = current.num;
            parent[neighbor] = 1;
            visited[neighbor] = true;
            queue.add(neighbor);
            current = current.next;
        }

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            Node neighborNode = nodes[currentNode];

            while (neighborNode != null) {
                int neighbor = neighborNode.num;
                if (!visited[neighbor]) {
                    parent[neighbor] = currentNode + 1;
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
                neighborNode = neighborNode.next;
            }
        }

        return parent;
    }
}

/*
7
1 6
6 3
3 5
4 1
2 4
4 7

4
6
1
3
1
4


12
1 2
1 3
2 4
3 5
3 6
4 7
4 8
5 9
5 10
6 11
6 12

1
1
2
3
3
4
4
5
5
6
6
*/