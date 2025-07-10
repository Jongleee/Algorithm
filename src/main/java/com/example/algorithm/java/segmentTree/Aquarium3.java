package com.example.algorithm.java.segmentTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Aquarium3 {
    private static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static ArrayList<Integer> positions, heights;
    private static int[] segmentTree;
    private static ArrayList<Long> prefixSum;
    private static ArrayList<TreeMap<Integer, Integer>> leftSide, rightSide;
    private static long answer;
    private static int heightCount;
    private static Node root;

    private static class Node implements Comparable<Node> {
        long maxValue;
        PriorityQueue<Node> subNodes = new PriorityQueue<>(2);

        @Override
        public int compareTo(Node other) {
            return Long.compare(other.maxValue, this.maxValue);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int totalPoints = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int prevX = Integer.parseInt(st.nextToken());
        int prevY = Integer.parseInt(st.nextToken());

        int direction = -1;
        long cumulativeSum = 0;
        int index = 0;

        positions = new ArrayList<>();
        heights = new ArrayList<>();
        prefixSum = new ArrayList<>();
        leftSide = new ArrayList<>();
        rightSide = new ArrayList<>();

        TreeMap<Integer, Integer> tempMap = new TreeMap<>();
        TreeMap<Integer, Integer> blankMap = new TreeMap<>();

        leftSide.add(blankMap);
        prefixSum.add(0L);
        positions.add(0);
        heights.add(0);

        for (int i = 1; i < totalPoints; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            if (prevY == y) {
                cumulativeSum += (long) y * (x - prevX);
                prefixSum.add(cumulativeSum);
                positions.add(x);
                prevX = x;
                index++;
                continue;
            }

            if (y > prevY) {
                if (direction > 0) {
                    leftSide.add(tempMap);
                    tempMap = new TreeMap<>();
                    heights.add(prevY);
                    direction = -1;
                }
                tempMap.put(y, index);
            } else {
                if (direction < 0) {
                    rightSide.add(tempMap);
                    tempMap = new TreeMap<>();
                    direction = 1;
                }
                tempMap.put(prevY, index);
            }

            prevY = y;
        }

        heights.add(0);
        heightCount = heights.size() - 1;
        rightSide.add(blankMap);
        leftSide.add(tempMap);

        segmentTree = new int[1 << 18];
        buildSegmentTree();

        root = new Node();
        computeArea(0, heightCount, root);

        int k = Integer.parseInt(br.readLine());
        for (int i = 0; i < k; i++) {
            process(root);
        }

        bw.write(Long.toString(answer));
        bw.newLine();
        bw.flush();
        bw.close();
    }

    private static long computeArea(int left, int right, Node node) {
        long innerArea = 0;

        if (left + 1 < right) {
            int mid = queryMinHeightIndex(left + 1, right - 1);
            Node leftNode = new Node();
            Node rightNode = new Node();
            innerArea += computeArea(left, mid, leftNode);
            innerArea += computeArea(mid, right, rightNode);
            node.subNodes.add(leftNode);
            node.subNodes.add(rightNode);
        }

        int currentHeight = Math.max(heights.get(left), heights.get(right));
        int leftIdx = rightSide.get(left).ceilingEntry(currentHeight).getValue();
        int rightIdx = leftSide.get(right).ceilingEntry(currentHeight).getValue();

        long outerArea = prefixSum.get(rightIdx) - prefixSum.get(leftIdx);
        outerArea -= (long) currentHeight * (positions.get(rightIdx) - positions.get(leftIdx));

        long baseArea = node.subNodes.isEmpty() ? 0 : node.subNodes.peek().maxValue;
        node.maxValue = baseArea + outerArea - innerArea;

        return outerArea;
    }

    private static void process(Node current) {
        if (!current.subNodes.isEmpty()) {
            Node top = current.subNodes.poll();
            process(top);
        }

        if (current == root) {
            answer += current.maxValue;
            current.maxValue = current.subNodes.isEmpty() ? 0 : current.subNodes.peek().maxValue;
        } else {
            if (!current.subNodes.isEmpty()) {
                root.subNodes.add(current.subNodes.poll());
            }
        }
    }

    private static void buildSegmentTree() {
        build(0, heightCount - 1, 1);
    }

    private static void build(int start, int end, int node) {
        if (start == end) {
            segmentTree[node] = start;
            return;
        }

        int mid = (start + end) >> 1;
        build(start, mid, node << 1);
        build(mid + 1, end, (node << 1) | 1);

        int leftIdx = segmentTree[node << 1];
        int rightIdx = segmentTree[(node << 1) | 1];
        segmentTree[node] = heights.get(leftIdx) < heights.get(rightIdx) ? leftIdx : rightIdx;
    }

    private static int queryMinHeightIndex(int left, int right) {
        return query(0, heightCount - 1, 1, left, right);
    }

    private static int query(int start, int end, int node, int left, int right) {
        if (start == left && end == right) {
            return segmentTree[node];
        }

        int mid = (start + end) >> 1;
        if (right <= mid) {
            return query(start, mid, node << 1, left, right);
        } else if (left > mid) {
            return query(mid + 1, end, (node << 1) | 1, left, right);
        } else {
            int lRes = query(start, mid, node << 1, left, mid);
            int rRes = query(mid + 1, end, (node << 1) | 1, mid + 1, right);
            return heights.get(lRes) < heights.get(rRes) ? lRes : rRes;
        }
    }
}

/*
14
0 0
0 5
1 5
1 3
2 3
2 4
3 4
3 2
5 2
5 4
6 4
6 3
8 3
8 0
2

25


4
0 0
0 5
8 5
8 0
1

40
*/