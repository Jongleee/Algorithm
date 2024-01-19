import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    static final int MAX = 20000001;
    ArrayList<ArrayList<Edge>> graph;

    class Edge implements Comparable<Edge> {
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

    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        buildGraph(paths, gates, summits);

        int[] intensity = new int[n + 1];
        Arrays.fill(intensity, MAX);

        return dijkstra(intensity, gates, summits);
    }

    private void buildGraph(int[][] paths, int[] gates, int[] summits) {
        for (int[] path : paths) {
            int from = path[0];
            int to = path[1];
            int weight = path[2];

            if (isGate(from, gates) || isSummit(to, summits)) {
                graph.get(from).add(new Edge(to, weight));
            } else if (isGate(to, gates) || isSummit(from, summits)) {
                graph.get(to).add(new Edge(from, weight));
            } else {
                graph.get(from).add(new Edge(to, weight));
                graph.get(to).add(new Edge(from, weight));
            }
        }
    }

    int[] dijkstra(int[] intensity, int[] gates, int[] summits) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        initializeDijkstra(intensity, pq, gates);

        while (!pq.isEmpty()) {
            Edge now = pq.poll();
            if (now.intensity > intensity[now.index])
                continue;

            ArrayList<Edge> edges = graph.get(now.index);
            for (Edge edge : edges) {
                updateIntensityAndQueue(intensity, pq, now, edge);
            }
        }

        return findMinIntensityAndIndex(intensity, summits);
    }

    private void initializeDijkstra(int[] intensity, PriorityQueue<Edge> pq, int[] gates) {
        for (int gate : gates) {
            pq.offer(new Edge(gate, 0));
            intensity[gate] = 0;
        }
    }

    private void updateIntensityAndQueue(int[] intensity, PriorityQueue<Edge> pq, Edge now, Edge edge) {
        int intensityNow = (edge.intensity == Integer.MAX_VALUE) ? now.intensity
                : Math.max(edge.intensity, now.intensity);
        if (intensityNow < intensity[edge.index]) {
            intensity[edge.index] = intensityNow;
            pq.offer(new Edge(edge.index, intensityNow));
        }
    }

    private int[] findMinIntensityAndIndex(int[] intensity, int[] summits) {
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

    private boolean isSummit(int index, int[] summits) {
        for (int summit : summits) {
            if (index == summit)
                return true;
        }
        return false;
    }

    private boolean isGate(int index, int[] gates) {
        for (int gate : gates) {
            if (index == gate)
                return true;
        }
        return false;
    }

    @Test
    void 정답() {
        int[][] paths = { { 1, 2, 3 }, { 2, 3, 5 }, { 2, 4, 2 }, { 2, 5, 4 },
                { 3, 4, 4 }, { 4, 5, 3 }, { 4, 6, 1 }, { 5, 6, 1 } };
        int[] gates = { 1, 3 };
        int[] summits = { 5 };

        Assertions.assertArrayEquals(new int[] { 5, 3 }, solution(6, paths, gates, summits));
    }
}
