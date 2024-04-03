package com.example.algorithm.java.tree;

import java.util.ArrayList;
import java.util.List;

public class DevideTestSite {
    static class Node {
        int data;
        int left, right;

        public Node(int data, int left, int right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    static int INF = 654321, MAX_SECTION = 10001;
    static List<Node>[] list;
    static int[][] cost;

    @SuppressWarnings("unchecked")
    public int solution(int k, int[] num, int[][] links) {
        int size = num.length;
        list = new ArrayList[size];
        for (int i = 0; i < size; i++) {
            list[i] = new ArrayList<>();
        }
        int sum = 0;
        boolean[] check = new boolean[size];
        for (int i = 0; i < size; i++) {
            int left = links[i][0];
            int right = links[i][1];
            if (left != -1)
                check[left] = true;
            if (right != -1)
                check[right] = true;
            list[i].add(new Node(num[i], left, right));
            sum += num[i];
        }

        int root = -1;
        for (int i = 0; i < size; i++) {
            if (!check[i]) {
                root = i;
                break;
            }
        }

        int left = sum / k;
        int right = sum;
        if (left == right)
            return right;
        while (left < right) {
            int mid = (left + right) / 2;
            cost = new int[size][2];
            if (checkSection(root, mid, k)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }

    static boolean checkSection(int pos, int w, int k) {
        Node curNode = list[pos].get(0);
        int data = curNode.data;
        int l = curNode.left;
        int r = curNode.right;

        if (l != -1 && !checkSection(l, w, k))
            return false;
        if (r != -1 && !checkSection(r, w, k))
            return false;

        if (l == -1 && r == -1) {
            if (data <= w) {
                cost[pos][0] = 1;
                cost[pos][1] = data;
            } else {
                cost[pos][0] = MAX_SECTION;
                cost[pos][1] = INF;
            }
        } else if (l != -1 && r != -1) {
            int sum = data + cost[l][1] + cost[r][1];
            if (sum <= w) {
                cost[pos][0] = cost[l][0] + cost[r][0] - 1;
                cost[pos][1] = sum;
            } else if (data + Math.min(cost[l][1], cost[r][1]) <= w) {
                cost[pos][0] = cost[l][0] + cost[r][0];
                cost[pos][1] = data + Math.min(cost[l][1], cost[r][1]);
            } else if (data <= w) {
                cost[pos][0] = cost[l][0] + cost[r][0] + 1;
                cost[pos][1] = data;
            } else {
                cost[pos][0] = MAX_SECTION;
                cost[pos][1] = INF;
            }
        } else {
            int child = (l == -1) ? r : l;
            int childCost = (l == -1) ? cost[r][1] : cost[l][1];

            if (data + childCost <= w) {
                cost[pos][0] = cost[child][0];
                cost[pos][1] = data + childCost;
            } else if (data <= w) {
                cost[pos][0] = cost[child][0] + 1;
                cost[pos][1] = data;
            } else {
                cost[pos][0] = MAX_SECTION;
                cost[pos][1] = INF;
            }
        }
        return cost[pos][0] <= k;
    }

    // @Test
    // void 정답() {
    //     int[] k = { 3, 1, 2, 4 };
    //     int[][] num = { { 12, 30, 1, 8, 8, 6, 20, 7, 5, 10, 4, 1 }, { 6, 9, 7, 5 }, { 6, 9, 7, 5 }, { 6, 9, 7, 5 } };
    //     int[][][] links = {
    //             { { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { 8, 5 }, { 2, 10 }, { 3, 0 }, { 6, 1 }, { 11, -1 },
    //                     { 7, 4 }, { -1, -1 }, { -1, -1 } },
    //             { { -1, -1 }, { -1, -1 }, { -1, 0 }, { 2, 1 } }, { { -1, -1 }, { -1, -1 }, { -1, 0 }, { 2, 1 } },
    //             { { -1, -1 }, { -1, -1 }, { -1, 0 }, { 2, 1 } } };
    //     int[] result = { 40, 27, 14, 9 };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertEquals(result[i], solution(k[i], num[i], links[i]));
    //     }
    // }
}
