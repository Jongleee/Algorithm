package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class TouchTheSky {
    static class Node implements Comparable<Node> {
        long l;
        long d;
        long limit;

        Node(long l, long d) {
            this.l = l;
            this.d = d;
            this.limit = l + d;
        }

        @Override
        public int compareTo(Node o) {
            return Long.compare(this.limit, o.limit);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        PriorityQueue<Node> balloons = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long l = Long.parseLong(st.nextToken());
            long d = Long.parseLong(st.nextToken());
            balloons.offer(new Node(l, d));
        }

        PriorityQueue<Long> used = new PriorityQueue<>((a, b) -> Long.compare(b, a));
        int count = 0;
        long totalHeight = 0;

        while (!balloons.isEmpty()) {
            Node current = balloons.poll();
            if (totalHeight <= current.l) {
                count++;
                totalHeight += current.d;
                used.offer(current.d);
            } else {
                long maxD = used.poll();
                long minD = Math.min(maxD, current.d);
                used.offer(minD);
                totalHeight += (minD - maxD);
            }
        }

        System.out.println(count);
    }
}

/*
3
1 4
1 5
9 2

2


4
0 1
0 2
0 3
0 4

1
*/