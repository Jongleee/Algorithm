package com.example.algorithm.java.graph;

import java.util.HashMap;
import java.util.Map;

public class DonutStickGraph {
    public int[] solution(int[][] edges) {
        int[] answer = new int[4];
        Map<Integer, int[]> nodeMap = initializeNodeMap(edges);
        int maxNode = getMaxNode(edges);

        for (int node = 1; node <= maxNode; node++) {
            if (nodeMap.containsKey(node)) {
                int[] degrees = nodeMap.get(node);
                if (degrees[0] >= 2 && degrees[1] == 0)
                    answer[0] = node;
                else if (degrees[0] == 0)
                    answer[2]++;
                else if (degrees[0] >= 2 && degrees[1] >= 2)
                    answer[3]++;
            }
        }

        answer[1] = nodeMap.get(answer[0])[0] - (answer[2] + answer[3]);

        return answer;
    }

    private Map<Integer, int[]> initializeNodeMap(int[][] edges) {
        Map<Integer, int[]> nodeMap = new HashMap<>();
        for (int[] edge : edges) {
            int from = edge[0], to = edge[1];
            nodeMap.putIfAbsent(from, new int[] { 0, 0 });
            nodeMap.putIfAbsent(to, new int[] { 0, 0 });

            nodeMap.get(from)[0]++;
            nodeMap.get(to)[1]++;
        }
        return nodeMap;
    }

    private int getMaxNode(int[][] edges) {
        int maxNode = -1;
        for (int[] edge : edges) {
            maxNode = Math.max(maxNode, Math.max(edge[0], edge[1]));
        }
        return maxNode;
    }

    // @Test
    // void 정답() {
    //     int[][][] edges = { { { 2, 3 }, { 4, 3 }, { 1, 1 }, { 2, 1 } },
    //             { { 4, 11 }, { 1, 12 }, { 8, 3 }, { 12, 7 }, { 4, 2 }, { 7, 11 }, { 4, 8 }, { 9, 6 }, { 10, 11 },
    //                     { 6, 10 }, { 3, 5 }, { 11, 1 }, { 5, 3 }, { 11, 9 }, { 3, 8 } } };
    //     int[][] result = { { 2, 1, 1, 0 }, { 4, 0, 1, 2 } };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertArrayEquals(result[i], solution(edges[i]));
    //     }
    // }
}
