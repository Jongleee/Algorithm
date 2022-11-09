package com.example.algorithm.greedy;

import java.util.Arrays;

public class ConnectingIslands {
    int [] parent;
    public int solution(int n, int[][] costs) {
        //4, new int[][]{{0,1,1},{0,2,2},{1,2,5},{1,3,1},{2,3,8}}
        int answer = 0;
        Arrays.sort(costs, (int[] c1, int[] c2) -> c1[2] - c2[2]);
        parent = new int[n];

        Arrays.setAll(parent, i -> i);

        for (int i = 0; i < costs.length; i++) {
            int startParent = findParent(costs[i][0]);
            int endParent = findParent(costs[i][1]);
            if(startParent != endParent) {
                answer += costs[i][2];
                parent[endParent]=startParent;
            }
        }
        return answer;
    }

    public int findParent( int node) {
        if (parent[node] == node) return node;
        return findParent(parent[node]);
    }
}
