package com.example.algorithm.java.searchDFS;

import java.util.ArrayList;
import java.util.List;

public class SheepAndWolf {
    List<List<Integer>> graph = new ArrayList<>();

    public int dfs(int sheep, int wolf, int curNode, List<Integer> nextNodes, int[] info) {
        if (info[curNode] == 0)
            sheep++;
        else
            wolf++;

        int ans = sheep;
        if (sheep <= wolf)
            return ans;

        for (int i = 0; i < nextNodes.size(); i++) {
            int nextNode = nextNodes.get(i);
            List<Integer> stackNodes = new ArrayList<>(nextNodes);
            stackNodes.remove((Integer) nextNode);
            stackNodes.addAll(graph.get(nextNode));
            ans = Math.max(ans, dfs(sheep, wolf, nextNode, stackNodes, info));
        }

        return ans;
    }

    public int solution(int[] info, int[][] edges) {
        int nodeLength = info.length;
        for (int i = 0; i < nodeLength; i++) {
            graph.add(new ArrayList<>());
        }

        int edgeLength = edges.length;
        for (int i = 0; i < edgeLength; i++) {
            graph.get(edges[i][0]).add(edges[i][1]);
        }

        List<Integer> nextNodes = new ArrayList<>(graph.get(0));
        return dfs(0, 0, 0, nextNodes, info);
    }

    // @Test
    // void 정답() {
    //     int[] info = { 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1 };
    //     int[][] edges = { { 0, 1 }, { 1, 2 }, { 1, 4 }, { 0, 8 }, { 8, 7 }, { 9, 10 }, { 9, 11 }, { 4, 3 }, { 6, 5 },
    //             { 4, 6 }, { 8, 9 } };

    //     Assertions.assertEquals(5, solution(info, edges));
    // }
}
