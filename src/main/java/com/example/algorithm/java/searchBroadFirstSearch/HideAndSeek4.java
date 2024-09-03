package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.StringTokenizer;

public class HideAndSeek4 {
    static class Position {
        int prev;

        public Position(int prev) {
            this.prev = prev;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        StringBuilder sb = new StringBuilder();

        if (n >= k) {
            sb.append(n - k).append('\n');
            for (int i = n; i >= k; i--) {
                sb.append(i).append(' ');
            }
            System.out.print(sb);
            return;
        }

        Position[] positions = new Position[100001];
        positions[n] = new Position(-1);

        bfs(positions, n, k);
        buildPath(positions, n, k, sb);

        System.out.println(sb);
    }

    private static void bfs(Position[] positions, int start, int target) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            if (current == target)
                return;

            checkAndAdd(queue, positions, current, current - 1);
            checkAndAdd(queue, positions, current, current + 1);
            checkAndAdd(queue, positions, current, current * 2);
        }
    }

    private static void checkAndAdd(Queue<Integer> queue, Position[] positions, int current, int next) {
        if (next >= 0 && next < 100001 && positions[next] == null) {
            positions[next] = new Position(current);
            queue.add(next);
        }
    }

    private static void buildPath(Position[] positions, int start, int target, StringBuilder sb) {
        Deque<Integer> path = new ArrayDeque<>();
        int steps = 0;

        while (target != start) {
            path.addFirst(target);
            target = positions[target].prev;
            steps++;
        }

        sb.append(steps).append('\n');
        sb.append(start).append(' ');

        for (int pos : path) {
            sb.append(pos).append(' ');
        }
    }
}

/*
5 17

4
5 4 8 16 17
*/