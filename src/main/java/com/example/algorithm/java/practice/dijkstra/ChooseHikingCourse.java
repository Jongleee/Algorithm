package com.example.algorithm.java.practice.dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class ChooseHikingCourse {

    static final int MAX = 20000001;
    static ArrayList<ArrayList<Edge>> graph;

    static class Edge implements Comparable<Edge> {
        int index;
        int intensity;

        Edge(int index, int intensity) {
            this.index = index;
            this.intensity = intensity;
        }

        @Override
        public int compareTo(Edge o) {
            return this.intensity - o.intensity;
        }

    }

    public static int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] i : paths) {
            if (isGate(i[0], gates) || isSummit(i[1], summits))
                graph.get(i[0]).add(new Edge(i[1], i[2]));
            else if (isGate(i[1], gates) || isSummit(i[0], summits))
                graph.get(i[1]).add(new Edge(i[0], i[2]));
            else {
                graph.get(i[0]).add(new Edge(i[1], i[2]));
                graph.get(i[1]).add(new Edge(i[0], i[2]));
            }
        }

        int[] intensity = new int[n + 1];

        Arrays.fill(intensity, MAX);

        return dijkstra(intensity, gates, summits);
    }

    static int[] dijkstra(int[] intensity, int[] gates, int[] summits) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        for (int gate : gates) {
            pq.offer(new Edge(gate, 0));
            intensity[gate] = 0;
        }

        while (!pq.isEmpty()) {
            Edge now = pq.poll();
            if (now.intensity > intensity[now.index])
                continue;

            ArrayList<Edge> edges = graph.get(now.index);
            for (Edge edge : edges) {
                int intensityNow = (edge.intensity == Integer.MAX_VALUE) ? now.intensity
                        : Math.max(edge.intensity, now.intensity);
                if (intensityNow < intensity[edge.index]) {
                    intensity[edge.index] = intensityNow;
                    pq.offer(new Edge(edge.index, intensityNow));
                }
            }
        }

        int index = -1;
        int minIntensity = Integer.MAX_VALUE;
        Arrays.sort(summits);
        for (int summit : summits) {
            if (intensity[summit] < minIntensity) {
                minIntensity = intensity[summit];
                index = summit;
            }
        }
        return new int[] { index, minIntensity };
    }

    private static boolean isSummit(int index, int[] summits) {
        for (int summit : summits) {
            if (index == summit)
                return true;
        }
        return false;
    }

    private static boolean isGate(int index, int[] gates) {
        for (int gate : gates) {
            if (index == gate)
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(6,
                new int[][] { { 1, 2, 3 }, { 2, 3, 5 }, { 2, 4, 2 }, { 2, 5, 4 }, { 3, 4, 4 }, { 4, 5, 3 }, { 4, 6, 1 },
                        { 5, 6, 1 } },
                new int[] { 1, 3 }, new int[] { 5 })));
    }
    //답 [5, 3]
}
