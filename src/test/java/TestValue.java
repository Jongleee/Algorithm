import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    class Edge implements Comparable<Edge> {
        int index;
        int cost;

        Edge(int index, int cost) {
            this.index = index;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge e) {
            return this.cost - e.cost;
        }
    }

    private int MAX = 20000001;
    ArrayList<ArrayList<Edge>> graph;

    public int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = MAX;

        graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] i : fares) {
            graph.get(i[0]).add(new Edge(i[1], i[2]));
            graph.get(i[1]).add(new Edge(i[0], i[2]));
        }

        int[] startA = dijkstra(a, n);
        int[] startB = dijkstra(b, n);
        int[] start = dijkstra(s, n);

        for (int i = 1; i <= n; i++)
            answer = Math.min(answer, startA[i] + startB[i] + start[i]);
        return answer;
    }

    int[] dijkstra(int start, int n) {
        int[] costs = new int[n + 1];
        Arrays.fill(costs, MAX);

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.offer(new Edge(start, 0));
        costs[start] = 0;

        while (!pq.isEmpty()) {
            Edge now = pq.poll();

            if (now.cost > costs[now.index])
                continue;

            ArrayList<Edge> edges = graph.get(now.index);
            for (Edge edge : edges) {
                int cost = costs[now.index] + edge.cost;

                if (cost < costs[edge.index]) {
                    costs[edge.index] = cost;
                    pq.offer(new Edge(edge.index, cost));
                }
            }
        }
        return costs;
    }

    @Test
    void 정답() {
        int[][] f1 = { { 4, 1, 10 }, { 3, 5, 24 }, { 5, 6, 2 }, { 3, 1, 41 },
                { 5, 1, 24 }, { 4, 6, 50 }, { 2, 4, 66 }, { 2, 3, 22 }, { 1, 6, 25 } };
        int[][] f2 = { { 5, 7, 9 }, { 4, 6, 4 }, { 3, 6, 1 }, { 3, 2, 3 }, { 2, 1, 6 } };
        int[][] f3 = { { 2, 6, 6 }, { 6, 3, 7 }, { 4, 6, 7 }, { 6, 5, 11 }, { 2, 5, 12 }, { 5, 3, 20 }, { 2, 4, 8 },
                { 4, 3, 9 } };
        Assertions.assertEquals(82, solution(6, 4, 6, 2, f1));
        Assertions.assertEquals(14, solution(7, 3, 4, 1, f2));
        Assertions.assertEquals(18, solution(6, 4, 5, 6, f3));
    }
}
