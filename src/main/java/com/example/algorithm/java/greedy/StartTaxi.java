package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class StartTaxi {
    static final int[] DX = { 0, 0, 1, -1 };
    static final int[] DY = { 1, -1, 0, 0 };
    static int n, m, k;
    static int[][] map;

    static class Node {
        int y, x, distance, destY, destX;

        Node(int y, int x, int distance, int destY, int destX) {
            this.y = y;
            this.x = x;
            this.distance = distance;
            this.destY = destY;
            this.destX = destX;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        map = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        int startY = Integer.parseInt(st.nextToken());
        int startX = Integer.parseInt(st.nextToken());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int destY = Integer.parseInt(st.nextToken());
            int destX = Integer.parseInt(st.nextToken());
            map[y][x] = -((destY - 1) * n + destX - 1) - 1;
        }

        if (!simulate(startY, startX)) {
            System.out.println(-1);
        } else {
            System.out.println(k);
        }
    }

    static boolean simulate(int startY, int startX) {
        while (m > 0) {
            Node nextPassenger = findClosestPassenger(startY, startX);
            if (nextPassenger == null || !moveToDestination(nextPassenger)) {
                return false;
            }
            startY = nextPassenger.destY;
            startX = nextPassenger.destX;
        }
        return true;
    }

    static Node findClosestPassenger(int startY, int startX) {
        Queue<Node> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[n + 1][n + 1];
        queue.add(new Node(startY, startX, 0, 0, 0));
        visited[startY][startX] = true;

        Node closest = null;
        int minDistance = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            if (map[node.y][node.x] < 0) {
                if (node.distance < minDistance ||
                        (node.distance == minDistance && (closest == null || isCloser(node, closest)))) {
                    closest = node;
                    minDistance = node.distance;
                }
                continue;
            }

            for (int d = 0; d < 4; d++) {
                int ny = node.y + DY[d];
                int nx = node.x + DX[d];
                if (isValidPosition(ny, nx, visited)) {
                    queue.add(new Node(ny, nx, node.distance + 1, 0, 0));
                    visited[ny][nx] = true;
                }
            }
        }

        if (closest != null) {
            closest.destY = (map[closest.y][closest.x] * -1 - 1) / n + 1;
            closest.destX = (map[closest.y][closest.x] * -1 - 1) % n + 1;
            map[closest.y][closest.x] = 0;
        }
        return closest;
    }

    static boolean moveToDestination(Node passenger) {
        int fuelNeeded = passenger.distance;
        if (k < fuelNeeded) {
            return false;
        }
        k -= fuelNeeded;

        int fuelRefund = move(passenger.y, passenger.x, passenger.destY, passenger.destX);
        if (fuelRefund == -1 || k < fuelRefund) {
            return false;
        }
        k += fuelRefund;
        m--;
        return true;
    }

    static int move(int startY, int startX, int destY, int destX) {
        Queue<Node> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[n + 1][n + 1];
        queue.add(new Node(startY, startX, 0, 0, 0));
        visited[startY][startX] = true;

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            if (node.y == destY && node.x == destX) {
                return node.distance;
            }

            for (int d = 0; d < 4; d++) {
                int ny = node.y + DY[d];
                int nx = node.x + DX[d];
                if (isValidPosition(ny, nx, visited)) {
                    queue.add(new Node(ny, nx, node.distance + 1, 0, 0));
                    visited[ny][nx] = true;
                }
            }
        }
        return -1;
    }

    static boolean isValidPosition(int y, int x, boolean[][] visited) {
        return y >= 1 && y <= n && x >= 1 && x <= n && map[y][x] != 1 && !visited[y][x];
    }

    static boolean isCloser(Node a, Node b) {
        return a.y < b.y || (a.y == b.y && a.x < b.x);
    }
}

/*
6 3 100
0 0 1 0 0 0
0 0 1 0 0 0
0 0 0 1 0 0
0 0 0 1 0 0
0 0 0 0 1 0
0 0 0 1 0 0
6 5
2 2 5 6
5 4 1 6
4 2 3 5

-1


6 3 15
0 0 1 0 0 0
0 0 1 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 1 0
0 0 0 1 0 0
6 5
2 2 5 6
5 4 1 6
4 2 3 5

14
*/