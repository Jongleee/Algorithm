package com.example.algorithm.java.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class Drop123 {
    public int[] solution(int[][] edges, int[] target) {
        int n = edges.length + 1;
        int numCases = 0;

        List<Integer>[] tree = createTree(edges, n);

        int[] pass = new int[n];
        int[] cnt = new int[n];
        boolean[] visited = new boolean[n];
        List<Integer> leafNodes = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (tree[i].isEmpty() && target[i] > 0) {
                numCases++;
            }
        }

        while (numCases > 0) {
            int node = 0;

            while (!tree[node].isEmpty()) {
                int childIndex = pass[node] % tree[node].size();
                int child = tree[node].get(childIndex);
                pass[node]++;
                node = child;
            }

            cnt[node]++;

            leafNodes.add(node);

            if (cnt[node] > target[node]) {
                return new int[] { -1 };
            }

            if (!visited[node] && target[node] <= 3 * cnt[node]) {
                visited[node] = true;
                numCases--;
            }
        }

        List<Integer> result = new ArrayList<>();
        makeResult(target, cnt, leafNodes, result);

        int[] answer = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            answer[i] = result.get(i);
        }
        return answer;
    }

    private List<Integer>[] createTree(int[][] edges, int n) {
        List<Integer>[] tree = new List[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int parent = edge[0] - 1;
            int child = edge[1] - 1;
            tree[parent].add(child);
        }
        for (List<Integer> children : tree) {
            Collections.sort(children);
        }
        return tree;
    }

    private void makeResult(int[] target, int[] cnt, List<Integer> leafNodes, List<Integer> result) {
        for (int node : leafNodes) {
            cnt[node]--;
            for (int val = 1; val <= 3; val++) {
                if (cnt[node] <= target[node] - val && target[node] - val <= 3 * cnt[node]) {
                    result.add(val);
                    target[node] -= val;
                    break;
                }
            }
        }
    }
}
