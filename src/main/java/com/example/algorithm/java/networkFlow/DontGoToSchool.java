package com.example.algorithm.java.networkFlow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class DontGoToSchool {
    private static int n, m, start, end;
    private static List<List<Integer>> graph;
    private static char[][] board;
    private static int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new char[n][m];
        graph = new ArrayList<>();
        for (int i = 0; i < 2 * n * m; i++) {
            graph.add(new ArrayList<>());
        }

        int startX = 0, startY = 0, endX = 0, endY = 0;
        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            for (int j = 0; j < m; j++) {
                board[i][j] = line.charAt(j);
                if (board[i][j] == 'K') {
                    startX = i;
                    startY = j;
                    start = getOutNode(i, j);
                } else if (board[i][j] == 'H') {
                    endX = i;
                    endY = j;
                    end = getInNode(i, j);
                }
            }
        }

        if (Math.abs(startX - endX) + Math.abs(startY - endY) == 1) {
            System.out.println(-1);
            return;
        }

        buildGraph();
        System.out.println(maxFlow());
    }

    private static void buildGraph() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == '#') {
                    continue;
                }

                int inNode = getInNode(i, j);
                int outNode = getOutNode(i, j);
                graph.get(inNode).add(outNode);

                for (int[] d : directions) {
                    int ni = i + d[0];
                    int nj = j + d[1];
                    if (isValid(ni, nj) && board[ni][nj] != '#' && board[ni][nj] != 'K') {
                        graph.get(outNode).add(getInNode(ni, nj));
                    }
                }
            }
        }
    }

    private static int maxFlow() {
        int flow = 0;
        while (true) {
            int[] prev = new int[2 * n * m];
            Arrays.fill(prev, -1);
            Queue<Integer> queue = new ArrayDeque<>();
            queue.add(start);

            getNext(prev, queue);

            if (prev[end] == -1) {
                break;
            }

            flow++;
            getNode(prev);
        }
        return flow;
    }

    private static void getNext(int[] prev, Queue<Integer> queue) {
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int next : graph.get(cur)) {
                if (prev[next] == -1) {
                    prev[next] = cur;
                    if (next == end)
                        break;
                    queue.add(next);
                }
            }
            if (prev[end] != -1)
                break;
        }
    }

    private static void getNode(int[] prev) {
        int node = end;
        while (node != start) {
            int prevNode = prev[node];
            graph.get(prevNode).remove((Integer) node);
            graph.get(node).add(prevNode);
            node = prevNode;
        }
    }

    private static int getInNode(int i, int j) {
        return i * m + j;
    }

    private static int getOutNode(int i, int j) {
        return getInNode(i, j) + n * m;
    }

    private static boolean isValid(int i, int j) {
        return i >= 0 && i < n && j >= 0 && j < m;
    }
}

/*
2 4
.#K.
.H#.

0


4 5
K....
...##
##...
....H

1


3 4
####
#KH#
####

-1
*/