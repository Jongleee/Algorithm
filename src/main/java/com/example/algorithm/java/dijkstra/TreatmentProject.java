package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class TreatmentProject {
    static class SegmentTree {
        List<Deque<int[]>> tree;
        int size;

        SegmentTree(int n) {
            size = 1;
            while (size < n)
                size <<= 1;
            tree = new ArrayList<>(size << 1);
            for (int i = 0; i < (size << 1); i++) {
                tree.add(new ArrayDeque<>());
            }
        }

        void update(int x, int[] val) {
            int node = x + size;
            tree.get(node).addLast(val);
            while ((node >>= 1) > 0) {
                tree.get(node).addLast(val);
            }
        }

        List<Integer> get(int x, int y) {
            List<Integer> result = new ArrayList<>();
            for (int l = size, r = x + size; l <= r; l >>= 1, r >>= 1) {
                if ((r & 1) == 0) {
                    while (!tree.get(r).isEmpty() && tree.get(r).getLast()[0] <= y) {
                        result.add(tree.get(r).removeLast()[1]);
                    }
                    r--;
                }
            }
            return result;
        }
    }

    static class Line {
        int x, y, nx, ny, cost;
        long answer = Long.MAX_VALUE;
        boolean isStart = false;
        boolean isEnd = false;
    }

    public static void main(String[] args) throws IOException {
        final long INF = Long.MAX_VALUE;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<Line> lines = new ArrayList<>();
        List<Integer> compressedX = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            Line line = new Line();
            line.x = l - t;
            line.y = l + t;
            line.nx = r + 1 - t;
            line.ny = r + 1 + t;
            line.cost = c;

            if (l == 1) {
                line.isStart = true;
                line.answer = c;
            }
            if (r == n) {
                line.isEnd = true;
            }

            compressedX.add(line.x);
            compressedX.add(line.nx);
            lines.add(line);
        }

        compressedX.sort(Comparator.naturalOrder());
        Map<Integer, Integer> coordMap = new HashMap<>();
        int idx = 0;
        for (int x : compressedX) {
            if (!coordMap.containsKey(x)) {
                coordMap.put(x, idx++);
            }
        }

        lines.sort((a, b) -> Integer.compare(b.y, a.y));

        SegmentTree segmentTree = new SegmentTree(coordMap.size());
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));

        for (int i = 0; i < m; i++) {
            Line line = lines.get(i);
            line.x = coordMap.get(line.x);
            line.nx = coordMap.get(line.nx);
            if (line.isStart) {
                pq.offer(new long[] { line.answer, i });
            } else {
                segmentTree.update(line.x, new int[] { line.y, i });
            }
        }

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            int idxLine = (int) cur[1];
            Line curLine = lines.get(idxLine);
            List<Integer> nextIndices = segmentTree.get(curLine.nx, curLine.ny);

            for (int nextIdx : nextIndices) {
                Line nextLine = lines.get(nextIdx);
                if (nextLine.answer == INF) {
                    nextLine.answer = curLine.answer + nextLine.cost;
                    pq.offer(new long[] { nextLine.answer, nextIdx });
                }
            }
        }

        long result = INF;
        for (Line line : lines) {
            if (line.isEnd) {
                result = Math.min(result, line.answer);
            }
        }

        System.out.println(result == INF ? -1 : result);
    }
}

/*
10 5
2 5 10 3
1 1 6 5
5 2 8 3
7 6 10 4
4 1 3 1

7


10 5
2 6 10 3
1 1 5 5
5 2 7 3
8 6 10 4
4 1 3 1

-1
*/