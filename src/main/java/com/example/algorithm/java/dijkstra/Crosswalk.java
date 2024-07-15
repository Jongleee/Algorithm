package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Crosswalk {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        long[] dist;
        List<Node>[] edges;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        edges = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            edges[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            edges[a].add(new Node(b, (long) i + 1));
            edges[b].add(new Node(a, (long) i + 1));
        }

        dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[1] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return (int) (o1.time - o2.time);
            }
        });
        pq.add(new Node(1, 0));

        while (!pq.isEmpty()) {
            Node now = pq.poll();
            if (dist[now.end] < now.time)
                continue;

            for (Node next : edges[now.end]) {
                long time = next.time - (now.time % m);
                if (time < 0)
                    time += m;

                long nextTime = now.time + time;
                if (nextTime < dist[next.end]) {
                    dist[next.end] = nextTime;
                    pq.add(new Node(next.end, nextTime));
                }
            }
        }

        System.out.println(dist[n]);
    }

    static class Node {
        int end;
        long time;

        Node(int end, long time) {
            this.end = end;
            this.time = time;
        }
    }
}

/*
4 5
1 2
3 4
1 3
4 1
2 3

4


8 12
3 4
5 6
7 8
2 3
1 5
4 8
1 2
6 7
2 3
7 8
1 2
6 7

18
 */