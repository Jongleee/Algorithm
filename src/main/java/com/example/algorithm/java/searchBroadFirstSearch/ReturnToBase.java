package com.example.algorithm.java.searchBroadFirstSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ReturnToBase {
    int[] distance;
    ArrayList<?>[] route;
    int limit=1000000;

    public int[] solution(int n, int[][] roads, int[] sources, int destination) {
        //Arrays.toString(method.solution(5, new int[][]{{1, 2}, {1, 4}, {2, 4}, {2, 5}, {4, 5}}, new int[]{1, 3, 5}, 5))
        distance = new int[n + 1];
        route = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            route[i] = new ArrayList<>();
        }
        for (int i = 0; i < roads.length; i++) {
            route[roads[i][0]].add(roads[i][1], null);
            route[roads[i][1]].add(roads[i][0], null);
        }
        Arrays.fill(distance,limit);
        bfs(destination);
        int[] answer = new int[sources.length];
        for (int i = 0; i < sources.length; i++) {
            answer[i] = (distance[sources[i]] < limit) ? distance[sources[i]] : -1;
        }
        return answer;
    }

    public void bfs(int destination) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(destination);
        distance[destination]=0;

        while (!queue.isEmpty()) {
            int start = queue.poll();

            for (int i = 0; i < route[start].size(); i++) {
                int next = (int) route[start].get(i);
                if(distance[next]>distance[start]+1){
                    distance[next]=distance[start]+1;
                    queue.add(next);
                }
            }
        }
    }
}
