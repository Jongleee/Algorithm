package com.example.algorithm.java.searchBroadFirstSearch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ExploreCave {
    @SuppressWarnings("Unchecked")
    public boolean solution(int n, int[][] path, int[][] order) {
        List<List<Integer>> graph = buildGraph(n, path);
        return canSearchAllRooms(n, graph, order);
    }

    private boolean canSearchAllRooms(int n, List<List<Integer>> graph, int[][] order) {
        int[] before = new int[n];
        int[] after = new int[n];

        for (int[] arr : order) {
            before[arr[1]] = arr[0];
            after[arr[0]] = arr[1];
        }

        int numOfRoomsVisited = 0;
        int[] visited = new int[n];
        Queue<Integer> q = new LinkedList<>();

        if (before[0] == 0) {
            q.offer(0);
            visited[0] = 2;
        }

        while (!q.isEmpty()) {
            int curNode = q.poll();
            numOfRoomsVisited++;

            for (int nextNode : graph.get(curNode)) {
                if (visited[nextNode] == 2) {
                    continue;
                }

                if (visited[before[nextNode]] != 2) {
                    visited[nextNode] = 1;
                    continue;
                }

                q.offer(nextNode);
                visited[nextNode] = 2;
            }

            int saveNode = after[curNode];

            if (saveNode != 0 && visited[saveNode] == 1) {
                q.offer(saveNode);
                visited[saveNode] = 2;
            }
        }

        return numOfRoomsVisited == n;
    }

    private List<List<Integer>> buildGraph(int n, int[][] path) {
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] arr : path) {
            graph.get(arr[0]).add(arr[1]);
            graph.get(arr[1]).add(arr[0]);
        }

        return graph;
    }

    // @Test
    // public void 정답() {
    //     int n1 = 9;
    //     int[][] p1 = { { 0, 1 }, { 0, 3 }, { 0, 7 }, { 8, 1 }, { 3, 6 }, { 1, 2 }, { 4, 7 }, { 7, 5 } };
    //     int[][] o1 = { { 8, 5 }, { 6, 7 }, { 4, 1 } };
    //     int n2 = 9;
    //     int[][] p2 = { { 8, 1 }, { 0, 1 }, { 1, 2 }, { 0, 7 }, { 4, 7 }, { 0, 3 }, { 7, 5 }, { 3, 6 } };
    //     int[][] o2 = { { 4, 1 }, { 5, 2 } };
    //     int n3 = 9;
    //     int[][] p3 = { { 0, 1 }, { 0, 3 }, { 0, 7 }, { 8, 1 }, { 3, 6 }, { 1, 2 }, { 4, 7 }, { 7, 5 } };
    //     int[][] o3 = { { 4, 1 }, { 8, 7 }, { 6, 5 } };
    //     Assertions.assertEquals(true, solution(n1, p1, o1));
    //     Assertions.assertEquals(true, solution(n2, p2, o2));
    //     Assertions.assertEquals(false, solution(n3, p3, o3));
    // }
}