package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Backup {
    private static class Edge implements Comparable<Edge> {
        int length;
        boolean disabled;
        Edge left;
        Edge right;

        Edge(int length, Edge left) {
            this.length = length;
            this.left = left;
            if (left != null) {
                left.right = this;
            }
        }

        @Override
        public int compareTo(Edge other) {
            return this.length - other.length;
        }
    }

    public static void main(String[] args) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final StringTokenizer st = new StringTokenizer(br.readLine());

        final int n = Integer.parseInt(st.nextToken()) - 1;
        int k = Integer.parseInt(st.nextToken());

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        Edge sentinel = new Edge(0, null);
        Edge prevEdge = sentinel;

        int prevValue = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++) {
            int currentValue = Integer.parseInt(br.readLine());
            Edge edge = new Edge(currentValue - prevValue, prevEdge);
            pq.offer(edge);
            prevEdge = edge;
            prevValue = currentValue;
        }

        prevEdge.right = sentinel;
        sentinel.left = prevEdge;

        int result = 0;
        while (k-- > 0) {
            Edge current;
            while ((current = pq.poll()).disabled)
                ;

            result += current.length;
            Edge left = current.left;
            Edge right = current.right;

            if (left == sentinel) {
                right.disabled = true;
                right.right.left = sentinel;
            } else if (right == sentinel) {
                left.disabled = true;
                left.left.right = sentinel;
            } else {
                left.disabled = true;
                right.disabled = true;

                current.length = left.length + right.length - current.length;
                current.left = left.left;
                current.right = right.right;

                if (current.left != sentinel) {
                    current.left.right = current;
                }
                if (current.right != sentinel) {
                    current.right.left = current;
                }

                pq.offer(current);
            }
        }

        System.out.println(result);
    }
}

/*
5 2
1
3
4
6
12

4
*/