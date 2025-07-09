package com.example.algorithm.java.sweepLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Pinball {
    static class Segment implements Comparable<Segment> {
        int x1, y1, x2, y2, idx;

        Segment(int x1, int y1, int x2, int y2, int idx) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.idx = idx;
        }

        @Override
        public int compareTo(Segment other) {
            int x = Math.max(this.x1, other.x1);
            double yThis = ((double) (this.y2 - this.y1)) / (this.x2 - this.x1) * (x - this.x1) + this.y1;
            double yOther = ((double) (other.y2 - other.y1)) / (other.x2 - other.x1) * (x - other.x1) + other.y1;
            if (yThis != yOther) {
                return Double.compare(yThis, yOther);
            }
            return this.idx - other.idx;
        }
    }

    static class Event {
        int x, idx, type;

        Event(int x, int idx, int type) {
            this.x = x;
            this.idx = idx;
            this.type = type;
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        final int MXN = 100_000;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        Segment[] segments = new Segment[n];
        Event[] events = new Event[2 * n];
        boolean[] visited = new boolean[MXN + 1];
        List<Integer>[] adj = new ArrayList[MXN + 1];
        for (int i = 0; i <= MXN; i++)
            adj[i] = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());
            if (x1 > x2) {
                int tmpX = x1, tmpY = y1;
                x1 = x2;
                y1 = y2;
                x2 = tmpX;
                y2 = tmpY;
            }
            segments[i] = new Segment(x1, y1, x2, y2, i);
            events[i] = new Event(x1, i, 0);
            events[i + n] = new Event(x2, i, 1);
        }

        int x0 = Integer.parseInt(br.readLine());

        Arrays.sort(events, Comparator.comparingInt(e -> e.x));

        TreeSet<Segment> active = new TreeSet<>();
        for (int i = 1, j = 0; i <= 2 * n; i++) {
            if (i == 2 * n || events[i].x != events[j].x) {
                for (int k = j; k < i; k++) {
                    if (events[k].type == 0) {
                        active.add(segments[events[k].idx]);
                    }
                }

                for (int k = j; k < i; k++) {
                    if (events[k].type == 1) {
                        Segment cur = segments[events[k].idx];
                        Segment lower = active.lower(cur);
                        Segment higher = active.higher(cur);
                        if (lower == null) {
                            adj[n].add(cur.idx);
                        } else {
                            adj[lower.idx].add(cur.idx);
                        }
                        if (higher != null) {
                            adj[cur.idx].add(higher.idx);
                        }
                    }
                }

                for (; j < i; j++) {
                    if (events[j].type == 1) {
                        active.remove(segments[events[j].idx]);
                    }
                }
            }
        }

        x0 = dfs(n, visited, adj, segments, n, x0);
        System.out.println(x0);
    }

    private static int dfs(int node, boolean[] visited, List<Integer>[] adj, Segment[] segments, int n, int x0) {
        if (visited[node])
            return x0;
        visited[node] = true;
        for (int next : adj[node]) {
            x0 = dfs(next, visited, adj, segments, n, x0);
        }
        if (node < n) {
            Segment s = segments[node];
            if (x0 >= s.x1 && x0 <= s.x2) {
                x0 = s.y1 < s.y2 ? s.x1 : s.x2;
            }
        }
        return x0;
    }
}

/*
2
-1 1 1 -1
1 -2 2 -3
0

2


3
-1 1 1 -1
1 -2 0 -3
1 -3 2 -4
0

0
*/