package com.example.algorithm.java.convexHull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

@SuppressWarnings("unchecked")
public class MatrixChainMultiplication3 {
    static class HArc implements Comparable<HArc> {
        int id, start, end, low;
        long product, base, numerator, denominator;

        HArc(int id, int start, int end) {
            this.id = id;
            this.start = start;
            this.end = end;
            this.low = weight[start] < weight[end] ? start : end;
            this.product = weight[start] * weight[end];
            this.base = cumulativeProduct[end] - cumulativeProduct[start] - this.product;
        }

        boolean contains(HArc b) {
            return start <= b.start && b.end <= end;
        }

        long getSupport() {
            return numerator / denominator;
        }

        @Override
        public int compareTo(HArc b) {
            return Long.compare(this.getSupport(), b.getSupport());
        }
    }

    static class Pair {
        int first, second;

        Pair(int f, int s) {
            this.first = f;
            this.second = s;
        }
    }

    static final int MAX = 1800002;
    static long[] weight = new long[MAX];
    static long[] cumulativeProduct = new long[MAX];
    static List<Pair> arcs = new ArrayList<>();
    static List<Integer>[] tree = new ArrayList[MAX];
    static List<HArc>[] connections = new ArrayList[MAX];
    static PriorityQueue<HArc>[] priorityQueues = new PriorityQueue[MAX];
    static int[] queueId = new int[MAX];
    static int[] subtreeSize = new int[MAX];
    static HArc[] hArcs = new HArc[MAX];
    static int numArcs = 0, numQueues = 0, n;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        StringTokenizer st;

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            weight[i] = Long.parseLong(st.nextToken());
            weight[i + 1] = Long.parseLong(st.nextToken());
        }
        n++;

        for (int i = 0; i < MAX; i++) {
            tree[i] = new ArrayList<>();
            connections[i] = new ArrayList<>();
            priorityQueues[i] = new PriorityQueue<>(Collections.reverseOrder());
        }

        initialize();
        System.out.println(solve());
    }

    static void initialize() {
        int v1 = 1;
        for (int i = 2; i <= n; i++) {
            if (weight[i] < weight[v1]) {
                v1 = i;
            }
        }

        long[] rotated = new long[n + 2];
        for (int i = 1; i <= n; i++) {
            rotated[i] = weight[(v1 + i - 2) % n + 1];
        }
        System.arraycopy(rotated, 1, weight, 1, n);
        weight[n + 1] = weight[1];

        collectHArcs();

        for (int i = 2; i <= n + 1; i++) {
            cumulativeProduct[i] = cumulativeProduct[i - 1] + weight[i - 1] * weight[i];
        }

        buildTree();
    }

    static void collectHArcs() {
        List<Integer> stack = new ArrayList<>();
        List<Pair> temp = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            while (stack.size() >= 2 && weight[stack.get(stack.size() - 1)] > weight[i]) {
                temp.add(new Pair(stack.get(stack.size() - 2), i));
                stack.remove(stack.size() - 1);
            }
            stack.add(i);
        }

        for (Pair arc : temp) {
            if (arc.first != 1 && arc.second != 1) {
                arcs.add(arc);
            }
        }
    }

    static void buildTree() {
        List<Integer> stack = new ArrayList<>();
        newArc(1, n + 1);

        for (Pair arc : arcs) {
            newArc(arc.first, arc.second);
            while (!stack.isEmpty() && hArcs[numArcs].contains(hArcs[stack.get(stack.size() - 1)])) {
                tree[numArcs].add(stack.remove(stack.size() - 1));
            }
            stack.add(numArcs);
        }

        while (!stack.isEmpty()) {
            tree[1].add(stack.remove(stack.size() - 1));
        }
    }

    static void newArc(int start, int end) {
        numArcs++;
        hArcs[numArcs] = new HArc(numArcs, start, end);
    }

    static long solve() {
        dfs(1);
        long result = 0;
        PriorityQueue<HArc> queue = priorityQueues[queueId[1]];
        while (!queue.isEmpty()) {
            result += queue.poll().numerator;
        }
        return result;
    }

    static void dfs(int node) {
        HArc current = hArcs[node];
        subtreeSize[node] = 1;

        if (tree[node].isEmpty()) {
            queueId[node] = ++numQueues;
            current.denominator = current.base;
            current.numerator = weight[current.low] * (current.denominator + current.product - getMultiplier(node));
            addArc(node);
            return;
        }

        current.denominator = current.base;

        for (int child : tree[node]) {
            dfs(child);
            subtreeSize[node] += subtreeSize[child];
            current.denominator -= hArcs[child].base;
        }

        current.numerator = weight[current.low] * (current.denominator + current.product - getMultiplier(node));
        mergePriorityQueue(node);

        PriorityQueue<HArc> queue = priorityQueues[queueId[node]];

        while (!queue.isEmpty() && queue.peek().getSupport() >= weight[current.low]) {
            HArc top = queue.peek();
            current.denominator += top.denominator;
            removeArc(node);
            current.numerator = weight[current.low] * (current.denominator + current.product - getMultiplier(node));
        }

        while (!queue.isEmpty() && current.compareTo(queue.peek()) <= 0) {
            HArc top = queue.peek();
            current.denominator += top.denominator;
            removeArc(node);
            current.numerator += top.numerator;
        }

        addArc(node);
    }

    static long getMultiplier(int node) {
        if (node == 1)
            return weight[1] * weight[2] + weight[1] * weight[n];
        HArc arc = hArcs[node];

        if (arc.start == arc.low) {
            if (connections[arc.start].isEmpty()
                    || !arc.contains(connections[arc.start].get(connections[arc.start].size() - 1))) {
                return weight[arc.start] * weight[arc.start + 1];
            }
            return connections[arc.start].get(connections[arc.start].size() - 1).product;
        } else {
            if (connections[arc.end].isEmpty()
                    || !arc.contains(connections[arc.end].get(connections[arc.end].size() - 1))) {
                return weight[arc.end] * weight[arc.end - 1];
            }
            return connections[arc.end].get(connections[arc.end].size() - 1).product;
        }
    }

    static void addArc(int node) {
        HArc arc = hArcs[node];
        priorityQueues[queueId[node]].add(arc);
        connections[arc.start].add(arc);
        connections[arc.end].add(arc);
    }

    static void removeArc(int node) {
        HArc top = priorityQueues[queueId[node]].peek();
        connections[top.start].remove(connections[top.start].size() - 1);
        connections[top.end].remove(connections[top.end].size() - 1);
        priorityQueues[queueId[node]].poll();
    }

    static void mergePriorityQueue(int node) {
        int maxChild = -1;

        for (int child : tree[node]) {
            if (maxChild == -1 || subtreeSize[maxChild] < subtreeSize[child]) {
                maxChild = child;
            }
        }

        queueId[node] = queueId[maxChild];
        PriorityQueue<HArc> mainQueue = priorityQueues[queueId[node]];

        for (int child : tree[node]) {
            if (child == maxChild)
                continue;
            PriorityQueue<HArc> childQueue = priorityQueues[queueId[child]];
            while (!childQueue.isEmpty()) {
                mainQueue.add(childQueue.poll());
            }
        }
    }
}

/*
3
5 3
3 2
2 6

90
*/