package com.example.algorithm.java.prim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CreateConstellation {
    private static class Node {
        double x;
        double y;

        public Node(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int vertices = Integer.parseInt(br.readLine());

        Node[] nodes = new Node[vertices];

        for (int i = 0; i < vertices; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());
            nodes[i] = new Node(x, y);
        }

        System.out.printf("%.2f", calculateMSTCost(vertices, nodes));
    }

    private static double calculateMSTCost(int vertices, Node[] nodes) {
        boolean[] visited = new boolean[vertices];
        double[] minEdges = new double[vertices];
        double totalCost = 0;

        Arrays.fill(minEdges, Double.MAX_VALUE);
        minEdges[0] = 0;

        for (int i = 0; i < vertices; i++) {
            double minDistance = Double.MAX_VALUE;
            int minVertex = -1;

            for (int j = 0; j < vertices; j++) {
                if (!visited[j] && minEdges[j] < minDistance) {
                    minDistance = minEdges[j];
                    minVertex = j;
                }
            }

            if (minVertex == -1)
                return -1;
            visited[minVertex] = true;
            totalCost += minDistance;

            for (int j = 0; j < vertices; j++) {
                if (!visited[j]) {
                    double distance = calculateDistance(nodes[minVertex], nodes[j]);
                    if (minEdges[j] > distance) {
                        minEdges[j] = distance;
                    }
                }
            }
        }

        return totalCost;
    }

    private static double calculateDistance(Node node1, Node node2) {
        return Math.sqrt(Math.pow(node1.x - node2.x, 2) + Math.pow(node1.y - node2.y, 2));
    }
}

/*
3
1.0 1.0
2.0 2.0
2.0 4.0

3.41
*/