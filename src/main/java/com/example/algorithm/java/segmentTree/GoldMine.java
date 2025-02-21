package com.example.algorithm.java.segmentTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class GoldMine {
    private static class Mine {
        int x, y, idx;
        long num;
        Mine next;

        Mine(int x, int y, long num) {
            this.x = x;
            this.y = y;
            this.num = num;
        }

        Mine add(Mine mine) {
            this.next = mine;
            return this;
        }
    }

    private static class Node {
        long sum, max, lMax, rMax;

        void fill(long num) {
            sum = num;
            max = num;
            lMax = num;
            rMax = num;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Mine[] mines = new Mine[n];
        int[] xArr = new int[n], yArr = new int[n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            xArr[i] = Integer.parseInt(st.nextToken());
            yArr[i] = Integer.parseInt(st.nextToken());
            mines[i] = new Mine(xArr[i], yArr[i], Long.parseLong(st.nextToken()));
        }

        int[] xUnique = compressCoordinates(xArr);
        int[] yUnique = compressCoordinates(yArr);
        int xLen = xUnique.length, yLen = yUnique.length;

        HashMap<Integer, Integer> xMap = createIndexMap(xUnique);
        HashMap<Integer, Integer> yMap = createIndexMap(yUnique);

        Mine[] mineList = new Mine[yLen];
        for (Mine mine : mines) {
            mine.idx = xMap.get(mine.x);
            int yIdx = yMap.get(mine.y);
            mineList[yIdx] = mine.add(mineList[yIdx]);
        }

        Node[] tree = new Node[4 * xLen];
        buildTree(tree, 1, 0, xLen - 1);
        long max = findMaxGold(tree, mineList, xLen, yLen);

        System.out.print(max);
    }

    private static int[] compressCoordinates(int[] arr) {
        return Arrays.stream(arr).distinct().sorted().toArray();
    }

    private static HashMap<Integer, Integer> createIndexMap(int[] uniqueArr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < uniqueArr.length; i++) {
            map.put(uniqueArr[i], i);
        }
        return map;
    }

    private static void buildTree(Node[] tree, int node, int start, int end) {
        tree[node] = new Node();
        if (start == end)
            return;
        int mid = (start + end) >> 1;
        buildTree(tree, node << 1, start, mid);
        buildTree(tree, node << 1 | 1, mid + 1, end);
    }

    private static void updateTree(Node[] tree, int node, int start, int end, int idx, long num) {
        if (start == end) {
            tree[node].fill(tree[node].sum + num);
            return;
        }
        int mid = (start + end) >> 1;
        if (idx <= mid)
            updateTree(tree, node << 1, start, mid, idx, num);
        else
            updateTree(tree, node << 1 | 1, mid + 1, end, idx, num);
        mergeNodes(tree[node << 1], tree[node << 1 | 1], tree[node]);
    }

    private static void mergeNodes(Node left, Node right, Node dest) {
        dest.sum = left.sum + right.sum;
        dest.max = Math.max(Math.max(left.max, right.max), left.rMax + right.lMax);
        dest.lMax = Math.max(left.lMax, left.sum + right.lMax);
        dest.rMax = Math.max(right.rMax, right.sum + left.rMax);
    }

    private static long findMaxGold(Node[] tree, Mine[] mineList, int xLen, int yLen) {
        long max = 0;
        for (int i = 0; i < yLen; i++) {
            resetTree(tree, 1, 0, xLen - 1);
            for (int j = i; j < yLen; j++) {
                for (Mine curr = mineList[j]; curr != null; curr = curr.next) {
                    updateTree(tree, 1, 0, xLen - 1, curr.idx, curr.num);
                }
                max = Math.max(max, tree[1].max);
            }
        }
        return max;
    }

    private static void resetTree(Node[] tree, int node, int start, int end) {
        tree[node].fill(0L);
        if (start == end)
            return;
        int mid = (start + end) >> 1;
        resetTree(tree, node << 1, start, mid);
        resetTree(tree, node << 1 | 1, mid + 1, end);
    }
}

/*
10
4 9 2  
6 10 -1
6 8 3
5 6 5
8 5 10
7 6 -7
9 10 4
20 1 1
10 8 6
10 6 -5

18


7
2 8 2
5 5 3
3 3 -1
10 2 5
9 7 -2
6 7 -1
7 3 -1

7
*/